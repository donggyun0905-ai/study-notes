# ch05-1. 사용자 입력 위젯 — TextField / Checkbox / Radio / Dropdown ★

> 예제: `ch05/lib/main.dart`(TextField), `main1.dart`(Checkbox·Switch), `main2.dart`(Radio), `main3.dart`(Dropdown)

---

## 1. ★★ 모든 입력 위젯의 공통 패턴

Checkbox, Switch, Radio, DropdownButton — **전부 똑같은 3단 구조**입니다. 이것만 이해하면 나머지는 속성 이름만 다릅니다.

```dart
위젯(
  value: 상태변수,            // ① 지금 상태를 화면에 보여주고
  onChanged: (value) {       // ② 사용자가 조작하면 호출되고
    setState(() {
      상태변수 = value!;      // ③ 상태를 갱신 → build() 재실행 → 화면 반영
    });
  },
)
```

```
 사용자 터치
    │
    ▼
 onChanged 호출 ──▶ setState { 상태변수 = 새값 }
                          │
                          ▼
                    build() 다시 실행
                          │
                          ▼
                    value: 에 새 값이 들어가 화면 갱신
```

### ★ 왜 위젯이 스스로 안 바뀌나

Flutter 입력 위젯은 **자기 상태를 기억하지 않습니다.** `value:`에 넣어준 값만 그대로 보여줍니다.
그래서 `setState`로 값을 안 바꾸면 **체크박스를 눌러도 안 켜집니다.** (에러도 안 남 — 그래서 더 헷갈림)

이게 오늘 `main3.dart`에서 드롭다운이 안 바뀐 이유였습니다.

---

## 2. TextField — main.dart

```dart
body: Column(
  children: [
    TextField(),                                                   // ① 기본
    TextField(decoration: InputDecoration(labelText: '여기에 입력하세요'),),  // ② 라벨
    SizedBox(height: 30,),                                         //   여백
    TextField(                                                     // ③ 테두리
      decoration: InputDecoration(
          labelText:'여기에 입력하세요',
          border: OutlineInputBorder()),
    )
  ],
),
```

| 코드 | 결과 |
|---|---|
| `TextField()` | 밑줄만 있는 입력창 |
| `decoration: InputDecoration(labelText: ...)` | 안내 글자. 입력 시작하면 **위로 올라감** |
| `border: OutlineInputBorder()` | 사각 테두리 |

### InputDecoration 주요 속성

| 속성 | 설명 |
|---|---|
| `labelText` | 라벨 (입력 시 위로 올라감) |
| `hintText` | 흐린 안내 글씨 (입력 시 **사라짐**) |
| `prefixIcon` / `suffixIcon` | 앞/뒤 아이콘 |
| `border: OutlineInputBorder()` | 사각 테두리 |
| `errorText` | 빨간 에러 메시지 |

### `SizedBox` — 여백 전용 위젯

```dart
SizedBox(height: 30)   // 세로 30 빈칸
SizedBox(width: 20)    // 가로 20 빈칸
```

`Container`보다 가볍고 의도가 분명합니다. Column/Row에서 간격을 줄 때 가장 많이 씁니다.

### 입력값은 어떻게 읽나 (예제엔 없지만 필수)

```dart
final controller = TextEditingController();   // ① 컨트롤러 선언
...
TextField(controller: controller),            // ② 연결
...
print(controller.text);                       // ③ 값 읽기
```

`TextField`는 유일하게 `value:`/`onChanged:` 패턴을 안 쓰고 **컨트롤러**로 값을 다룹니다 (`onChanged`도 있지만 글자 하나하나 입력될 때마다 호출됩니다).

---

## 3. Checkbox / Switch — main1.dart

```dart
class _MyAppState extends State<MyApp> {

  bool isChecked = false;              // ① 상태 변수

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home:Scaffold(
        body: Center(
          child:Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Checkbox(
                  value: isChecked,
                  onChanged: (value){
                    //setState: 상태 변수를 바꾸고 화면 다시 그리도록 요청
                    setState((){
                      isChecked = value!;
                    });
                  }),
              SizedBox(height: 50,),
              Switch(
                value: isChecked,
                onChanged: (value) => setState(() => isChecked = value))
            ],
          )
        )
      )
    );
  }
}
```

### 포인트 3가지

**① Checkbox와 Switch가 같은 변수를 공유한다**

`isChecked` 하나를 둘이 같이 쓰므로 **하나를 바꾸면 다른 하나도 같이 움직입니다.** "상태가 화면의 원천"이라는 걸 보여주는 좋은 예제입니다.

**② 람다로 줄여 쓰기** — Switch 쪽 표기

```dart
// 긴 버전
onChanged: (value){
  setState((){
    isChecked = value;
  });
}

// 짧은 버전 (완전히 같은 코드)
onChanged: (value) => setState(() => isChecked = value)
```

**③ `value!`의 `!`가 Checkbox엔 붙고 Switch엔 안 붙는 이유**

