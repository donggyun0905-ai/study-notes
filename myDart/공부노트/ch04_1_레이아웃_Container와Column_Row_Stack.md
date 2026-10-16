# ch04-1. 레이아웃 — Container와 Column/Row/Stack

> 예제: `ch04/lib/main.dart`(Container), `main1.dart`(Column), `main2.dart`(Row), `main3.dart`(Stack)

---

## 1. Container — 모든 박스의 기본

**색·크기·여백을 가진 상자.** HTML의 `<div>`에 해당합니다.

```dart
Container(
  color: Colors.red,
  width: 100,
  height: 100,
  //EdgeInsets.all: 상 하 좌 우 전부 같은 값
  padding: const EdgeInsets.all(8.0),   // 안쪽 여백
  margin: EdgeInsets.all(8.0),          // 바깥쪽 여백
)
```

### padding vs margin ★

```
        ┌─────────────────────┐
        │   margin (바깥 여백)  │   ← 다른 위젯과의 거리
        │  ┌───────────────┐  │
        │  │ padding(안쪽) │  │   ← 테두리와 내용물의 거리
        │  │   ┌───────┐   │  │
        │  │   │ child │   │  │
        │  │   └───────┘   │  │
        │  └───────────────┘  │
        └─────────────────────┘
```

| 속성 | 의미 | 색이 칠해지나 |
|---|---|---|
| `padding` | 상자 **안쪽** 여백 | ✅ 칠해짐 (상자 내부) |
| `margin` | 상자 **바깥쪽** 여백 | ❌ 안 칠해짐 (투명) |

> 자식(`child`)이 없으면 `padding`은 눈에 안 보입니다. ch04 예제들은 색 블록만 보려고 `child` 없이 씁니다.

### EdgeInsets 4종

| 생성자 | 의미 |
|---|---|
| `EdgeInsets.all(8)` | 상하좌우 전부 8 |
| `EdgeInsets.symmetric(vertical: 8, horizontal: 16)` | 위아래 8, 좌우 16 |
| `EdgeInsets.only(left: 8, top: 4)` | 지정한 쪽만 |
| `EdgeInsets.fromLTRB(1,2,3,4)` | 좌·상·우·하 순서 |

### `const`는 왜 붙이나
`const EdgeInsets.all(8.0)` — 값이 절대 안 바뀌는 객체라고 알려주면 Flutter가 **재사용**해서 성능이 좋아집니다. 안 붙여도 동작은 같습니다.

---

## 2. 레이아웃 3형제 ★

| 위젯 | 배치 방향 | 비유 |
|---|---|---|
| **Column** | ↓ 세로로 쌓기 | 책을 위로 쌓기 |
| **Row** | → 가로로 나열 | 책을 책장에 세우기 |
| **Stack** | 겹쳐 쌓기 (z축) | 종이를 겹쳐 놓기 |

세 위젯 모두 자식이 여러 개라서 **`children: [ ... ]`** 를 씁니다 (`child` 아님!).

### Column — main1.dart
```dart
body: Column(
  children: [
    Container(color: Colors.red,   width:100, height:100, ...),
    Container(color: Colors.green, width:100, height:100, ...),
    Container(color: Colors.blue,  width:100, height:100, ...),
  ],
),
```
→ 빨강·초록·파랑이 **위에서 아래로** 쌓입니다.

### Row — main2.dart
```dart
body: Row(
  mainAxisSize: MainAxisSize.max,                   //가로로 꽉 채우기
  crossAxisAlignment: CrossAxisAlignment.center,    //세로방향 가운데 정렬
  children: [ ... ],
),
```
→ 주황·갈색·하늘색이 **왼쪽에서 오른쪽으로** 나열됩니다.

