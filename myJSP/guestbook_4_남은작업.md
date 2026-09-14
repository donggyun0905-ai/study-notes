# GuestBook 프로젝트 (4) — 아직 안 만든 것

로그인/로그아웃만 완성됐고, 나머지는 페이지 껍데기(`<%@ page %>`만 있고 내용 없음)만 있는 상태입니다. 다음에 채울 순서 가이드입니다.

---

## 1. 전체 미완성 목록

| 파일 | 상태 | 필요한 것 |
|---|---|---|
| `showGuestBook.jsp` | 빈 껍데기 | `mgr.listGuestBooks(id, grade)` 호출해서 목록 출력 |
| `postGuestBook.jsp` | 빈 껍데기 | 글쓰기 입력 폼 |
| `postGuestProc.jsp` | 빈 껍데기 | `mgr.insertGuestBook(bean)` 호출 |
| `updateGuestBook.jsp` | 빈 껍데기 | 수정 폼 (기존 값 채워서) |
| `updateGuestBookProc.jsp` | 빈 껍데기 | `mgr.updateGuestBook(bean)` 호출 |
| `deleteGuestBook.jsp` | 빈 껍데기 | `mgr.deleteGuestBook(num)` 호출 |
| `commentProc.jsp` | 빈 껍데기 | 댓글 저장 처리 |
| `CommentMgr.java` | 클래스만 있고 내용 없음 | 댓글 CRUD 메소드 전체 |

## 2. showGuestBook.jsp — 목록 (제일 먼저 할 것)

ch09_3의 `teamList.jsp`와 거의 같은 구조입니다. 다른 점은 **로그인 여부·권한에 따라 보이는 글이 달라진다**는 것.

```jsp
<%
    String id = (String) session.getAttribute("idKey");
    if(id == null){
        response.sendRedirect("login.jsp");   // ch07에서 배운 로그인 체크 패턴
        return;
    }
    JoinBean login = (JoinBean) session.getAttribute("login");
    String grade = login.isGrade() ? "1" : "0";   // boolean → "0"/"1" 문자열로 (listGuestBooks가 문자열을 받으므로)

    Vector<GuestBookBean> vlist = mgr.listGuestBooks(id, grade);
%>
<% for(GuestBookBean bean : vlist){ %>
    <%=bean.getContents()%> - <%=bean.getRegdate()%>
    <a href="updateGuestBook.jsp?num=<%=bean.getNum()%>">수정</a>
    <a href="deleteGuestBook.jsp?num=<%=bean.getNum()%>">삭제</a>
<% } %>
<a href="postGuestBook.jsp">글쓰기</a>
```

> `JoinBean.isGrade()`가 `boolean`을 돌려주는데, `listGuestBooks(String id, String grade)`는 `String`을 받습니다 — 여기서도 타입을 맞춰 변환해야 합니다(guestbook_2에서 본 것과 같은 종류의 함정).

## 3. postGuestBook.jsp / postGuestProc.jsp — 글쓰기

ch09_3의 `teamInsert.jsp` / `teamInsertProc.jsp` 패턴과 동일합니다.

```jsp
<%-- postGuestBook.jsp : 입력 폼 --%>
<form method="post" action="postGuestProc.jsp">
    <textarea name="contents"></textarea>
    <input type="radio" name="secret" value="0" checked>공개
    <input type="radio" name="secret" value="1">비밀
    <input type="submit" value="등록">
</form>
```

```jsp
<%-- postGuestProc.jsp : 처리 --%>
<jsp:useBean id="mgr" class="guestbook.GuestBookMgr"/>
<jsp:useBean id="bean" class="guestbook.GuestBookBean"/>
<jsp:setProperty property="*" name="bean"/>
<%
    String id = (String) session.getAttribute("idKey");
    if(id == null){ response.sendRedirect("login.jsp"); return; }

    bean.setId(id);                        // 글쓴이는 로그인한 사람으로 강제 (폼에서 안 받음 — 조작 방지)
    bean.setIp(request.getRemoteIP());     // ch07에서 배운 request.getRemoteAddr()
    mgr.insertGuestBook(bean);
    response.sendRedirect("showGuestBook.jsp");
%>
```
> `bean.setId(id)`처럼 **로그인 세션에서 가져온 값을 강제로 덮어쓰는 것이 중요**합니다. 폼의 hidden input 같은 걸로 `id`를 받으면, 사용자가 개발자도구로 값을 조작해서 남의 이름으로 글을 쓸 수 있기 때문입니다.

## 4. updateGuestBook.jsp / updateGuestBookProc.jsp — 수정

ch09_3 `teamUpdate.jsp`/`teamUpdateProc.jsp`와 동일한 패턴 + **본인 글인지 확인하는 절차**가 추가로 필요합니다.

```jsp
<%
    GuestBookBean bean = mgr.getGuestBook(num);
    String id = (String) session.getAttribute("idKey");
    if(!bean.getId().equals(id)){
        // 남의 글을 수정하려는 시도 → 막아야 함
        response.sendRedirect("showGuestBook.jsp");
        return;
    }
%>
```

## 5. deleteGuestBook.jsp — 삭제

ch09_6에서 배운 파라미터 검증 패턴 + 위와 같은 "본인 글인지" 체크가 필요합니다. ch09의 `teamDelete.jsp`가 좋은 참고 예시입니다.

## 6. CommentMgr.java — 댓글 CRUD (아직 시작 전)

`CommentBean`은 이미 완성돼 있으니, `GuestBookMgr`와 똑같은 패턴으로 만들면 됩니다:

```java
public class CommentMgr {
    private DBConnectionMgr pool;

    public CommentMgr() {
        this.pool = DBConnectionMgr.getInstance();   // guestbook_2에서 배운 것 — 잊지 말기!
    }

    // 특정 글(num)에 달린 댓글 전체 조회
    public Vector<CommentBean> listComments(int num) { ... }

    // 댓글 작성
    public void insertComment(CommentBean bean) { ... }

    // 댓글 삭제
    public void deleteComment(int cnum) { ... }
}
```
`table.sql`의 `tblComment` 구조(`cnum`, `num`, `cid`, `comment`, `cip`, `cregDate`)를 그대로 SELECT/INSERT/DELETE 하면 됩니다 — ch09_2의 JDBC 4단계 템플릿 그대로 적용.

## 7. 전체 작업 순서 추천

```
1. showGuestBook.jsp        (목록부터 — 있어야 나머지 링크를 테스트할 수 있음)
2. postGuestBook/Proc.jsp    (글이 있어야 목록에 뭔가 보임)
3. updateGuestBook/Proc.jsp
4. deleteGuestBook.jsp
5. CommentMgr.java + commentProc.jsp  (댓글은 가장 마지막 — 글 기능이 먼저 안정돼야 함)
```
