# ch03-1. Flutter 앱의 기본 구조

> 예제: `ch03/lib/main1.dart`, `main2.dart`

---

## 1. 앱이 시작되는 길

```
main()  →  runApp(MyApp())  →  MaterialApp  →  Scaffold  →  appBar / body
 진입점        앱 실행          디자인 틀      페이지 골격     실제 내용
```

```dart
import 'package:flutter/material.dart';   // ① Material Design 위젯 전부 들여오기

void main(){                              // ② 프로그램의 시작점
  runApp(MyApp());                        // ③ 위젯을 화면에 띄움
}
```

| 단계 | 코드 | 설명 |
|---|---|---|
| ① import | `package:flutter/material.dart` | `Text`, `Scaffold` 등을 쓰려면 필수. JSP가 `jakarta.servlet.http.*`를 자동 import 해주는 것과 달리 **직접 써줘야 함** |
| ② main | `void main(){ }` | Dart 프로그램의 유일한 진입점. **없으면 실행 불가** |
| ③ runApp | `runApp(위젯)` | 넘겨받은 위젯을 화면 전체에 붙임 |

### 람다 스타일 — 둘은 완전히 같은 코드

```dart
void main(){
  runApp(MyApp());
}

void main() => runApp(MyApp());   // 한 줄이면 이렇게 줄일 수 있다
```

`=>`는 `{ return ...; }`의 줄임입니다. 수업 예제는 거의 이 형태를 씁니다.

---

## 2. 위젯(Widget)이란

**Flutter에서는 화면의 모든 것이 위젯입니다.** 버튼도 위젯, 글자도 위젯, 여백도 위젯, 화면 전체도 위젯.

위젯은 **나무처럼 중첩**되고, 이걸 **위젯 트리**라고 합니다.

```
MaterialApp
└── Scaffold
    ├── AppBar
    │   └── Text('MyApp')
    └── Center
        └── Text('반갑다 Flutter')
```

```dart
return MaterialApp(
  title:'Flutter App',
  home: Scaffold(
    appBar: AppBar(title: Text('MyApp'),),
    body: Center(
      child: Text('반갑다 Flutter', style: TextStyle(fontSize: 24),),
    ),
  ),
);
```

괄호가 깊게 중첩되는 게 Flutter 코드의 특징입니다. **들여쓰기를 맞춰야** 어디가 어디 자식인지 보입니다 (`Ctrl+Alt+L`).

---

## 3. 핵심 3대 위젯

### MaterialApp — 앱 전체의 틀
구글 Material Design을 적용해주는 **최상위 위젯**. 테마·제목·시작 화면을 정합니다.

```dart
MaterialApp(
  title: 'Flutter Demo',                          // 앱 제목(작업 전환기 표시용)
  theme: ThemeData(primarySwatch: Colors.blue),   // 앱 전체 기본 색상
  home: MyHomePage(),                             // 처음 보여줄 화면
)
```

### Scaffold — 페이지 하나의 골격
**Scaffold = "건설 현장의 비계(발판)"**. 앱바·본문·버튼 위치를 미리 잡아둔 틀입니다.

| 속성 | 위치 |
|---|---|
| `appBar` | 상단 바 |
| `body` | 가운데 본문 (가장 많이 씀) |
| `floatingActionButton` | 우측 하단 둥근 버튼 |
| `bottomNavigationBar` | 하단 탭 바 |
| `drawer` | 왼쪽에서 밀려나오는 메뉴 |

### Center / Text
```dart
Center(child: Text('반갑다 Flutter'))
```
- `Center`: 자식 **하나**를 가운데 정렬 (`child` — 단수)
- `Text`: 글자. 꾸미려면 `style: TextStyle(...)`

> **`child` vs `children` 구분** — 자식이 하나면 `child:`, 여러 개면 `children: [...]`. `Center`는 `child`, `Column`은 `children`입니다.

---

## 4. StatelessWidget — 변하지 않는 화면

```dart
class MyApp extends StatelessWidget{
  @override
  Widget build(BuildContext context) {   // 위젯을 만들어 돌려주는 메소드
    return MaterialApp( ... );
  }
}
```

| 요소 | 의미 |
|---|---|
| `extends StatelessWidget` | **상태가 변하지 않는** 위젯. 한 번 그려지면 그대로 |
| `@override` | 부모 클래스의 `build`를 재정의한다는 표시 (안 써도 되지만 관례) |
| `Widget build(...)` | **Flutter가 자동으로 호출**하는 메소드. 내가 부르지 않음 |
| `BuildContext context` | 이 위젯이 트리에서 어디 있는지 알려주는 정보. 지금은 그냥 받아두면 됨 |

**언제 쓰나**: 글자·이미지·레이아웃만 있고 사용자 조작에 따라 바뀌지 않는 화면.
→ 바뀌어야 한다면 `StatefulWidget` ([ch03-2](./ch03_2_상태관리와setState.md))

---

## 5. 클래스 분리 — main1 vs main2

### main1.dart — 한 클래스에 다 넣기
```dart
class MyApp extends StatelessWidget{
  Widget build(context) {
    return MaterialApp(
      home: Scaffold( appBar: ..., body: ... ),   // 여기에 화면 내용까지 전부
    );
  }
}
```

### main2.dart — 두 클래스로 분리 ★
```dart
class MyApp extends StatelessWidget{          // ① 앱 설정 담당
  Widget build(context) {
    return MaterialApp(
      theme: ThemeData(primarySwatch: Colors.blue),
      home : MyHomePage(),                    // ← 화면은 따로
    );
  }
}

class MyHomePage extends StatelessWidget{     // ② 화면 내용 담당
  Widget build(context) {
    return Scaffold(
      appBar: AppBar(title: Text('Hello World'),),
      body: Text('반갑다 Flutter', style: TextStyle(fontSize: 50),),
    );
  }
}
```

**왜 분리하나**
- 역할이 분리됨: `MyApp`은 앱 설정(테마·제목), `MyHomePage`는 화면 내용
- 화면이 늘어나면 `MyHomePage2`, `MyHomePage3`로 추가하기 쉬움
- `MaterialApp` 안에 모든 화면을 다 쓰면 괄호가 수십 겹이 돼서 읽을 수 없음

**이게 앞으로 모든 예제의 기본형**입니다. ch04 전체가 `MyApp` + `MyHomePage` 구조입니다.

---

## 6. 이렇게 써야 한다 — 새 파일 템플릿

```dart
//mainN.dart

import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'chXX',
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Subject'),),
      body: Center(
        child: /* ← 여기에 실습할 위젯 */,
      ),
    );
  }
}
```

상태가 바뀌어야 하는 예제라면 `StatelessWidget` 대신 StatefulWidget 형태를 씁니다 → [ch03-2](./ch03_2_상태관리와setState.md)
