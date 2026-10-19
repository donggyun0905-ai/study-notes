# ch06-2. 복잡한 UI 조립하기 — BottomNav + 외부 패키지

> 예제: `ch06/carousel_page.dart`
> 카카오T 같은 **실제 앱 화면**을 만들어보는 종합 실습. ch04에서 배운 위젯이 거의 다 등장합니다.

---

## 1. 화면 구성

```
┌──────────────────────────────┐
│       복잡한 UI          [+] │ ← AppBar (centerTitle, actions)
├──────────────────────────────┤
│  🚕    🚕    🚕    🚕        │ ← buildTop()    : Row + Column 격자
│  택시  블랙  바이크  대리     │
│  🚕    🚕    🚕    🚕(흐림)  │
│                              │
│  ┌────────────────────────┐  │ ← buildMiddle() : CarouselSlider
│  │   자동 넘김 이미지 배너   │  │   (외부 패키지)
│  └────────────────────────┘  │
│                              │
│  🔔 [이벤트] 이것은 공지…     │ ← buildBottom() : ListView 10개
│  🔔 [이벤트] 이것은 공지…     │
├──────────────────────────────┤
│   홈      이용서비스   내정보  │ ← BottomNavigationBar
└──────────────────────────────┘
```

---

## 2. ★★ build()를 메소드로 쪼개기

ch04까지는 `build()` 안에 전부 썼습니다. 화면이 복잡해지면 **괄호가 수십 겹**이 되어 읽을 수 없습니다.

```dart
class Page1 extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return ListView(
      children: [buildTop(), buildMiddle(), buildBottom()],   // ← 한눈에 구조가 보임
    );
  }

  //상단
  Widget buildTop(){ ... }

  //중단
  Widget buildMiddle() { ... }

  //하단
  Widget buildBottom() { ... }
}
```

### 규칙
- 메소드의 **반환 타입은 `Widget`**
- 이름은 `buildXxx` 관례
- `build()`는 "큰 덩어리를 조립"만 하고, 세부는 각 메소드가 담당

**이게 ch06에서 가장 중요한 실무 습관입니다.** 들여쓰기 10단을 3단으로 줄여줍니다.

> 더 커지면 메소드가 아니라 **별도 위젯 클래스**로 분리합니다 (`Page1`, `Page2`, `Page3`처럼). 메소드 분리는 "같은 파일 안에서 가독성만 높이는" 중간 단계입니다.

---

## 3. BottomNavigationBar에 상태 연결 ★

[ch04-3](./ch04_3_화면전환_PageView_Tab_BottomNav.md)에서 "하단 탭은 직접 상태 관리해야 한다"고 배운 걸 실제로 구현합니다.

```dart
class _MyHomePageState extends State<MyHomePage> {

  var _index = 0;                               // ① 선택된 탭 번호
  var _pages = [Page1(), Page2(), Page3()];     // ② 화면 3개를 리스트로

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _pages[_index],                     // ③ 번호에 맞는 화면 꺼내 쓰기
      bottomNavigationBar: BottomNavigationBar(
          onTap: (index){
            setState(() {
              _index = index;                   // ④ 번호 바꾸고 다시 그림
            });
          },
          currentIndex: _index,                 // ⑤ 현재 선택 표시
          items: [
            BottomNavigationBarItem(icon: Icon(Icons.home), label: '홈'),
            BottomNavigationBarItem(icon: Icon(Icons.assessment), label: '이용서비스'),
            BottomNavigationBarItem(icon: Icon(Icons.account_circle), label: '내 정보'),
          ]),
    );
  }
}
```

### `_pages[_index]` — 리스트로 화면 교체

```
_index = 0  →  body: Page1()   (홈)
_index = 1  →  body: Page2()   (이용서비스)
_index = 2  →  body: Page3()   (내 정보)
```

[ch05-2](./ch05_2_버튼과진행표시기.md)의 삼항 연산자 분기와 같은 아이디어인데, **선택지가 3개 이상이면 리스트 인덱스**가 더 깔끔합니다.

| 상태 변수 | `value`/`currentIndex` | 변경 콜백 |
|---|---|---|
| `_index` | `currentIndex: _index` | `onTap: (index) => setState(...)` |

