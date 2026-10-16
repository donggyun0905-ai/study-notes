# ch03-2. 상태 관리와 setState ★

> 예제: `ch03/lib/main3.dart`
> **오늘 배운 것 중 가장 중요한 개념.** ch05 전체가 이 원리의 반복입니다.

---

## 1. "상태(State)"란

**화면을 다시 그려야 하는 원인이 되는 값.**

| 예 | 상태 변수 |
|---|---|
| 버튼을 누르면 글자가 Hello↔World | `String text` |
| 체크박스가 켜졌나 꺼졌나 | `bool isChecked` |
| 라디오로 남/여 중 뭘 골랐나 | `Gender gender` |
| 다운로드 중인가 | `bool isDownloading` |

---

## 2. StatelessWidget vs StatefulWidget

| | StatelessWidget | StatefulWidget |
|---|---|---|
| 화면이 바뀌나? | 아니오 (항상 같음) | **예** (상태에 따라) |
| 클래스 개수 | 1개 | **2개** (위젯 + State) |
| 상태 변수 | 못 가짐 (`final`만) | State 클래스 안에 가짐 |
| 바꾸는 방법 | — | `setState()` |
| 예 | 로고, 안내 문구 | 체크박스, 카운터, 로딩 |

---

## 3. StatefulWidget의 2클래스 구조 ★

```dart
class MyHomePage extends StatefulWidget {          // ① 껍데기 (위젯)
  @override
  State<MyHomePage> createState() => _MyHomePageState();
  //  └ "내 상태를 관리할 객체는 이거다" 라고 알려주는 한 줄
}

class _MyHomePageState extends State<MyHomePage> { // ② 실제 내용 (State)
  var text = 'Hello';                              // ← 상태 변수는 여기!

  @override
  Widget build(BuildContext context) {             // ← build도 여기!
    return Scaffold( ... );
  }
}
```

### 왜 클래스를 2개로 나누나

| | 역할 | 수명 |
|---|---|---|
| `MyHomePage` (위젯) | 설정값을 담는 **불변** 껍데기 | 화면 갱신마다 **버려지고 새로 만들어짐** |
| `_MyHomePageState` (State) | 상태 변수 + `build()` | 화면이 갱신돼도 **계속 살아있음** |

위젯은 계속 새로 만들어지기 때문에 **거기에 변수를 두면 값이 매번 초기화**됩니다. 그래서 값은 오래 사는 State 쪽에 둡니다.

### 외워야 할 문법 포인트

| 코드 | 의미 |
|---|---|
| `State<MyHomePage>` | 꺾쇠 안에 **짝이 되는 위젯 이름** |
| `_MyHomePageState` | 앞의 `_`(언더바)는 Dart에서 **"이 파일 안에서만 쓴다"(private)**는 뜻 |
| `=> _MyHomePageState()` | State 객체를 새로 만들어 돌려줌 |

> ch05 예제는 `MyApp` 자체를 StatefulWidget으로 만들어서 클래스 이름이 `_State` 또는 `_MyAppState`입니다. 이름은 자유롭지만 `_`로 시작하는 관례는 지킵니다.

---

## 4. setState() — 핵심 원리 ★★

```dart
floatingActionButton: FloatingActionButton(onPressed: (){
  setState(() {
    text = (text == 'World') ? 'Hello' : 'World';
  });
}),
```

### setState가 하는 일은 2가지

```
setState(() { 값 변경 })
   │
   ├─ ① 괄호 안의 코드를 실행해서 상태 변수를 바꾼다
   │
   └─ ② Flutter에게 "이 위젯 다시 그려줘" 라고 요청한다
            │
            ▼
        build() 가 다시 실행됨 → 바뀐 값으로 화면 생성
```

**`build()`는 내가 부르는 게 아니라 `setState`가 부르게 만드는 함수**입니다.

### 자주 하는 실수 2가지 (오늘 둘 다 겪음)

**① setState 없이 값만 바꿈**
```dart
onPressed: (){
  text = 'World';     // 값은 바뀌었지만...
}                     // → 화면은 그대로! Flutter가 다시 그릴 줄 모름
```

**② setState는 불렀지만 값을 안 바꿈** ← `main3.dart`(ch05) 드롭다운 버그
```dart
setState(() {
  // 비어 있음 → 다시 그렸지만 값이 같으니 결과도 똑같음
});
```

> **한 문장 정리: `setState`는 "다시 그려라"는 신호일 뿐, 값은 내가 바꿔야 한다.**

---

## 5. 전체 예제 분석 — main3.dart

```dart
class _MyHomePageState extends State<MyHomePage> {

  var text = 'Hello';                    // ① 상태 변수 (초기값 Hello)

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title:Text('Hello World!!!'),),
      body: Text(text, style: TextStyle(fontSize: 40),),   // ② 상태 변수를 화면에 표시
      floatingActionButton: FloatingActionButton(onPressed: (){
          setState(() {
            //text='World';                                // ③ 한 번만 바뀌는 버전
            text = (text=='World') ? 'Hello' : 'World';    // ④ 누를 때마다 토글
          });
      }),
    );
  }
}
```

| 번호 | 포인트 |
|---|---|
| ① | `var`는 타입 자동 추론 — `'Hello'`를 넣었으니 `String`으로 확정됨 |
| ② | `Text(text)` — 상태 변수를 그대로 꽂아둔다. 값이 바뀌면 여기 표시도 바뀜 |
| ③ | `text='World'`만 쓰면 첫 클릭에만 변하고 그 다음은 변화 없음 |
| ④ | **삼항 연산자로 토글** — 조건 ? 참일때 : 거짓일때 |

### 삼항 연산자 (`? :`)

```dart
text = (text=='World') ? 'Hello' : 'World';
//      ─────조건─────   ──참──   ──거짓──
```

if-else를 한 줄로 쓴 것입니다. **Flutter에서는 `build()` 안에서 위젯을 골라 끼울 때도 이걸 씁니다** → [ch05-2](./ch05_2_버튼과진행표시기.md)

---

## 6. `var` 쓸 때 주의

```dart
var text = 'Hello';   // String으로 확정 → 이후 숫자 대입 불가
text = 123;           // ❌ 에러
```

`var`는 "아무거나 담는 상자"가 아니라 **"타입을 알아서 추론해달라"**는 뜻입니다. 명확하게 쓰려면 타입을 직접 적는 게 좋습니다:

```dart
String text = 'Hello';
bool isChecked = false;
int count = 0;
```

---

## 7. 이렇게 써야 한다 — StatefulWidget 템플릿

```dart
//mainN.dart

import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  //① 상태 변수 선언
  bool isChecked = false;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title:'chXX',
      home:Scaffold(
        appBar: AppBar(title:Text('Subject')),
        body: Center(
          child: /* ② 상태 변수를 쓰는 위젯 */,
        ),
      ),
    );
  }
}
```

`const MyApp({super.key});`는 성능 최적화를 위한 관례적 생성자입니다. 지금은 **"StatefulWidget에는 이 줄을 같이 쓴다"** 정도로 외워두면 됩니다.