| 위젯 | onChanged가 주는 타입 |
|---|---|
| `Checkbox` | `bool?` ← **null 가능** (3상태 체크박스를 지원하기 때문) |
| `Switch` | `bool` ← null 없음 |

그래서 Checkbox만 `!`가 필요합니다.

---

## 4. ★ null safety — `?` 와 `!`

Dart는 **null을 넣을 수 있는 타입과 없는 타입을 구분**합니다.

| 표기 | 의미 |
|---|---|
| `bool` | true/false만. **null 불가** |
| `bool?` | true/false/**null** 가능 |
| `value!` | "이 값은 null이 아니라고 내가 보증한다" (실제로 null이면 런타임 에러) |
| `value ?? false` | null이면 false를 대신 사용 |

```dart
bool isChecked = false;        // null 못 받는 변수

onChanged: (bool? value){      // 받는 값은 null 가능
  isChecked = value;           // ❌ 컴파일 에러
  isChecked = value!;          // ✅ null 아님을 보증
  isChecked = value ?? false;  // ✅ null이면 false (더 안전)
}
```

> 교재는 `!`를 쓰지만, 더 안전한 방식은 `?? 기본값`입니다. 입력 위젯의 `onChanged`는 실제로 null을 주지 않으므로 `!`로도 문제없습니다.

---

## 5. Radio + enum — main2.dart

```dart
//라디오 버튼의 선택 값으로 사용할 열거형
enum Gender {MAN, WOMEN}        // ★ 클래스 밖에 선언

class _MyAppState extends State<MyApp> {

  Gender gender = Gender.MAN;   // 상태 변수 (초기 선택값)

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        home:Scaffold(
          body: Padding(
              padding:const EdgeInsets.all(8.0),
              child: Center(
                child: Column(
                  children: [
                    ListTile(
                      title:Text('남자'),
                      leading: Radio(
                          value:Gender.MAN,        // ① 이 버튼이 가진 값
                          groupValue: gender,      // ② 그룹의 현재 선택값
                        onChanged: (Gender? value){
                            setState((){
                              gender = value!;     // ③ 선택값 갱신
                            });
                        },
                      ),
                    ),
                    ListTile(
                      title:Text('여자'),
                      leading: Radio(
                        value:Gender.WOMEN,
                        groupValue: gender,
                        onChanged: (Gender? value){
                          setState((){ gender = value!; });
                        },
                      ),
                    ),
                  ],
                )
              )
          ),
        )
    );
  }
}
```

### ★ Radio만 속성이 3개 — `value` + `groupValue`

| 속성 | 의미 |
|---|---|
| `value` | **이 버튼의 고유 값** (버튼마다 다름) |
| `groupValue` | **그룹의 현재 선택값** (모든 버튼이 같은 변수를 봄) |
| `onChanged` | 이 버튼이 선택됐을 때 |

**동작 원리: `value == groupValue`인 버튼만 점이 칠해집니다.**

```
gender = Gender.MAN  일 때
  남자 버튼: value(MAN)   == groupValue(MAN)  →  ● 선택됨
  여자 버튼: value(WOMEN) != groupValue(MAN)  →  ○ 안 됨
```

**여러 버튼을 같은 `groupValue` 변수에 연결하는 것만으로 "하나만 선택"이 구현**됩니다.

| | 변수 | 결과 |
|---|---|---|
| Checkbox | 각자 따로 (`isA`, `isB`) | 여러 개 동시 선택 |
| Radio | **하나를 공유** (`gender`) | 하나만 선택 |

### enum (열거형)이란

```dart
enum Gender {MAN, WOMEN}
```

**정해진 값만 담을 수 있는 타입.** `String gender = '남자';`로 해도 되지만, 오타('남성', 'man')가 나도 컴파일러가 못 잡습니다. enum을 쓰면 `Gender.MAN` / `Gender.WOMEN` 외엔 쓸 수 없어 **오타가 컴파일 단계에서 걸립니다.**

> enum은 **클래스 밖(파일 최상단)**에 선언합니다. 클래스 안에 넣으면 에러.

### ListTile에 넣는 이유

`Radio`만 쓰면 **동그라미만** 나오고 글자가 없습니다. `ListTile`의 `leading`에 넣으면 제목이 붙고 목록 형태로 정렬됩니다 ([ch04-2](./ch04_2_스크롤과리스트_ListView_GridView.md)에서 배운 `ListTile` 재활용).

> `ListTile`의 `onTap`에도 같은 `setState`를 넣으면 글자를 눌러도 선택됩니다.

### ★ RadioListTile — 둘을 합친 위젯 (더 간단함)

`ListTile` + `Radio` 조합을 Flutter가 미리 합쳐둔 위젯이 있습니다.

```dart
RadioListTile(
    title: Text('남자'),
    value: Gender.MAN,
    groupValue: gender,
    onChanged: (Gender? value){
      setState(() {
        gender = value!;
      });
  },
),
```

| | `ListTile` + `Radio` | `RadioListTile` |
|---|---|---|
| 중첩 깊이 | 2단 (감싸야 함) | **1단** |
| 글자를 눌러도 선택 | `onTap` 직접 추가해야 함 | **기본 동작** |
| 라디오 위치 | `leading`/`trailing` 자유 | `controlAffinity`로 지정 |

```dart
RadioListTile(
  controlAffinity: ListTileControlAffinity.trailing,   // 라디오를 오른쪽으로
  ...
)
```

**실무에서는 `RadioListTile`을 씁니다.** `ListTile` + `Radio`는 "둘이 어떻게 조합되는지" 이해하기 위한 과정입니다.
`CheckboxListTile`, `SwitchListTile`도 같은 방식으로 존재합니다.

> `main2.dart`는 위쪽에 `ListTile`+`Radio` 2개, 아래쪽에 `RadioListTile` 2개를 두고 **같은 `gender` 변수를 공유**합니다. 그래서 위에서 '여자'를 고르면 아래 것도 같이 '여자'로 바뀝니다 — 상태가 화면의 원천임을 보여주는 좋은 비교 예제입니다.

### 오늘 겪은 에러 3건

| 원인 | 해결 |
|---|---|
| `enum Gender`를 정의하지 않고 사용 | `enum Gender {MAN, WOMEN}` 추가 |
| `gender = value;` (`Gender?` → `Gender`) | `gender = value!;` |
| 두 번째 라벨이 복붙으로 '남자' | '여자'로 수정 |

### ⚠️ deprecated 안내

Flutter 3.32부터 `Radio`의 `groupValue`/`onChanged`는 **deprecated**입니다 (`RadioGroup`으로 감싸는 방식 권장). `flutter analyze`에 info로 뜨지만 **아직 정상 동작**합니다.

---

## 6. DropdownButton — main3.dart

```dart
final valueList = ['첫번째','두번째','세번째'];   // 항목 목록
var selectedValue = '첫번째';                    // 상태 변수

...
child: DropdownButton(
  value: selectedValue,                 //현재 선택된 항목값
  items: valueList.map((value){         //드롭다운에 표시될 항목 리스트
    return DropdownMenuItem(value:value, child:Text(value));
  }).toList(),
  onChanged: (value){
    setState(() {
      selectedValue = value!;           //선택한 항목으로 변경
    });
  },
),
```

| 속성 | 역할 |
|---|---|
| `value` | 현재 선택값 (닫힌 상태에 표시됨) |
| `items` | `DropdownMenuItem` 리스트 |
| `onChanged` | 선택이 바뀔 때 |

### `DropdownMenuItem`의 value와 child

```dart
DropdownMenuItem(
  value: value,          // ① 선택됐을 때 onChanged로 전달될 "데이터"
  child: Text(value),    // ② 화면에 보일 "모양"
)
```

둘이 **달라도 됩니다.** 실무에서는 이렇게 씁니다:

```dart
DropdownMenuItem(value: 1, child: Text('서울'))     // 화면엔 '서울', 데이터는 1
```

### `.map().toList()` 재등장

[ch04-2](./ch04_2_스크롤과리스트_ListView_GridView.md)의 `map` 문법과 같습니다. 문자열 리스트를 위젯 리스트로 바꾸는 것:

```
['첫번째','두번째','세번째']
      │ map
      ▼
[DropdownMenuItem(...), DropdownMenuItem(...), DropdownMenuItem(...)]
```

**`.toList()`를 빠뜨리면 타입 에러**가 납니다 (`map`은 `Iterable`을 주는데 `items`는 `List`를 요구).

### ★ 주의: value가 items에 없으면 런타임 에러

```dart
var selectedValue = '네번째';   // ❌ valueList에 없는 값 → 에러
```

`value`는 **반드시 `items`의 `value` 중 하나**여야 합니다. 아무것도 선택 안 된 상태로 시작하려면 `null`을 허용하고 `hint`를 씁니다:

```dart
String? selectedValue;              // null 허용
...
DropdownButton(
  value: selectedValue,
  hint: Text('선택하세요'),           // value가 null일 때 표시
  ...
)
```

---

## 7. 한눈에 정리

| 위젯 | 상태 변수 타입 | 핵심 속성 | 선택 개수 |
|---|---|---|---|
| `TextField` | — (`TextEditingController`) | `controller`, `decoration` | — |
| `Checkbox` | `bool` | `value`, `onChanged` | 여러 개 |
| `Switch` | `bool` | `value`, `onChanged` | 여러 개 |
| `Radio` | `enum` 또는 `String` | `value`, **`groupValue`**, `onChanged` | **하나만** |
| `DropdownButton` | `String` 등 | `value`, `items`, `onChanged` | 하나만 |

### 새 입력 위젯을 만날 때 확인할 3가지

1. **상태 변수 타입**이 뭔가? (`bool`? `enum`? `String`?)
2. `onChanged`가 주는 값이 **null 가능**인가? (`!` 또는 `??`가 필요한가)
3. `setState` 안에서 **값을 실제로 바꿨는가?** ← 가장 많이 틀리는 부분
