# JSP 공부노트 — 목차

`myapp` 프로젝트로 진행한 JSP 수업 내용을 분야별로 나눠 정리한 공부노트입니다.
각 파일은 **구조 파악 → 코드 한 줄씩 분석 → "이럴 땐 이렇게 써야 한다" 템플릿** 순서로 되어 있습니다.

---

## 파일 목록

### 2026-09-10 (ch06 마무리 · ch07 · ch09 시작)

| 파일 | 내용 | 관련 챕터 |
|---|---|---|
| [00_개발환경_트러블슈팅.md](./00_개발환경_트러블슈팅.md) | Tomcat/Maven 설정, WAR 배포, 빌드 명령, "갑자기 404" 사고 분석 | 전체 |
| [ch06_jsp-forward.md](./ch06_jsp-forward.md) | 액션 태그 `<jsp:forward>`, include/forward/redirect 비교 | ch06 |
| [ch07_내장객체.md](./ch07_내장객체.md) | request / response / session / out | ch07 |
| [ch09_1_JavaBean.md](./ch09_1_JavaBean.md) | JavaBean 규칙, 빈 액션 태그 3형제, `property="*"` | ch09 |
| [ch09_2_JDBC_DAO패턴.md](./ch09_2_JDBC_DAO패턴.md) | 커넥션 풀, JDBC 4단계, MySQL/Oracle 접속 | ch09 |
| [ch09_3_TeamCRUD실습.md](./ch09_3_TeamCRUD실습.md) | Team 게시판 CRUD 전체 흐름, DTO/DAO | ch09 |

### 2026-09-11 (ch09 계속 · ch12 시작)

| 파일 | 내용 | 관련 챕터 |
|---|---|---|
| [ch09_4_Scope.md](./ch09_4_Scope.md) | JavaBean의 page/request/session/application scope | ch09 |
| [ch09_5_AJAX중복확인.md](./ch09_5_AJAX중복확인.md) | UNIQUE 제약 + fetch()로 페이지 이동 없이 중복확인 | ch09 |
| [ch09_6_서블릿매핑.md](./ch09_6_서블릿매핑.md) | `@WebServlet` 매핑, doGet/doPost, 서블릿 파일템플릿 | ch09 |
| [ch12_1_Cookie.md](./ch12_1_Cookie.md) | 쿠키 생성/조회, 쿠키 vs 세션 | ch12 |
| [ch12_2_세션로그인.md](./ch12_2_세션로그인.md) | 세션 기반 로그인/로그아웃, "잘못된 예제" 분석 | ch12 |
| [99_UI확장_Tailwind와다크모드.md](./99_UI확장_Tailwind와다크모드.md) | Tailwind CDN, 공통 include, 다크모드 토글 (수업 외 실무 팁) | 전체 |

### 2026-09-11 오후 (ch13 파일업로드)

| 파일 | 내용 | 관련 챕터 |
|---|---|---|
| [ch13_1_파일업로드.md](./ch13_1_파일업로드.md) | COS 라이브러리(로컬 Maven 설치), `MultipartRequest`, 업로드 흐름, 한글 인코딩 함정 | ch13 |
| [ch13_2_다중삭제와IN연산자.md](./ch13_2_다중삭제와IN연산자.md) | 체크박스 전체/개별선택 JS 패턴, `deleteFile` vs `deleteFile2`(IN 연산자), **발견한 버그**(파라미터 인덱스 고정) | ch13 |
| [ch13_3_파일다운로드.md](./ch13_3_파일다운로드.md) | 스트림 다운로드, 브라우저별 인코딩 분기, `Content-Disposition` | ch13 |

### 2026-09-14 오전 (GuestBook 종합 프로젝트)

| 파일 | 내용 | 관련 챕터 |
|---|---|---|
| [guestbook_1_프로젝트구조.md](./guestbook_1_프로젝트구조.md) | 회원·방명록·댓글 3테이블 관계형 설계, 전체 파일 구성 | guestbook |
| [guestbook_2_useBean생성자함정.md](./guestbook_2_useBean생성자함정.md) | `<jsp:useBean>`이 매개변수 없는 생성자를 요구하는 이유, 오늘 겪은 에러 원인 | guestbook |
| [guestbook_3_DAO와로그인.md](./guestbook_3_DAO와로그인.md) | `GuestBookMgr` 버그 4개 분석, 실제 DB 대조하는 "진짜" 로그인 구현 | guestbook |
| [guestbook_4_남은작업.md](./guestbook_4_남은작업.md) | 목록/글쓰기/수정/삭제/댓글 — 아직 안 만든 부분 가이드 | guestbook |

