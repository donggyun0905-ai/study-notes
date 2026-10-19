# Dart / Flutter 공부노트 — 목차

`ch03` ~ `ch06` 프로젝트로 진행한 Flutter 수업 내용을 분야별로 나눠 정리한 공부노트입니다.
각 파일은 **구조 파악 → 코드 한 줄씩 분석 → "이럴 땐 이렇게 써야 한다" 템플릿** 순서로 되어 있습니다.

> 환경: Flutter 3.47.7 (stable) · Android Studio · platform-tools 37.0.1

---

## 파일 목록

### 2026-10-15 (ch03 기본구조 · ch04 레이아웃/위젯 · ch05 입력위젯)

| 파일 | 내용 | 관련 챕터 |
|---|---|---|
| [00_개발환경_트러블슈팅.md](./공부노트/00_개발환경_트러블슈팅.md) | 설치 버전, `main.dart`가 아닌 파일 실행법(`-t`), 겪은 에러 3건, Hot Reload vs Restart, `flutter analyze` 읽는 법, **와이파이로 휴대폰 연결(adb pair)** | 전체 |
| [ch03_1_Flutter기본구조.md](./공부노트/ch03_1_Flutter기본구조.md) | `main()`→`runApp()`→`MaterialApp`→`Scaffold` 흐름, 위젯 트리, `child` vs `children`, StatelessWidget, 클래스 분리(MyApp/MyHomePage) | ch03 |
| [ch03_2_상태관리와setState.md](./공부노트/ch03_2_상태관리와setState.md) | **★ 가장 중요.** StatefulWidget의 2클래스 구조, `createState`, `setState`가 하는 일 2가지, 자주 하는 실수 2가지, 삼항 연산자 토글 | ch03 |
| [ch04_1_레이아웃_Container와Column_Row_Stack.md](./공부노트/ch04_1_레이아웃_Container와Column_Row_Stack.md) | `Container`의 padding/margin 차이, `EdgeInsets` 4종, Column/Row/Stack, **mainAxis vs crossAxis**, RenderFlex overflow 해결 | ch04 |
| [ch04_2_스크롤과리스트_ListView_GridView.md](./공부노트/ch04_2_스크롤과리스트_ListView_GridView.md) | Column은 스크롤이 안 된다, `SingleChildScrollView`, `List.generate`·`map().toList()`, `ListView`+`ListTile`, `GridView.count`(width가 무시되는 이유) | ch04 |
| [ch04_3_화면전환_PageView_Tab_BottomNav.md](./공부노트/ch04_3_화면전환_PageView_Tab_BottomNav.md) | `PageView`, 탭 3개 세트(`DefaultTabController`+`TabBar`+`TabBarView`)와 숫자 일치 규칙, `BottomNavigationBar`는 **직접 상태 관리**해야 하는 이유 | ch04 |
| [ch04_4_기본위젯_버튼_Text_Icon.md](./공부노트/ch04_4_기본위젯_버튼_Text_Icon.md) | 버튼 3종(`IconButton`만 `icon:`), **`onPressed: 함수` vs `함수()` 차이**, `TextStyle`, `Icon`, `Image.asset`의 pubspec 등록 3단계 | ch04 |
| [ch05_1_입력위젯_Checkbox_Radio_Dropdown.md](./공부노트/ch05_1_입력위젯_Checkbox_Radio_Dropdown.md) | **★ 모든 입력 위젯의 공통 3단 패턴**(`value`/`onChanged`/`setState`), `TextField`, Checkbox·Switch 변수 공유, null safety `?`·`!`, Radio의 `groupValue` 동작 원리, enum, **`RadioListTile`**, `DropdownButton` | ch05 |
| [ch05_2_버튼과진행표시기.md](./공부노트/ch05_2_버튼과진행표시기.md) | 버튼↔로딩 전환, **build()는 매번 실행되는 함수**(선언형 UI), 조건부 위젯 3표기법, `CircularProgressIndicator` 두 모드, `Future.delayed`, **`mounted` 체크가 필요한 이유** | ch05 |

### 2026-10-16 (ch05 터치 감지 · ch06 화면이동/복잡한UI/Form)