### Stack — main3.dart
```dart
body: Stack(
  children: [
    Container(color: Colors.orange, width:100, height:100, ...),  // 맨 아래
    Container(color: Colors.brown,  width:90,  height:90,  ...),
    Container(color: Colors.cyan,   width:80,  height:80,  ...),  // 맨 위
  ],
),
```
→ **리스트 순서가 쌓이는 순서**입니다. 나중에 쓴 것이 위로 올라옵니다.
크기를 100→90→80으로 줄여서 아래 것이 테두리처럼 보이게 한 예제입니다.

---

## 3. mainAxis vs crossAxis ★ (가장 헷갈리는 부분)

**주축(main)과 교차축(cross)은 위젯에 따라 방향이 뒤바뀝니다.**

| | Column | Row |
|---|---|---|
| **mainAxis** (주축) | ↕ 세로 | ↔ 가로 |
| **crossAxis** (교차축) | ↔ 가로 | ↕ 세로 |

> 외우는 법: **주축 = 자식이 나열되는 방향**. Column은 세로로 쌓이니 주축이 세로.

### 속성 3개

| 속성 | 하는 일 | 값 예시 |
|---|---|---|
| `mainAxisSize` | 주축 방향으로 **얼마나 차지할지** | `max`(꽉), `min`(자식만큼) |
| `mainAxisAlignment` | 주축 방향 **정렬** | `start`, `center`, `end`, `spaceBetween`, `spaceEvenly`, `spaceAround` |
| `crossAxisAlignment` | 교차축 방향 **정렬** | `start`, `center`, `end`, `stretch` |

### 가운데 정렬하는 법
```dart
Column(
  mainAxisAlignment: MainAxisAlignment.center,    // 세로 가운데
  crossAxisAlignment: CrossAxisAlignment.center,  // 가로 가운데
  children: [...],
)
```

> 자식이 하나면 `Center(child: ...)` 가 훨씬 간단합니다. 여러 개를 가운데 모을 때 위 방식을 씁니다.

### spaceBetween / spaceEvenly / spaceAround 차이
```
spaceBetween :  [A]        [B]        [C]      ← 양끝 붙이고 사이만 균등
spaceEvenly  :    [A]    [B]    [C]            ← 간격 전부 균등
spaceAround  :   [A]     [B]     [C]           ← 양끝은 절반 간격
```

---

## 4. 자주 만나는 에러

### ① "RenderFlex overflowed by N pixels" (노란/검정 줄무늬)
자식의 총 크기가 화면보다 커서 넘침.

| 해결 | 방법 |
|---|---|
| 스크롤 가능하게 | `SingleChildScrollView`로 감싸기 → [ch04-2](./ch04_2_스크롤과리스트_ListView_GridView.md) |
| 남은 공간을 나눠 쓰게 | 자식을 `Expanded(child: ...)`로 감싸기 |
| 넘치면 줄바꿈 | `Column`/`Row` 대신 `Wrap` 사용 |

### ② Column 안의 Row가 화면을 다 먹음
`Row`의 기본 `mainAxisSize`는 `max`라서 가로를 전부 차지합니다. 자식만큼만 쓰려면 `MainAxisSize.min`.

---

## 5. 이렇게 써야 한다

| 하고 싶은 것 | 쓸 위젯 |
|---|---|
| 색/크기/여백 있는 박스 | `Container` |
| 위에서 아래로 나열 | `Column` |
| 왼쪽에서 오른쪽으로 나열 | `Row` |
| 이미지 위에 글자 겹치기 | `Stack` |
| 자식 하나를 가운데 | `Center` |
| 여백만 주고 싶다 | `Padding` 또는 `SizedBox(height: 20)` |
| 항목이 많아 스크롤 필요 | `ListView` → ch04-2 |

> **팁**: `Ctrl + D`로 줄 복제하면 `Container` 여러 개를 빠르게 만들 수 있습니다. `Alt + Enter` → `Wrap with ...`로 기존 위젯을 `Center`나 `Padding`으로 감쌀 수 있습니다.
