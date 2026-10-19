# ch06-3. Form 유효성 검사 — BMI 계산기 ★

> 예제: `ch06/bmi_page.dart`
> 입력 → 검증 → 계산 → 결과 화면. **지금까지 배운 것이 전부 들어있는 종합 실습**입니다.

---

## 1. 전체 흐름

```
[BmiMain]  키/몸무게 입력
    │
    ▼  '결과' 버튼
 ① formKey.currentState!.validate()   ← 빈칸이면 에러 메시지 표시하고 중단
    │ 통과
    ▼
 ② double.parse()로 문자열 → 숫자 변환
    │
    ▼  await Navigator.push
[BmiResult]  BMI 계산 → 판정 문구 + 표정 아이콘
    │
    ▼  뒤로 가기
 ③ 입력창 비우기 (controller.clear())
```

---

## 2. TextEditingController — 입력값 읽기 ★

```dart
class _BmiMainState extends State<BmiMain> {

  //유효성 검사
  final formKey = GlobalKey<FormState>();
  final heightController = TextEditingController();     // ① 선언
  final weightController = TextEditingController();

  //불필요한 리소스 정리 메소드이고 화면이 사라질때 자동으로 호출
  @override
  void dispose() {
   heightController.dispose();                          // ③ 정리
   weightController.dispose();
    super.dispose();
  }
```

```dart
TextFormField(
  controller: heightController,                         // ② 연결
  ...
)
...
heightController.text          // 입력된 글자 읽기
heightController.clear()       // 입력창 비우기
```

[ch05-1](./ch05_1_입력위젯_Checkbox_Radio_Dropdown.md)에서 "TextField만 컨트롤러로 값을 다룬다"고 배운 걸 실제로 쓰는 부분입니다.

| 메소드/속성 | 하는 일 |
|---|---|
| `.text` | 현재 입력된 문자열 |
| `.clear()` | 입력창 비우기 |
| `.text = '값'` | 입력값 지정 (수정 화면에서 기존 값 채울 때) |
| `.dispose()` | **메모리 정리** |

### ★ `dispose()` — 왜 필요한가

```dart
@override
void dispose() {
  heightController.dispose();   // 내가 만든 것 먼저 정리
  weightController.dispose();
  super.dispose();              // ★ 반드시 마지막에 부모 호출
}
```

`TextEditingController`는 **위젯이 사라져도 메모리에 남습니다.** 화면을 여러 번 열고 닫으면 쓰레기가 쌓입니다(메모리 누수). `dispose()`는 **화면이 사라질 때 Flutter가 자동으로 호출**해주는 메소드라서, 여기서 정리합니다.

| 메소드 | 호출 시점 |
|---|---|
| `initState()` | 화면이 **생길 때** 한 번 (초기화) |
| `build()` | 화면을 **그릴 때마다** (setState마다) |
| `dispose()` | 화면이 **사라질 때** 한 번 (정리) |

> **규칙: Controller를 만들면 `dispose()`도 같이 쓴다.** `TextEditingController`, `AnimationController`, `ScrollController` 전부 마찬가지입니다.

---

## 3. ★★ Form + GlobalKey + validator — 유효성 검사

### ① Form으로 감싸고 key를 붙인다
```dart
final formKey = GlobalKey<FormState>();
...
child: Form(
    key: formKey,                    // ← 이 key로 Form을 "조종"한다
    child: Column(children: [...])
),
```

### ② 각 입력칸에 validator를 쓴다
```dart
TextFormField(
  decoration: InputDecoration(
    border: OutlineInputBorder(),
    hintText: '키'
  ),
  controller: heightController,
  keyboardType: TextInputType.number,     // 숫자 키보드
  validator: (value){
    if(value != null && value.trim().isEmpty){
      return '키를 입력하세요';              // 문자열 반환 = 에러 (빨간 글씨로 표시)
    }
    return null;                           // null 반환 = 통과
  },
),
```

### ③ 버튼에서 한 번에 검사한다
```dart
onPressed: () async {
  //폼에 유효성 검사
  if(formKey.currentState!.validate()){     // 모든 validator를 실행
    // 통과했을 때만 여기 실행
  }
}
```

### ★ validator의 반환값 규칙 (헷갈리기 쉬움)

