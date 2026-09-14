# ch12-1. 쿠키 (Cookie)

---

## 1. 쿠키란

**서버가 브라우저에게 "이거 저장해놨다가 나중에 다시 보낼 때 같이 보내줘"라고 부탁하는 작은 데이터.**

- 이름 유래: 부스러기(crumb)처럼 클라이언트에 흔적이 남는다는 의미에서 Cookie
- 저장 위치: **브라우저(클라이언트)**. 서버가 아님 — 이게 세션과의 가장 큰 차이(세션은 서버에 저장)
- 용도: 로그인 유지, 최근 본 상품, 사용자 설정 기억 등

## 2. 쿠키 만들기 — cookCookie.jsp

```jsp
<%
    String cookieName = "myCookie";
    Cookie cookie = new Cookie(cookieName, "Apple");   // ① 쿠키 생성 (이름, 초기값)
    cookie.setMaxAge(60);                               // ② 유지시간 60초(1분)
    cookie.setValue("Melone");                          // ③ 값 덮어쓰기
    response.addCookie(cookie);                         // ④ 응답에 실어서 브라우저로 전송
%>
```

> `Cookie` 클래스는 `import` 없이 바로 씁니다 — JSP는 `jakarta.servlet.http.*` 패키지를 **모든 페이지에 자동으로 import**해주기 때문입니다 (JSP 스펙에 정해진 규칙).

### 단계별

| 단계 | 코드 | 설명 |
|---|---|---|
| ① 생성 | `new Cookie("이름", "초기값")` | 이 시점엔 아직 브라우저로 안 나감 |
| ② 유지시간 | `setMaxAge(초)` | 안 정하면 **브라우저를 끄면 사라짐**(세션 쿠키) |
| ③ 값 변경 | `setValue("새값")` | `addCookie` 하기 **전에** 바꿔야 반영됨 |
| ④ 전송 | `response.addCookie(cookie)` | 이제야 실제로 `Set-Cookie` 응답 헤더가 붙음 |

이 예제는 "Apple"로 만들고 바로 "Melone"으로 바꾸는데 — **최종적으로 전송되는 값은 `addCookie()` 시점의 값인 `Melone`**입니다. `setValue`가 `addCookie`를 부르기 전에 값을 바꿀 수 있다는 걸 보여주는 예제입니다.

### 실제 HTTP 응답에서는 이렇게 나갑니다
```
Set-Cookie: myCookie=Melone; Max-Age=60; Expires=Fri, 11 Sep 2026 02:32:10 GMT
```

## 3. 쿠키 읽기 — tasteCookie.jsp

```jsp
<%
    // 브라우저가 보낸 쿠키 전부를 배열로 받음
    Cookie cookies[] = request.getCookies();
    if(cookies != null){
        for(int i = 0; i < cookies.length; i++){
%>
            Cookie Name: <%=cookies[i].getName()%> <br>
            Cookie Value: <%=cookies[i].getValue()%> <p>
<%
        }
    }
%>
```

### 꼭 알아야 할 것 3가지

**① `getCookies()`는 `null`일 수 있다** — 브라우저가 쿠키를 하나도 안 보냈으면 `null` (빈 배열이 아니라 `null`!). 그래서 `if(cookies != null)` 체크가 필수입니다. 이거 없으면 `NullPointerException`.

**② 내가 만든 쿠키만 나오는 게 아니다** — `getCookies()`는 **브라우저가 보낸 쿠키 전부**를 돌려줍니다. 톰캣이 세션 관리용으로 자동으로 심어놓은 `JSESSIONID`도 같이 나옵니다:
```
Cookie Name: myCookie
Cookie Value: Melone

Cookie Name: JSESSIONID
Cookie Value: 501B104D7A8F09EC51646ECBF662FF9F
```
특정 쿠키만 보고 싶으면 이름으로 걸러야 합니다:
```jsp
if(cookies[i].getName().equals("myCookie")){
    // 이것만 처리
}
```

**③ 시간이 지나면 사라진다** — `setMaxAge(60)`이었으니 1분 뒤엔 `myCookie`가 안 보이고 `JSESSIONID`만 남습니다.

## 4. 쿠키 vs 세션 — 언제 뭘 쓰나

| | 쿠키 | 세션 |
|---|---|---|
| 저장 위치 | 브라우저 | 서버 |
| 용량 | 작음 (보통 4KB 제한) | 서버 메모리라 비교적 큼 |
| 보안 | 클라이언트가 보거나 바꿀 수 있음 | 서버에만 있어서 더 안전 |
| 유지기간 | `setMaxAge`로 직접 조절 (브라우저 꺼도 유지 가능) | 보통 브라우저 닫으면 끝 (또는 타임아웃) |
| 대표 용도 | "최근 본 상품", 자동 로그인 체크박스 | 로그인 상태, 장바구니 |

**민감한 정보(비밀번호, 개인정보)는 쿠키에 직접 저장하면 안 됩니다** — 클라이언트 PC에 평문으로 남기 때문입니다. 로그인 상태는 세션에 저장하고, 쿠키는 "세션을 찾아가기 위한 키(JSESSIONID)"만 들고 다니는 용도로 쓰는 게 기본 원칙입니다 (다음 노트의 로그인 예제가 이 방식입니다).
