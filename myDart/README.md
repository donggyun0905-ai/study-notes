# Dart / Flutter 공부노트 — 목차

`ch03` ~ `ch05` 프로젝트로 진행한 Flutter 수업 내용을 분야별로 나눠 정리한 공부노트입니다.
각 파일은 **구조 파악 → 코드 한 줄씩 분석 → "이럴 땐 이렇게 써야 한다" 템플릿** 순서로 되어 있습니다.

> 환경: Flutter 3.47.7 (stable) · Android Studio · platform-tools 37.0.1

---

## 파일 목록

### 2026-10-15 (ch03 기본구조 · ch04 레이아웃/위젯 · ch05 입력위젯)

| 파일 | 내용 | 관련 챕터 |
|---|---|---|
| [00_개발환경_트러블슈팅.md](./공부노트/00_개발환경_트러블슈팅.md) | 설치 버전, `main.dart`가 아닌 파일 실행법(`-t`), 오늘 겪은 에러 3건, Hot Reload vs Restart, `flutter analyze` 읽는 법, **와이파이로 휴대폰 연결(adb pair)** | 전체 |
| [ch03_1_Flutter기본구조.md](./공부노트/ch03_1_Flutter기본구조.md) | `main()`→`runApp()`→`MaterialApp`→`Scaffold` 흐름, 위젯 트리, `child` vs `children`, StatelessWidget, 클래스 분리(MyApp/MyHomePage) | ch03 |
| [ch03_2_상태관리와setState.md](./공부노트/ch03_2_상태관리와setState.md) | **★ 가장 중요.** StatefulWidget의 2클래스 구조, `createState`, `setState`가 하는 일 2가지, 자주 하는 실수 2가지, 삼항 연산자 토글 | ch03 |
| [ch04_1_레이아웃_Container와Column_Row_Stack.md](./공부노트/ch04_1_레이아웃_Container와Column_Row_Stack.md) | `Container`의 padding/margin 차이, `EdgeInsets` 4종, Column/Row/Stack, **mainAxis vs crossAxis**, RenderFlex overflow 해결 | ch04 |
| [ch04_2_스크롤과리스트_ListView_GridView.md](./공부노트/ch04_2_스크롤과리스트_ListView_GridView.md) | Column은 스크롤이 안 된다, `SingleChildScrollView`, `List.generate`·`map().toList()`, `ListView`+`ListTile`, `GridView.count`(width가 무시되는 이유) | ch04 |
| [ch04_3_화면전환_PageView_Tab_BottomNav.md](./공부노트/ch04_3_화면전환_PageView_Tab_BottomNav.md) | `PageView`, 탭 3개 세트(`DefaultTabController`+`TabBar`+`TabBarView`)와 숫자 일치 규칙, `BottomNavigationBar`는 **직접 상태 관리**해야 하는 이유 | ch04 |
| [ch04_4_기본위젯_버튼_Text_Icon.md](./공부노트/ch04_4_기본위젯_버튼_Text_Icon.md) | 버튼 3종(`IconButton`만 `icon:`), **`onPressed: 함수` vs `함수()` 차이**, `TextStyle`, `Icon`, `Image.asset`의 pubspec 등록 3단계 | ch04 |
| [ch05_1_입력위젯_Checkbox_Radio_Dropdown.md](./공부노트/ch05_1_입력위젯_Checkbox_Radio_Dropdown.md) | **★ 모든 입력 위젯의 공통 3단 패턴**(`value`/`onChanged`/`setState`), `TextField`, Checkbox·Switch 변수 공유, null safety `?`·`!`, Radio의 `groupValue` 동작 원리, enum, `DropdownButton` | ch05 |
| [ch05_2_버튼과진행표시기.md](./공부노트/ch05_2_버튼과진행표시기.md) | 버튼↔로딩 전환, **build()는 매번 실행되는 함수**(선언형 UI), 조건부 위젯 3표기법, `CircularProgressIndicator` 두 모드, `Future.delayed`, **`mounted` 체크가 필요한 이유** | ch05 |

---

## 소스 코드

| 폴더 | 내용 |
|---|---|
| [ch03/lib](./ch03/lib) | 기본 구조 (`main1`~`main3`) |
| [ch04/lib](./ch04/lib) | 레이아웃·기본 위젯 (`main`~`main12`) |
| [ch05/lib](./ch05/lib) | 사용자 입력 위젯 (`main`~`main4`) |

### 예제 파일 ↔ 위젯 대응표

**ch03 — 기본 구조**

| 파일 | 내용 |
|---|---|
| `main.dart` | 프로젝트 생성 시 나오는 카운터 예제 (템플릿 그대로) |
| `main1.dart` | 기본 구조 분석. `MaterialApp`→`Scaffold`→`AppBar`/`body` |
| `main2.dart` | `MyApp` / `MyHomePage` 두 클래스로 분리 |
| `main3.dart` | **StatefulWidget + setState** — Hello ↔ World 토글 |

**ch04 — 레이아웃 & 기본 위젯**

| 파일 | 위젯 | 핵심 |
|---|---|---|
| `main.dart` | `Container` | padding=안쪽, margin=바깥쪽 |
| `main1.dart` | `Column` | 세로 배치 |
| `main2.dart` | `Row` | 가로 배치 |
| `main3.dart` | `Stack` | 겹쳐 배치 (리스트 순서 = 쌓는 순서) |
| `main4.dart` | `SingleChildScrollView` + `ListBody` | `List.generate`, `map().toList()` |
| `main5.dart` | `ListView` + `ListTile` | `leading`/`title`/`trailing`/`onTap` |
| `main6.dart` | `GridView.count` | `crossAxisCount` = 열 개수 |
| `main7.dart` | `PageView` | 좌우 스와이프 |
| `main8.dart` | `TabBar` | `length` = `tabs` 개수 = `children` 개수 |
| `main9.dart` | `BottomNavigationBar` | `items` 2개 이상 필수 |
| `main10.dart` | `ElevatedButton`/`TextButton`/`IconButton` | `onPressed`에 함수 이름만 (괄호 X) |
| `main11.dart` | `Text` + `TextStyle` | `fontSize`, `fontWeight`, `letterSpacing` |
| `main12.dart` | `Icon`, `Image.asset` | 이미지는 pubspec 등록 필요 |

