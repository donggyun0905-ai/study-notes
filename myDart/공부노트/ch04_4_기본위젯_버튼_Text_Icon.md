# ch04-4. 기본 위젯 — 버튼 / Text / Icon / Image

> 예제: `ch04/lib/main10.dart`(버튼), `main11.dart`(Text), `main12.dart`(Icon·Image)

---

## 1. 버튼 3종 — main10.dart

```dart
body: Center(
    //child: ElevatedButton(onPressed: (){}, child: Text('ElevatedButton')),
    //child: TextButton(onPressed: (){}, child: Text('TextButton')),
    child: IconButton(
      //onPressed: (){ print('Icon 버튼이 클릭되었습니다.'); },
      onPressed: iconButtonOnPressed,
      icon: Icon(Icons.add),
      color: Colors.red,
      iconSize: 100.0,
    ),
)
```

| 버튼 | 모양 | 자식 속성 |
|---|---|---|
| `ElevatedButton` | 배경색 + 그림자 (**입체 버튼**) | `child:` |
| `TextButton` | 배경 없는 글자만 | `child:` |
| `OutlinedButton` | 테두리만 있는 버튼 | `child:` |
| `IconButton` | 아이콘만 | **`icon:`** (child 아님!) |
| `FloatingActionButton` | 우하단 둥근 버튼 | `child:` |

> **`IconButton`만 `icon:`을 씁니다.** 나머지는 `child:`. 헷갈려서 `child: Icon(...)`을 쓰면 에러가 납니다.

### `onPressed`는 필수다
```dart
ElevatedButton(onPressed: (){}, child: Text('버튼'))   // 빈 함수라도 넣어야 함
ElevatedButton(onPressed: null, child: Text('버튼'))   // null을 주면 "비활성(회색) 버튼"
```
`onPressed`를 빼면 컴파일 에러입니다. `null`을 주는 건 **의도적으로 버튼을 비활성화**하는 문법입니다.

---

## 2. ★ onPressed에 함수를 넘기는 3가지 방법

이 예제의 진짜 핵심입니다.

```dart
// ① 익명 함수 — 짧은 코드일 때
onPressed: (){ print('클릭!'); },

// ② 함수 이름만 넘기기 — 코드가 길 때 (예제 방식)
onPressed: iconButtonOnPressed,

// ③ 람다 한 줄
onPressed: () => print('클릭!'),
```

```dart
void iconButtonOnPressed(){
  print('Icon 버튼이 클릭되었습니다.');
}
```

### ★★ `iconButtonOnPressed` 와 `iconButtonOnPressed()` 의 차이

| 코드 | 의미 | 결과 |
|---|---|---|
| `onPressed: iconButtonOnPressed` | 함수 **자체**를 넘김 | ✅ 누를 때 실행됨 |
| `onPressed: iconButtonOnPressed()` | 함수를 **지금 실행**하고 결과(void)를 넘김 | ❌ 에러 + 화면 뜰 때 바로 실행됨 |

**괄호를 붙이면 안 됩니다.** Dart에서 함수는 값처럼 주고받을 수 있고(1급 객체), `onPressed`는 "나중에 실행할 함수"를 원하기 때문입니다.

> 이 패턴은 ch05 `main4.dart`에서 `onPressed: download` 로 다시 씁니다.

### `print()`는 어디에 찍히나
Android Studio 하단 **Run / Debug Console**에 출력됩니다. 화면에는 안 나옵니다. 값 확인용 디버깅 수단입니다.
화면에 알림을 띄우려면 `SnackBar`를 씁니다.

---

## 3. Text + TextStyle — main11.dart

```dart
child: Text(
    'Hello Flutter',
    style: TextStyle(
      fontSize: 40.0,
      fontStyle: FontStyle.italic,      // 기울임
      fontWeight: FontWeight.bold,      // 굵게
      color: Colors.brown,
      letterSpacing: 4.0,               // 글자 사이 간격
    ),
),
```

| 속성 | 값 | 설명 |
|---|---|---|
| `fontSize` | `40.0` | 글자 크기 |
| `color` | `Colors.brown` | 글자색 |
| `fontWeight` | `bold`, `w100`~`w900`, `normal` | 굵기 |
| `fontStyle` | `italic`, `normal` | 기울임 |
| `letterSpacing` | `4.0` | 자간 (음수도 가능) |
| `height` | `1.5` | 행간 (배수) |
| `decoration` | `TextDecoration.underline` | 밑줄/취소선 |

