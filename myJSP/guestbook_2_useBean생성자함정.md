# GuestBook 프로젝트 (2) — `<jsp:useBean>`이 요구하는 생성자

오늘 로그인하자마자 에러가 났던 진짜 원인. **JavaBean 규칙을 어기면 언제 어떻게 터지는지**를 실제로 겪은 사례라 꼭 이해하고 넘어가야 합니다.

---

## 1. 증상

```jsp
<%-- loginProc.jsp --%>
<jsp:useBean id="mgr" class="guestbook.GuestBookMgr"/>
```

로그인 폼을 제출하는 순간(`loginProc.jsp`가 실행되는 순간) 바로 에러 화면이 떴습니다.

## 2. 원인 — 생성자가 안 맞음

```java
public class GuestBookMgr {
    public GuestBookMgr(DBConnectionMgr pool) {   // 매개변수가 있는 생성자만 있었음
        this.pool = pool;
    }
    ...
}
```

**`<jsp:useBean>`은 내부적으로 항상 `new 클래스명()` — 매개변수가 하나도 없는 생성자를 호출합니다.** 그런데 `GuestBookMgr`엔 `DBConnectionMgr`를 받는 생성자밖에 없어서, "매개변수 없는 생성자가 없다"는 에러가 났습니다.

이건 ch09_1 노트에서 배운 **JavaBean 규칙 1번("매개변수 없는 public 생성자 필요")**을 실제로 어겨서 터진 사례입니다. 그때는 `void`를 잘못 붙여서 생성자 자체가 없어진 경우였고, 이번엔 **생성자는 있는데 매개변수가 필요한 것만 있어서** 역시 조건을 못 채운 경우입니다.

## 3. 왜 처음부터 `DBConnectionMgr pool`을 받게 만들었을까

```java
public GuestBookMgr(DBConnectionMgr pool) {
    this.pool = pool;
}
```

이 방식은 **의존성 주입(Dependency Injection)**이라고 부르는 패턴입니다. `GuestBookMgr` 스스로 `DBConnectionMgr.getInstance()`를 부르지 않고, **바깥에서 만들어진 pool을 건네받기만** 합니다.

| | 스스로 만듦 (ch09/ch13 방식) | 주입받음 (이번 시도) |
|---|---|---|
| 코드 | `pool = DBConnectionMgr.getInstance();` | `this.pool = pool;` (매개변수로 받음) |
| 장점 | 어디서든 `new GuestBookMgr()` 한 줄로 끝 | 테스트할 때 가짜(mock) pool을 넣어서 실제 DB 없이 테스트 가능 |
| 단점 | 테스트 시 항상 진짜 DB 필요 | `<jsp:useBean>`처럼 "자동으로 매개변수 없이 생성"하는 도구와 안 맞음 |

**실무에서는 의존성 주입이 더 좋은 설계로 취급**받습니다(Spring 프레임워크가 통째로 이 개념 위에 만들어짐). 다만 **`<jsp:useBean>`은 그런 것까지 이해 못 하는 단순한 도구**라서, 이 둘을 같이 쓰려면 절충이 필요합니다.

## 4. 해결 — 두 생성자를 같이 두기 (오버로딩)

```java
public class GuestBookMgr {
    private DBConnectionMgr pool;

    // <jsp:useBean> 용 — 매개변수 없이 스스로 pool을 구해옴
    public GuestBookMgr() {
        this.pool = DBConnectionMgr.getInstance();
    }

    // 필요하면 바깥에서 pool을 직접 넣어줄 수도 있게 (테스트 등에서 활용 가능)
    public GuestBookMgr(DBConnectionMgr pool) {
        this.pool = pool;
    }
    ...
}
```

**생성자 오버로딩**(같은 이름, 다른 매개변수)으로 두 마리 토끼를 다 잡았습니다. `<jsp:useBean>`은 자동으로 `GuestBookMgr()`(위 것)를 쓰고, 필요할 때 직접 `new GuestBookMgr(어떤풀)`도 여전히 가능합니다.

## 5. 이 규칙, 어디서 또 걸릴 수 있나

`<jsp:useBean>`을 쓰는 **모든** 클래스에 똑같이 적용됩니다. 앞으로 새 DAO/DTO를 만들 때 체크리스트:

```
[ ] public 클래스인가?
[ ] 매개변수 없는(no-arg) public 생성자가 있는가?
    (직접 안 만들면 자바가 자동으로 만들어주지만,
     다른 생성자를 하나라도 직접 만들면 자동 생성이 사라지므로 직접 추가해야 함)
[ ] <jsp:useBean id="..." class="..."/> 로 쓸 계획이면 위 2개는 필수
```

> `<jsp:useBean>` 대신 `<% GuestBookMgr mgr = new GuestBookMgr(DBConnectionMgr.getInstance()); %>`처럼 스크립트릿으로 직접 만들면 이 제약을 안 받습니다. 다만 지금까지 이 프로젝트 전체가 `<jsp:useBean>` 스타일로 통일돼 있어서, 생성자를 맞춰주는 쪽으로 해결했습니다.