| 파일 | 내용 | 관련 챕터 |
|---|---|---|
| [ch05_3_터치감지_GestureDetector_InkWell.md](./공부노트/ch05_3_터치감지_GestureDetector_InkWell.md) | `Text`·`Icon`처럼 `onPressed`가 없는 위젯을 누르게 만들기, **GestureDetector vs InkWell**(물결 효과), 감지 가능한 제스처 목록, **터치 영역이 글자 크기만큼인 함정** | ch05 |
| [ch06_1_화면이동_Navigator와_파일분리.md](./공부노트/ch06_1_화면이동_Navigator와_파일분리.md) | **★ 파일 여러 개로 분리**, `package:프로젝트명/파일.dart` import, **Navigator.push/pop(화면 Stack)**, `MaterialPageRoute`의 `builder`에 함수를 넘기는 이유, **생성자로 데이터 넘기기**, 이름 있는 매개변수 vs 위치 매개변수 | ch06 |
| [ch06_2_복잡한UI_BottomNav_외부패키지.md](./공부노트/ch06_2_복잡한UI_BottomNav_외부패키지.md) | **★ build()를 buildTop/Middle/Bottom으로 쪼개기**, `_pages[_index]`로 하단 탭 상태 관리, AppBar `actions`·`centerTitle`·테마, **외부 패키지 3단계**(pubspec→pub get→import)와 `^5.0.0` 의미, `MediaQuery`, `ClipRRect`, `Opacity`, **스크롤 안 스크롤(`shrinkWrap`+`NeverScrollableScrollPhysics`)** | ch06 |
| [ch06_3_Form유효성검사_BMI계산기.md](./공부노트/ch06_3_Form유효성검사_BMI계산기.md) | **★ Form + GlobalKey + validator**(문자열=에러 / null=통과), `TextField` vs `TextFormField`, `TextEditingController`와 **`dispose()`가 필요한 이유**, **`async/await`로 화면이 닫히기를 기다리기**, `parse` vs `tryParse`, if-else if는 큰 값부터, 개선 포인트 6가지 | ch06 |

---

## 소스 코드

| 폴더 | 내용 |
|---|---|
| [ch03/lib](./ch03/lib) | 기본 구조 (`main1`~`main3`) |
| [ch04/lib](./ch04/lib) | 레이아웃·기본 위젯 (`main`~`main12`) |
| [ch05/lib](./ch05/lib) | 사용자 입력 위젯 (`main`~`main4`, `main7`) |
| [ch06](./ch06) | 화면 이동 · 복잡한 UI · Form (파일별 독립 예제) |

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
| `main2.dart` | `Radio`, **`RadioListTile`** + `enum Gender` | `Gender gender` |
| `main3.dart` | `DropdownButton` | `String selectedValue` |
| `main4.dart` | `ElevatedButton` ↔ `CircularProgressIndicator` | `bool isDownloading` |
| `main7.dart` | **`GestureDetector`, `InkWell`** | — (`print`로 확인) |

**ch06 — 화면 이동 · 복잡한 UI · Form**

| 파일 | 주제 | 핵심 |
|---|---|---|
| `main.dart` + `first_page.dart` + `second_page.dart` | **화면 이동** | `Navigator.push`/`pop`, `MaterialPageRoute`, 생성자로 `Person` 전달 |
| `carousel_page.dart` | **복잡한 UI** | `buildTop`/`buildMiddle`/`buildBottom` 분리, `BottomNavigationBar`+`_pages[_index]`, `CarouselSlider`(외부 패키지), `shrinkWrap` |
| `bmi_page.dart` | **Form 유효성 검사** | `Form`+`GlobalKey`+`validator`, `TextEditingController`+`dispose`, `await Navigator.push`, BMI 계산 |

> ⚠️ `ch06`은 `pubspec.yaml`과 `lib/`가 없는 상태로 `.dart` 파일만 모여 있습니다.
> `package:ch06/...` import가 해석되지 않아 **그대로는 실행되지 않습니다.**
> `flutter create ch06` 후 파일을 `ch06/lib/`로 옮기면 동작합니다 (자세한 내용은 [ch06-1](./공부노트/ch06_1_화면이동_Navigator와_파일분리.md) 2절).
> `carousel_page.dart`는 추가로 `carousel_slider` 패키지와 `assets/image1~3.jpg`가 필요합니다.

---

## 큰 그림 — 배운 것들이 어떻게 이어지나

```
[진입점]  void main() => runApp(MyApp());            ← ch03_1
   │       * 이게 없으면 실행 자체가 안 된다
   ▼
[앱 틀]   MaterialApp (테마, 제목, home)             ← ch03_1, ch06_2(appBarTheme)
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
   │              * 스크롤 안에 스크롤 → shrinkWrap   ← ch06_2
   │
   ├──▶ [화면전환] PageView, TabBar, BottomNav       ← ch04_3
   │              * TabBar는 자동, BottomNav는 직접 관리 → ch06_2에서 실제 구현
   │
   └──▶ [내용]  Text, Icon, Image, Button            ← ch04_4
                  * onPressed: 함수 (괄호 없이)
                  * 버튼 아닌 위젯은 InkWell로 감싸기  ← ch05_3
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
   ├──▶ [분기]  isX ? 위젯A : 위젯B                   ← ch05_2
   │              * 버튼 ↔ 로딩 표시기 전환
   │              * 분기 3개 이상이면 메소드로            ← ch06_3(buildIcon)
   │              * 선택지가 리스트면 _pages[_index]     ← ch06_2
   │
   └──▶ [비동기]  Future.delayed / await             ← ch05_2, ch06_3
                  * mounted 체크
   │
   ▼
[여러 화면]  파일 분리 + Navigator                    ← ch06_1 ★
   │       * import 'package:프로젝트명/파일.dart'
   │       * push로 쌓고 pop으로 치운다
   │       * 데이터는 생성자로 넘긴다
   │       * await push → 돌아온 뒤 처리              ← ch06_3
   │
   ▼
[종합]   입력 → 검증 → 계산 → 결과 화면               ← ch06_3 (BMI 계산기)
         Form + GlobalKey + validator + Navigator
```

