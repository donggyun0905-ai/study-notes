# ch04-2. 스크롤과 리스트 — ListView / GridView

> 예제: `ch04/lib/main4.dart`(SingleChildScrollView), `main5.dart`(ListView+ListTile), `main6.dart`(GridView)

---

## 1. Column은 스크롤이 안 된다

```dart
Column(children: [ /* 100개 */ ])   // → 화면 넘어가면 overflow 경고(노란 줄무늬)
```

**`Column`은 스크롤 기능이 없습니다.** 항목이 화면보다 많으면 잘립니다. 스크롤이 필요하면 아래 3가지 중 하나를 씁니다.

| 위젯 | 언제 |
|---|---|
| `SingleChildScrollView` | 기존 `Column`을 그대로 스크롤 가능하게 만들 때 |
| `ListView` | 세로 목록 전용. **가장 많이 씀** |
| `GridView` | 격자(2열, 3열) 배치 |

---

## 2. SingleChildScrollView + ListBody — main4.dart

```dart
class MyHomePage extends StatelessWidget{
  //0~99까지 번호를 리스트로 만듬
  final items = List.generate(100, (i) => i).toList();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Subject'),),
      body: SingleChildScrollView(
        child: ListBody(
          //map 함수 : 반복되는 값을 다른 형태로 리턴
          children: items.map((i) => Text('$i')).toList(),
        ),
      ),
    );
  }
}
```

### Dart 문법 2개가 핵심입니다

#### ① `List.generate(100, (i) => i)` — 반복으로 리스트 만들기
```dart
List.generate(개수, (인덱스) => 만들값)
```
| 코드 | 결과 |
|---|---|
| `List.generate(5, (i) => i)` | `[0, 1, 2, 3, 4]` |
| `List.generate(5, (i) => i * 10)` | `[0, 10, 20, 30, 40]` |
| `List.generate(3, (i) => '항목${i+1}')` | `['항목1','항목2','항목3']` |

Java의 `for(int i=0;i<100;i++) list.add(i);`를 한 줄로 쓴 것입니다.

#### ② `.map()` — 리스트의 모든 값을 다른 형태로 바꾸기 ★
```dart
items.map((i) => Text('$i')).toList()
//    │         └ 각 값을 Text 위젯으로 변환
//    └ 모든 값에 대해 반복
```

```
[0, 1, 2, ...]  ──map──▶  [Text('0'), Text('1'), Text('2'), ...]
   숫자 리스트              위젯 리스트
```

**`.toList()`를 꼭 붙여야 합니다.** `map()`의 결과는 `Iterable`이고 `children:`은 `List`를 요구하기 때문입니다. 빠뜨리면 타입 에러가 납니다.

#### `'$i'` — 문자열 보간
```dart
Text('$i')              // 변수 값을 문자열에 끼워넣기
Text('번호: $i')
Text('${i + 1}번')      // 식을 넣을 땐 ${ } 로 감싼다
```
JSP의 `<%= i %>`와 같은 역할입니다.

> `ListBody`는 자식을 세로로 나열하되 스크롤 영역 안에서 쓰기 좋게 만든 위젯입니다. `Column`으로 바꿔도 대부분 동작합니다.

---

## 3. ListView + ListTile — main5.dart ★ 실무에서 가장 많이 씀

```dart
body: ListView(
  children: [
    ListTile(
      leading: Icon(Icons.home),              // 왼쪽 아이콘
      title: Text('Home'),                    // 제목
      trailing: Icon(Icons.navigate_next),    // 오른쪽 아이콘
      //농도 : 50,100,200,300,400,500,600,700,800,900
      tileColor: Colors.yellow[900],           // 배경색
      onTap:(){},                              // 탭했을 때
    ),
    ListTile(leading: Icon(Icons.event),  title: Text('Event'),  trailing: Icon(Icons.navigate_next),),
    ListTile(leading: Icon(Icons.camera), title: Text('Camera'), trailing: Icon(Icons.navigate_next),),
  ],
),
```

