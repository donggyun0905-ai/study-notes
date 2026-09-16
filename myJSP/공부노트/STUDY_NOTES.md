# JSP 개발환경 구축 & 문법 학습 노트

아무것도 없는 컴퓨터에 JSP 개발환경을 처음부터 갖추는 법(Part 1)과, JSP 문법을 기초부터 예제로 익히는 내용(Part 2)을 정리했습니다.

- **Tomcat** 10.1.x · **Maven** 3.9.x · **IDE** IntelliJ IDEA Ultimate
- 대상: JDK는 설치돼 있고 Tomcat·Maven은 없는 상태에서 시작

---

## 목차

**Part 1 — 환경 구축**
0. [준비물](#0-준비물)
1. [Tomcat 설치](#1-tomcat-설치)
2. [Maven 설치 · 환경변수](#2-maven-설치--환경변수-등록)
3. [프로젝트 구조 만들기](#3-maven-웹-프로젝트-구조-만들기)
4. [pom.xml 작성](#4-pomxml-작성)
5. [web.xml 작성](#5-webxml-작성--한글-깨짐-방지)
6. [IntelliJ 실행 설정](#6-intellij에서-톰캣-실행-설정하기)
7. [포트 개념 정리](#7-포트-개념-정리)
8. [수정 반영 규칙](#8-수정하면-뭘-해야-반영되나)
9. [에러 대처법 사전](#9-에러-대처법-사전)
10. [단축키 사전](#10-단축키-사전)

**Part 2 — JSP 문법**
- [JSP란](#20-jsp란)
- [5대 태그 문법](#21-jsp-5대-태그)
- [내장 객체](#22-jsp-내장-객체)
- [예제로 배우는 JSP 문법 (예제 1~8)](#23-예제로-배우는-jsp-문법)
- [자주 하는 실수 모음](#24-자주-하는-실수-모음)
- [다음에 공부할 것](#25-다음에-공부할-것)

---

# Part 1 — 개발환경 구축하기

0부터 시작해서, 브라우저에 첫 JSP 화면이 뜨기까지 순서대로 따라가는 튜토리얼입니다.

## 0. 준비물

JSP는 혼자 실행되지 않습니다. 아래 네 가지가 각자 다른 역할을 맡습니다.

| 구성요소 | 역할 |
|---|---|
| JDK | 자바 코드를 실행하는 엔진. JSP도 결국 자바로 변환되어 여기서 돌아감 |
| Apache Tomcat | JSP·서블릿을 실제로 실행해주는 **웹 서버(서블릿 컨테이너)** |
| Apache Maven | 필요한 라이브러리를 자동으로 받아주고 프로젝트를 빌드해주는 도구 |
| IntelliJ IDEA (Ultimate) | 코드 작성 + 위 세 가지를 편하게 다루는 IDE |

> **중요** — 톰캣은 내가 만드는 프로젝트 폴더 *안에* 넣으면 안 됩니다. 톰캣은 프로젝트를 실행해주는 서버 프로그램이고, 프로젝트는 그 서버가 실행하는 대상입니다. 워드 문서 폴더 안에 워드 프로그램 자체를 넣지 않는 것과 같은 이치입니다. 항상 프로젝트 폴더 *바깥*의 별도 위치(예: `C:\Java\`)에 설치하세요.

## 1. Tomcat 설치

Tomcat은 두 가지 배포판이 있습니다. **zip(Core) 버전**을 추천합니다.

| | 설치 프로그램(.exe) | zip(Core) — 추천 |
|---|---|---|
| 설치 방식 | Windows 서비스로 등록 | 압축만 풀면 끝 |
| 권한 | 관리자 권한 필요, 폴더 접근도 제한될 수 있음 | 일반 사용자 권한으로 충분 |
| 설정 파일 수정 | 번거로움 | 자유롭게 수정 가능 |

1. [tomcat.apache.org](https://tomcat.apache.org/) 에서 **Tomcat 10.1.x**(Servlet 6.0 / JSP 3.1 규격) zip 다운로드
2. 프로젝트 바깥의 원하는 위치, 예: `C:\Java\apache-tomcat-10.1.x` 에 압축 해제

> **버전이 왜 중요한가** — Tomcat 10부터 서블릿 API 패키지명이 `javax.servlet.*` → `jakarta.servlet.*`로 통째로 바뀌었습니다. 2022년 이전 예제·교재 코드를 그대로 쓰면 `import`부터 컴파일 에러가 납니다. 아래는 전부 **Tomcat 10.1 / jakarta 기준**입니다.

## 2. Maven 설치 & 환경변수 등록

1. [maven.apache.org/download.cgi](https://maven.apache.org/download.cgi) 에서 Maven zip 다운로드 후 `C:\Java\apache-maven-3.9.x` 에 압축 해제
2. **사용자 환경변수** 등록 (관리자 권한 불필요 — 시스템 환경변수가 아니라 사용자 환경변수로 등록하면 관리자 권한 없이도 됩니다)

```
JAVA_HOME     = C:\Java\jdk-XX
CATALINA_HOME = C:\Java\apache-tomcat-10.1.x
MAVEN_HOME    = C:\Java\apache-maven-3.9.x
Path         += %JAVA_HOME%\bin;%MAVEN_HOME%\bin;%CATALINA_HOME%\bin
```

> **주의** — 환경변수는 *새로 여는 터미널부터* 적용됩니다. 등록 전에 이미 열려있던 터미널에서는 `mvn` 명령을 못 찾습니다.

확인:
```
java -version
mvn -version
```

## 3. Maven 웹 프로젝트 구조 만들기

Maven은 **폴더 위치 자체가 규칙**입니다. 아래 자리를 지켜야 톰캣과 IntelliJ가 알아서 인식합니다.

```
프로젝트루트\
├── pom.xml                     ← 라이브러리 목록 + 빌드 설정
└── src\main\
    ├── java\                    ← .java 는 반드시 여기
    ├── resources\               ← db.properties 같은 설정파일
    └── webapp\                  ← .jsp/.html/.css/이미지는 반드시 여기
        └── WEB-INF\web.xml      ← 배포 설명서. 브라우저로 직접 열람 불가
```

> **자주 하는 실수** — `.jsp` 파일을 `src\main\java`에 두면 웹에서 절대 안 보입니다. 무조건 `src\main\webapp` 밑이어야 합니다.

## 4. pom.xml 작성

Tomcat 10.1(Servlet 6.0 / JSP 3.1) 규격에 맞춰 버전을 **고정**합니다. `scope=provided`는 "톰캣이 이미 갖고 있으니 완성물(WAR)에는 넣지 마라"는 뜻이고, 없으면 실제로 포함되어야 합니다.

```xml
<packaging>war</packaging>

<dependency>  <!-- 톰캣이 이미 갖고 있음 → WAR에 포함 안 함 -->
  <groupId>jakarta.servlet</groupId>
  <artifactId>jakarta.servlet-api</artifactId>
  <version>6.0.0</version>
  <scope>provided</scope>
</dependency>
<dependency>
  <groupId>jakarta.servlet.jsp</groupId>
  <artifactId>jakarta.servlet.jsp-api</artifactId>
  <version>3.1.1</version>
  <scope>provided</scope>
</dependency>

<dependency>  <!-- JSTL: 톰캣에 없음 → 실제로 WAR 안에 포함되어야 함 -->
  <groupId>jakarta.servlet.jsp.jstl</groupId>
  <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
  <version>3.0.0</version>
</dependency>

<dependency>  <!-- DB 쓸 거면 JDBC 드라이버도 -->
  <groupId>com.mysql</groupId>
  <artifactId>mysql-connector-j</artifactId>
  <version>9.4.0</version>
</dependency>
```

## 5. web.xml 작성 — 한글 깨짐 방지

예전 교재의 인코딩 필터 클래스는 **필요 없습니다**. Servlet 6.0부터는 web.xml 설정 두 줄로 끝납니다.

```xml
<request-character-encoding>UTF-8</request-character-encoding>
<response-character-encoding>UTF-8</response-character-encoding>

<jsp-config>
  <jsp-property-group>
    <url-pattern>*.jsp</url-pattern>
    <page-encoding>UTF-8</page-encoding>
  </jsp-property-group>
</jsp-config>
```

## 6. IntelliJ에서 톰캣 실행 설정하기

같은 톰캣을 IntelliJ에서 실행하는 방법은 두 가지입니다. 용도가 다르니 둘 다 알아두면 좋습니다.

| | A. SmartTomcat (플러그인) | B. 내장 Tomcat Server |
|---|---|---|
| 정체 | 제3자 플러그인 (마켓플레이스에서 설치) | IntelliJ **Ultimate** 공식 기능 |
| 배포 방식 | `src/main/webapp` 원본을 직접 참조 | 빌드된 **복사본(Artifact)**을 배포 |
| JSP 수정 반영 | 새로고침만 하면 바로 반영 | Update 액션 필요 (자동화 가능) |
| 추천 상황 | JSP 위주로 계속 고칠 때 | 파일 하나 클릭으로 바로 열고 싶을 때 |

### A. SmartTomcat 설정

1. Settings → Plugins → 마켓플레이스에서 **SmartTomcat** 검색 후 설치, IDE 재시작
2. **Run → Edit Configurations → + → SmartTomcat**
3. 아래 표대로 입력

| 필드 | 값 | 의미 |
|---|---|---|
| `Tomcat server` | 설치한 Tomcat 경로 | 실행할 톰캣 본체 |
| `Port` | `8080` | 브라우저 접속용 HTTP 포트 |
| `Context path` | `/프로젝트이름` | URL 접두어 |
| `Deployment directory` | `src/main/webapp` | 원본 소스 폴더를 그대로 배포 |
| `Catalina base` | 프로젝트 **밖**의 아무 경로 (예: `~\.SmartTomcat\프로젝트명`) | 실행 캐시 저장 위치. 비워두면 프로젝트 폴더 안에도 캐시가 생길 수 있어, 밖으로 지정해두는 게 깔끔함 |

### B. 내장 Tomcat Server 설정

1. `Ctrl+Alt+Shift+S` → **Artifacts** 탭 → `+` → Web Application: Exploded → For Maven module
2. **Run → Edit Configurations → + → Tomcat Server → Local**
3. Server 탭: Tomcat Home 지정
4. Deployment 탭: 방금 만든 Artifact 선택, Application context 지정
5. **On frame deactivation → Update classes and resources** — 설정해두면 IDE 창에서 브라우저로 포커스만 옮겨도 자동 반영됨

이 방식으로 실행하면, JSP 파일을 열었을 때 에디터 오른쪽 위에 작은 브라우저 아이콘이 뜨고 눌러서 그 파일로 바로 이동할 수 있습니다.

## 7. 포트 개념 정리

| 포트 | 설정 위치 | 용도 | 브라우저로 접속? |
|---|---|---|---|
| `8005`(기본값) | `server.xml`의 `<Server port>` | 톰캣을 **종료**시키는 내부 신호용 | ❌ |
| `8080`(기본값) | `server.xml`의 `<Connector port>` | 실제 웹 요청을 받는 HTTP 포트 | ✅ `localhost:8080/...` |

> **흔한 실수** — IntelliJ 실행 설정의 `Port` 필드(=HTTP 포트)에 실수로 셧다운 포트와 같은 값(예: 8005)을 넣으면, 한 프로세스 안에서 같은 포트를 두 번 열려다 `BindException: Address already in use`가 납니다. 두 포트는 항상 다른 값으로 유지하세요.

## 8. 수정하면 뭘 해야 반영되나

| 고친 파일 | 해야 할 일 | 이유 |
|---|---|---|
| `.jsp` | 브라우저 새로고침만 | 요청이 올 때마다 톰캣(Jasper 엔진)이 변경 여부를 확인하고 알아서 재컴파일함 |
| `.java` | 다시 실행 (`Shift+F10`) | Run은 컴파일(Make)을 자동으로 먼저 함. 이미 떠있는 JVM에 로드된 클래스는 파일을 고쳐도 저절로 안 바뀜 |

"그냥 다시 실행만 하면 반영되던데?"라는 건 — **Run을 누를 때마다 최신 코드로 새로 빌드+배포**하는 과정이 자동으로 딸려오기 때문입니다. 톰캣을 켜둔 채로 부분 갱신(Update 버튼)하는 것과 최종 결과는 같습니다.

## 9. 에러 대처법 사전

| 증상 | 원인 | 해결 |
|---|---|---|
| `Module not specified` | Maven 프로젝트 import가 덜 끝남 | IntelliJ 오른쪽 Maven 창 → Reload All Maven Projects |
| `BindException: Address already in use` | HTTP 포트와 셧다운 포트가 겹침, 또는 이미 켜진 톰캣이 그 포트를 씀 | 포트 값을 서로 다르게, 안 쓰는 톰캣은 종료 |
| `No suitable driver found` | 톰캣이 기동 시 DriverManager를 미리 초기화해서 WEB-INF/lib의 드라이버가 자동 등록 안 됨 | DB 연결 클래스의 static 블록에서 `Class.forName("com.mysql.cj.jdbc.Driver")` 직접 호출 |
| `import ○○Util` 컴파일 에러 | 그 클래스가 "이름 없는 패키지"에 있음. JSP는 항상 이름 있는 패키지의 서블릿으로 변환되는데, 자바 규칙상 이름 있는 패키지에서 이름 없는 패키지를 import 불가 | 유틸 클래스에 `package 이름;` 선언 추가 |
| 폼 제출 시 404 | `<form action="상대경로">`인데 폼과 목적지 파일이 다른 폴더에 있음 | 두 파일을 같은 폴더로 이동하거나 절대경로 사용 |
| `import javax.servlet...` 에러 | Tomcat 10부터 패키지명이 jakarta로 바뀜 | `javax` → `jakarta`로 교체 |

## 10. 단축키 사전

| 단축키 | 기능 |
|---|---|
| `Shift+F10` | 선택된 실행 구성 실행 |
| `Ctrl+Shift+F10` | 현재 커서 위치 기준 문맥 실행 |
| `Alt+Insert` | 선택한 폴더에 새 파일 생성 (템플릿 목록 뜸) |
| `Ctrl+Alt+S` | Settings 열기 |
| `Ctrl+Alt+Shift+S` | Project Structure 열기 (Artifacts 등) |

---

# Part 2 — JSP 문법 학습 노트

JSP가 뭔지부터, 태그 5종류, 내장 객체, 그리고 실제 예제 코드로 문법을 익힙니다.

## 2.0 JSP란

**JSP(JavaServer Pages)**는 HTML 안에 자바 코드를 끼워 넣을 수 있게 해주는 기술입니다. 브라우저가 `.jsp` 파일을 요청하면, 톰캣이 그걸 **자바 서블릿 클래스(.java)로 변환 → 컴파일(.class) → 실행**해서 결과 HTML을 돌려줍니다. 이 변환은 파일이 바뀔 때마다 요청 시점에 자동으로 다시 일어나서, JSP만 고치면 새로고침만으로 반영됩니다.

## 2.1 JSP 5대 태그

다섯 태그의 차이는 **변환된 서블릿의 어느 위치에 코드가 들어가느냐**로 결정됩니다.

| 태그 | 이름 | 설명 |
|---|---|---|
| `<%@ %>` | 지시자(Directive) | 페이지 설정. `page`, `taglib` 등. 실행 코드가 아니라 컴파일 옵션. |
| `<%! %>` | 선언문(Declaration) | 서블릿 클래스의 필드/메소드가 됨. 로드 시 1번, 모든 요청이 공유. |
| `<% %>` | 스크립트릿(Scriptlet) | `_jspService()` 안의 지역변수. 요청마다 새로 생성. |
| `<%= %>` | 표현식(Expression) | 값을 바로 화면에 출력. `out.print(...)`와 동일. |
| `<%-- --%>` | JSP 주석 | 소스보기에도 안 남고, 안의 JSP 코드는 실행 자체가 안 됨. |

### `<%!` vs `<%` — 변환된 서블릿으로 보면

```java
public class example_jsp extends HttpServlet {

    // <%! %> 여기로 들어감 — 클래스 멤버
    String dec = "선언문 변수";
    public String decMethod(){ return dec; }

    public void _jspService(request, response) {
        // <% %> 여기로 들어감 — 메소드 지역변수
        String scriptlet = "스크립트릿";
        ...
    }
}
```

| | `<%! %>` 선언문 | `<% %>` 스크립트릿 |
|---|---|---|
| 생성 시점 | 서블릿 로드 시 딱 한 번 | 요청이 올 때마다 |
| 공유 여부 | 모든 사용자가 공유 (위험할 수 있음) | 요청마다 독립적 (안전) |
| 메소드 선언 | 가능 | 불가능 |

> **실무 주의** — `<%!`에 요청마다 달라져야 하는 값(이름, 입력값 등)을 두면 서블릿 인스턴스를 모든 사용자가 공유하기 때문에 다른 사용자 화면에까지 영향을 줄 수 있습니다. 진짜로 공유돼도 되는 것(유틸 메소드, 상수)에만 쓰세요.

## 2.2 JSP 내장 객체

JSP는 아래 객체들을 `import` 없이 바로 쓸 수 있게 미리 만들어 둡니다.

| 객체 | 역할 |
|---|---|
| `out` | 브라우저로 보내는 출력 스트림. `System.out`과 다름 — `System.out`은 서버 콘솔에, `out`은 브라우저로 감 |
| `request` | 클라이언트 요청 정보. `request.getParameter("name")`으로 폼 값 받음 |
| `response` | 서버 → 클라이언트 응답 제어 |
| `application` | 웹앱 전체가 공유하는 서버 객체. `application.getRealPath("/")`로 실제 배포 경로 확인 가능 |
| `session` | 사용자별로 유지되는 상태 (로그인 등) |
| `pageContext` | 현재 페이지의 각종 정보에 접근 |

## 2.3 예제로 배우는 JSP 문법

아래는 실제로 작성하고 실행까지 확인한 예제들입니다. 순서대로 보면 기초 문법 → 내장 객체 → 외부 클래스 재사용 → 폼 데이터 처리 → 반복문으로 표 만들기로 난이도가 올라갑니다.

### 예제 1 — 가장 기본적인 JSP

스크립트릿과 일반 HTML을 섞어 쓰는 최소 형태.

```jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<%

%>
<h1>hi</h1>
```

### 예제 2 — 내장 객체 & 표현식

`application.getRealPath()`로 실제 배포 경로를 확인하고, `<%= %>`로 변수를 출력. JSP가 서블릿으로 변환되어 저장되는 위치가 `workDir`라는 개념도 함께 익힘.

```jsp
<%@ page contentType="text/html; charset=UTF-8"%>
<%
    String str = "오늘은 머 먹지?";
    //jsp가 servlet으로 변환된 파일을 저장하는 위치 = workDir (server.xml/context.xml에서 지정)
    out.print(application.getRealPath("/")+"<br>");
%>
MSG: <%=str%>
```

**workDir 지정하기** — `src/main/webapp/META-INF/context.xml`에 아래처럼 넣으면 변환된 `.java`/`.class`가 어디 생기는지 원하는 위치로 지정할 수 있습니다.

```xml
<Context workDir="C:/원하는/경로/work" />
```

### 예제 3 — 선언문 · 스크립트릿 · 표현식 · 주석 3종 비교

한 파일 안에서 JSP의 핵심 태그 4가지를 전부 비교.

```jsp
<!-- 선언문(Declaration) -->
<%!
    String dec = "선언문 변수";
    public String decMethod(){ return dec; }
%>
<!-- 스크립트릿(Scriptlet)-->
<%
    String scriptlet = "스크립트릿";
    out.println("내장 객체를 이용한 출력: " + dec + "<br>");
%>
선언문1: <%= dec%><br>
선언문2: <%= decMethod()%><br>
스크립트릿: <%= scriptlet%><br>

<%-- JSP 주석: 소스 보기에도 전혀 안 남음 --%>
<%-- 안에 JSP 코드를 넣으면 실행 자체가 안 됨 → <%= dec %> --%>
```

| 종류 | 문법 | 소스보기에 남나 | 안의 JSP 코드가 실행되나 |
|---|---|---|---|
| HTML 주석 | `<!-- -->` | 남음 | 실행됨 (결과가 주석 안에 그대로 찍힘) |
| 자바 주석 | `// /* */` | `<% %>` 안에서만 유효 | 그 자바 코드 자체가 안 돌아감 |
| **JSP 주석** | `<%-- --%>` | **전혀 안 남음** | **전혀 실행 안 됨** |

### 예제 4 — System.out vs out, 선언문 안에서 유틸 메소드 정의

for문 반복 + `<%!`로 선언한 메소드(`randomColor`) 호출. 서버 콘솔용 출력과 브라우저용 출력의 차이를 비교.

```jsp
<%!
    public static String randomColor(){
        Random r = new Random();
        String rgb = Integer.toHexString(r.nextInt(256));
        rgb += Integer.toHexString(r.nextInt(256));
        rgb += Integer.toHexString(r.nextInt(256));
        return "#"+rgb;
    }
%>
<%
    System.out.println("Tomcat 서버 콘솔창 스트림");
    //out.print : 클라이언트 브라우저로 가는 스트림
    for(int i=0;i<10;i++){
        out.println("<font color=" + randomColor() + ">");
        out.println("오늘은 즐거운 수요일<br>");
        out.println("</font>");
    }
%>
```

> **여기서 나올 수 있는 함정** — `Integer.toHexString()`은 0~15 사이 값이면 한 글자만 돌려줍니다(예: `5`). 색상 코드 3개를 이어 붙이면 `#5a3`처럼 자릿수가 안 맞는 값이 나올 수 있습니다. 항상 두 자리로 고정하려면 `String.format("%02x", ...)`를 씁니다 (예제 5에서 이렇게 고침).

### 예제 5 — 외부 클래스를 유틸리티로 재사용하기

여러 JSP에서 반복해서 쓰는 로직(랜덤 색상)은 별도 자바 클래스로 뽑아서 `import` 후 재사용합니다.

```java
// src/main/java/ch05/MUtil.java
package ch05;

public class MUtil {
    public static String randomColor(){
        Random r = new Random();
        // %02x로 두 자리씩 고정 (예제 4의 함정을 여기서 해결)
        String rgb = String.format("%02x", r.nextInt(256));
        rgb += String.format("%02x", r.nextInt(256));
        rgb += String.format("%02x", r.nextInt(256));
        return "#" + rgb;
    }
}
```

```jsp
<%@ page import="ch05.MUtil" %>
<%
    for(int a = 0; a < 10; a++){
        out.print("<font color=" + MUtil.randomColor() + ">");
        out.println("오늘은 즐거운 수요일</font><br>");
    }
%>
```

> **여기서 만나는 함정** — 유틸 클래스에 `package` 선언이 없으면(=이름 없는 패키지) `import` 자체가 컴파일 에러입니다. JSP는 항상 `org.apache.jsp.ch05` 같은 이름 있는 패키지의 서블릿으로 변환되는데, 자바 언어 규칙상 이름 있는 패키지에서 이름 없는 패키지의 클래스는 import할 수 없기 때문입니다. → 유틸 클래스에는 반드시 `package` 선언을 넣어야 합니다.

### 예제 6 — 폼으로 파라미터 받아 분기 처리

입력 폼(html) → GET 전송 → `request.getParameter()`로 받아서 `if~else if~else`로 분기.

```html
<!-- if.html : 입력 폼 -->
<form method="get" action="if.jsp">
이름 : <input name="name"><p>
좋아하는 색깔 :
<select name="color">
    <option value="blue">파란색</option>
    <option value="red">붉은색</option>
</select>
<input type="submit" value="보내기">
</form>
```

```jsp
<%-- if.jsp : 분기 로직 --%>
<%
    String name = request.getParameter("name");
    String color = request.getParameter("color");
    String msg = "";
    if(color.equals("blue")){ msg = "파란색"; }
    else if(color.equals("red")){ msg = "빨간색"; }
    else{ msg = "기타"; color = "white"; }
%>
<body bgcolor="<%=color%>">
<%=name%>님이 좋아하는 색상은 <%=msg%>입니다.
</body>
```

> **여기서 만나는 함정** — `<form action="if.jsp">`처럼 상대경로를 쓸 때는, 폼(html)과 목적지(jsp)가 **같은 폴더**에 있어야 합니다. 폴더가 다르면 브라우저는 폼 파일 기준으로 경로를 계산해서 엉뚱한 주소로 보내고, 404가 납니다.

### 예제 7 — 반복문으로 표 만들기, 두 가지 방식

같은 결과를 **표현식(`<%= %>`)** 방식과 **내장객체 `out`** 방식, 두 가지로 만들어 비교.

```jsp
<%
String[] topGirlGroups = { "BLACKPINK", "IVE", "aespa", "TWICE", "LE SSERAFIM" };
%>

<!-- 방식 1: 표현식 -->
<table>
<%for(int i=0;i<topGirlGroups.length;i++){%>
  <tr><td><%=i+1%></td>
      <td><font color="<%=randomColor()%>"><%=topGirlGroups[i]%></font></td></tr>
<%}%>
</table>

<!-- 방식 2: out.println -->
<table>
<%
  for(int i=0;i<topGirlGroups.length;i++){
    out.println("<tr>");
    out.println("<td><font color='"+MUtil.randomColor()+"'>"+topGirlGroups[i]+"</font></td>");
    out.println("</tr>");
  }
%>
</table>
```

두 가지 함정이 있었던 예제입니다:

- ✕ `</table> ... <% out.println("<tr>"...) %>` — 테이블이 이미 닫힌 다음에 행을 찍으면 브라우저가 표로 인식하지 못함
  ✓ `<% out.println("<tr>"...) %> ... </table>` — 여는 태그와 닫는 태그 **사이**에서 찍어야 함
- ✕ `<td color="">` — `color`는 `<td>`에 존재하지 않는 속성
  ✓ `<td><font color="...">...</font></td>` — 텍스트 색은 `<font color>`나 `style="color:..."`로

### 예제 8 — 이중 반복문, 구구단표

반복문 **순서**를 바꾸는 것만으로 표의 방향이 바뀐다는 걸 확인하는 예제. 1단~9단을 세로로 나열하려면?

```jsp
<%
// 바깥 루프 = 행(곱하는 수 1~9), 안쪽 루프 = 열(단 1~9)
// → 이렇게 해야 각 단이 "세로로" 1배~9배 순서로 쌓임
for(int j = 1; j < 10; j++){
    out.println("<tr>");
    for(int dan = 1; dan < 10; dan++){
        out.println("<td><font color='" + MUtil.randomColor() + "'>"
                + dan + " * " + j + " = " + (dan*j) + "</font></td>");
    }
    out.println("</tr>");
}
%>
```

**핵심 원리** — 바깥 루프가 화면의 행(가로 한 줄)이 되고, 안쪽 루프가 그 행 안에서 반복되는 열이 됩니다. 바깥을 "단"으로 돌리면 각 단이 한 줄씩 가로로 나오고, 바깥을 "곱하는 수"로 돌리면 모든 단이 같은 줄에서 나란히 세로로 쌓입니다.

```
바깥=단   →  1단 한 줄: 1*1 1*2 1*3 ... 1*9
바깥=곱수 →  1배 한 줄: 1*1 2*1 3*1 ... 9*1  (== 1단,2단,3단...이 세로로)
```

여기에 `<body bgcolor="skyblue">`, `<div align="center">`, `<th bgcolor="lightgray">`를 더하면 배경색·가운데 정렬·헤더 색까지 있는 완성된 표가 됩니다.

## 2.4 자주 하는 실수 모음

| 실수 | 왜 안 되나 |
|---|---|
| 유틸 클래스에 `package` 선언 누락 | JSP에서 import 자체가 컴파일 에러 (예제 5) |
| `Integer.toHexString()`으로 색상 코드 만들기 | 0~15 사이 값에서 한 자리만 나와 깨진 코드가 나올 수 있음 (예제 4) |
| `<td color="...">` | `color`는 `<td>`의 유효한 속성이 아님 (예제 7) |
| 반복문으로 만든 행을 `</table>` 뒤에 출력 | 표가 이미 닫힌 뒤라 브라우저가 표로 인식 못 함 (예제 7) |
| 폼과 목적지 파일을 다른 폴더에 두기 | 상대경로 `action`이 엉뚱한 주소를 가리켜 404 (예제 6) |
| 예전 교재의 `javax.servlet` import | Tomcat 10부터 `jakarta.servlet`으로 이름이 바뀜 |

## 2.5 다음에 공부할 것

여기 정리엔 없지만, 다음 단계로 자연스럽게 이어지는 주제들입니다.

| 주제 | 배우게 될 것 |
|---|---|
| 액션 태그 (`<jsp:include>` 등) | 페이지를 여러 조각(상단/본문/하단)으로 나눠 재사용하기 |
| page 지시자 응용 | 에러 페이지 지정, 페이지 간 이동 방식 (forward/include) 차이 |
| while문 예제 | for문과 다른 반복 구조에서도 같은 원리가 적용되는지 확인 |
| 세션(session) | 로그인 상태처럼 사용자별로 유지되는 값 다루기 |

---

*myapp 개발 수첩 · C:\Jsp\myapp*
