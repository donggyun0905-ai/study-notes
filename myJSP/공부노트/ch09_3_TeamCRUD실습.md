# ch09-3. Team 게시판 CRUD 실습

`mydb2.tblteam` (num, name, city, age, team) 을 관리하는 화면 모음.
CRUD = **C**reate(추가) / **R**ead(조회) / **U**pdate(수정) / **D**elete(삭제)

---

## 1. 전체 구조

```
                          ┌──────────────────────────┐
                          │   TeamMgr (DAO)          │
                          │   - listTeam()          │──┐
                          │   - insertTeam(bean)    │  │
                          │   - getTeam(num) [예정] │  │  DBConnectionMgr
                          │   - updateTeam() [예정] │  │       │
                          │   - deleteTeam() [예정] │  │       ▼
                          └──────────────────────────┘  │  ┌────────┐
                                     ▲  │ TeamBean       │  │ mydb2  │
                                     │  ▼ (DTO)          └─▶│tblteam │
   ┌──────────────┐   ┌────────────────────────┐            └────────┘
   │ teamInsert   │──▶│ teamInsertProc(2).jsp   │──┐
   │ .html / .jsp │   │ (bean에 담아 insert)    │  │ sendRedirect
   └──────────────┘   └────────────────────────┘  ▼
                                          ┌────────────────┐
                                          │ teamList.jsp   │  전체 목록
                                          └────────────────┘
                                                   │ num 클릭
                                                   ▼
                                          ┌────────────────┐
                                          │ teamRead.jsp   │  상세 (getTeam)
                                          └────────────────┘
                                             │ UPDATE / DELETE 링크 [예정]
```

---

## 2. TeamBean.java — DTO

```java
package ch09;

public class TeamBean {
    private int num;
    private String name;
    private String city;
    private int age;
    private String team;

    public TeamBean() {}                          // 기본 생성자 (jsp:useBean 용)

    public TeamBean(int num, String name, String city, int age, String team) {
        this.num = num;
        this.name = name;
        this.city = city;
        this.age = age;
        this.team = team;
    }

    // getter / setter 5쌍 (num, name, city, age, team)
    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }
    // ... 나머지도 동일 패턴
}
```

**포인트**
- 컬럼 5개 ↔ 필드 5개 1:1
- `TeamMgr.listTeam()`에서 `new TeamBean(rs.getInt(1), ...)` 로 채움 → 전체 생성자 필요
- `<jsp:useBean>` 이 `new TeamBean()` 함 → 기본 생성자 필요
- **`public void TeamBean(...)` 처럼 `void` 붙이면 생성자 아님** (이번에 실수)

---

## 3. TeamMgr.java — DAO (현재 상태)

```java
package ch09;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class TeamMgr {
    private DBConnectionMgr pool;

    public TeamMgr() {
        pool = DBConnectionMgr.getInstance();
    }

    // ===== R: 목록 =====
    public Vector<TeamBean> listTeam() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vector<TeamBean> vlist = new Vector<TeamBean>();
        try {
            con = pool.getConnection();
            String sql = "select * from tblteam";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                vlist.add(new TeamBean(
                    rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getInt(4), rs.getString(5)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);     // SELECT → 3-arg
        }
        return vlist;
    }

    // ===== C: 추가 =====
    public void insertTeam(TeamBean bean) {
        Connection con = null;
        PreparedStatement pstmt = null;              // rs 없음
        try {
            con = pool.getConnection();
            String sql = "insert tblteam values(null, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getCity());
            pstmt.setInt(3, bean.getAge());
            pstmt.setString(4, bean.getTeam());
            pstmt.executeUpdate();                   // INSERT → executeUpdate
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);         // INSERT → 2-arg
        }
    }

    // //read   //update   //delete   //team list  ← 다음에 채울 자리
}
```

### SQL 뜯어보기
- `insert tblteam values(null, ?, ?, ?, ?)`
  - `INTO` 생략됨 — MySQL은 허용 (표준 SQL은 `insert into` 필수)
  - 첫 값 `null` — `num`이 `auto_increment`라 DB가 알아서 채움
  - `?` 4개 = name, city, age, team

---

## 4. teamInsert — 입력 (2가지 처리 방식)

### teamInsert.html / teamInsert.jsp — 입력 폼