**하나의 흐름으로 읽으면**: `main()`으로 시작해 `Scaffold` 안에 위젯을 배치하고(ch04), 사용자 조작이 `onChanged`/`onPressed`로 들어오면 `setState`로 상태를 바꾸고(ch03_2), `build()`가 다시 실행되면서 바뀐 상태가 화면에 반영된다(ch05). 화면이 여러 개가 되면 파일을 나누고 `Navigator`로 오간다(ch06).

- **ch04** = "화면을 어떻게 그리나"
- **ch03_2 / ch05** = "화면을 어떻게 바꾸나"
- **ch06** = "화면이 여러 개일 때 어떻게 조립하나"

---

## 한 장 요약 — 자주 틀리는 것 TOP 8

| # | 실수 | 증상 | 해결 |
|---|---|---|---|
| 1 | `void main()` 누락 | 실행 버튼(▶)이 안 보임, `Could not find 'main'` | `void main() => runApp(MyApp());` |
| 2 | `setState` 안에서 값을 안 바꿈 | 눌러도 화면이 그대로 (**에러 없음**) | `상태변수 = value!;` |
| 3 | `setState` 없이 값만 바꿈 | 값은 변했는데 화면이 그대로 | `setState(() { ... })`로 감싸기 |
| 4 | `onPressed: 함수()` 괄호 붙임 | 화면 뜰 때 바로 실행됨 | 괄호 없이 `onPressed: 함수` |
| 5 | `map()` 뒤 `.toList()` 누락 | 타입 에러 (`Iterable`≠`List`) | `.map(...).toList()` |
| 6 | 스크롤 위젯 안에 스크롤 위젯 | `Vertical viewport was given unbounded height` | 안쪽에 `shrinkWrap: true` + `physics: NeverScrollableScrollPhysics()` |
| 7 | `validator` 반환값을 거꾸로 | 검증이 항상 통과/실패 | **문자열 = 에러, `null` = 통과** |
| 8 | `Controller`에 `dispose()` 안 씀 | 메모리 누수 (당장은 안 보임) | `dispose()`에서 `controller.dispose()` |

---

## 프로젝트 폴더 구조 (2026-10-16 기준)

```
C:\Users\<사용자>\StudioProjects\
├── ch03\
│   └── lib\  main.dart, main1~main3.dart
├── ch04\
│   └── lib\  main.dart, main1~main12.dart
├── ch05\
│   └── lib\  main.dart, main1~main4.dart, main7.dart
├── ch06\      ← pubspec 없음. .dart 파일만 모아둔 상태
│   ├── main.dart, first_page.dart, second_page.dart   (화면 이동 세트)
│   ├── carousel_page.dart                              (독립 실행)
│   └── bmi_page.dart                                   (독립 실행)
└── 공부노트\  2026-10-15.md   (날짜별 일지 — 이 폴더는 주제별 정리본)
```

> Flutter는 `lib\main.dart` 하나만 진입점으로 쓰므로, `mainN.dart`를 실행할 때는
> `flutter run -t lib/mainN.dart` 또는 Android Studio에서 `void main()` 옆 ▶ 를 누릅니다.

---

## 다음에 할 것

- [ ] `ch06`을 실제 Flutter 프로젝트로 만들어 3개 예제 모두 실행해보기
- [ ] `carousel_slider` 패키지 설치 + `assets/` 이미지 준비
- [ ] `bmi_page.dart` 개선 6가지 적용 ([ch06-3](./공부노트/ch06_3_Form유효성검사_BMI계산기.md) 9절)
- [ ] `Navigator.pop(context, 값)`으로 결과 돌려받기
- [ ] `Navigator.pushNamed` — 이름으로 화면 이동 (라우트 테이블)
- [ ] `ListView.builder`로 대량 데이터 처리
- [ ] `Radio`를 최신 `RadioGroup` 방식으로 바꿔보기 (deprecated 대응)
- [ ] `http` 패키지로 실제 서버 통신 (JSP에서 배운 서버 쪽과 연결)
