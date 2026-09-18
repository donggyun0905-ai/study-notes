# GuestBook 프로젝트 (5) — 댓글까지 전체 완성

[guestbook_4](./guestbook_4_남은작업.md)에서 "아직 안 만든 것"으로 남겨뒀던 목록이 이번에 전부 채워졌습니다. 이 문서는 **가이드(4번 문서)가 예상했던 것과 실제 완성 코드가 어떻게 다른지** 비교하면서 정리합니다 — 예상이 빗나간 지점일수록 배울 게 많기 때문입니다.

---

## 1. 완성된 것 vs 가이드가 예상했던 것

| 파일 | guestbook_4의 예상 | 실제 완성 코드 |
|---|---|---|
| `showGuestBook.jsp` | `grade`를 `boolean→String` 변환해서 넘김 | **변환 코드 자체가 없음** (아래 6번 참고) |
| `postGuestBook.jsp`/`Proc.jsp` | id는 hidden으로 받되 서버에서 세션값으로 덮어써야 함 | hidden input의 id를 **그대로 신뢰**해서 사용 (5번 참고) |
| `updateGuestBook.jsp` | 본인 글인지 서버에서 확인 필요 | **서버단 확인 없음** — 화면에만 버튼을 숨겨놨을 뿐 (4번 참고) |
| `deleteGuestBook.jsp` | — | 댓글도 같이 지워야 한다는 **주석만 있고 실제 삭제 코드는 없음** (7번 참고) |
| `CommentMgr.java` | CRUD 4종 | `listComment`/`insertComment`/`deleteComment`/`deleteAllComment` 4종 다 구현됨 |

즉 "완성"이라고 해서 가이드가 걱정했던 문제들이 다 해결된 게 아니라, **일부는 해결됐고 일부는 여전히 구멍이 남아있는 상태**입니다. 실무에서도 흔한 상황이라 오히려 좋은 실습 재료입니다.

## 2. 로그인 후 원래 가려던 페이지로 — `url` 파라미터 패턴

`showGuestBook.jsp`처럼 로그인이 필요한 페이지는 로그인 안 된 상태로 들어오면 이렇게 튕깁니다.

```jsp
String id = (String)session.getAttribute("idKey");
if(id==null){
    StringBuffer url = request.getRequestURL();   // 지금 오려던 주소를 기억
    response.sendRedirect("login.jsp?url="+url);
    return;
}
```

`login.jsp`는 이 `url`을 hidden input에 담아 폼과 함께 넘기고, `loginProc.jsp`가 로그인 성공 시 그 주소로 돌려보냅니다.

```jsp
<%-- login.jsp --%>
<input type="hidden" name="url" value="<%=url%>">
```
```jsp
<%-- loginProc.jsp --%>
String url = "login.jsp";
if(request.getParameter("url")!=null
        && !request.getParameter("url").equals("null")){   // ★ 주목
    url = request.getParameter("url");
}
```

**`.equals("null")` 체크가 왜 필요한가** — `url` 파라미터가 애초에 없으면 `request.getParameter("url")`은 진짜 `null`(자바 null)을 돌려줍니다. 그런데 `login.jsp`의 `<%=url%>`에서 `url` 변수가 null이면, JSP는 예외를 던지는 대신 **문자열 `"null"`을 그대로 화면에 찍어버립니다.** 그래서 hidden input의 값이 실제 문자열 `"null"`이 되고, 폼을 다시 제출하면 `request.getParameter("url")`은 이번엔 (자바 null이 아니라) **문자열 `"null"`을 돌려줍니다.** 두 가지 "null"을 구분 못 하면 무한히 `login.jsp?url=null`로 튕기는 버그가 생겨서, 문자열 비교로 걸러주는 겁니다.

> `<%= null변수 %>` → 화면엔 `null`이라는 글자가 찍힌다는 것, 은근히 자주 걸리는 함정입니다.

## 3. `showGuestBook.jsp` — 목록 + 댓글을 한 화면에

구조는 가이드 예상과 같지만 (`mgr.listGuestBook(id, grade)` 호출 → for문으로 출력), 실제로는 **글 하나마다 그 자리에서 댓글까지 같이 조회**합니다.

