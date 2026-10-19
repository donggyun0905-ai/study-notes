# ch06-1. 화면 이동 — Navigator와 파일 분리 ★

> 예제: `ch06/main.dart`, `first_page.dart`, `second_page.dart`

지금까지는 **한 파일 = 한 화면**이었습니다. ch06부터는 **파일을 여러 개로 나누고 화면 사이를 이동**합니다.

---

## 1. 파일 3개의 역할

```
main.dart          ← 앱 시작 + 공용 클래스(Person)
   │ home: FirstPage()
   ▼
first_page.dart    ← 첫 화면. 버튼을 누르면 →
   │ Navigator.push
   ▼
second_page.dart   ← 두 번째 화면. 넘겨받은 데이터 표시 → Navigator.pop으로 복귀
```

### main.dart
```dart
import 'package:flutter/material.dart';
import 'package:ch06/first_page.dart';        // ★ 내가 만든 파일 가져오기

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'ch06',
      home: FirstPage(),                      // 첫 화면을 다른 파일에서 가져옴
    );
  }
}

class Person{                                 // 데이터를 담는 클래스
  String name;
  int age;
  Person(this.name, this.age);
}
```

---

## 2. ★ import 하는 법 — `package:프로젝트명/파일명`

```dart
import 'package:flutter/material.dart';      // 외부 패키지(Flutter 기본)
import 'package:ch06/first_page.dart';       // 내 프로젝트의 다른 파일
import 'package:ch06/second_page.dart';
```

| 형태 | 의미 |
|---|---|
| `package:flutter/...` | Flutter가 제공하는 것 |
| `package:ch06/...` | **내 프로젝트** 안의 파일. `ch06`은 `pubspec.yaml`의 `name:` 값 |
| `import 'second_page.dart';` | 같은 폴더면 상대경로로도 가능 |

**`package:ch06/first_page.dart` 는 `프로젝트/lib/first_page.dart` 를 가리킵니다.** `lib/`는 경로에 안 적습니다.

### Java의 import와 비교
| Java | Dart |
|---|---|
| 클래스 단위로 import | **파일 단위**로 import (그 파일의 모든 클래스가 들어옴) |
| `package` 선언 필요 | 선언 없음. 폴더 구조가 그대로 경로 |

> `main.dart`의 `Person` 클래스를 쓰려면 `import 'package:ch06/main.dart';` — **main.dart도 그냥 파일이라 import 대상**입니다. `first_page.dart`와 `second_page.dart`가 둘 다 이걸 import하고 있습니다.

### ⚠️ 이 코드를 실행하려면

현재 `ch06` 폴더에는 `pubspec.yaml`과 `lib/` 폴더가 없어 **그대로는 실행되지 않습니다.** `package:ch06/...` 가 해석되지 않기 때문입니다.

```
ch06\                      ← 현재: 그냥 폴더
├── main.dart
├── first_page.dart
└── second_page.dart
```

제대로 돌리려면 Flutter 프로젝트로 만들고 `lib/` 안에 넣어야 합니다:
```bash
flutter create ch06        # 프로젝트 생성 (pubspec.yaml의 name: ch06)
# 그 다음 .dart 파일들을 ch06/lib/ 안으로 이동
```
```
ch06\
├── pubspec.yaml           ← name: ch06
└── lib\
    ├── main.dart
    ├── first_page.dart
    └── second_page.dart
```

> 프로젝트 이름이 `ch06`이 아니면 import 경로의 `ch06`도 그 이름으로 바꿔야 합니다.

---

## 3. 화면을 나누는 기준

| 파일 | 담는 것 |
|---|---|
| `main.dart` | `main()`, `MyApp`(앱 설정), 공용 데이터 클래스 |
| `xxx_page.dart` | 화면 하나당 파일 하나 |

Dart 파일명은 **snake_case**(소문자+밑줄)가 관례입니다: `first_page.dart`, `bmi_page.dart`.
클래스명은 **PascalCase**: `FirstPage`, `BmiResult`.

> `Person` 같은 데이터 클래스는 보통 `models/person.dart`로 따로 빼지만, 예제에서는 `main.dart`에 뒀습니다.

