# GuestBook 프로젝트 (3) — GuestBookMgr 완성 & 로그인 흐름

---

## 1. GuestBookMgr 전체 메소드 정리

| 메소드 | 역할 | SQL |
|---|---|---|
| `loginJoin(id, pwd)` | 로그인 인증 (있으면 true) | `select id from tblJoin where id=? and pwd=?` |
| `getJoin(id)` | 회원 정보 1건 조회 | `select * from tblJoin where id=?` |
| `listGuestBooks(id, grade)` | 글 목록 (권한별로 다르게) | 아래 참고 |
| `insertGuestBook(bean)` | 글쓰기 | `insert tblGuestBook(...) values(...)` |
| `getGuestBook(num)` | 글 1건 상세 조회 | `select * from tblGuestBook where num=?` |
| `updateGuestBook(bean)` | 글 수정 | `update tblGuestBook set ... where num=?` |
| `deleteGuestBook(num)` | 글 삭제 | `delete from tblGuestBook where num=?` |

전부 ch09_2에서 배운 **JDBC 4단계 패턴**(연결→준비→실행→반납) 그대로입니다.

## 2. 오늘 이 파일 완성하면서 잡은 버그들

### ① `getJoin()` — 타입 불일치
```java
// 틀림
bean.setGrade(rs.getString(6));   // grade는 char(2) 컬럼이라 String으로 나옴

// 맞음
bean.setGrade("1".equals(rs.getString(6)));   // JoinBean.setGrade는 boolean만 받음
```
`JoinBean.grade` 필드가 `boolean`인데, DB `grade` 컬럼은 `char(2)`(`"0"`/`"1"`)라서 **DB에서 나온 문자열을 boolean으로 직접 변환**해야 합니다. `"1".equals(문자열)`이 가장 안전한 변환 방법입니다(`문자열.equals("1")`로 쓰면 문자열이 `null`일 때 예외가 나서, 상수를 앞에 두는 `"1".equals(...)` 순서가 더 안전).

### ② `listGuestBooks()` — 컬럼명 오타
```java
// 틀림
SDF_DATE.format(rs.getDate("regData"))   // 실제 컬럼: regdate

// 맞음
SDF_DATE.format(rs.getDate("regdate"))
```
`regData`(대문자 D, "Data") ≠ `regdate`("date") — 그냥 대소문자 차이가 아니라 **철자 자체가 다른** 오타라서, 실행하면 "그런 컬럼 없다"는 에러가 납니다.

### ③ `insertGuestBook()` — SQL 오타 + 잘못된 실행 메소드
```java
// 틀림
sql = "insert tblGuestBook(...) valuest(?,?,?,now(),now(),?)";   // "valuest" 오타
rs = pstmt.executeQuery();                                        // INSERT인데 executeQuery

// 맞음
sql = "insert tblGuestBook(...) values(?,?,?,now(),now(),?)";
pstmt.executeUpdate();
```

**`executeQuery()`는 SELECT 전용, `executeUpdate()`는 INSERT/UPDATE/DELETE 전용**입니다(ch09_2에서 배운 내용). INSERT에 `executeQuery()`를 쓰면 "이 문장은 결과 집합을 리턴 안 한다"는 예외가 납니다.

### ④ `getGuestBook()` — 메소드 몸통 밖으로 코드가 샘 (가장 심각)
```java
// 틀림 — 메소드가 {}로 바로 닫히고, 그 아래 코드가 클래스 레벨에 그냥 나열되어 있었음
public GuestBookBean getGuestBook(int num){}
Connection con = null;
...
```
중괄호 `{}`를 실수로 바로 닫아버려서, 그 아래 작성한 코드가 **메소드 안이 아니라 클래스 몸통에 그냥 떠 있는 상태**였습니다. 자바에서 클래스 몸통엔 필드 선언이나 메소드만 올 수 있고 `try`문 같은 실행 코드는 못 들어가서 **컴파일 자체가 안 되는 상태**였습니다. 중괄호 위치를 맞춰서 전부 메소드 안으로 넣어 해결했습니다.