| 반환값 | 의미 |
|---|---|
| **문자열** 반환 | ❌ **에러** — 그 문자열이 입력칸 아래 빨간 글씨로 표시됨 |
| **`null`** 반환 | ✅ **통과** |

"에러 메시지를 돌려주면 에러, 돌려줄 게 없으면 통과"로 이해하면 됩니다.

### `TextField` vs `TextFormField`

| | `TextField` | `TextFormField` |
|---|---|---|
| 단독 사용 | ✅ | ✅ |
| `Form` 안에서 검증 | ❌ | ✅ **`validator` 지원** |

**`Form`을 쓸 때는 `TextFormField`를 써야 합니다.** 겉모습은 똑같습니다.

### `GlobalKey<FormState>` 란

`Form` 위젯을 **바깥에서 조종하기 위한 리모컨**입니다.

```dart
formKey.currentState!.validate()   // 전체 검사 실행
formKey.currentState!.save()       // onSaved 콜백 전부 실행
formKey.currentState!.reset()      // 전부 초기화
```

`currentState`는 "지금 연결된 Form의 상태"이고, 연결이 안 돼 있으면 `null`일 수 있어서 `!`를 붙입니다 ([ch05-1](./ch05_1_입력위젯_Checkbox_Radio_Dropdown.md)의 null safety).

### ⚠️ 이 예제 validator의 빈틈

```dart
if(value != null && value.trim().isEmpty){ ... }
```

**아무것도 입력하지 않은 경우를 못 잡습니다.** 처음 상태의 `value`는 `null`이 아니라 **빈 문자열 `''`** 이라서 `trim().isEmpty`로 걸리긴 하지만, 만약 `value`가 `null`이면 `&&` 때문에 조건이 거짓이 되어 **통과**됩니다. 더 안전한 형태:

```dart
validator: (value){
  if(value == null || value.trim().isEmpty){      // ★ || 로 바꾸기
    return '키를 입력하세요';
  }
  if(double.tryParse(value.trim()) == null){      // ★ 숫자인지도 검사
    return '숫자만 입력하세요';
  }
  return null;
},
```

> `keyboardType: TextInputType.number`는 **숫자 키보드를 띄워주는 것뿐**이고, 입력 자체를 막지는 않습니다 (붙여넣기나 하드웨어 키보드로 글자가 들어올 수 있음). 그래서 숫자 검증이 따로 필요합니다.

---

## 4. ★ async / await — 다음 화면이 닫힐 때까지 기다리기

```dart
onPressed: () async{                            // ① async 선언
  if(formKey.currentState!.validate()){
    await Navigator.push(                       // ② 결과 화면이 닫힐 때까지 대기
      context,
      MaterialPageRoute(
          builder: (context) =>
              BmiResult(
                double.parse(heightController.text.trim()),
                double.parse(weightController.text.trim())
              )
      )
    );
    //돌아온 뒤 입력창 비우기
    heightController.clear();                   // ③ 돌아온 다음에 실행됨
    weightController.clear();
  }
},
```

### 실행 순서

```
버튼 클릭
   │
   ▼ validate() 통과
Navigator.push ───▶ [BmiResult 화면 표시]
   │                       │
  await 로 대기 ...        사용자가 보고 뒤로 가기
   │                       │
   ◀───────────────────────┘
   ▼
clear() 실행 ← 돌아온 "다음에" 실행된다
```

| 키워드 | 의미 |
|---|---|
| `async` | "이 함수 안에서 `await`를 쓸 거다"라는 선언 |
| `await` | 그 작업이 **끝날 때까지 기다린 뒤** 다음 줄로 |

**`Navigator.push`는 `Future`를 돌려줍니다** — 그 화면이 `pop`될 때 완료되는 Future입니다. 그래서 `await`를 붙이면 "돌아온 뒤에 할 일"을 그 아래에 쓸 수 있습니다.

`await` 없이 쓰면 **화면이 올라가는 동시에 clear()가 실행**되어, 결과 화면을 보고 돌아왔을 때 이미 비워져 있게 됩니다 (이 예제에선 결과가 같아 보이지만, 값을 받아 쓸 때는 완전히 달라집니다).

> [ch05-2](./ch05_2_버튼과진행표시기.md)의 `Future.delayed`와 같은 비동기 개념입니다. 그쪽은 "시간을 기다림", 이쪽은 "화면이 닫히기를 기다림".