---

## 4. ★★ Navigator — 화면 쌓기와 빼기

Flutter의 화면 이동은 **접시 쌓기(Stack)** 로 동작합니다.

```
        ┌───────────┐
push →  │ SecondPage│  ← 지금 보이는 화면 (맨 위)
        ├───────────┤
        │ FirstPage │
        └───────────┘
             ▲
           pop 하면 SecondPage가 치워지고 FirstPage가 보임
```

| 명령 | 동작 |
|---|---|
| `Navigator.push(context, route)` | 새 화면을 **위에 쌓기** |
| `Navigator.pop(context)` | 맨 위 화면을 **치우기** (이전 화면으로) |

**뒤로 가기 버튼(←)은 자동으로 생깁니다.** `Scaffold`+`AppBar`가 "쌓인 화면이 있다"는 걸 알고 알아서 붙여줍니다. 안드로이드 하드웨어 백 버튼도 `pop`과 같게 동작합니다.

### push — first_page.dart
```dart
body: ElevatedButton(
    onPressed: (){
      final person = Person('홍길동', 28);
      //새로운 화면(Route)를 Stack에 쌓는다.
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => SecondPage(person: person))
      );
    },
    child: Text('다음 페이지로')),
```

| 조각 | 의미 |
|---|---|
| `Navigator.push(context, ...)` | `context`로 "지금 어느 위치에서 이동하는지" 알려줌 |
| `MaterialPageRoute` | **화면 전환 애니메이션**을 담당 (안드로이드는 아래→위, iOS는 오른→왼) |
| `builder: (context) => SecondPage(...)` | 띄울 화면을 **만드는 함수** |

### ★ 왜 `builder`에 함수를 넘기나

```dart
MaterialPageRoute(builder: (context) => SecondPage(person: person))
//                         ─────── 함수 ───────
```

화면을 **지금 만들지 않고, 필요할 때 만들도록** 함수로 넘깁니다. 전환 애니메이션이 시작될 시점에 Flutter가 이 함수를 호출합니다.

[ch04-4](./ch04_4_기본위젯_버튼_Text_Icon.md)의 `onPressed: 함수` (괄호 없이) 와 같은 원리입니다 — **함수 자체를 값으로 넘기는 것.**

### pop — second_page.dart
```dart
ElevatedButton(
    onPressed: () {
      //현재 페이지를 네비게이션 스택에서 제거하고 이전 페이지로 전환
      Navigator.pop(context);
    },
    child: Text('이전 페이지로'))
```

---

## 5. ★ 다음 화면으로 데이터 넘기기 — 생성자 사용

```dart
// 보내는 쪽 (first_page.dart)
final person = Person('홍길동', 28);
... SecondPage(person: person) ...
```

```dart
// 받는 쪽 (second_page.dart)
class SecondPage extends StatelessWidget {

  final Person person;                      // ① 받을 자리를 만들고

  const SecondPage({required this.person}); // ② 생성자로 받는다

  @override
  Widget build(BuildContext context) {
    return ... Text('이름: ${person.name}') ...   // ③ 쓴다
  }
}
```

**데이터 전달은 특별한 기능이 아니라 그냥 생성자 매개변수**입니다. 위젯도 클래스니까요.

### 문법 3가지

| 코드 | 의미 |
|---|---|
| `final Person person;` | **`final` 필수.** StatelessWidget의 필드는 변경 불가여야 함 |
| `{required this.person}` | 중괄호 = **이름 있는 매개변수**, `required` = 생략 불가 |
| `this.person` | 받은 값을 필드에 바로 대입 (Dart 단축 문법) |

### 이름 있는 매개변수 vs 위치 매개변수

```dart
// ① 이름 있는 매개변수 — 중괄호 { }
const SecondPage({required this.person});
SecondPage(person: person)          // 호출 시 이름을 적는다

// ② 위치 매개변수 — 괄호만 (bmi_page.dart 방식)
BmiResult(this.height, this.weight);
BmiResult(170.0, 65.0)              // 순서대로 넣는다
```

