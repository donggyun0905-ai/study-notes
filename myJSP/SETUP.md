# JSP 개발환경 세팅 문서

작성일: 2026-09-09 · 대상 프로젝트: `C:\Jsp\myapp`

---

## 0. 먼저 — 톰캣을 프로젝트 폴더 안에 넣으면 안 됩니다

질문하셨던 "톰캣을 `myapp` 안으로 드래그하면 되나요?"의 답은 **아니오**입니다.

|  | 정체 | 있어야 할 위치 |
|---|---|---|
| `myapp` | 내가 만드는 **웹 애플리케이션** | `C:\Jsp\myapp` |
| 톰캣 | 그 앱을 **실행해 주는 서버** | 프로젝트 **바깥** (`C:\Java\apache-tomcat-10.1.59`) |

톰캣은 워드 문서가 아니라 워드 프로그램에 해당합니다. 문서 폴더 안에 워드를 복사해 넣지 않는 것과 같은 이유로, 프로젝트 안에 넣으면 안 됩니다. 넣으면 이런 문제가 생깁니다.

- 빌드할 때마다 톰캣 수만 개 파일을 훑어 느려지고, WAR 안에 서버가 통째로 들어갑니다
- git에 톰캣 전체가 커밋됩니다
- 톰캣이 자기 자신을 배포하려 드는 무한 재귀가 생깁니다
- 프로젝트를 지우면 서버도 같이 사라집니다

---

## 1. 이번에 바뀐 것 요약

### 발견한 문제

이미 설치되어 있던 톰캣은 그대로 쓸 수 없는 상태였습니다.

```
C:\Program Files\Apache Software Foundation\Tomcat 10.1
  → 폴더 접근 거부 (Access is denied)
  → 서비스 계정이 NT Authority\LocalService
  → 서비스는 Running 인데 8080 포트에 아무것도 안 뜸 (8005만 점유)
```

설치 프로그램(`.exe`) 버전으로 설치하면 관리자 전용 ACL이 걸립니다. 실습할 때마다 관리자 권한이 필요해지고, 파일 하나 고칠 때마다 막힙니다. **JSP 실습용으로는 zip(Core) 버전을 사용자가 쓸 수 있는 폴더에 푸는 것이 정석**이라 그렇게 새로 깔았습니다.

기존 것은 **건드리지 않고 그대로 두었습니다.** 대신 새 톰캣의 shutdown 포트를 8005 → **8006** 으로 옮겨 충돌을 피했습니다.

### 설치한 것

| 소프트웨어 | 버전 | 경로 |
|---|---|---|
| JDK | 24.0.1 | `C:\Java\jdk-24` *(원래 있던 것)* |
| Apache Tomcat | **10.1.59** | `C:\Java\apache-tomcat-10.1.59` *(새로 설치)* |
| Apache Maven | **3.9.16** | `C:\Java\apache-maven-3.9.16` *(새로 설치)* |
| IntelliJ IDEA | 2026.2.2 **Ultimate** | `C:\Program Files\JetBrains\...` *(원래 있던 것)* |
| MySQL Server | 8.0 | `C:\Program Files\MySQL\MySQL Server 8.0` *(원래 있던 것, 현재 중지)* |

### 등록한 환경변수 (사용자 범위 — 관리자 권한 불필요)

```
JAVA_HOME     = C:\Java\jdk-24
CATALINA_HOME = C:\Java\apache-tomcat-10.1.59
MAVEN_HOME    = C:\Java\apache-maven-3.9.16
Path          += %JAVA_HOME%\bin ; %MAVEN_HOME%\bin ; %CATALINA_HOME%\bin
```

> 환경변수는 **새로 연 터미널부터** 적용됩니다. 이미 열려 있던 창은 닫았다 다시 여세요.

확인:

```powershell
java -version
mvn -version
```

---

## 2. 프로젝트 구조

일반 Java 프로젝트에서 **Maven 웹 프로젝트**로 바꿨습니다. Maven은 폴더 위치가 곧 규칙이라, 아래 자리를 지켜야 합니다.