```jsp
Vector<GuestBookBean> vlist = mgr.listGuestBook(id, login.getGrade());
for(int i=0;i<vlist.size();i++){
    GuestBookBean bean = vlist.get(i);
    JoinBean writer = mgr.getJoin(bean.getId());   // 글쓴이 정보를 매번 다시 조회
    // ... 글 출력 ...
    Vector<CommentBean> cvlist = cmgr.listComment(bean.getNum());   // 이 글의 댓글들
    // ... 댓글 출력 ...
}
```
글 N개면 `getJoin()`이 N번, `listComment()`가 N번 더 호출되는 구조입니다. 지금 데이터 양에서는 문제없지만, "게시글이 많아지면 목록 화면 하나 띄우는 데 SQL이 몇십 번씩 나간다"는 걸 눈으로 볼 수 있는 좋은 예시입니다 (N+1 쿼리 문제라고 부릅니다).

**댓글 개수 버튼으로 펼치기/접기 흉내:**
```html
<div id="cmt<%=bean.getNum()%>" style="display: block" display="none">
    <!-- 댓글 목록 -->
</div>
<button onclick="disFn('<%=bean.getNum()%>')">댓글<%=cvlist.size()%></button>
```
`disFn`은 지금 `alert(num)`만 하는 자리표시자입니다 — 원래는 `div`를 열고 닫는 토글 함수가 들어갈 자리인데 비어있습니다. 댓글 자체는 항상 펼쳐진 채로 보입니다.

## 4. 수정 — 화면엔 막아놨지만 서버는 안 막음 (발견한 구멍)

`showGuestBook.jsp`에서 [수정] 링크는 본인 글일 때만 보입니다.

```jsp
boolean chk = id.equals(writer.getId());
if(chk||login.getGrade().equals("1")){
    if(chk){ %>
        <a href="javascript:updateFn('<%=bean.getNum()%>')">[수정]</a>
    <%}
}
```

그런데 `updateGuestBook.jsp`를 열어보면:
```jsp
GuestBookBean bean = mgr.getGuestBook(num);   // 이게 전부. 본인 글인지 확인 안 함
```
**`num`만 알면(=주소창에 숫자만 바꾸면) 남의 글도 수정 화면을 열 수 있습니다.** guestbook_4에서 미리 경고했던 "본인 글 확인 절차"가 실제로는 빠진 채로 완성됐습니다. 화면(링크를 안 보여줌)만 막고 서버(요청을 실제로 처리하는 곳)는 안 막은 전형적인 사례 — **화면에서 숨기는 것과 서버에서 막는 것은 다른 문제**라는 걸 실제 코드로 확인한 셈입니다.

## 5. 글쓰기 — hidden input의 id를 그대로 믿음

```jsp
<%-- postGuestBook.jsp --%>
<input type="hidden" name="id" value="<%=login.getId()%>">
```
```jsp
<%-- postGuestBookProc.jsp --%>
<jsp:setProperty property="*" name="bean"/>   <!-- id를 폼값 그대로 bean에 채움 -->
mgr.insertGuestBook(bean);
```
guestbook_4는 "폼의 id를 그대로 믿지 말고 세션의 id로 강제 덮어써야 위조를 막는다"고 짚었는데, 실제 코드는 폼값을 그대로 씁니다. 지금은 `value`에 로그인한 자기 자신의 id를 미리 채워만 뒀을 뿐이라 **개발자도구로 이 hidden 값을 바꾸면 다른 사람 이름으로 글을 쓸 수 있는 구조**입니다. (같은 이유로 `ip`도 hidden으로 받고 있어 이론적으로 위조 가능합니다.) 당장 실습에는 문제없지만, 실무였다면 `bean.setId((String)session.getAttribute("idKey"))`처럼 서버에서 덮어쓰는 한 줄이 꼭 필요합니다.

**체크박스의 특성 — 안 누르면 파라미터가 아예 안 옴:**
```jsp
if(bean.getSecret()==null)
    bean.setSecret("0");   // 체크 안 하면 secret 파라미터 자체가 안 넘어옴
```
라디오 버튼과 달리 체크박스는 `checked`가 아니면 그 `name` 자체가 요청에서 빠집니다. 그래서 "안 채워지면 null" → "null이면 기본값 0"으로 보정하는 코드가 항상 필요합니다.

## 6. `grade` 타입 문제 — 가이드의 예상이 틀렸던 이유

guestbook_2, guestbook_4에서 계속 "`JoinBean.isGrade()`는 boolean인데 메소드는 String을 받아서 변환이 필요하다"고 짚었는데, `showGuestBook.jsp`의 실제 코드는 변환이 아예 없습니다.