입력 위젯의 **`value` + `onChanged` + `setState` 3단 패턴**([ch05-1](./ch05_1_입력위젯_Checkbox_Radio_Dropdown.md))과 이름만 다르고 구조는 같습니다.

### 변수명 앞의 `_`
`_index`, `_pages` — Dart에서 `_`로 시작하면 **이 파일 안에서만 쓰는 private**입니다. 클래스 내부 전용 변수에 붙이는 관례입니다.

---

## 4. AppBar 꾸미기

```dart
appBar: AppBar(
  title: Text('복잡한 UI', style: TextStyle(color: Colors.black),),
  centerTitle: true,                              //중앙에 위치
  actions: [                                      // 오른쪽 버튼들
    IconButton(onPressed: (){}, icon: Icon(Icons.add, color: Colors.black,))
  ],
),
```

| 속성 | 역할 |
|---|---|
| `centerTitle: true` | 제목 가운데 (안드로이드 기본은 왼쪽) |
| `actions: [...]` | **오른쪽**에 아이콘 버튼들 (검색, 추가, 설정) |
| `leading:` | 왼쪽 (기본은 뒤로가기/메뉴 버튼) |
| `backgroundColor` | 배경색 |
| `elevation: 0` | 그림자 없애기 (요즘 디자인) |

### 앱 전체 AppBar 색을 한 번에 — 테마

```dart
return MaterialApp(
  theme: ThemeData(
    appBarTheme: AppBarTheme(
      backgroundColor: Colors.blue,
      foregroundColor: Colors.white,     // 제목/아이콘 색
    )
  ),
  home: MyHomePage(),
);
```

**`theme`에 한 번 정의하면 앱의 모든 AppBar에 적용**됩니다. 화면마다 색을 쓰는 것보다 유지보수가 쉽습니다.

> 예제는 테마로 `foregroundColor: Colors.white`를 줬는데 `title`에서 다시 `color: Colors.black`을 줘서 **개별 설정이 테마를 덮어씁니다.** 이렇게 테마와 개별 설정이 충돌하면 항상 **개별 설정이 이깁니다.**

---

## 5. ★ 외부 패키지 쓰기 — carousel_slider

