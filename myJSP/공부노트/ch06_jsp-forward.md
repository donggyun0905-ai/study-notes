# ch06. 액션 태그 — `<jsp:forward>`

---

## 1. 액션 태그란

`<jsp:xxx>` 형태의 태그. JSP가 제공하는 **미리 만들어진 기능**을 태그 문법으로 쓰는 것.
`taglib` 선언 없이 바로 쓸 수 있음 (`jsp:` 네임스페이스는 기본 내장).

| 태그 | 기능 |
|---|---|
| `<jsp:include>` | 다른 페이지를 현재 위치에 끼워넣기 |
| `<jsp:forward>` | 다른 페이지로 제어를 넘기기 |
| `<jsp:param>` | include/forward 할 때 파라미터 추가 |
| `<jsp:useBean>` `<jsp:setProperty>` `<jsp:getProperty>` | JavaBean 관련 (→ ch09 노트) |

---

## 2. 페이지 이동 3가지 방식 — 반드시 구분

| | `<jsp:include>` | `<jsp:forward>` | `response.sendRedirect()` |
|---|---|---|---|
| **한 줄 요약** | 남의 결과를 **끼워넣고 돌아옴** | **넘어가고 안 돌아옴** | 브라우저에게 "저기로 다시 요청해" |
| request 객체 | 같이 넘어감 (공유) | 같이 넘어감 (공유) | **안 넘어감** (완전 새 요청) |
| 주소창 URL | 그대로 | 그대로 | **바뀜** |
| 서버 왕복 횟수 | 1번 | 1번 | **2번** (응답 → 재요청) |
| 처리 위치 | 서버 내부 | 서버 내부 | 브라우저 경유 |
| 쓰는 상황 | 헤더/푸터 공통 조각 | 폼 처리 후 결과 페이지로 (같은 요청 안에서) | 처리 완료 후 목록으로 (요청 분리) |

### 그림으로

```
[include]   A.jsp ── 실행중 ──▶ B.jsp 실행 ──▶ 결과를 A에 삽입 ──▶ A 계속 실행
[forward]   A.jsp ── 실행중 ──▶ B.jsp 로 완전히 넘김 (A는 여기서 끝)
[redirect]  A.jsp ──▶ 브라우저에 "B로 가라" 응답 ──▶ 브라우저가 B를 새로 요청
```

### 핵심 판단 기준
- **request에 담긴 값(폼 파라미터)을 다음 페이지에서도 써야 한다** → `forward` 또는 `include`
- **처리 끝났고 깔끔하게 목록 화면으로 보내고 싶다 (새로고침해도 재전송 안 되게)** → `sendRedirect`

---

## 3. 예제 A — forwardTag1 (컨트롤러 패턴)

### 구조
```
forwardTag1.html      →  forwardTag1_1.jsp   →  forwardTag1_2.jsp
(아이디/비번 입력 폼)    (받아서 검증/처리만)     (실제 화면 출력)
     POST                  화면 출력 없음           클라이언트로 응답
```

### forwardTag1.html — 입력 폼
```html
<form method="post" action="forwardTag1_1.jsp">
  아이디 : <input name="id" value="aaa">
  패스워드 : <input type="password" name="pwd" value="1234">
  <input type="submit" value="보내기">
</form>
```

### forwardTag1_1.jsp — "컨트롤러" 역할
```jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String id = request.getParameter("id");
    String pwd = request.getParameter("pwd");
%>
<%-- forward는 화면 표시 없이 Control(제어) 역할만 함 --%>
<%-- include처럼 request(요청 바구니)도 그대로 같이 넘어감 --%>
<jsp:forward page="forwardTag1_2.jsp"/>
```

**포인트**
- 이 페이지는 HTML을 거의 안 찍음 → "화면"이 아니라 "처리 담당"
- `<jsp:forward>` 위에 있는 스크립트릿에서 검증/가공을 하고, 최종 화면은 다른 파일에 맡김
- 이게 **컨트롤러 / 뷰 분리**의 기초

