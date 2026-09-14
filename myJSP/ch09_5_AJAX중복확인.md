# ch09-5. AJAX로 중복확인 만들기 (실무 패턴)

Team Insert 화면에 "이름 중복확인" 버튼을 추가하면서 쓴 기법 정리. **페이지 이동 없이** 서버에 물어보고 결과만 화면 일부에 표시하는, 실무에서 아주 흔한 패턴입니다.

---

## 1. 왜 필요한가

지금까지는 폼을 제출하면 무조건 **다른 페이지로 이동**했습니다 (`sendRedirect`, `forward`). 그런데 "이름이 이미 있는지"처럼 **제출하기 전에 미리** 확인하고 싶은 경우, 페이지 전체가 넘어가면 입력하던 내용이 다 날아갑니다.

**해결책**: 자바스크립트가 백그라운드에서 서버에 살짝 물어보고, 그 결과만 화면 한 조각에 표시 — 이게 AJAX입니다. (Asynchronous JavaScript And XML — 지금은 XML 대신 텍스트/JSON을 주고받는 게 보통이라 이름만 남음)

## 2. 전체 구조

```
[브라우저]                              [서버]
이름 입력칸 + [중복확인] 버튼
        │ 클릭
        ▼
   fetch('checkName.jsp?name=홍길동')  ──────▶  checkName.jsp
        │                                         │ DB 조회
        │ ◀──────────────────────────────────────┘
   응답 텍스트 "dup" 또는 "ok"
        │
        ▼
   화면의 <p> 태그 내용만 바꿔줌 (페이지 이동 없음)
```

## 3. DB 단계 — UNIQUE 제약

화면에서 막아도 되지만, **최종 방어선은 항상 DB**에 둡니다.

```sql
ALTER TABLE tblteam ADD CONSTRAINT uq_tblteam_name UNIQUE (name);
```

이렇게 해두면 화면 검증을 어떻게든 건너뛰고 저장을 시도해도, DB가 중복 `name`을 거부합니다 (`SQLException` 발생 → `insertTeam`의 `catch`에서 잡힘).

## 4. DAO 단계 — 중복 확인 메소드

```java
// TeamMgr.java
public boolean isNameDuplicate(String name){
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean duplicate = false;
    try {
        con = pool.getConnection();
        String sql = "select count(*) from tblteam where name = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, name);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            duplicate = rs.getInt(1) > 0;   // 개수가 1개 이상이면 true
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        pool.freeConnection(con, pstmt, rs);
    }
    return duplicate;
}
```
지금까지 배운 JDBC 4단계 패턴(ch09_2 참고) 그대로입니다. `count(*)`가 0보다 크면 이미 있는 이름.

## 5. 화면 없는 엔드포인트 — checkName.jsp

```jsp
<%@ page import="ch09.TeamMgr" %>
<%@ page contentType="text/plain; charset=UTF-8" %>   <%-- HTML이 아니라 순수 텍스트로 응답 --%>
<%
    request.setCharacterEncoding("UTF-8");
    String name = request.getParameter("name");
    TeamMgr mgr = new TeamMgr();
    boolean dup = (name != null && !name.trim().isEmpty()) && mgr.isNameDuplicate(name);
%><%= dup ? "dup" : "ok" %>
```

**포인트**
- `contentType="text/plain"` — 화면에 뿌릴 HTML이 아니라, JS가 읽을 **순수 텍스트 하나**만 응답
- 사람이 직접 이 URL을 열어도 그냥 `dup` 또는 `ok` 글자만 보입니다 (정상)

## 6. 화면 단계 — fetch + 결과 표시

```html
<input name="name" value="홍길동" onchange="resetNameCheck()" oninput="resetNameCheck()">
<button type="button" onclick="checkNameDup()">중복확인</button>
<p id="nameCheckMsg"></p>
```
> `<button type="button">` — `type`을 안 주면 기본값이 `submit`이라 버튼 누르면 폼이 그대로 제출돼버립니다. 반드시 `type="button"`으로 막아야 합니다.

```javascript
var nameChecked = false;   // "중복확인 통과했는지" 상태 저장

function checkNameDup(){
    var name = document.frm.name.value;
    if(name == ""){ alert("이름을 입력하세요"); return; }

    fetch('checkName.jsp?name=' + encodeURIComponent(name))   // ① 서버에 요청
        .then(function(res){ return res.text(); })             // ② 응답을 텍스트로 변환
        .then(function(result){                                // ③ 받은 텍스트로 화면 갱신
            var msg = document.getElementById('nameCheckMsg');
            if(result.trim() === 'dup'){
                msg.textContent = '이미 사용 중인 이름입니다';
                nameChecked = false;
            } else {
                msg.textContent = '사용 가능한 이름입니다';
                nameChecked = true;
            }
        })
        .catch(function(){ alert('중복 확인 중 오류가 발생했습니다'); });  // ④ 네트워크 에러 처리
}

function resetNameCheck(){       // 이름을 다시 고치면 이전 확인 결과는 무효화
    nameChecked = false;
    document.getElementById('nameCheckMsg').textContent = '';
}
```

### `fetch()` 기본 패턴 — 외워두면 계속 쓰임

```javascript
fetch('URL')
    .then(res => res.text())      // 텍스트로 받을 때 (또는 res.json() : JSON으로 받을 때)
    .then(data => { /* 화면 갱신 */ })
    .catch(err => { /* 실패 처리 */ });
```

### encodeURIComponent — 왜 필요한가

```javascript
fetch('checkName.jsp?name=' + encodeURIComponent(name))
```
이름에 `&`, `=`, 한글, 공백 같은 게 들어가면 URL 문법이 깨집니다. `encodeURIComponent()`가 이런 문자를 URL에 안전한 형태(`%XX`)로 바꿔줍니다. **쿼리스트링에 사용자 입력값을 넣을 땐 항상 감싸야 합니다.**

## 7. 제출 막기 — 확인 안 하면 저장 못 하게

```javascript
function check() {
    f = document.frm;
    if(f.name.value==""){ alert("이름을 입력하세요"); f.name.focus(); return; }
    if(!nameChecked){ alert("이름 중복 확인을 먼저 해주세요"); return; }   // ← 여기
    // ... 나머지 검증 ...
    f.submit();
}
```
`nameChecked`가 `true`가 아니면 `f.submit()`까지 도달을 못 합니다. 이름칸을 다시 고치면(`oninput`) `resetNameCheck()`가 불려서 `nameChecked`가 다시 `false`로 돌아가므로, **확인 → 수정 → 재확인 없이 제출**하는 걸 막습니다.

## 8. 2중 방어 구조 — 왜 DB + 화면 둘 다 하나

| 계층 | 막아주는 것 |
|---|---|
| 화면(JS) | 대부분의 정상적인 사용자 — 제출 전에 바로 알려줘서 UX가 좋음 |
| DB(UNIQUE) | JS를 끄거나, 개발자도구로 요청을 조작하거나, 여러 탭에서 동시에 같은 이름으로 저장하는 경우까지 **전부** 막아줌 |

화면 검증만 믿으면 안 되는 이유: 브라우저 쪽 코드는 누구나 끄거나 바꿔서 보낼 수 있습니다. **화면 검증 = 사용자 편의, DB 제약 = 진짜 방어선.**