> **중괄호 위치는 항상 짝을 눈으로 확인하는 습관이 중요합니다.** IntelliJ에서 여는 중괄호에 커서를 놓으면 짝이 되는 닫는 중괄호가 강조 표시됩니다.

## 3. 로그인 처리 흐름 — loginProc.jsp

```jsp
<jsp:useBean id="mgr" class="guestbook.GuestBookMgr"/>
<jsp:useBean id="login" class="guestbook.JoinBean"/>
<jsp:setProperty property="*" name="login"/>
<%
    boolean result = mgr.loginJoin(login.getId(), login.getPwd());
    if(result){
        login = mgr.getJoin(login.getId());          // 회원 상세정보 다시 조회
        session.setAttribute("idKey", login.getId()); // ch07/ch12에서 배운 로그인 체크용 키
        session.setAttribute("login", login);          // 회원 정보 통째로 세션에 저장
        response.sendRedirect(url);
    }else{
        response.sendRedirect("login.jsp?url=" + url);
    }
%>
```

**포인트**
- `<jsp:setProperty property="*" name="login"/>` — ch09_1에서 배운 자동 바인딩. 폼의 `id`, `pwd`가 `login` 빈에 자동으로 담김
- `mgr.loginJoin(...)`으로 **진짜 DB 대조** (ch12의 "잘못된 예제"와 다른 점)
- 로그인 성공하면 `getJoin()`을 **한 번 더** 호출해서 이름·이메일 등 상세정보까지 채운 뒤 세션에 저장 — `login.jsp` 화면에서 `<%=login.getName()%>`으로 이름을 보여줄 수 있는 이유
- `session.setAttribute("idKey", ...)` 와 `session.setAttribute("login", ...)` **둘 다** 저장 — `idKey`는 "로그인했는지 여부"만 빠르게 확인용, `login`은 이름 등 상세정보가 필요할 때 씀

## 4. login.jsp — 로그인 상태에 따라 다른 화면

```jsp
<jsp:useBean id="login" class="guestbook.JoinBean" scope="session"/>
<%
    String id = (String)session.getAttribute("idKey");
%>
<% if(id != null){ %>
    <b><%=login.getName()%></b>님 환영합니다.
    <a href="showGuestBook.jsp">방명록</a> <a href="logout.jsp">로그아웃</a>
<% }else{ %>
    <%-- 로그인 폼 --%>
<% } %>
```

`scope="session"`으로 `useBean`을 쓰면, `loginProc.jsp`에서 세션에 저장해둔 `login`(JoinBean)을 **같은 이름으로 그대로 재사용**합니다(ch09_4 Scope 노트에서 배운 "session scope는 같은 id면 재사용" 원리 그대로).

## 5. logout.jsp

```jsp
<%
    session.invalidate();
%>
<script>
    alert('로그아웃 되었습니다.');
    location.href="login.jsp";
</script>
```
ch12에서 배운 `session.invalidate()` 그대로. 다만 페이지 이동을 `response.sendRedirect()`가 아니라 `alert + location.href`로 하고 있는데, 이건 ch12_2에서 "부차적인 문제"로 짚었던 옛날 방식입니다 — 동작은 하지만, 사용자에게 알림을 보여준 뒤 이동시키고 싶을 때 쓰는 절충안 정도로 이해하면 됩니다.

## 6. 실제 확인해본 테스트 계정

`table.sql`로 만든 `tblJoin`에 이미 들어있는 데이터:

| id | pwd | grade |
|---|---|---|
| aaa | 1234 | 0 (일반회원) |
| admin | 1234 | 1 (관리자) |
| bbb | 1234 | 0 |
| ccc | 1234 | 0 |

`aaa/1234`로 로그인 → "홍길동님 환영합니다" 정상 확인됨.
