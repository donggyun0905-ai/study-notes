# ch09-4. JavaBean의 scope — 값이 어디까지 살아있나

---

## 1. scope란

`<jsp:useBean>`으로 만든 빈이 **얼마나 오래, 누구와 공유되며 살아있는지**를 정하는 속성.

```jsp
<jsp:useBean id="이름" scope="범위" class="클래스"/>
```

| scope | 살아있는 기간 | 공유 범위 |
|---|---|---|
| `page` (기본값) | 이 JSP 하나 처리하는 동안만 | 아무와도 안 공유 |
| `request` | 요청 하나가 끝날 때까지 | forward/include로 넘어간 페이지끼리 공유 |
| `session` | 세션이 유지되는 동안 (보통 브라우저 닫기 전까지) | 같은 사용자의 모든 페이지가 공유 |
| `application` | **서버가 꺼질 때까지** | 접속한 **모든 사용자**가 공유 |

범위가 넓어질수록(page→application) **더 오래, 더 많은 사람과** 값이 공유됩니다.

## 2. 공통 메소드 — 4가지 scope 객체가 전부 똑같이 가짐

| 메소드 | 용도 |
|---|---|
| `setAttribute("key", value)` | 값 저장 |
| `getAttribute("key")` | 값 꺼내기 (`Object` 반환 → 캐스팅 필요) |
| `removeAttribute("key")` | 값 제거 |

`request`, `session`, `application` 내장객체에 전부 이 3개가 똑같이 있습니다. `<jsp:useBean scope="...">`은 내부적으로 이 메소드들을 호출해주는 것뿐입니다.

## 3. 예제 — scopeBean1.jsp

```jsp
<jsp:useBean id="pBean" scope="page" class="ch09.ScopeBean"/>
<jsp:useBean id="sBean" scope="session" class="ch09.ScopeBean"/>
<%
    ScopeBean sBean2 = new ScopeBean();
    session.setAttribute("sBean2", sBean2);   // useBean 없이 직접 세션에 저장하는 법
%>
<jsp:setProperty name="pBean" property="num" value="<%=pBean.getNum() + 10%>"/>
<jsp:setProperty name="sBean" property="num" value="<%=sBean.getNum() + 10%>"/>

pBean num값: <jsp:getProperty name="pBean" property="num"/><br>
sBean num값: <jsp:getProperty name="sBean" property="num"/><br>
```

### 핵심 실험 — 새로고침을 여러 번 눌러보면

| | pBean (`page`) | sBean (`session`) |
|---|---|---|
| 새로고침마다 | **항상 0부터 시작** → `+10` = 항상 10 | **이전 값에 이어서** `+10` 누적 (10, 20, 30...) |
| 왜? | 매 요청마다 새로 `new` 됨 | 세션에 저장된 **같은 객체를 재사용** |

> **`<jsp:useBean>`의 진짜 동작**: "해당 scope에 같은 id로 저장된 객체가 **있으면 재사용**, 없으면 새로 `new`"입니다. `session` scope라서 두 번째 방문부터는 `new`를 안 하고 세션에 있던 걸 그대로 꺼내 쓰는 겁니다 — 그래서 값이 누적됩니다.

## 4. 예제 — scopeBean2.jsp (세션 값 지우기)

```jsp
<%
    // 세션에서 "sBean" 이름의 값만 하나 제거
    session.removeAttribute("sBean");

    // 세션 자체를 통째로 폐기 (로그아웃과 같은 동작)
    // → 새 세션이 생성되고, 새 세션ID가 클라이언트로 전송됨
    session.invalidate();

    response.sendRedirect("scopeBean1.jsp");
%>
```

| 메소드 | 효과 |
|---|---|
| `session.removeAttribute("key")` | 그 **키 하나만** 삭제. 세션 자체는 유지 |
| `session.invalidate()` | 세션 **전체**를 폐기. 그 세션에 저장된 모든 값이 다 날아감 |

> 이 파일에서 `removeAttribute("sBean")`을 먼저 부른 다음 바로 `invalidate()`를 부르는 건 사실 **의미 없는 중복**입니다 — `invalidate()` 한 줄이 이미 `sBean`을 포함한 세션 전체를 지우기 때문입니다. `removeAttribute`는 "특정 값 하나만 지우고 세션은 유지"하고 싶을 때 쓰는 것이고, 이 예제는 둘의 차이를 보여주려고 일부러 같이 써둔 것으로 보입니다.

## 5. 이렇게 써야 한다

### 장바구니처럼 "이 사용자"에게만 필요한 값 → session
```jsp
<jsp:useBean id="cart" scope="session" class="shop.CartBean"/>
```

### 게시판 조회수처럼 "모든 사용자가 공유"하는 값 → application
```java
Integer count = (Integer) application.getAttribute("viewCount");
if (count == null) count = 0;
application.setAttribute("viewCount", count + 1);
```
> `application`은 서버 전체가 공유하는 값이라, **동시에 여러 사용자가 동시에 고치면 값이 꼬일 수 있음**(동시성 문제) — 실무에서는 이런 공유 카운터를 DB나 별도 동기화 처리로 관리하는 게 안전합니다. 간단한 예제에서만 이렇게 씁니다.

### forward 받는 페이지랑만 값 공유 → request
```jsp
<%-- A.jsp --%>
<jsp:useBean id="data" scope="request" class="x.DataBean"/>
<jsp:forward page="B.jsp"/>
```
```jsp
<%-- B.jsp : forward로 넘어왔으니 request scope 공유됨 --%>
<jsp:getProperty name="data" property="..."/>
```