### 그 외 참고 자료

| 파일 | 내용 |
|---|---|
| [SETUP.md](./SETUP.md) | JSP 개발환경 처음부터 구축하는 튜토리얼 |
| [STUDY_NOTES.md](./STUDY_NOTES.md) | 환경 구축 + 예제 1~8로 배우는 JSP 문법 (종합본) |
| [WAR_DEPLOY.md](./WAR_DEPLOY.md) | WAR 파일 구조/생성/배포 3가지 방법 |
| [_templates](./_templates) | IntelliJ 파일 템플릿 원본 (Servlet 등) |

---

## 큰 그림 — 배운 것들이 어떻게 이어지나

```
[화면]  HTML 폼 입력
   │
   ▼  (파라미터 전송)
[내장객체]  request.getParameter()  ← ch07
   │
   ▼  (값을 객체에 담기)
[JavaBean]  <jsp:useBean> + <jsp:setProperty property="*">  ← ch09
   │        scope로 page/request/session/application 중 선택 ← ch09_4
   │
   ▼  (제출 전 미리 검증 — 페이지 이동 없이)
[AJAX]  fetch()로 서버에 물어보고 결과만 화면 일부에 표시  ← ch09_5
   │
   ▼  (DB에 저장/조회)
[JDBC]  DBConnectionMgr → PreparedStatement → executeUpdate/Query  ← ch09_2
   │
   ▼  (결과 화면으로 이동)
[페이지 이동]  response.sendRedirect() / <jsp:forward>  ← ch06, ch07
   │
   ▼  (같은 사용자인지 기억)
[쿠키/세션]  Cookie, session.setAttribute("idKey", id)  ← ch12
   │
   ▼  (텍스트 아닌 바이너리 데이터 주고받기)
[파일 업로드/다운로드]  MultipartRequest → 저장 → 스트림으로 전송  ← ch13
   │
   ▼  (지금까지 배운 것 전부를 실제 서비스 하나로)
[종합 프로젝트]  회원(tblJoin) → 방명록(tblGuestBook) → 댓글(tblComment)  ← guestbook
```

Team CRUD 실습([ch09_3](./ch09_3_TeamCRUD실습.md))에 JavaBean·JDBC·페이지이동이, 로그인 예제([ch12_2](./ch12_2_세션로그인.md))에 세션이, 이름 중복확인([ch09_5](./ch09_5_AJAX중복확인.md))에 AJAX가, 파일 관리 실습([ch13_1~3](./ch13_1_파일업로드.md))에 JDBC·체크박스UI·스트림 처리가, GuestBook 프로젝트([guestbook_1~4](./guestbook_1_프로젝트구조.md))에 이 모든 게 다 같이 들어있습니다.

---

## 프로젝트 폴더 구조 (2026-09-11 기준)

```
C:\Jsp\myapp\
├── src\main\
│   ├── java\
│   │   ├── ch07\  MUtil.java
│   │   ├── ch09\  DBConnectionMgr.java, DBConnectionMgr2.java, MUtil.java
│   │   │        SimpleBean.java, ScopeBean.java, TeamBean.java, TeamMgr.java
│   │   │        MysqlMgr.java, OracleMgr.java, TeamDeleteServlet.java
│   │   └── ch13\  DBConnectionMgr.java, MUtil.java, FileloadBean.java, FileloadMgr.java
│   └── webapp\
│       ├── common\  themeToggle.jspf (모든 페이지 공통 다크모드 버튼)
│       ├── ch06\  forwardTag1~2, includeTag3 ...
│       ├── ch07\  request1~2, response1~2, session1, out1 ...
│       ├── ch09\  simpleBean*, scopeBean*, teamInsert*, teamList, teamRead, checkName.jsp ...
│       ├── ch12\  cookCookie.jsp, tasteCookie.jsp, login.jsp, loginProc.jsp, loginOK.jsp, logout.jsp
│       └── ch13\  fileSelect.jsp, fupload.jsp, fuploadProc.jsp, viewPage.jsp,
│                 flist.jsp, fdeleteProc.jsp, fdownload.jsp, storage\(업로드 저장 폴더)
└── 공부노트\  (이 폴더)
```