### ⚠️ `await` 뒤에 `setState`를 쓴다면
`mounted` 체크가 필요합니다 ([ch05-2](./ch05_2_버튼과진행표시기.md) 참고). 이 예제는 `setState`가 아니라 `clear()`만 하므로 안전합니다.

---

## 5. 문자열 → 숫자 변환

```dart
double.parse(heightController.text.trim())
```

| 코드 | 결과 |
|---|---|
| `double.parse('170')` | `170.0` |
| `int.parse('28')` | `28` |
| `double.parse('abc')` | 💥 **예외 발생** (FormatException) |
| `double.tryParse('abc')` | `null` (예외 안 남) |

### `.trim()` — 앞뒤 공백 제거
```dart
' 170 '.trim()    // '170'
```
사용자가 실수로 스페이스를 넣으면 `parse`가 실패하므로 꼭 붙입니다.

### ★ parse vs tryParse
```dart
double.parse('abc')      // 💥 앱이 죽는다
double.tryParse('abc')   // null → if로 처리 가능
```

**사용자 입력에는 `tryParse`가 안전합니다.**
```dart
final height = double.tryParse(heightController.text.trim());
if (height == null) { /* 에러 처리 */ return; }
```

이 예제는 validator가 먼저 걸러주길 기대하고 `parse`를 썼지만, 위 3번에서 본 것처럼 **validator가 숫자 검사를 안 하므로** 글자를 입력하면 앱이 죽습니다. (직접 테스트해볼 만한 부분)

---

## 6. BmiResult — 계산과 판정

```dart
class BmiResult extends StatelessWidget {

  final double height;
  final double weight;

  BmiResult(this.height, this.weight);      // 위치 매개변수 (순서 중요!)

  @override
  Widget build(BuildContext context) {
    final bmi = weight / ((height / 100) * (height / 100));
    print('bmi: $bmi');
    ...
  }
```

### BMI 공식
```
BMI = 몸무게(kg) ÷ 키(m)²
```
키를 cm로 입력받으므로 `height / 100`으로 m로 바꿉니다.

| 예 | 계산 |
|---|---|
| 170cm, 65kg | 65 ÷ (1.7 × 1.7) = **22.49** → 정상 |

### ⚠️ 위치 매개변수의 위험
```dart
BmiResult(this.height, this.weight);       // 순서: 키, 몸무게
BmiResult(65.0, 170.0)                     // ❌ 바꿔 넣어도 에러가 안 남!
```
둘 다 `double`이라 **컴파일러가 못 잡습니다.** 결과만 이상하게 나옵니다.
[ch06-1](./ch06_1_화면이동_Navigator와_파일분리.md)에서 본 **이름 있는 매개변수**가 더 안전한 이유입니다:
```dart
BmiResult({required this.height, required this.weight});
BmiResult(weight: 65.0, height: 170.0)     // 순서 상관없고 명확
```

---

## 7. if-else if 체인 — 판정 문구

```dart
//BMI 측정
String calcBmi(double bmi) {
  var result = '저체중';                  // 기본값
  if (bmi >= 35) {
    result = '고도 비만';
  } else if (bmi >= 30) {
    result = '2단계 비만';
  } else if (bmi >= 25) {
    result = '1단계 비만';
  } else if (bmi >= 23) {
    result = '과체중';
  } else if (bmi >= 18.5) {
    result = '정상';
  }
  return result;
}
```

### ★ 큰 값부터 검사하는 이유

`else if`는 **위에서부터 처음 맞는 것 하나만** 실행됩니다. 그래서 **경계가 큰 것부터** 써야 합니다.

```dart
// ❌ 작은 것부터 쓰면
if (bmi >= 18.5) return '정상';     // BMI 40도 여기서 걸려버림!
else if (bmi >= 35) return '고도 비만';   // 절대 도달 못 함
```

| BMI | 판정 |
|---|---|
| ~18.5 | 저체중 (기본값) |
| 18.5~23 | 정상 |
| 23~25 | 과체중 |
| 25~30 | 1단계 비만 |
| 30~35 | 2단계 비만 |
| 35~ | 고도 비만 |

