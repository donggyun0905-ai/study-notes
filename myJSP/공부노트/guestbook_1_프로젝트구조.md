# GuestBook 프로젝트 (1) — 전체 구조

지금까지 따로따로 배운 JavaBean·JDBC·세션·CRUD를 **하나의 실제 서비스**로 합쳐보는 종합 실습입니다. "회원가입한 사람이 로그인해서 방명록을 쓰고, 그 글에 댓글을 다는" 흔한 커뮤니티 게시판 구조입니다.

---

## 1. 왜 테이블이 3개인가 — 관계형 설계

Team 게시판(ch09)은 테이블 1개(`tblteam`)로 끝났지만, 이번엔 **서로 연결된 테이블 3개**입니다.

```sql
tblJoin        (회원)
├─ id (PK)
├─ pwd, name, email, hp
└─ grade         -- '0'=일반회원, '1'=관리자

tblGuestBook   (방명록 글)
├─ num (PK, auto_increment)
├─ id             -- 누가 썼는지 (tblJoin.id를 가리킴, 실제 FK 제약은 안 걸었지만 의미상 연결)
├─ contents, ip, regdate, regtime
└─ secret         -- '0'=공개글, '1'=비밀글(본인+관리자만 열람)

tblComment     (댓글)
├─ cnum (PK, auto_increment)
├─ num            -- 어느 글의 댓글인지 (tblGuestBook.num을 가리킴)
├─ cid, comment, cip, cregDate
```

**관계**: 회원 한 명이 → 방명록 글 여러 개를 쓸 수 있고(1:N), 방명록 글 하나에 → 댓글이 여러 개 달릴 수 있음(1:N). `num` 컬럼이 `tblComment`와 `tblGuestBook`을 이어주는 연결고리입니다.

> 실무에서는 이런 연결에 진짜 `FOREIGN KEY` 제약을 거는 게 정석이지만(예: `tblGuestBook.id`가 `tblJoin`에 없는 값이면 저장 자체를 막음), 여기선 코드 레벨에서만 관계를 유지합니다.

## 2. 파일 구성 — 계층별로 정리

```
guestbook/
├── (자바) DTO — 테이블 하나당 하나씩
│   JoinBean.java, GuestBookBean.java, CommentBean.java
│
├── (자바) DAO — 테이블 하나당 하나씩
│   GuestBookMgr.java (회원 조회 + 방명록 CRUD)  ← 완성
│   CommentMgr.java                              ← 아직 빈 껍데기
│   DBConnectionMgr.java (커넥션 풀)
│   MUtil.java (공용 유틸: ph(), toIntArr() 등 — ch13과 동일)
│
└── (JSP) 화면
    login.jsp / loginProc.jsp / logout.jsp        ← 완성, 테스트까지 함
    showGuestBook.jsp                             ← 목록 (비어있음)
    postGuestBook.jsp / postGuestProc.jsp          ← 글쓰기 (비어있음)
    updateGuestBook.jsp / updateGuestBookProc.jsp  ← 수정 (비어있음)
    deleteGuestBook.jsp                            ← 삭제 (비어있음)
    commentProc.jsp                                ← 댓글 처리 (비어있음)
```

## 3. 큰 그림 — 오늘까지 완성된 흐름

```
[미완성 상태]
회원가입? → login.jsp (로그인) → loginProc.jsp (DB 대조) → 세션에 로그인 정보 저장
                                                              │
                                                              ▼ (다음에 이어서)
                                                  showGuestBook.jsp (목록, 비밀글은 본인/관리자만)
                                                              │
                                          ┌───────────────────┼───────────────────┐
                                          ▼                   ▼                   ▼
                                  postGuestBook.jsp   updateGuestBook.jsp   deleteGuestBook.jsp
                                    (글쓰기)              (수정)                (삭제)
                                          │
                                          ▼
                                  commentProc.jsp (댓글 달기)
```

**오늘 실제로 끝낸 부분은 `login.jsp → loginProc.jsp → logout.jsp`**입니다 — 나머지는 뼈대(DTO)만 있거나 빈 파일입니다. 자세한 건 다음 노트들 참고:

- [guestbook_2_useBean생성자함정.md](./guestbook_2_useBean생성자함정.md) — 오늘 겪은 핵심 에러 원인
- [guestbook_3_DAO와로그인.md](./guestbook_3_DAO와로그인.md) — `GuestBookMgr` 전체 분석 + 로그인 처리
- [guestbook_4_남은작업.md](./guestbook_4_남은작업.md) — 아직 비어있는 파일들 가이드

## 4. 이 프로젝트가 특별한 이유 — "진짜" 로그인

ch12에서 만든 로그인 예제는 강사님이 일부러 `<%--잘못된 로그인 처리 방식--%>`라고 표시해둔 **가짜 로그인**이었습니다(아이디/비번에 뭐라도 입력하면 무조건 통과). 이번 `loginJoin()`은:

```java
sql = "select id from tblJoin where id =? and pwd = ?";
```

**진짜로 DB에서 아이디+비밀번호가 일치하는 회원이 있는지 대조**합니다. ch12에서 "이게 왜 잘못된 예제인지" 배운 게, 이번에 "그럼 제대로 하면 이렇게 한다"로 이어진 겁니다.