```
C:\Jsp\myapp\
├── pom.xml                     ← 라이브러리 목록 + 빌드 설정
├── SETUP.md                    ← 이 문서
├── sql\
│   └── schema.sql              ← 실습용 DB/테이블 생성 스크립트
└── src\main\
    ├── java\                   ← .java 파일은 반드시 여기
    │   ├── Main.java                        (원래 있던 파일, 보존)
    │   └── com\example\myapp\
    │       ├── HelloServlet.java            서블릿 예제
    │       └── db\
    │           ├── DB.java                  DB 연결 유틸
    │           └── Member.java              members 테이블 DTO
    ├── resources\              ← 설정 파일은 반드시 여기
    │   ├── db.properties               실제 접속 정보 (git 제외됨)
    │   └── db.properties.example       팀 공유용 견본
    └── webapp\                 ← .jsp / css / js / 이미지는 반드시 여기
        ├── index.jsp                   환경 확인 대시보드
        ├── jstl-test.jsp               JSTL 3.0 동작 확인
        ├── db-test.jsp                 MySQL 연결 확인
        ├── css\style.css
        └── WEB-INF\
            └── web.xml                 배포 설명서 (인코딩 설정 포함)
```

자주 하는 실수:

- `.jsp` 를 `src\main\java` 에 두기 → 웹에서 안 보입니다. **`src\main\webapp`** 이 맞습니다
- `WEB-INF` 안의 파일은 **브라우저에서 직접 열 수 없습니다.** 일부러 그렇게 만든 보안 폴더입니다

---

## 3. 실행 방법

### 방법 A — IntelliJ에서 실행 (권장)

설치된 IntelliJ가 **Ultimate**라 톰캣 실행 구성을 쓸 수 있습니다. JSP를 고치고 브라우저 새로고침만 하면 바로 반영돼서 실습에 제일 편합니다.

1. IntelliJ에서 **File → Open** → `C:\Jsp\myapp` 폴더 선택
   - Maven 프로젝트로 가져올지 물으면 **Trust / Import** 를 누릅니다
   - 오른쪽 Maven 탭에 `myapp` 이 뜨면 정상입니다
2. **Run → Edit Configurations → `+` → Tomcat Server → Local**
3. **Server** 탭
   - *Application server* 옆 **Configure...** → Tomcat Home 에
     `C:\Java\apache-tomcat-10.1.59` 지정
   - *After launch* 에 열릴 URL: `http://localhost:8080/myapp/`
   - *On Update action* → **Update classes and resources**
   - *On frame deactivation* → **Update classes and resources**
     ← 이 두 개를 설정해야 저장하자마자 반영됩니다
4. **Deployment** 탭 → `+` → **Artifact** → **`myapp:war exploded`** 선택
   - *Application context* 를 **`/myapp`** 으로 지정
5. 초록색 실행 버튼

> `war` 가 아니라 **`war exploded`** 를 골라야 합니다. `war` 는 압축 파일이라 매번 다시 묶어야 하고, `exploded` 는 폴더째 배포라 파일 하나만 바꿔 치울 수 있습니다.

### 방법 B — 명령줄에서 실행

IntelliJ 없이도 되도록 톰캣에 배포 설정을 미리 넣어 두었습니다.

`C:\Java\apache-tomcat-10.1.59\conf\Catalina\localhost\myapp.xml`

```xml
<Context docBase="C:\Jsp\myapp\target\myapp" reloadable="true" />
```

톰캣이 `webapps` 폴더 대신 **프로젝트의 빌드 결과물을 직접** 바라보게 한 설정입니다. 그래서 파일을 복사해 옮길 필요가 없습니다.

```powershell
# 1. 빌드
cd C:\Jsp\myapp
mvn clean package

# 2. 톰캣 시작
startup.bat

# 3. 브라우저에서 http://localhost:8080/myapp/

# 4. 톰캣 종료
shutdown.bat
```