### forwardTag1_2.jsp — 최종 화면
```jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String id = request.getParameter("id");    // forward로 넘어왔지만 request가 같이 와서 됨
    String pwd = request.getParameter("pwd");
%>
<%-- 이 페이지가 client로 최종 응답 --%>
id: <%=id%> / pwd: <%=pwd%>
```

**포인트**
- `forwardTag1_1.jsp`에서 넘어왔는데도 `request.getParameter("id")`가 값을 가져옴
- → **forward는 request를 공유**한다는 증거
- 만약 `sendRedirect`였다면 여기서 `id`, `pwd`는 전부 `null`

---

## 4. 예제 B — forwardTag2 / includeTag3 (`<jsp:param>` + 동적 page)

### 구조
```
forwardTag2.html   →  forwardTag2_1.jsp  →  A.jsp 또는 B.jsp 또는 O.jsp 또는 AB.jsp
(혈액형 라디오)       (분기 + 이름 추가)      (혈액형별 결과)
```

### forwardTag2_1.jsp
```jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String bloodType = request.getParameter("bloodType");   // "A", "B", "O", "AB"
    String name = "홍길동";
%>
<jsp:forward page='<%= bloodType + ".jsp" %>'>       <%-- ① 동적 page --%>
    <jsp:param value="<%=name%>" name="name"/>        <%-- ② 파라미터 추가 --%>
</jsp:forward>
```

**① 동적 page 지정**
- `page='<%= bloodType + ".jsp" %>'`
- `bloodType`이 `"A"`면 → `page="A.jsp"`, `"AB"`면 → `page="AB.jsp"`
- 태그 속성값 안에 표현식 `<%= %>`을 넣을 수 있음
- 작은따옴표 `'`로 감싼 이유: 표현식 안에 큰따옴표 `"`가 들어가므로 (`".jsp"`)

**② `<jsp:param>` — 파라미터 추가 전달**
- forward/include 하면서 원래 request에 **없던 값을 추가로** 실어보냄
- 넘어간 페이지(`A.jsp` 등)에서 `request.getParameter("name")` → `"홍길동"`

### includeTag3.jsp — 같은 원리, include 버전
```jsp
<%
    String bloodType = request.getParameter("bloodType");
    String name = "기안84";
%>
<jsp:include page='<%=bloodType+".jsp"%>'>
    <jsp:param value="<%=name%>" name="name"/>
</jsp:include>
```
- `forward`와 문법 똑같음. 차이는 "끼워넣고 돌아오느냐(include) / 넘어가고 끝이냐(forward)"

---

## 5. 이렇게 써야 한다 — 템플릿

### 폼 처리 → 결과 화면 (같은 요청 유지)
```jsp
<%-- proc.jsp : 처리 담당 --%>
<%
    // 1. 파라미터 받기
    String x = request.getParameter("x");
    // 2. 검증 / 가공 / (DB 작업)
    // 3. 결과 화면으로 제어 넘기기
%>
<jsp:forward page="result.jsp"/>
```

### 처리 완료 → 목록으로 (요청 분리, 새로고침 안전)
```jsp
<%
    // DB insert/update/delete 완료
    response.sendRedirect("list.jsp");   // 브라우저가 list.jsp를 새로 요청
%>
```
> INSERT 후 `forward`로 목록을 보여주면, 사용자가 새로고침할 때 **INSERT가 다시 실행**됨(중복 저장). 그래서 쓰기 작업 후에는 `sendRedirect`가 정석.

### 공통 조각 재사용
```jsp
<jsp:include page="top.jsp"/>
<h1>본문</h1>
<jsp:include page="bottom.jsp"/>
```

---

## 6. 자주 하는 실수

| 실수 | 결과 |
|---|---|
| 쓰기 작업(INSERT) 후 `forward`로 목록 표시 | 새로고침 시 중복 저장 → `sendRedirect` 써야 함 |
| `sendRedirect` 후 `request.getParameter()` 기대 | 항상 `null` (request 안 넘어감) |
| `page="<%=x%>"` 처럼 큰따옴표로 감쌈 | 표현식 안 `"`와 충돌 → 작은따옴표 `'`로 |
| 라디오/체크박스 선택 안 하고 제출 | `bloodType`이 `null` → `null + ".jsp"` = `"null.jsp"` → 404 |