### Text 위젯 자체의 속성 (style 밖)
```dart
Text(
  '긴 글...',
  textAlign: TextAlign.center,       // 정렬
  maxLines: 2,                        // 최대 줄 수
  overflow: TextOverflow.ellipsis,    // 넘치면 ... 으로
)
```

### 테마 글자 스타일 쓰기
직접 크기를 정하는 대신 앱 테마의 스타일을 쓸 수도 있습니다 (ch03 카운터 예제에 나온 방식):
```dart
Text('$_counter', style: Theme.of(context).textTheme.headlineMedium)
```

---

## 4. Icon / Image — main12.dart

```dart
child: Icon(Icons.home, color: Colors.green, size: 100.0,)
//child: Image.asset('assets/sample.jpg'),
```

### Icon — 설치 없이 바로 쓸 수 있다
```dart
Icon(Icons.home, color: Colors.green, size: 100.0)
```
Material 아이콘 수천 개가 기본 내장이라 **추가 설정 없이** 바로 씁니다.
아이콘 이름 찾기: [fonts.google.com/icons](https://fonts.google.com/icons) 또는 `Icons.` 까지 입력하면 자동완성 목록이 뜸.

| 자주 쓰는 아이콘 | |
|---|---|
| `Icons.home` `Icons.person` `Icons.settings` | 메뉴 |
| `Icons.add` `Icons.remove` `Icons.edit` `Icons.delete` | 동작 |
| `Icons.navigate_next` `Icons.arrow_back` | 이동 |
| `Icons.favorite` `Icons.star` `Icons.search` | 기타 |

### ★ Image.asset은 pubspec.yaml 등록이 필수

예제에서 주석 처리된 이유가 이것입니다. 이미지는 3단계가 필요합니다:

**① 프로젝트 루트에 폴더 만들고 파일 넣기**
```
ch04\
├── assets\
│   └── sample.jpg
├── lib\
└── pubspec.yaml
```

**② `pubspec.yaml`에 등록** — 들여쓰기가 매우 중요합니다
```yaml
flutter:
  uses-material-design: true

  assets:
    - assets/sample.jpg      # 파일 하나씩
    # - assets/              # 폴더 전체 (끝에 / 필수)
```
> YAML은 **공백 들여쓰기**만 허용합니다. 탭(Tab)을 쓰면 에러. `assets:`는 `flutter:` 아래 2칸, 파일 목록은 4칸.

**③ `flutter pub get` 실행** 후 **Hot Restart** (Reload로는 반영 안 됨)

### 이미지 종류
| 코드 | 출처 |
|---|---|
| `Image.asset('assets/a.jpg')` | 앱에 포함된 파일 |
| `Image.network('https://...')` | 인터넷 URL (설정 불필요) |
| `Image.file(File(path))` | 기기 저장소 파일 |

### 자주 쓰는 속성
```dart
Image.asset(
  'assets/sample.jpg',
  width: 200,
  height: 200,
  fit: BoxFit.cover,     // 비율 유지하며 꽉 채우기(넘치는 부분 잘림)
)
```
| `fit` 값 | 동작 |
|---|---|
| `cover` | 비율 유지 + 꽉 채움 (잘림) — 가장 많이 씀 |
| `contain` | 비율 유지 + 전체 보임 (여백 생김) |
| `fill` | 비율 무시하고 꽉 채움 (찌그러짐) |

---

## 5. 정리

| 하고 싶은 것 | 코드 |
|---|---|
| 일반 버튼 | `ElevatedButton(onPressed: (){}, child: Text('확인'))` |
| 아이콘 버튼 | `IconButton(onPressed: (){}, icon: Icon(Icons.add))` |
| 긴 처리를 함수로 분리 | `onPressed: 함수이름` (**괄호 없이!**) |
| 글자 꾸미기 | `Text('글', style: TextStyle(...))` |
| 아이콘 | `Icon(Icons.home, size: 100, color: Colors.green)` |
| 이미지 | `Image.asset(...)` + **pubspec.yaml 등록** |
| 콘솔 출력 확인 | `print()` → Run 탭 |