Flutter에 없는 기능은 [pub.dev](https://pub.dev/)에서 패키지를 받아 씁니다.

### 3단계

**① `pubspec.yaml`에 추가**
```yaml
dependencies:
  flutter:
    sdk: flutter
  carousel_slider: ^5.0.0     # 최신 버전으로 업데이트

flutter:
  assets:
    - assets/                  # 이미지 폴더 등록
```

**② 패키지 받기**
```bash
flutter pub get
```
(Android Studio는 `pubspec.yaml`을 저장하면 상단에 `Pub get` 버튼이 뜹니다)

**③ import 후 사용**
```dart
import 'package:carousel_slider/carousel_slider.dart';
```

### `^5.0.0` 의 의미 (캐럿 버전)
| 표기 | 허용 범위 |
|---|---|
| `^5.0.0` | 5.0.0 이상, **6.0.0 미만** (호환되는 업데이트는 자동 허용) |
| `5.0.0` | 정확히 5.0.0만 |
| `any` | 아무 버전 (권장 안 함) |

> 메이저 버전(앞자리)이 올라가면 사용법이 바뀔 수 있어 자동 업데이트에서 제외합니다.

### ⚠️ 이 예제를 실행하려면 준비물
1. `pubspec.yaml`에 `carousel_slider` 추가 + `flutter pub get`
2. `assets/image1.jpg`, `image2.jpg`, `image3.jpg` 파일 준비
3. `pubspec.yaml`의 `assets:` 등록 ([ch04-4](./ch04_4_기본위젯_버튼_Text_Icon.md) 참고)

이미지가 없으면 **회색/주황 박스만 보이거나 에러**가 납니다. 빠르게 확인하려면 `Image.asset` 대신 `Image.network('https://picsum.photos/400/200')`로 바꿔보면 됩니다.

---

## 6. CarouselSlider — 자동 넘김 배너

```dart
Widget buildMiddle() {
  return CarouselSlider(
      options: CarouselOptions(height: 150, autoPlay: true),
      items: dummyItems.map((path){
        return Builder(builder: (BuildContext context){
          return Container(
            width: MediaQuery.of(context).size.width,     //현재화면 너비
            margin: EdgeInsets.symmetric(horizontal: 5.0),//좌우여백 5
            decoration: BoxDecoration(color: Colors.amber),
            child: ClipRRect(
              borderRadius: BorderRadius.circular(8.0),   //모서리를 둥글기
              child: Image.asset(path, fit: BoxFit.cover,),//크기는 화면에 맞게
            ),
          );
        });
      }).toList(),
  );
}
```

| 조각 | 설명 |
|---|---|
| `options: CarouselOptions(height:, autoPlay:)` | 높이 + **자동 넘김** 설정 |
| `items:` | 보여줄 위젯 리스트 |
| `.map(...).toList()` | 경로 리스트 → 위젯 리스트 ([ch04-2](./ch04_2_스크롤과리스트_ListView_GridView.md)와 같은 문법) |

### 새로 나온 위젯/문법 4개

**① `MediaQuery.of(context).size.width` — 화면 크기 알아내기**
```dart
MediaQuery.of(context).size.width    // 화면 너비
MediaQuery.of(context).size.height   // 화면 높이
```
기기마다 화면 크기가 다르므로 **고정 숫자(300) 대신 화면 비율**로 잡는 게 안전합니다.
```dart
width: MediaQuery.of(context).size.width * 0.9,   // 화면 너비의 90%
```

**② `BoxDecoration` — Container를 더 꾸미기**
```dart
decoration: BoxDecoration(
  color: Colors.amber,
  borderRadius: BorderRadius.circular(8),
  border: Border.all(color: Colors.grey),
  boxShadow: [BoxShadow(blurRadius: 4, color: Colors.black26)],
)
```
> ⚠️ `Container`에 `color:`와 `decoration:`을 **같이 쓰면 에러**입니다. `decoration`을 쓸 때는 색도 그 안에 넣습니다.

**③ `ClipRRect` — 모서리 둥글게 자르기**
```dart
ClipRRect(
  borderRadius: BorderRadius.circular(8.0),
  child: Image.asset(path),
)
```
이미지는 `borderRadius`를 직접 못 받으므로 **자르는 위젯으로 감쌉니다.** 원형으로 자르려면 `ClipOval`.

**④ `Builder` — context를 새로 만들어주는 위젯**
`MediaQuery.of(context)`가 필요해서 썼습니다. 이 예제에서는 `items`를 만드는 함수에 이미 context가 있어도 되는데, 패키지 예제 코드의 관례를 따른 것입니다. **없어도 대부분 동작합니다.**

---

## 7. buildTop — Row + Column 격자 만들기

```dart
Row(
  //모든 자식 사이의 간격을 균등하게 정렬
  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
  children: [
    GestureDetector(
      onTap: (){ print('클릭'); },
      child: Column(children:[Icon(Icons.local_taxi, size: 40,), Text('택시')],),
    ),
    Column(children: [Icon(Icons.local_taxi, size: 40,), Text('블랙')],),
    ...
  ],
),
```

### ★ Row 안에 Column — 2차원 격자의 기본
```
Row  ───────────────────────────▶ (가로로 나열)
 │  Column    Column    Column
 ▼   아이콘     아이콘     아이콘      (각 칸은 세로로 쌓기)
     글자      글자      글자
```

`GridView`를 쓰지 않고 **`Row` + `Column` 조합으로도 격자**를 만들 수 있습니다. 칸 수가 고정이고 적을 때는 이게 더 간단합니다.

| 기법 | 쓰임 |
|---|---|
| `mainAxisAlignment: spaceEvenly` | 아이콘 4개를 가로로 균등 분배 ([ch04-1](./ch04_1_레이아웃_Container와Column_Row_Stack.md)) |
| `GestureDetector`로 감싸기 | 아이콘+글자 묶음을 누르게 ([ch05-3](./ch05_3_터치감지_GestureDetector_InkWell.md)) |
| `Opacity(opacity: 0.5, child: ...)` | **반투명** 처리 — "준비중" 메뉴 표현 |

```dart
Opacity(
  opacity: 0.5,          // 0.0(투명) ~ 1.0(불투명)
  child: Column(children: [Icon(...), Text('바이크')],),
)
```

> 예제는 4개 중 1개만 `GestureDetector`로 감쌌습니다. 나머지는 눌러도 반응이 없습니다 — 실제로 만들 때는 전부 감싸거나, 아예 메뉴 하나를 위젯 클래스로 만들어 재사용하는 게 맞습니다.

---

## 8. ★★ 스크롤 안에 스크롤 — shrinkWrap / physics

```dart
Widget buildBottom() {
  final items = List.generate(10, (i){
    return ListTile(
      leading: Icon(Icons.notifications_none),
      title: Text('[이벤트] 이것은 공지사항입니다'),
    );
  });

  return ListView(
    //상위 위젯이 스크롤 기능이 있으면 자신은 스크롤 비활성화
    physics: NeverScrollableScrollPhysics(),
    shrinkWrap: true, //상위 위젯과 크기 동일하게 세팅
    children: items,
  );
}
```

### 문제 상황
`Page1`의 `build()`가 이미 `ListView`인데, 그 안에 또 `ListView`가 들어갑니다.

```
ListView            ← 바깥 (스크롤 O)
└── ListView        ← 안쪽 ... 높이가 무한? 스크롤이 둘? → 에러!
```

그냥 넣으면 이런 에러가 납니다:
```
Vertical viewport was given unbounded height
```

### 해결 — 안쪽 ListView에 2가지 옵션

| 옵션 | 의미 |
|---|---|
| `shrinkWrap: true` | **자식 크기만큼만 높이를 차지**해라 (무한 높이 금지) |
| `physics: NeverScrollableScrollPhysics()` | **스크롤하지 마라** (바깥 스크롤에 맡김) |

둘을 같이 줘야 "스크롤은 바깥이 담당하고, 안쪽은 그냥 줄줄이 나열만" 하는 상태가 됩니다.

> **규칙으로 외우기: 스크롤 위젯 안에 스크롤 위젯을 넣으면 안쪽에 `shrinkWrap: true` + `physics: NeverScrollableScrollPhysics()`.**

### `List.generate`로 위젯 리스트 만들기
```dart
final items = List.generate(10, (i){
  return ListTile(...);       // 10개의 ListTile
});
```
[ch04-2](./ch04_2_스크롤과리스트_ListView_GridView.md)에서 배운 `List.generate`를 **위젯 생성**에 쓴 것입니다. 실제 앱에서는 이 자리에 서버에서 받은 데이터가 들어갑니다.

> 지금은 10개 모두 같은 내용입니다. `Text('[이벤트] 공지 ${i+1}')`처럼 `i`를 쓰면 구분됩니다.

---

## 9. 이 예제에서 배운 것 정리

| 기법 | 코드 |
|---|---|
| build 쪼개기 | `Widget buildTop(){...}` → `children: [buildTop(), ...]` |
| 하단 탭 상태 관리 | `_index` + `_pages[_index]` + `onTap: setState` |
| AppBar 오른쪽 버튼 | `actions: [IconButton(...)]` |
| 앱 전체 AppBar 색 | `theme: ThemeData(appBarTheme: ...)` |
| 외부 패키지 | `pubspec.yaml` → `flutter pub get` → `import` |
| 화면 크기 | `MediaQuery.of(context).size.width` |
| 모서리 둥글게 | `ClipRRect(borderRadius: ...)` |
| 반투명 | `Opacity(opacity: 0.5, ...)` |
| 격자 (칸 수 고정) | `Row` + `Column` 조합 |
| 스크롤 안 스크롤 | `shrinkWrap: true` + `NeverScrollableScrollPhysics()` |

### 복잡한 화면을 만드는 순서 (실무 접근법)
1. 화면을 **덩어리로 나눈다** (상단/중단/하단)
2. 덩어리마다 `buildXxx()` 메소드를 **빈 Container로** 만들어 자리부터 잡는다
3. 한 덩어리씩 채운다 — Hot Reload로 바로 확인하면서
4. 덩어리가 커지면 **별도 위젯 클래스**로 독립시킨다