`teamInsert.jsp`는 `.html` 버전에 **팀명 드롭다운**(기존 팀 목록)을 추가한 것:
```jsp
<jsp:useBean id="mgr" class="ch09.TeamMgr"/>
<% Vector<String> vlist = mgr.teamList(); %>       <%-- [예정] 팀명 목록 메소드 --%>
...
<select onchange="selectTeam(this.value)">
    <option value="">팀을 선택하세요</option>
    <% for (String team : vlist) { %>
        <option value="<%=team%>"><%=team%></option>
    <% } %>
</select>
```
> ⚠️ 현재 `TeamMgr.teamList()` 메소드가 없어서 `teamInsert.jsp`는 실행 시 에러.
> `teamInsert.html` 은 드롭다운 없이 잘 됨.

폼:
```html
<form name="frm" method="post" action="teamInsertProc.jsp">
  이름   : <input name="name" value="홍길동">
  사는곳 : <input name="city" value="부산">
  나이   : <input name="age" value="27">
  팀명   : <input name="team" value="산적">
  <input type="button" value="SAVE"  onclick="check()">      <%-- → teamInsertProc.jsp --%>
  <input type="button" value="SAVE2" onclick="check2()">     <%-- → teamInsertProc2.jsp --%>
</form>
```
- `check()` : JS로 빈 값 검증 후 submit → `teamInsertProc.jsp`
- `check2()` : `action`을 `teamInsertProc2.jsp`로 바꿔 submit

### teamInsertProc.jsp — 수동 방식
```jsp
<%@ page import="ch09.TeamMgr" %>
<%@ page import="ch09.TeamBean" %>
<%@ page import="ch09.MUtil" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="mgr"  class="ch09.TeamMgr"/>
<jsp:useBean id="bean" class="ch09.TeamBean"/>
<%
    bean.setName(request.getParameter("name"));
    bean.setCity(request.getParameter("city"));
    bean.setAge(MUtil.parseInt(request, "age"));      // 문자열 → int
    bean.setTeam(request.getParameter("team"));

    mgr.insertTeam(bean);
    response.sendRedirect("teamList.jsp");            // 저장 후 목록으로
%>
```

### teamInsertProc2.jsp — 액션 태그 방식 (짧다)
```jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="mgr"  class="ch09.TeamMgr"/>
<jsp:useBean id="bean" class="ch09.TeamBean"/>
<jsp:setProperty property="*" name="bean"/>          <%-- 폼 값 4개 전부 자동 세팅 + 타입변환 --%>
<%
    mgr.insertTeam(bean);
    response.sendRedirect("teamList.jsp");
%>
```

**비교**: `setProperty property="*"` 한 줄이 `getParameter` 4번 + `setter` 4번을 대체.
조건은 폼 `name`(name/city/age/team)과 빈 필드 이름이 일치할 것.

> **왜 `sendRedirect`?** — INSERT 후 `forward`로 목록을 보여주면, 사용자가 새로고침할 때 INSERT가 다시 실행됨(중복 저장). `sendRedirect`는 브라우저가 목록을 새 요청으로 받으므로 안전.

---

## 5. teamList.jsp — 목록 (R)

```jsp
<%@ page import="ch09.TeamMgr" %>
<%@ page import="ch09.TeamBean" %>
<%@ page import="java.util.Vector" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    TeamMgr mgr = new TeamMgr();
    Vector<TeamBean> vlist = mgr.listTeam();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><link href="style.css" rel="stylesheet"></head>
<body>
<div align="center">
<h1>Team List</h1>
<table border="1">
    <tr>
        <td>번호</td><td>이름</td><td>사는곳</td><td>나이</td><td>팀명</td>
    </tr>
<%
    for (TeamBean bean : vlist) {                     // 향상된 for
%>
    <tr>
        <td><a href=""><%=bean.getNum()%></a></td>    <%-- 클릭 → teamRead.jsp?num=... [연결 예정] --%>
        <td><%=bean.getName()%></td>
        <td><%=bean.getCity()%></td>
        <td><%=bean.getAge()%></td>
        <td><%=bean.getTeam()%></td>
    </tr>
<%
    }
%>
</table>
<p>총 <%=vlist.size()%>건</p>
<a href="teamInsert.html">INSERT</a>
</div>
</body>
</html>
```

**포인트**
- `mgr.listTeam()` → `Vector<TeamBean>` 받아서 `for (TeamBean bean : vlist)` 로 뿌림
- 번호에 `<a>` 를 걸어 `teamRead.jsp?num=<%=bean.getNum()%>` 로 연결 예정

---

## 6. teamRead.jsp — 상세 조회 (R) + 파라미터 검증 패턴