**ch05 — 사용자 입력 위젯**

| 파일 | 위젯 | 상태 변수 |
|---|---|---|
| `main.dart` | `TextField` | — (`InputDecoration`, `OutlineInputBorder`) |
| `main1.dart` | `Checkbox`, `Switch` | `bool isChecked` |
| `main2.dart` | `Radio` + `enum Gender` | `Gender gender` |
| `main3.dart` | `DropdownButton` | `String selectedValue` |
| `main4.dart` | `ElevatedButton` ↔ `CircularProgressIndicator` | `bool isDownloading` |

---

## 큰 그림 — 배운 것들이 어떻게 이어지나

```
[진입점]  void main() => runApp(MyApp());            ← ch03_1
   │       * 이게 없으면 실행 자체가 안 된다
   ▼
[앱 틀]   MaterialApp (테마, 제목, home)             ← ch03_1
   │
   ▼
[페이지]  Scaffold (appBar / body / bottomNav ...)   ← ch03_1
   │
   ├──▶ [배치]  Container, Column, Row, Stack        ← ch04_1
   │              * 자식 1개는 child, 여러 개는 children
   │              * 주축(main) = 자식이 나열되는 방향
   │
   ├──▶ [목록]  ListView+ListTile, GridView          ← ch04_2
   │              * Column은 스크롤 안 됨
   │              * map().toList() 로 데이터→위젯 변환
   │
   ├──▶ [화면전환] PageView, TabBar, BottomNav       ← ch04_3
   │              * TabBar는 자동, BottomNav는 직접 관리
   │
   └──▶ [내용]  Text, Icon, Image, Button            ← ch04_4
                  * onPressed: 함수 (괄호 없이)
   │
   ▼
[상태]   StatefulWidget + setState                   ← ch03_2 ★
   │       * setState = 값 변경 + "다시 그려라" 요청
   │       * build() 는 setState 마다 다시 실행되는 함수
   │
   ├──▶ [입력]  value + onChanged + setState         ← ch05_1 ★
   │              Checkbox / Switch / Radio / Dropdown
   │              * 위젯은 자기 상태를 기억하지 않는다
   │              * Radio만 groupValue 추가 (하나만 선택)
   │
   └──▶ [분기]  isX ? 위젯A : 위젯B                   ← ch05_2
                  * 버튼 ↔ 로딩 표시기 전환
                  * Future.delayed + mounted 체크
```

**하나의 흐름으로 읽으면**: `main()`으로 시작해 `Scaffold` 안에 위젯을 배치하고(ch04), 사용자 조작이 `onChanged`/`onPressed`로 들어오면 `setState`로 상태를 바꾸고(ch03_2), `build()`가 다시 실행되면서 바뀐 상태가 화면에 반영된다(ch05).

ch04는 **"화면을 어떻게 그리나"**, ch03_2와 ch05는 **"화면을 어떻게 바꾸나"** 입니다.

---

## 한 장 요약 — 자주 틀리는 것 TOP 5

| # | 실수 | 증상 | 해결 |
|---|---|---|---|
| 1 | `void main()` 누락 | 실행 버튼(▶)이 안 보임, `Could not find 'main'` | `void main() => runApp(MyApp());` |
| 2 | `setState` 안에서 값을 안 바꿈 | 눌러도 화면이 그대로 (**에러 없음**) | `상태변수 = value!;` |
| 3 | `setState` 없이 값만 바꿈 | 값은 변했는데 화면이 그대로 | `setState(() { ... })`로 감싸기 |
| 4 | `onPressed: 함수()` 괄호 붙임 | 화면 뜰 때 바로 실행됨 | 괄호 없이 `onPressed: 함수` |
| 5 | `map()` 뒤 `.toList()` 누락 | 타입 에러 (`Iterable`≠`List`) | `.map(...).toList()` |

---

## 프로젝트 폴더 구조 (2026-10-15 기준)

```
C:\Users\<사용자>\StudioProjects\
├── ch03\
│   └── lib\  main.dart, main1~main3.dart
├── ch04\
│   └── lib\  main.dart, main1~main12.dart
├── ch05\
│   └── lib\  main.dart, main1~main4.dart
└── 개발일지\  2026-10-15.md   (날짜별 일지 — 이 공부노트는 주제별 정리본)
```

> Flutter는 `lib\main.dart` 하나만 진입점으로 쓰므로, `mainN.dart`를 실행할 때는
> `flutter run -t lib/mainN.dart` 또는 Android Studio에서 `void main()` 옆 ▶ 를 누릅니다.

---

## 다음에 할 것

- [ ] ch05 나머지 입력 위젯 (`Slider`, `DatePicker`, `Form` + 유효성 검사)
- [ ] `Radio`를 최신 `RadioGroup` 방식으로 바꿔보기 (deprecated 대응)
- [ ] `main4.dart`에 실제 진행률(`CircularProgressIndicator(value:)`) 표시
- [ ] 다운로드 완료 후 `SnackBar` 알림
- [ ] 화면 이동 — `Navigator.push` / `Navigator.pop`
- [ ] `ListView.builder`로 대량 데이터 처리
- [ ] `http` 패키지로 실제 서버 통신 (JSP에서 배운 서버 쪽과 연결)
