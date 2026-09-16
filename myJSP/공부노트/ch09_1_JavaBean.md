# ch09-1. JavaBean & 빈 액션 태그

---

## 1. JavaBean이란

데이터를 담아서 이 페이지 → 저 페이지, 자바 → JSP 로 **나르는 그릇** 역할의 클래스.
"빈(Bean)"이라 부르려면 아래 **규칙 3가지**를 지켜야 함.

### 규칙 3가지

| 규칙 | 이유 |
|---|---|
| 1. `public` 기본 생성자(매개변수 없는 것) 필요 | `<jsp:useBean>`이 `new Bean()`으로 만들기 때문 |
| 2. 필드는 `private` | 캡슐화 (직접 접근 막고 메소드로만) |
| 3. 필드마다 `public` getter/setter | `getXxx()` / `setXxx()` — 외부에서 값 읽고 쓰는 통로 |

### 예 — SimpleBean.java
```java
package ch09;

public class SimpleBean {
    private String msg;          // 규칙 2: private
    private int cnt;

    // 규칙 3: getter / setter
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public int getCnt() { return cnt; }
    public void setCnt(int cnt) { this.cnt = cnt; }
}
```
> 기본 생성자를 아예 안 쓰면 자바가 자동으로 만들어 줌 (규칙 1 자동 충족).
> 하지만 **매개변수 있는 생성자를 하나라도 직접 만들면**, 기본 생성자는 자동 생성이 안 됨 → 그땐 직접 추가해야 함.

---

## 2. 생성자 함정 (오늘 실수)

```java
// ❌ 틀림 — 이건 생성자가 아니라 "TeamBean이라는 이름의 메소드"
public void TeamBean(int num, String name, ...) { ... }

// ✅ 맞음 — 생성자는 반환타입(void 포함)이 없어야 함
public TeamBean(int num, String name, ...) { ... }
```

- `void`가 붙는 순간 → 그냥 메소드. 클래스명과 이름만 같은 것.
- `new TeamBean(1, "홍길동", ...)` 이 "매칭되는 생성자 없음" 에러 나던 이유.
- 기본 생성자 + 전체 매개변수 생성자, **둘 다** 만들어두는 게 안전:
```java
public TeamBean() {}                                   // 기본 (jsp:useBean 용)
public TeamBean(int num, String name, ...) { ... }     // 전체 (DAO에서 new 할 때 편함)
```

---

## 3. 빈 액션 태그 3형제

| 태그 | 하는 일 | 자바로 치면 |
|---|---|---|
| `<jsp:useBean id="bean" class="ch09.SimpleBean"/>` | 빈 객체 생성 (없으면 new, 있으면 재사용) | `SimpleBean bean = new SimpleBean();` |
| `<jsp:setProperty name="bean" property="msg"/>` | 폼 파라미터 `msg` → `bean.setMsg()` 자동 호출 | `bean.setMsg(request.getParameter("msg"));` |
| `<jsp:getProperty name="bean" property="msg"/>` | `bean.getMsg()` 결과를 화면에 출력 | `<%= bean.getMsg() %>` |

### `<jsp:setProperty>` 3가지 형태

```jsp
<%-- ① 특정 필드 = 같은 이름의 파라미터 --%>
<jsp:setProperty name="bean" property="msg"/>

<%-- ② 특정 필드 = 지정한 파라미터 --%>
<jsp:setProperty name="bean" property="msg" param="message"/>

<%-- ③ 모든 필드 = 이름 맞는 모든 파라미터 (제일 많이 씀) --%>
<jsp:setProperty name="bean" property="*"/>
```

### `property="*"` — 자동 일괄 매핑

- 폼 필드 `name`과 빈의 `setXxx` 이름이 맞으면 **한 줄로 전부 세팅**
- **조건**: `<input name="msg">` ↔ `setMsg()`, `<input name="cnt">` ↔ `setCnt()` — 이름 일치
- **타입 변환 자동**: `cnt`는 문자열로 오지만 `setCnt(int)` 에 맞게 int로 변환해줌
- 이름이 안 맞는 파라미터는 무시됨