```jsp
<%@ page import="ch09.TeamBean" %>
<%@ page import="ch09.MUtil" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="mgr" class="ch09.TeamMgr"/>
<%
    int num = 0;
    TeamBean bean = null;

    if (request.getParameter("num") == null) {              // ① num 없음
        response.sendRedirect("teamList.jsp");
        return;                                             // 아래 코드 실행 막기
    } else if (!MUtil.isNumeric(request.getParameter("num"))) {   // ② 숫자 아님
        response.sendRedirect("teamList.jsp");
        return;
    } else {                                                // ③ 정상
        num = MUtil.parseInt(request, "num");
        bean = mgr.getTeam(num);                            // [예정] 상세 조회
    }
%>
<%-- 표로 bean.getNum(), getName(), getCity(), getAge(), getTeam() 출력 --%>
<a href="teamList.jsp">LIST</a>
<a href="teamUpdate.jsp?num=<%=num%>">UPDATE</a>       <%-- [예정] --%>
<a href="teamDelete.jsp?num=<%=num%>">DELETE</a>       <%-- [예정] --%>
```

### 파라미터 검증 패턴 (외워두면 유용)
```jsp
<%
    if (파라미터 == null) {           response.sendRedirect("목록"); return; }
    else if (형식이 틀림) {           response.sendRedirect("목록"); return; }
    else {                            정상 처리 }
%>
```
- `return;` 이 중요 — 리다이렉트만 하고 아래 코드가 계속 실행되면
  `IllegalStateException: response already committed` 나거나 `bean`이 null인 채로 출력 시도

### MUtil 헬퍼 (ch09/MUtil.java)
```java
public static int parseInt(HttpServletRequest request, String name) {
    return Integer.parseInt(request.getParameter(name));
}
public static boolean isNumeric(String s) {          // 숫자면 true
    try { Integer.parseInt(s); return true; }
    catch (NumberFormatException e) { return false; }
}
```
> `import jakarta.servlet.http.HttpServletRequest;` 필요 (Tomcat 10 = jakarta, javax 아님)

---

## 7. CRUD 정리표

| 기능 | JSP | DAO 메소드 | SQL | execute | freeConnection |
|---|---|---|---|---|---|
| Create | teamInsertProc(2).jsp | `insertTeam(bean)` | `INSERT ... VALUES(null,?,?,?,?)` | `executeUpdate()` | `(con, pstmt)` |
| Read (목록) | teamList.jsp | `listTeam()` | `SELECT * FROM tblteam` | `executeQuery()` | `(con, pstmt, rs)` |
| Read (상세) | teamRead.jsp | `getTeam(num)` | `SELECT * FROM tblteam WHERE num=?` | `executeQuery()` | `(con, pstmt, rs)` |
| Update | teamUpdate.jsp | `updateTeam(bean)` | `UPDATE tblteam SET name=?,... WHERE num=?` | `executeUpdate()` | `(con, pstmt)` |
| Delete | teamDelete.jsp | `deleteTeam(num)` | `DELETE FROM tblteam WHERE num=?` | `executeUpdate()` | `(con, pstmt)` |

---

## 8. 아직 안 만든 것 (다음 수업)

`TeamMgr.java` 하단 주석 자리(`//read //update //delete //team list`)에 채울 것:

### getTeam(int num) — 상세 조회
```java
public TeamBean getTeam(int num) {
    Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
    TeamBean bean = null;
    try {
        con = pool.getConnection();
        pstmt = con.prepareStatement("select * from tblteam where num = ?");
        pstmt.setInt(1, num);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            bean = new TeamBean(rs.getInt(1), rs.getString(2),
                                rs.getString(3), rs.getInt(4), rs.getString(5));
        }
    } catch (Exception e) { e.printStackTrace(); }
    finally { pool.freeConnection(con, pstmt, rs); }
    return bean;
}
```

### teamList() — 팀명 목록 (드롭다운용)
```java
public Vector<String> teamList() {
    Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
    Vector<String> list = new Vector<String>();
    try {
        con = pool.getConnection();
        pstmt = con.prepareStatement("select distinct team from tblteam");
        rs = pstmt.executeQuery();
        while (rs.next()) { list.add(rs.getString(1)); }
    } catch (Exception e) { e.printStackTrace(); }
    finally { pool.freeConnection(con, pstmt, rs); }
    return list;
}
```

### updateTeam / deleteTeam — insertTeam 패턴과 동일 (executeUpdate, 2-arg free)
- `teamUpdate.jsp`, `teamDelete.jsp` 파일도 새로 만들어야 함