| | 장점 | 단점 |
|---|---|---|
| 이름 있는 | 순서 무관, 뭘 넣는지 명확 | 타이핑 길어짐 |
| 위치 | 짧음 | **순서 틀리면 조용히 버그** (키/몸무게 바꿔 넣기) |

> Flutter 기본 위젯들이 전부 `child:`, `color:` 처럼 이름 있는 매개변수를 쓰는 이유입니다. 값이 2개 이상이면 이름 있는 방식을 쓰는 게 안전합니다.

---

## 6. Person — 순수 Dart 클래스

```dart
class Person{
  String name;
  int age;
  Person(this.name, this.age);    // 생성자
}
```

위젯이 아닌 **데이터만 담는 클래스**입니다 (Java의 DTO/VO, JSP에서 배운 JavaBean과 같은 역할).

```dart
final person = Person('홍길동', 28);   // new 없이 생성 (Dart는 new 생략)
print(person.name);                   // 홍길동
```

| Java | Dart |
|---|---|
| `new Person("홍길동", 28)` | `Person('홍길동', 28)` — **`new` 불필요** |
| getter/setter 필요 | 필드 직접 접근 가능 |
| `this.name = name;` | `Person(this.name, this.age);` 로 한 줄 |

### `${person.name}` — 문자열 보간
```dart
Text('이름: ${person.name}')      // 점(.)이 있으면 ${ } 로 감싼다
Text('나이: ${person.age}')
Text('$name')                      // 단순 변수면 { } 생략 가능
```

---

## 7. 이전 화면으로 결과 돌려주기 (확장)

예제에는 없지만 자주 쓰는 패턴입니다.

```dart
// 뒤로 가면서 값 전달
Navigator.pop(context, '선택한 값');

// 받는 쪽: push 결과를 await
final result = await Navigator.push(
  context,
  MaterialPageRoute(builder: (context) => SecondPage(...))
);
print(result);   // '선택한 값'
```

`push`는 **`Future`를 돌려줍니다** — "그 화면이 닫힐 때까지 기다렸다가 결과를 받는" 것이죠.
[ch06-3](./ch06_3_Form유효성검사_BMI계산기.md)의 BMI 예제가 `await Navigator.push(...)`로 "결과 화면에서 돌아온 뒤 입력창 비우기"를 구현합니다.

---

## 8. Navigator 명령 정리

| 명령 | 동작 | 쓰임새 |
|---|---|---|
| `Navigator.push(context, route)` | 쌓기 | 다음 화면으로 |
| `Navigator.pop(context)` | 치우기 | 뒤로 |
| `Navigator.pop(context, 값)` | 치우면서 값 전달 | 선택 결과 반환 |
| `Navigator.pushReplacement(...)` | 현재 화면을 **교체** | 로그인 성공 → 메인 (뒤로 가기로 로그인 화면에 못 돌아가게) |
| `Navigator.popUntil(...)` | 특정 화면까지 전부 치우기 | 여러 단계 뒤로 |

### ⚠️ 첫 화면에서 pop하면
쌓인 게 없어 앱이 종료되거나 검은 화면이 됩니다. `FirstPage`에는 뒤로 가기 버튼이 없는 이유도 이것입니다 (쌓인 화면이 없으니 자동으로 안 만들어짐).

---

## 9. 정리 — 이렇게 써야 한다

```dart
// ① 보내는 화면
Navigator.push(
  context,
  MaterialPageRoute(builder: (context) => 다음화면(데이터: 값)),
);

// ② 받는 화면
class 다음화면 extends StatelessWidget {
  final 타입 데이터;
  const 다음화면({required this.데이터});
  ...
}

// ③ 돌아오기
Navigator.pop(context);
```

| 체크리스트 | |
|---|---|
| import 했나? | `import 'package:프로젝트명/파일.dart';` |
| 받는 필드가 `final`인가? | StatelessWidget은 필수 |
| `required` 붙였나? | 안 넘기면 컴파일 에러로 잡아줌 |
| 뒤로 가기 버튼? | `AppBar`가 자동 생성 |