### ListTile의 구조 — 목록 한 줄을 미리 만들어둔 위젯

```
┌──────────────────────────────────────────┐
│ [leading]  title                [trailing] │
│            subtitle                        │
└──────────────────────────────────────────┘
```

| 속성 | 위치/역할 |
|---|---|
| `leading` | 왼쪽 (주로 아이콘, 프로필 사진) |
| `title` | 제목 (필수에 가까움) |
| `subtitle` | 제목 아래 작은 글씨 |
| `trailing` | 오른쪽 (화살표, 스위치, 체크박스) |
| `onTap` | 한 줄 전체를 탭했을 때 실행 |
| `tileColor` | 배경색 |

> **ch05에서 재등장**: 라디오 버튼을 `ListTile`의 `leading`에 넣으면 "글자를 눌러도 선택되는" 목록이 됩니다.

### 색 농도 — `Colors.yellow[900]`
```dart
Colors.yellow        // 기본
Colors.yellow[50]    // 가장 연함
Colors.yellow[900]   // 가장 진함
```
농도 값은 **50, 100, 200, 300, ... 900** 만 쓸 수 있습니다. 숫자가 클수록 진합니다.

> `Colors.red`처럼 `[ ]` 없이 쓰면 `[500]`과 같습니다. 단 `Colors.black`/`white`는 농도가 없고 `black54` 같은 투명도 변형을 씁니다.

### ListView vs ListView.builder
| | 언제 | 특징 |
|---|---|---|
| `ListView(children:[...])` | 항목이 **적고 고정**일 때 | 전부 한 번에 만듦 |
| `ListView.builder(itemCount:, itemBuilder:)` | 항목이 **많거나 DB에서 가져올** 때 | 화면에 보이는 것만 만듦 (성능 ↑) |

항목이 수백 개면 `builder`를 써야 합니다 (아직 안 배웠지만 다음 단계).

---

## 4. GridView.count — main6.dart

```dart
//GridView : 2차원 격자형 배치, 스크롤이 가능한 위젯
body: GridView.count(
    crossAxisCount: 2,     //열의 개수
    children: [
      Container(color: Colors.cyan,  ...),
      Container(color: Colors.green, ...),
      Container(color: Colors.amber, ...),
    ],
),
```

| 속성 | 설명 |
|---|---|
| `crossAxisCount` | **열(세로줄) 개수**. 2면 2열 격자 |
| `mainAxisSpacing` | 행 사이 간격 |
| `crossAxisSpacing` | 열 사이 간격 |
| `childAspectRatio` | 칸의 가로/세로 비율 (1.0이면 정사각형) |

### ★ 주의: GridView 안에서는 width/height가 무시된다
예제에서 `width: 100, height: 100`을 줬지만 **적용되지 않습니다.** 칸 크기는 `crossAxisCount`와 화면 너비로 GridView가 계산하기 때문입니다.
칸 모양을 조절하려면 `childAspectRatio`를 씁니다.

```dart
GridView.count(
  crossAxisCount: 2,
  childAspectRatio: 1.0,      // 정사각형
  mainAxisSpacing: 8,
  crossAxisSpacing: 8,
  children: [...],
)
```

---

## 5. 정리 — 어떤 걸 쓸까

| 상황 | 위젯 |
|---|---|
| 항목 적고 스크롤 불필요 | `Column` |
| 기존 Column을 스크롤만 되게 | `SingleChildScrollView(child: Column(...))` |
| 세로 목록 (메뉴, 게시글) | `ListView` + `ListTile` |
| 항목이 수백 개 / DB 데이터 | `ListView.builder` |
| 2열·3열 격자 (사진첩, 상품) | `GridView.count` |

**스크롤 위젯 중첩 주의**: `ListView` 안에 `ListView`를 넣으면 "스크롤 영역이 무한" 에러가 납니다. 안쪽에 `shrinkWrap: true`, `physics: NeverScrollableScrollPhysics()`를 줘야 합니다.