| 무엇을 고쳤나 | 필요한 작업 |
|---|---|
| `.jsp`, `.css`, 이미지 | 브라우저 새로고침만 (`mvn package` 후) |
| `.java` | `mvn package` → 톰캣이 자동 리로드 (`reloadable="true"`) |
| `pom.xml`, `web.xml` | `mvn package` 후 톰캣 재시작 |

---

## 4. 동작 확인용 페이지

톰캣을 띄운 뒤 아래 주소로 확인하세요. 전부 실제로 요청해서 정상 동작을 확인해 두었습니다.

| 주소 | 확인 내용 | 현재 상태 |
|---|---|---|
| `http://localhost:8080/myapp/` | 서버 정보, EL/JSTL 계산 | 정상 |
| `http://localhost:8080/myapp/hello?name=홍길동` | 서블릿 매핑, 한글 파라미터 | 정상 |
| `http://localhost:8080/myapp/jstl-test.jsp` | JSTL 3.0 태그, 숫자·날짜 포맷 | 정상 |
| `http://localhost:8080/myapp/db-test.jsp` | MySQL 연결 | **DB 설정 남음 (5번 참고)** |

확인된 값:

```
서블릿 컨테이너 : Apache Tomcat/10.1.59
서블릿 규격     : 6.0
JSP 규격        : 3.1
JVM             : 24.0.1 (Oracle Corporation)
한글 파라미터   : ?name=홍길동  →  "안녕하세요, 홍길동님"  (깨짐 없음)
```

---

## 5. 남은 작업 — MySQL 연동 (관리자 권한 필요)

여기까지는 제 권한으로 할 수 없어 남겨 둡니다. **3단계면 끝납니다.**

### 5-1. MySQL 서비스 켜기

`Windows PowerShell`을 **관리자 권한으로 실행** 후:

```powershell
Start-Service MySQL80
Get-Service MySQL80        # Status 가 Running 이면 성공
```

매번 켜기 번거로우면 자동 시작으로:

```powershell
Set-Service MySQL80 -StartupType Automatic
```

### 5-2. 데이터베이스와 테이블 만들기

```powershell
cd C:\Jsp\myapp\sql
& "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p < schema.sql
```

MySQL 설치할 때 정한 root 비밀번호를 입력하면 `myapp_db` 와 `members` 테이블이 만들어지고, 한글 이름 3건이 들어갑니다.

> MySQL Workbench 로 해도 됩니다. `schema.sql` 을 열어서 실행하면 같습니다.

### 5-3. 비밀번호 적어 넣기

`src\main\resources\db.properties` 의 `CHANGE_ME` 를 실제 비밀번호로 바꿉니다.

```properties
db.url=jdbc:mysql://localhost:3306/myapp_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
db.username=root
db.password=여기에_실제_비밀번호
```

그리고 다시 빌드:

```powershell
cd C:\Jsp\myapp
mvn clean package
```

`http://localhost:8080/myapp/db-test.jsp` 에 **연결 성공** 과 회원 3명이 나오면 완료입니다.

> `db.properties` 는 비밀번호가 들어가므로 `.gitignore` 에 넣어 두었습니다. 커밋되지 않습니다. 팀에 공유할 때는 `db.properties.example` 을 쓰세요.

---

## 6. 톰캣 10.1에서 꼭 알아야 할 것

### `javax` 가 아니라 `jakarta` 입니다

톰캣 10부터 패키지 이름이 통째로 바뀌었습니다. **2022년 이전 교재나 블로그 코드를 그대로 붙여넣으면 컴파일 에러가 납니다.**

```java
// 옛날 코드 (톰캣 9 이하) — 여기서는 에러
import javax.servlet.http.HttpServlet;

// 지금 써야 할 코드 (톰캣 10 이상)
import jakarta.servlet.http.HttpServlet;
```

### JSTL taglib 주소도 바뀌었습니다

```jsp
<%-- 옛날 코드 — 여기서는 동작 안 함 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 지금 써야 할 코드 --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
```

### 한글 인코딩 필터는 이제 필요 없습니다

