# ch05-3. 아무 위젯이나 누를 수 있게 — GestureDetector / InkWell

> 예제: `ch05/lib/main7.dart`

---

## 1. 왜 필요한가

`Text`, `Icon`, `Container`, `Image`는 **`onPressed`가 없습니다.** 버튼이 아니니까요.

```dart
Text('클릭 Me!!', onPressed: ...)   // ❌ 그런 속성 없음
```

하지만 "글자를 누르면 동작하게" 하고 싶은 경우가 많습니다. 그때 **터치를 감지하는 위젯으로 감싸면** 됩니다.

| 위젯 | 역할 |
|---|---|
| `GestureDetector` | 터치 감지 (**시각 효과 없음**) |
| `InkWell` | 터치 감지 + **물결 효과(ripple)** |

---

## 2. 전체 코드 — main7.dart

```dart
body: Center(
  child: Column(
    mainAxisAlignment: MainAxisAlignment.center,
    children: [
      GestureDetector(
        onTap: (){
          print('GestureDetector 클릭됨....');
        },
        child: Text('클릭 Me!!', style: TextStyle(fontSize: 40),),
      ),
      SizedBox(height: 50,),
      InkWell(
        onTap: (){
          print('InkWell 클릭됨....');
        },
        child: Text('클릭2 Me!!', style: TextStyle(fontSize: 40),),
      )
    ],
  ),
)
```

### 구조는 둘 다 똑같습니다

```dart
감지위젯(
  onTap: (){ /* 할 일 */ },
  child: 누르게 만들 위젯,      // ← 감쌀 대상
)
```

**"감싸기(wrapping)"가 Flutter의 기본 사고방식**입니다. 기능을 추가하려면 상속이 아니라 **바깥에 위젯을 하나 더 두르는** 식입니다.

> `Center`로 가운데 정렬, `Padding`으로 여백, `GestureDetector`로 터치 — 모두 같은 "감싸기" 패턴입니다. Android Studio에서 `Alt + Enter` → `Wrap with widget...`으로 빠르게 감쌀 수 있습니다.

---

## 3. GestureDetector vs InkWell ★

| | GestureDetector | InkWell |
|---|---|---|
| 터치 감지 | ✅ | ✅ |
| **누를 때 시각 효과** | ❌ 아무 반응 없음 | ✅ 물결(ripple) 퍼짐 |
| 감지 가능한 동작 | **매우 많음** (드래그, 확대, 길게누르기…) | 탭 관련 위주 |
| 필요 조건 | 없음 | **`Material` 위젯 안**에 있어야 함 |

### 실행해보면 차이가 보입니다
- 위쪽 '클릭 Me!!' → 눌러도 아무 변화 없이 콘솔에만 찍힘
- 아래쪽 '클릭2 Me!!' → **회색 물결이 퍼짐** + 콘솔 출력

### ★ 어느 쪽을 쓸까

**사용자가 누르는 UI라면 `InkWell`이 기본값입니다.** 눌렀다는 피드백이 없으면 사용자는 "먹통인가?" 하고 또 누릅니다.

`GestureDetector`는 이럴 때 씁니다:
- 터치 효과가 **없어야** 할 때 (예: 배경 탭으로 키보드 내리기)
- `onTap` 외의 제스처가 필요할 때 (스와이프, 확대/축소)

### InkWell의 "Material 안에 있어야 함"

물결 효과는 Material 디자인 기능이라 **`Material` 위젯이 조상에 있어야** 보입니다.
`Scaffold`가 이미 `Material`을 포함하므로 보통은 신경 쓸 필요 없습니다. 하지만 `Container`에 색을 칠한 위에 올리면 물결이 색 밑에 깔려 안 보일 수 있습니다. 그럴 때는:

```dart
Material(
  color: Colors.amber,              // 색은 Material이 칠하고
  child: InkWell(
    onTap: (){},
    child: Padding(padding: EdgeInsets.all(16), child: Text('누르기')),
  ),
)
```

---

## 4. GestureDetector가 감지할 수 있는 동작들

```dart
GestureDetector(
  onTap: (){},              // 한 번 탭
  onDoubleTap: (){},        // 두 번 탭
  onLongPress: (){},        // 길게 누르기
  onTapDown: (details){},   // 손가락이 닿은 순간
  onTapUp: (details){},     // 손가락을 뗀 순간
  onTapCancel: (){},        // 누르다가 밖으로 끌어서 취소
  onPanUpdate: (details){}, // 드래그 중 (details.delta 로 이동량)
  onScaleUpdate: (details){},// 두 손가락 확대/축소
  child: ...,
)
```

| 콜백 | 쓰임새 |
|---|---|
| `onTap` | 가장 많이 씀 |
| `onLongPress` | 목록에서 "꾹 눌러 삭제 메뉴" |
| `onDoubleTap` | 좋아요(인스타그램), 지도 확대 |
| `onPanUpdate` | 드래그로 위젯 옮기기 |

`InkWell`도 `onTap` / `onDoubleTap` / `onLongPress` 는 지원합니다.

---

## 5. ★ 터치 영역 함정 — 글자 크기만큼만 눌린다

```dart
GestureDetector(
  onTap: (){},
  child: Text('클릭 Me!!'),   // ← 글자가 차지하는 영역만 터치됨
)
```

글자 주변 빈 공간은 **안 눌립니다.** 터치 영역을 넓히는 방법:

```dart
// ① Padding으로 여백을 안쪽에 주기 (여백도 터치 영역이 됨)
InkWell(
  onTap: (){},
  child: Padding(
    padding: EdgeInsets.all(16),
    child: Text('클릭 Me!!'),
  ),
)

// ② Container로 크기 지정
GestureDetector(
  onTap: (){},
  child: Container(
    width: 200, height: 60,
    color: Colors.transparent,         // ★ 투명이라도 색을 주면 터치됨
    child: Center(child: Text('클릭')),
  ),
)
```

> **색이 아예 없는(`null`) Container는 터치가 통과**해버립니다. 투명하게 두되 터치는 받으려면 `color: Colors.transparent`를 주거나, `GestureDetector`에 `behavior: HitTestBehavior.opaque`를 줍니다.

---

## 6. 정리 — 이렇게 써야 한다

| 하고 싶은 것 | 코드 |
|---|---|
| 글자/아이콘을 누르게 | `InkWell(onTap: (){}, child: Text(...))` |
| 효과 없이 터치만 감지 | `GestureDetector(onTap: (){}, child: ...)` |
| 꾹 누르기 메뉴 | `onLongPress: (){}` |
| 터치 영역 넓히기 | `child`를 `Padding`이나 크기 있는 `Container`로 |
| 목록 한 줄 전체를 누르게 | `ListTile(onTap: (){})` ← 이미 내장 ([ch04-2](./ch04_2_스크롤과리스트_ListView_GridView.md)) |

### 버튼 vs 감지 위젯

| 상황 | 쓸 것 |
|---|---|
| 평범한 버튼 | `ElevatedButton` / `TextButton` ([ch04-4](./ch04_4_기본위젯_버튼_Text_Icon.md)) |
| 아이콘 버튼 | `IconButton` |
| 목록 한 줄 | `ListTile(onTap:)` |
| **그 외 아무 위젯** | **`InkWell`** |

> ch06의 `carousel_page.dart`에서 아이콘+글자 묶음(`Column`)을 `GestureDetector`로 감싸 "택시 메뉴"를 만드는 걸 볼 수 있습니다 → [ch06-2](./ch06_2_복잡한UI_BottomNav_외부패키지.md)