> 아무 조건에도 안 걸리면 기본값 `'저체중'`이 그대로 반환됩니다. **기본값을 먼저 정해두는 패턴**이 `else`를 안 써도 되게 만들어줍니다.

---

## 8. 조건에 따라 위젯 반환 — buildIcon

```dart
//BMI 아이콘
Widget buildIcon(double bmi){
  if(bmi >= 23){
    return Icon(Icons.sentiment_very_dissatisfied, color: Colors.red, size: 100,);
  }else if(bmi >= 18.5){
    return Icon(Icons.sentiment_satisfied, color: Colors.green, size: 100,);
  }else{
    return Icon(Icons.sentiment_dissatisfied, color: Colors.orange, size: 100,);
  }
}
```

[ch05-2](./ch05_2_버튼과진행표시기.md)에서 배운 **조건부 위젯**의 세 번째 방식(함수로 분리)입니다.

| 분기 수 | 추천 |
|---|---|
| 2개 | 삼항 연산자 `조건 ? A : B` |
| **3개 이상** | **메소드로 분리해서 `if-else`** ← 이 예제 |

삼항 연산자를 3단 중첩하면 읽을 수 없으므로, 메소드로 빼는 게 맞습니다.

### 표정 아이콘
| 아이콘 | 모양 |
|---|---|
| `Icons.sentiment_very_dissatisfied` | 😞 매우 불만 (빨강) |
| `Icons.sentiment_dissatisfied` | 🙁 불만 (주황) |
| `Icons.sentiment_satisfied` | 🙂 만족 (초록) |

---

## 9. 이 예제의 개선 포인트 (직접 해보기)

| # | 문제 | 해결 |
|---|---|---|
| 1 | validator가 `null`과 숫자 검사를 안 함 | `value == null \|\| value.trim().isEmpty` + `double.tryParse` 검사 |
| 2 | 글자를 입력하면 `double.parse`에서 앱이 죽음 | `tryParse` 사용 |
| 3 | `BmiResult(height, weight)` 순서 바꿔도 안 걸림 | 이름 있는 매개변수로 |
| 4 | 결과 화면의 `Column`이 위에 붙어 있음 | `mainAxisAlignment: MainAxisAlignment.center` 추가 |
| 5 | BMI 숫자 자체가 안 보임 | `Text('BMI: ${bmi.toStringAsFixed(1)}')` 추가 (소수점 1자리) |
| 6 | 키를 0으로 입력하면 `Infinity` | `if (height <= 0)` 검사 |

### `toStringAsFixed` — 소수점 자리수 맞추기
```dart
22.491349480968858.toStringAsFixed(1)    // '22.5'
```
계산 결과를 그대로 `Text`에 넣으면 소수점이 길게 나옵니다.

---

## 10. 정리 — Form 입력 화면 템플릿

```dart
class _XxxState extends State<Xxx> {

  final formKey = GlobalKey<FormState>();          // ① Form 리모컨
  final controller = TextEditingController();      // ② 입력값 보관

  @override
  void dispose() {                                 // ③ 정리 (필수)
    controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: formKey,
          child: Column(
            children: [
              TextFormField(
                controller: controller,
                keyboardType: TextInputType.number,
                decoration: InputDecoration(border: OutlineInputBorder(), hintText: '키'),
                validator: (value){                // ④ 검증 규칙
                  if (value == null || value.trim().isEmpty) return '입력하세요';
                  return null;
                },
              ),
              ElevatedButton(
                onPressed: () async {
                  if (formKey.currentState!.validate()) {   // ⑤ 통과하면 실행
                    await Navigator.push(context,
                      MaterialPageRoute(builder: (_) => 결과화면(...)));
                    controller.clear();                     // ⑥ 돌아온 뒤 처리
                  }
                },
                child: Text('확인'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
```

### 체크리스트
| | |
|---|---|
| `Form` + `key` 붙였나? | 없으면 `validate()` 못 함 |
| `TextField`가 아니라 `TextFormField`인가? | `validator`는 Form 전용 |
| validator가 `null`을 반환하면 통과 | 반대로 외우지 않기 |
| Controller에 `dispose()` 썼나? | 메모리 누수 방지 |
| `await` 쓰려면 `async` 선언했나? | |
| 사용자 입력에 `parse` 대신 `tryParse` | 앱이 죽지 않게 |
