# ch04-3. 화면 전환 — PageView / TabBar / BottomNavigationBar

> 예제: `ch04/lib/main7.dart`(PageView), `main8.dart`(Tab), `main9.dart`(BottomNavigationBar)

---

## 1. 세 가지 비교

| 위젯 | 조작 방법 | 위치 | 대표 앱 |
|---|---|---|---|
| **PageView** | 좌우로 밀기(스와이프) | 화면 전체 | 앱 첫 실행 튜토리얼, 이미지 넘기기 |
| **TabBar** | 상단 탭 터치 (+스와이프) | 앱바 아래 | 카카오톡 친구/채팅, 쇼핑몰 카테고리 |
| **BottomNavigationBar** | 하단 버튼 터치 | 화면 맨 아래 | 인스타그램, 유튜브 |

---

## 2. PageView — main7.dart

```dart
body: PageView(
  children:[
    Container(color: Colors.red,),
    Container(color: Colors.blueGrey,),
    Container(color: Colors.blueAccent,),
  ]
),
```

**이게 전부입니다.** `children`에 넣은 위젯이 좌우 스와이프로 한 장씩 넘어갑니다.

> `Container`에 `width`/`height`를 안 줬더니 **화면 전체를 채웁니다.** Container는 크기 지정이 없고 자식도 없으면 부모가 주는 공간을 전부 차지합니다. (ch04-1 예제에서는 크기를 줬으니 100x100이었음)

### 자주 쓰는 옵션
| 속성 | 설명 |
|---|---|
| `scrollDirection: Axis.vertical` | 위아래로 넘기기 (틱톡 같은 형태) |
| `controller: PageController()` | 코드로 특정 페이지로 이동 |
| `onPageChanged: (index){}` | 페이지가 바뀔 때 호출 (인디케이터 점 표시용) |

---

## 3. TabBar — main8.dart ★ 3개 세트

탭은 **세 위젯이 항상 같이** 있어야 합니다.

```dart
return DefaultTabController(        // ① 탭 상태를 관리하는 컨트롤러
    length: 3,                      //    탭 개수 (반드시 아래 개수와 일치!)
    child: Scaffold(
      appBar: AppBar(
        title: Text('Tab'),
        bottom: TabBar(             // ② 탭 버튼 줄 (앱바 아래)
            tabs:[
              Tab(icon: Icon(Icons.tag_faces),),
              Tab(text: '메뉴2',),
              Tab(icon: Icon(Icons.info), text: '메뉴3',)
            ]),
      ),
      body: TabBarView(             // ③ 탭별 내용
        children: [
          Container(color: Colors.yellow,),
          Container(color: Colors.brown,),
          Container(color: Colors.cyan,),
        ]),
  )
);
```

### 역할 분담
| 위젯 | 역할 |
|---|---|
| `DefaultTabController` | "지금 몇 번째 탭인지"를 기억 + 탭↔내용 연결. **가장 바깥**에 둠 |
| `TabBar` | 탭 버튼들. `AppBar`의 `bottom:` 속성에 넣음 |
| `TabBarView` | 탭에 대응하는 내용. `Scaffold`의 `body:`에 넣음 |

### ★ 숫자 3개가 반드시 일치해야 한다
```
length: 3   ==   tabs: [3개]   ==   children: [3개]
```
하나라도 안 맞으면 **런타임 에러**가 납니다 (컴파일은 통과하므로 더 찾기 어렵습니다).

### Tab 위젯 — 3가지 표기
```dart
Tab(icon: Icon(Icons.tag_faces))              // 아이콘만
Tab(text: '메뉴2')                             // 글자만
Tab(icon: Icon(Icons.info), text: '메뉴3')     // 둘 다 (아이콘 위, 글자 아래)
```
예제가 일부러 세 형태를 하나씩 쓴 것입니다.

> `DefaultTabController` 대신 `TabController`를 직접 만들 수도 있지만, `StatefulWidget` + `with SingleTickerProviderStateMixin`이 필요해 복잡합니다. 탭 전환을 코드로 제어할 필요가 없으면 `DefaultTabController`가 정답입니다.

---

## 4. BottomNavigationBar — main9.dart

```dart
return Scaffold(
  appBar: AppBar(title: Text('Subject'),),
  bottomNavigationBar: BottomNavigationBar(items:[
    BottomNavigationBarItem(icon: Icon(Icons.home),          label:'Home'),
    BottomNavigationBarItem(icon: Icon(Icons.person),        label:'Profile'),
    BottomNavigationBarItem(icon: Icon(Icons.notifications), label:'Notification'),
  ]),
);
```

| 속성 | 설명 |
|---|---|
| `items` | 버튼 목록. **2개 이상 필수** (1개면 에러) |
| `icon` / `label` | 아이콘과 글자. 둘 다 필요 |
| `currentIndex` | 지금 선택된 버튼 번호 (0부터) |
| `onTap: (index){}` | 버튼을 눌렀을 때. index를 받음 |

### ★ 이 예제는 눌러도 아무 일이 안 납니다

`TabBar`와 달리 `BottomNavigationBar`는 **화면을 자동으로 바꿔주지 않습니다.** 직접 상태로 관리해야 합니다:

```dart
class _MyHomePageState extends State<MyHomePage> {

  int selectedIndex = 0;                     // ① 선택된 번호를 상태로
  final pages = [HomePage(), ProfilePage(), NotiPage()];   // ② 화면 목록

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: pages[selectedIndex],            // ③ 번호에 맞는 화면 표시
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: selectedIndex,         // ④ 현재 선택 표시
        onTap: (index){
          setState(() {
            selectedIndex = index;           // ⑤ 번호 바꾸고 다시 그림
          });
        },
        items: [ ... ],
      ),
    );
  }
}
```

**`value`/`onChanged` + `setState` 패턴과 똑같은 구조**입니다 (여기선 이름이 `currentIndex`/`onTap`). ch05의 입력 위젯들과 완전히 같은 원리입니다 → [ch05-1](./ch05_1_입력위젯_Checkbox_Radio_Dropdown.md)

### 아이템이 4개 이상이면
기본 `type`이 `fixed`에서 `shifting`으로 바뀌어 애니메이션이 생기고 색이 달라집니다. 고정하려면:
```dart
type: BottomNavigationBarType.fixed,
```

---

## 5. 정리 — 어떤 걸 쓸까

| 하고 싶은 것 | 위젯 | 상태 관리 |
|---|---|---|
| 좌우로 넘기는 소개 화면 | `PageView` | 자동 |
| 카테고리 탭 (상단) | `DefaultTabController`+`TabBar`+`TabBarView` | **자동** |
| 앱의 메인 메뉴 (하단) | `BottomNavigationBar` | **직접 해야 함** |
| 왼쪽에서 밀려나오는 메뉴 | `Scaffold(drawer: Drawer(...))` | 자동 |

**기억할 차이**: `TabBar`는 `DefaultTabController`가 상태를 알아서 관리해주지만, `BottomNavigationBar`는 `currentIndex`를 내가 `setState`로 바꿔줘야 합니다.