옛날 교재에 나오는 `request.setCharacterEncoding("UTF-8")` 이나 인코딩 필터 클래스는 안 만들어도 됩니다. `web.xml` 에 아래를 넣어 두었고, 실제로 한글이 깨지지 않는 것을 확인했습니다.

```xml
<request-character-encoding>UTF-8</request-character-encoding>
<response-character-encoding>UTF-8</response-character-encoding>
```

### JDBC 드라이버는 직접 등록해야 합니다

톰캣은 기동할 때 `DriverManager` 를 미리 초기화합니다(메모리 누수 방지 기능). 그 탓에 `WEB-INF/lib` 에 드라이버 jar 를 넣어도 자동 등록이 안 되고 `No suitable driver found` 가 납니다. **이 프로젝트에서 실제로 겪은 문제입니다.**

`DB.java` 에 아래 static 블록을 넣어 해결해 두었습니다.

```java
static {
    Class.forName("com.mysql.cj.jdbc.Driver");
}
```

---

## 7. 자주 나는 오류

| 증상 | 원인과 해결 |
|---|---|
| `mvn` 을 못 찾음 | 환경변수 등록 전에 열어 둔 터미널입니다. 창을 닫고 새로 여세요 |
| 404 Not Found | 주소 확인. `localhost:8080` 이 아니라 **`localhost:8080/myapp/`** 입니다 |
| 포트 8080 이미 사용 중 | `netstat -ano` 결과에서 `:8080` 을 찾아 PID 확인 후 종료 |
| `Address already in use: 8005` | 옛 톰캣 서비스가 잡고 있습니다. 새 톰캣은 8006으로 옮겨 뒀으니, 이 오류가 나면 `server.xml` 의 `<Server port=...>` 를 확인하세요 |
| `import javax.servlet` 에러 | 6번 참고. `jakarta` 로 바꾸세요 |
| JSP에서 `${...}` 가 글자 그대로 나옴 | `.jsp` 가 `src\main\webapp` 밖에 있거나 taglib 주소가 옛 버전입니다 |
| 한글이 `???` 로 나옴 | 파일을 UTF-8로 저장했는지 확인 (IntelliJ 우하단 인코딩 표시) |
| `No suitable driver found` | 6번 마지막 항목 참고 |
| 코드를 고쳤는데 반영이 안 됨 | `mvn clean package` 를 다시 돌리세요. IntelliJ면 Update 설정(3-A-3) 확인 |

로그는 여기 쌓입니다:

```
C:\Java\apache-tomcat-10.1.59\logs\catalina.<날짜>.log
C:\Java\apache-tomcat-10.1.59\logs\localhost.<날짜>.log
```

---

## 8. (선택) 예전 톰캣 정리

`C:\Program Files\Apache Software Foundation\Tomcat 10.1` 은 안 쓰지만 8005 포트를 계속 잡고 있습니다. 정리하려면 **관리자 권한 PowerShell**에서:

```powershell
# 서비스만 멈추고 자동 시작 해제 (되돌리기 쉬움 — 이것부터 권장)
Stop-Service Tomcat10
Set-Service Tomcat10 -StartupType Disabled
```

완전히 지우려면 제어판 → 프로그램 추가/제거에서 **Apache Tomcat 10.1 Tomcat10** 을 제거하세요. 지운 뒤에는 새 톰캣의 shutdown 포트를 8005로 되돌려도 됩니다(그대로 8006으로 둬도 아무 문제 없습니다).

---

## 9. 되돌리는 방법

| 되돌릴 것 | 방법 |
|---|---|
| 톰캣 `server.xml` | `C:\Java\apache-tomcat-10.1.59\conf\server.xml.orig` 로 원본 백업해 뒀습니다 |
| 예전 IntelliJ 설정 | `C:\Jsp\myapp\.idea.bak\` 에 백업해 뒀습니다 |
| 환경변수 | `설정 → 시스템 → 정보 → 고급 시스템 설정 → 환경 변수` 에서 사용자 변수 삭제 |
| 새로 깐 톰캣/Maven | `C:\Java\apache-tomcat-10.1.59`, `C:\Java\apache-maven-3.9.16` 폴더 삭제 |
