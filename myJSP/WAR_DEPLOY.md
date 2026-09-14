# WAR 파일 배포 정리

JSP/서블릿 프로젝트를 **WAR(Web Application Archive)** 파일로 만들어서 톰캣에 배포하는 방법 정리입니다.

---

## 1. WAR가 뭔가

**WAR = 웹 애플리케이션 하나를 통째로 압축한 파일.** 확장자만 `.war`일 뿐, 내용물은 `.zip`과 똑같은 구조입니다. 실제로 압축 프로그램으로 열어보면 그대로 열립니다.

배포할 때 폴더를 통째로 복사하는 대신 **파일 하나로 뭉쳐서 옮기기 위한 형태**라고 생각하면 됩니다 — 서버로 옮기기도 쉽고, 버전 관리하기도 쉽습니다.

## 2. WAR 안의 구조

`myapp.war`를 풀면(=exploded 상태) 이런 모양입니다. 이게 프로젝트의 `src/main/webapp` 구조와 거의 동일합니다.

```
myapp.war  (풀면 아래 구조)
├── index.jsp                  ← 웹 루트 = webapp 폴더 내용물이 그대로 최상위로
├── ch03/  ch05/  ch06/
├── css/
├── META-INF/
│   └── context.xml            ← workDir 같은 배포별 설정 (있으면)
└── WEB-INF/                   ← 브라우저에서 직접 접근 불가능한 보호 영역
    ├── web.xml                ← 배포 설명서 (인코딩 설정 등)
    ├── classes/                ← 컴파일된 .class 파일들 (서블릿, 유틸 클래스)
    │   ├── ch05/MUtil.class
    │   └── com/example/myapp/HelloServlet.class
    └── lib/                    ← 의존 라이브러리 jar (JSTL, JDBC 드라이버 등)
        ├── jakarta.servlet.jsp.jstl-3.0.1.jar
        └── mysql-connector-j-9.4.0.jar
```

| 자리 | 브라우저로 직접 접근 | 들어가는 것 |
|---|---|---|
| 최상위(`.jsp`, `.html`, `css/` 등) | ✅ 가능 | 화면에 보여줄 것들 |
| `WEB-INF/` | ❌ 불가능 (보안 목적) | 서버만 쓰는 설정·코드·라이브러리 |

## 3. WAR 만드는 방법

### 방법 A — Maven (지금 쓰는 방식, 권장)

`pom.xml`에 `<packaging>war</packaging>`로 지정해두면, 빌드 명령 한 줄로 만들어집니다.

```powershell
cd C:\Jsp\myapp
mvn clean package
```

결과물: `target\myapp.war`

Maven이 자동으로 해주는 일:
- `src/main/java`의 `.java`를 컴파일해서 `WEB-INF/classes`에 넣음
- `pom.xml`의 의존성(JSTL, JDBC 드라이버 등)을 받아서 `WEB-INF/lib`에 넣음 (단, `scope=provided`인 건 제외 — 톰캣이 이미 갖고 있으므로)
- `src/main/webapp`의 내용을 그대로 최상위에 복사
- 전부 합쳐서 `.war`로 압축

### 방법 B — 수동으로 jar 명령 사용

Maven 없이도 JDK에 포함된 `jar` 명령으로 만들 수 있습니다 (원리 이해용).

```powershell
cd C:\Jsp\myapp\target\myapp   # 이미 풀려있는(exploded) 폴더 기준
jar -cvf ..\myapp.war .
```

`jar` 명령은 `zip`과 내부적으로 거의 같은 포맷을 씁니다 — `.war`도 그냥 특정 구조를 갖춘 `.zip`이라는 걸 보여주는 방법이기도 합니다.

### 방법 C — IntelliJ에서 만들기

**Build → Build Artifacts → myapp:war → Build**

`Project Structure → Artifacts`에 war 타입 아티팩트가 등록되어 있어야 이 메뉴가 뜹니다.

## 4. 배포 방법 (WAR를 톰캣에 올리는 3가지 방법)

### 방법 1 — webapps 폴더에 war 파일 직접 넣기 (가장 기본)