```jsp
Vector<GuestBookBean> vlist = mgr.listGuestBook(id, login.getGrade());  // 변환 없이 바로 전달
if(chk||login.getGrade().equals("1")){                                  // String이라 .equals() 그대로 사용
```

이게 가능한 이유는 **`JoinBean.grade` 필드 자체를 처음부터 `boolean`이 아니라 `String`으로 설계**했기 때문입니다. DB의 `char(2)` 컬럼값("0"/"1")을 가공 없이 그대로 들고 다니는 겁니다.

> **타입 불일치가 자꾸 발목을 잡는다면, 변환 코드를 추가하는 것보다 애초에 타입을 하나로 통일하는 설계가 더 근본적인 해결책**입니다. guestbook_2에서 만났던 "boolean으로 바꿔서 저장하기" 방식도 틀린 건 아니지만, 그러면 그 이후 모든 곳에서 계속 변환이 따라다닙니다. 이번처럼 "DB 원본 타입(String)을 끝까지 유지"하는 쪽이 코드가 더 짧아집니다.

(참고: `GuestBookMgr`의 메소드 이름도 최종적으로 `listGuestBooks`가 아니라 **`listGuestBook`**(단수)로 정리됐습니다 — 다른 CRUD 메소드들이 `getGuestBook`, `updateGuestBook`처럼 전부 단수형이라 이름 규칙을 맞춘 것으로 보입니다.)

## 7. 삭제 — 주석은 있는데 코드가 없는 곳 (또 다른 구멍)

```jsp
<%-- deleteGuestBook.jsp --%>
mgr.deleteGuestBook(num);
//방명록 원글 삭제시 관련된 댓글 모두 삭제
```

`CommentMgr`에는 이 용도로 쓰라고 **`deleteAllComment(int num)`까지 이미 만들어져 있는데**, `deleteGuestBook.jsp`에서 실제로 호출하는 코드가 빠져있습니다. 지금 상태로는 글을 지워도 그 글에 달렸던 댓글들은 `tblComment`에 그대로 남습니다(원글 없는 "고아 댓글"). 필요하면 이렇게 한 줄만 추가하면 됩니다.

```jsp
mgr.deleteGuestBook(num);
cmgr.deleteAllComment(num);   // <jsp:useBean id="cmgr" class="guestbook.CommentMgr"/> 선언 추가 필요
```

## 8. 댓글 처리 — 하나의 파일에서 insert/delete 둘 다

```jsp
<%-- commentProc.jsp --%>
String flag = request.getParameter("flag");
if(flag.equals("insert")){
    cmgr.insertComment(cbean);
}else if(flag.equals("delete")){
    cmgr.deleteComment(cbean.getCnum());
}
response.sendRedirect("showGuestBook.jsp");
```

댓글 작성 폼과 댓글 삭제 버튼이 **같은 `delFrm`/`cFrm` hidden 필드 구조를 재사용**해서 `flag` 값만 바꿔 하나의 처리 파일로 보냅니다. `showGuestBook.jsp` 안의 자바스크립트를 보면 감이 옵니다.

```js
function commentFn(frm){ frm.submit(); }                    // cFrm: flag=insert로 제출
function delCFn(cnum){
    document.delFrm.cnum.value=cnum;
    document.delFrm.flag.value="delete";
    document.delFrm.action="commentProc.jsp";
    document.delFrm.submit();                                 // delFrm: flag=delete로 재활용
}
```
ch06에서 배운 "폼 하나로 여러 동작을 처리할 땐 hidden 필드로 분기값을 넘긴다" 패턴이 방명록 수정/삭제, 댓글 작성/삭제까지 전부 같은 방식으로 반복되고 있습니다.

## 9. 오늘 이 파일들 보면서 정리한 교훈

1. **"완성됐다"와 "구멍이 없다"는 다른 말입니다.** 화면 UI로 막아둔 것(수정 버튼 숨기기)과 서버 로직으로 막아둔 것(권한 재검증)은 별개이고, 실습 코드에는 전자만 있는 경우가 많습니다.
2. **주석으로 "해야 한다"고 적어놓고 실제 코드가 없는 부분**은 실무 코드에서도 흔합니다 — TODO 주석을 발견하면 정말 구현이 안 됐는지 항상 의심하고 확인하는 습관이 필요합니다.
3. **타입 불일치 문제는 변환 코드보다 설계 통일이 낫다** — `boolean` vs `String` 문제로 몇 번을 헤맸는데, 결국 해결책은 "둘 중 하나로 통일"이었습니다.
