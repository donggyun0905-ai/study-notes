# 99. 화면 꾸미기 — Tailwind CSS & 다크모드 토글

수업 내용은 아니지만, Team 화면들을 꾸미면서 실무에서 바로 쓸 수 있는 기법들을 따로 정리했습니다.

---

## 1. Tailwind CSS — 빌드 없이 바로 쓰기 (Play CDN)

원래 Tailwind는 npm으로 설치하고 빌드 과정을 거치는 게 정석이지만, **학습/프로토타입 단계**에서는 CDN 스크립트 한 줄로 바로 씁니다.

```html
<head>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
```

이후 아무 태그에나 클래스만 붙이면 바로 적용됩니다:
```html
<div class="bg-white rounded-lg shadow p-5">
    <button class="px-4 py-2 rounded bg-blue-600 text-white hover:bg-blue-700">저장</button>
</div>
```

> **주의**: 이 스크립트는 페이지가 열릴 때마다 CDN에서 Tailwind 엔진을 통째로 내려받아 그 자리에서 CSS를 생성합니다. 연습/과제용으론 충분하지만, 실제 서비스 배포용으로는 `npm install tailwindcss`로 제대로 빌드하는 게 맞습니다 (훨씬 빠르고, 오프라인에서도 동작).

### 자주 쓰는 클래스 감 잡기

| 목적 | 클래스 예 |
|---|---|
| 카드형 박스 | `bg-white rounded-lg shadow` |
| 여백 | `px-4`(좌우) `py-2`(상하) `p-5`(전체) `mt-4`(위쪽 바깥) |
| 배치 | `flex items-center gap-2` (가로 정렬 + 세로 중앙 + 사이 간격) |
| 글자 | `text-sm text-gray-500 font-medium` |
| 버튼 색 | `bg-blue-600 hover:bg-blue-700 text-white` |
| 테두리/포커스 | `border border-gray-300 focus:ring-2 focus:ring-blue-400` |

## 2. 모든 페이지에 공통 요소 끼워넣기 — `<%@ include %>`

버튼 하나를 모든 페이지에 추가해야 할 때, 페이지마다 다 타이핑하는 대신 **조각 파일 하나 + include 한 줄**로 처리합니다.

```
src/main/webapp/common/themeToggle.jspf   ← 버튼 + CSS + JS 전부 들어있는 조각
```

각 페이지의 `</body>` 바로 앞에 한 줄만 추가:
```jsp
<%@ include file="/common/themeToggle.jspf" %>
</body>
```

`file="/..."`처럼 **슬래시로 시작**하면 어느 하위 폴더(`ch05`, `ch09` 등)에 있는 페이지든 똑같이 `/common/themeToggle.jspf`를 찾아갑니다 — 웹앱 루트 기준 절대경로라서 상대경로 계산할 필요가 없습니다.

### 공통 조각(`.jspf`) 만들 때 잊기 쉬운 것 — 인코딩 설정

`web.xml`의 UTF-8 설정이 `*.jsp`에만 걸려있으면 `.jspf` 확장자는 그 규칙을 안 타서 **한글/이모지가 깨집니다.**

```xml
<jsp-property-group>
  <url-pattern>*.jsp</url-pattern>
  <url-pattern>*.jspf</url-pattern>   <%-- 이 줄 빠뜻리면 조각 파일만 인코딩이 깨짐 --%>
  <page-encoding>UTF-8</page-encoding>
</jsp-property-group>
```

## 3. 레거시 페이지까지 한꺼번에 다크모드로 — CSS `filter: invert()` 트릭

페이지마다 색 입히는 방식이 제각각(`bgcolor`, `<font>`, Tailwind 등)일 때, 하나하나 다크 버전을 새로 만드는 대신 **문서 전체의 색을 반전**시켜서 일괄 처리하는 방법:

```css
html[data-theme="dark"] {
    filter: invert(1) hue-rotate(180deg);   /* 전체 반전 */
}
html[data-theme="dark"] img,
html[data-theme="dark"] video {
    filter: invert(1) hue-rotate(180deg);   /* 이미지/영상은 한 번 더 반전 → 원래 색 유지 */
}
```

```javascript
function toggleTheme() {
    var html = document.documentElement;
    var isDark = html.getAttribute('data-theme') === 'dark';
    if (isDark) {
        html.removeAttribute('data-theme');
        localStorage.setItem('site-theme', 'light');
    } else {
        html.setAttribute('data-theme', 'dark');
        localStorage.setItem('site-theme', 'dark');
    }
}
```

- `localStorage`에 선택을 저장해두면 **다른 페이지로 이동해도 테마가 유지**됩니다 (페이지 로드 시 저장된 값을 읽어서 다시 적용).
- 이 방법의 장점: 페이지를 하나도 안 고쳐도 전체가 "다크모드처럼" 보임. 단점: 색을 세세하게 디자인한 건 아니라서 정교한 다크모드는 아님(임시방편에 가까움). 제대로 하려면 Tailwind의 `dark:` 접두사 클래스(`dark:bg-gray-900` 등)로 각 요소 색을 따로 지정하는 게 정석입니다.