톰캣의 `webapps` 폴더는 `autoDeploy="true"` 설정 덕분에, **파일명과 같은 이름의 폴더로 자동 압축 해제**합니다.

```powershell
# 1. 톰캣 끄기 (켜진 채로 건드리면 파일 잠금 문제로 죽을 수 있음 — 실제로 겪은 문제)
& "C:\Jsp\Tomcat 10.1\apache-tomcat-10.1.59\bin\shutdown.bat"

# 2. 기존 배포본 정리 + war 파일 복사
Remove-Item "C:\Jsp\Tomcat 10.1\apache-tomcat-10.1.59\webapps\myapp" -Recurse -Force -ErrorAction SilentlyContinue
Copy-Item "C:\Jsp\myapp\target\myapp.war" "C:\Jsp\Tomcat 10.1\apache-tomcat-10.1.59\webapps\myapp.war"

# 3. 톰캣 켜기 → 시작하면서 자동으로 압축 풀림
& "C:\Jsp\Tomcat 10.1\apache-tomcat-10.1.59\bin\startup.bat"
```

몇 초 뒤 `webapps` 폴더 안에 `myapp.war` 옆에 `myapp`(풀린 폴더)이 자동으로 생깁니다.

> **⚠️ 실제로 겪은 실수** — 톰캣이 켜진 상태에서 `webapps` 안의 폴더/파일을 직접 지우거나 덮어쓰면, 톰캣이 그 파일을 이미 붙잡고 있어서(Windows 파일 잠금) 충돌로 톰캣 전체가 죽을 수 있습니다. **반드시 끄고 → 바꾸고 → 켜는 순서**를 지켜야 합니다.

### 방법 2 — Tomcat Manager 웹 화면으로 업로드 (배포 수업에서 자주 다루는 방식)

톰캣에는 브라우저로 war 파일을 업로드해서 배포하는 관리자 화면이 기본 포함되어 있습니다.

**사전 준비 — 관리자 계정 만들기**

`conf/tomcat-users.xml`을 열어서 `</tomcat-users>` 바로 위에 추가:

```xml
<role rolename="manager-gui"/>
<user username="admin" password="원하는비밀번호" roles="manager-gui"/>
```

톰캣 재시작 후:

1. `http://localhost:8080/manager/html` 접속 → 방금 만든 계정으로 로그인
2. **WAR file to deploy** 항목에서 **파일 선택** → `target\myapp.war` 선택
3. **Deploy** 버튼 클릭
4. 목록에 `/myapp`이 뜨면 배포 완료 — 톰캣을 끄고 켤 필요 없이 화면에서 바로 배포/중지/재시작 가능

이 방식의 장점은 **파일 탐색기 없이, 톰캣을 끄지도 않고** 배포할 수 있다는 것입니다. 실무 배포 자동화(CI/CD)에서도 이 관리자 화면과 같은 원리의 API를 스크립트로 호출하는 방식을 씁니다.

### 방법 3 — Context 서술자로 war 위치만 알려주기

war 파일을 `webapps`로 옮기지 않고, 원래 있던 위치(`target\myapp.war`)를 그대로 가리키기만 하는 방법입니다.

```xml
<!-- conf/Catalina/localhost/myapp.xml -->
<Context docBase="C:\Jsp\myapp\target\myapp.war" />
```

파일을 복사할 필요가 없어서 편하지만, war를 새로 만들 때마다 톰캣이 변경을 감지하고 다시 풀어야 하므로 실습용으로는 방법 1이 더 명확합니다.

## 5. 세 가지 방법 비교

| | 방법 1: webapps에 복사 | 방법 2: Manager 업로드 | 방법 3: Context 서술자 |
|---|---|---|---|
| 톰캣 재시작 필요 | 필요 (또는 자동 감지 대기) | **불필요** | 필요 |
| 파일 탐색기 필요 | 필요 | **불필요** (브라우저만) | 필요 (설정 파일 한 번) |
| 실습·수업용 적합도 | ★★★ 가장 기본 | ★★★ 실무형 | ★★ 이해용 |

## 6. 확인

```
http://localhost:8080/myapp/
```

배포 후 이 주소로 접속해서 화면이 뜨면 성공입니다.