---

## 4. 같은 일 두 가지 방법 — simpleBean1 vs simpleBean2

### 흐름
```
simpleBean.html
  ├─ Send1 →  simpleBean1.jsp  (수동: request.getParameter + new + setter)
  └─ Send2 →  simpleBean2.jsp  (액션태그: useBean + setProperty property="*")
```

### simpleBean1.jsp — 수동 (길다)
```jsp
<%@ page import="ch09.MUtil" %>
<%@ page import="ch09.SimpleBean" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String msg = request.getParameter("msg");
    int cnt = MUtil.parseInt(request, "cnt");       // 문자열 → int 직접 변환

    SimpleBean bean = new SimpleBean();             // 직접 new
    bean.setMsg(msg);                               // 직접 set
    bean.setCnt(cnt);
%>
<h3>SimpleBean1</h3>
msg: <%=bean.getMsg()%><br>
cnt: <%=bean.getCnt()%><br>
```

### simpleBean2.jsp — 액션 태그 (짧다)
```jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="bean" class="ch09.SimpleBean"/>
<jsp:setProperty name="bean" property="*"/>        <%-- 폼 값 전부 자동 세팅 --%>
<h3>SimpleBean2</h3>
msg: <jsp:getProperty name="bean" property="msg"/><br>
cnt: <jsp:getProperty name="bean" property="cnt"/><br>
```

### 비교

| | simpleBean1 (수동) | simpleBean2 (액션태그) |
|---|---|---|
| 코드 길이 | 길다 | 짧다 |
| import | 필요 | 불필요 |
| 파라미터 → 필드 | 한 개씩 `getParameter` + `setter` | `property="*"` 한 줄 |
| 타입 변환 | 직접 (`parseInt`) | 자동 |
| 필드 많아지면 | 줄 수가 계속 늘어남 | 그대로 한 줄 |

→ **필드 이름과 폼 name을 맞춰놓고 `property="*"` 쓰는 게 실무 기본.**

---

## 5. 한글 깨짐 주의

`<jsp:setProperty>`는 내부적으로 `request.getParameter`를 씀.
POST 방식 한글이 깨진다면 setProperty **이전에** 인코딩 설정이 되어 있어야 함.
이 프로젝트는 `web.xml`에 `<request-character-encoding>UTF-8</...>` 이 있어서 자동 처리됨.
(예전 방식이면 `<% request.setCharacterEncoding("UTF-8"); %>` 을 맨 위에)

---

## 6. 이렇게 써야 한다 — 템플릿

### 폼 값을 빈에 담아 DAO로 넘기기 (가장 흔한 패턴)
```jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="mgr"  class="ch09.TeamMgr"/>      <%-- DAO --%>
<jsp:useBean id="bean" class="ch09.TeamBean"/>     <%-- DTO --%>
<jsp:setProperty property="*" name="bean"/>        <%-- 폼 → DTO 자동 --%>
<%
    mgr.insertTeam(bean);                          <%-- DTO를 DAO로 --%>
    response.sendRedirect("teamList.jsp");
%>
```

### 조회 결과 빈을 화면에 뿌리기
```jsp
<jsp:useBean id="bean" class="ch09.TeamBean"/>
...
이름: <jsp:getProperty name="bean" property="name"/>
<%-- 또는 스크립트릿: <%= bean.getName() %> --%>
```

---

## 7. 용어 정리

| 용어 | 뜻 | 이 프로젝트에서 |
|---|---|---|
| DTO (Data Transfer Object) | 데이터를 담아 나르는 객체 | `SimpleBean`, `TeamBean` |
| DAO (Data Access Object) | DB 접근(CRUD)을 담당하는 객체 | `TeamMgr`, `MysqlMgr`, `OracleMgr` |
| VO (Value Object) | DTO와 비슷 (값 자체를 표현) | — |
| Bean | 위 규칙 3가지 지킨 클래스 (DTO도 보통 빈) | `*Bean.java` |
