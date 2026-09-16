# ch09-2. JDBC & DAO 패턴

---

## 1. JDBC란

**J**ava **D**ata**B**ase **C**onnectivity — 자바에서 DB에 접속해 SQL을 실행하는 표준 API.

```
자바 코드  →  JDBC API  →  JDBC 드라이버(DB별)  →  실제 DB
                            (mysql-connector-j / ojdbc11)
```

- DB마다 드라이버 jar가 다름 → `pom.xml`에 의존성으로 추가
  - MySQL: `com.mysql:mysql-connector-j`
  - Oracle: `com.oracle.database.jdbc:ojdbc11`

---

## 2. DBConnectionMgr — 커넥션 풀 (강사 제공)

### 왜 필요한가
- DB 연결을 매번 새로 만들면 느림 (연결 수립 자체가 비쌈)
- 미리 여러 개 만들어 놓고 **빌려주고 → 반납받고** 재사용 = 커넥션 풀

### 사용법 (3단계)
```java
DBConnectionMgr pool = DBConnectionMgr.getInstance();   // ① 풀 얻기 (싱글톤)
Connection con = pool.getConnection();                  // ② 빌리기
// ... 쿼리 ...
pool.freeConnection(con, pstmt, rs);                    // ③ 반납 (close 아님! 풀에 돌려줌)
```

### 접속 정보 (클래스 상단 필드)
```java
private String _driver = "com.mysql.cj.jdbc.Driver",
_url = "jdbc:mysql://localhost:3306/mydb2?characterEncoding=UTF-8&serverTimezone=UTC",
_user = "root",
_password = "1234";
```

### freeConnection 오버로드 — 상황에 맞게

| 메소드 | 언제 |
|---|---|
| `freeConnection(con)` | 커넥션만 반납 |
| `freeConnection(con, pstmt)` | INSERT / UPDATE / DELETE (ResultSet 없음) |
| `freeConnection(con, pstmt, rs)` | SELECT (ResultSet 있음) |

---

## 3. JDBC 4단계 패턴 — 통째로 외우기

```java
public Vector<TeamBean> listTeam() {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Vector<TeamBean> vlist = new Vector<TeamBean>();

    try {
        con = pool.getConnection();                            // ① 연결
        String sql = "select * from tblteam";
        pstmt = con.prepareStatement(sql);                     // ② SQL 준비
        rs = pstmt.executeQuery();                             // ③ 실행
        while (rs.next()) {                                    // ④ 결과 처리
            vlist.add(new TeamBean(
                rs.getInt(1),      // num   (컬럼 번호는 1부터!)
                rs.getString(2),   // name
                rs.getString(3),   // city
                rs.getInt(4),      // age
                rs.getString(5)    // team
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();                                   // 에러는 콘솔에 찍힘
    } finally {
        pool.freeConnection(con, pstmt, rs);                   // ⑤ 반납 (finally에서 반드시)
    }
    return vlist;
}
```

### 단계별 정리

| 단계 | 코드 | 설명 |
|---|---|---|
| ① 연결 | `con = pool.getConnection()` | DB 커넥션 빌리기 |
| ② 준비 | `pstmt = con.prepareStatement(sql)` | SQL 미리 컴파일 (`?` 자리표시자 사용) |
| ③ 실행 | `rs = pstmt.executeQuery()` (SELECT)<br>`int n = pstmt.executeUpdate()` (그 외) | |
| ④ 처리 | `while (rs.next()) { rs.getXxx(n) }` | 결과 한 행씩 |
| ⑤ 반납 | `pool.freeConnection(...)` in `finally` | 예외 나도 반드시 실행되게 |

### executeQuery vs executeUpdate

| SQL | 메소드 | 반환 |
|---|---|---|
| SELECT | `executeQuery()` | `ResultSet` (결과 테이블) |
| INSERT / UPDATE / DELETE | `executeUpdate()` | `int` (영향받은 행 수) |

---

## 4. PreparedStatement — `?` 자리표시자

```java
sql = "insert tblteam values(null, ?, ?, ?, ?)";
pstmt = con.prepareStatement(sql);
pstmt.setString(1, bean.getName());   // 첫 번째 ?
pstmt.setString(2, bean.getCity());   // 두 번째 ?
pstmt.setInt(3, bean.getAge());       // 세 번째 ?  (int니까 setInt)
pstmt.setString(4, bean.getTeam());   // 네 번째 ?
pstmt.executeUpdate();
```

- `?` 순서는 **1부터**
- 타입에 맞는 setter: `setString`, `setInt`, `setDate` ...
- **왜 `?` 를 쓰나** — 문자열을 직접 이어붙이면(`"... where id='" + id + "'"`) SQL 인젝션 공격에 뚫림. `?` 를 쓰면 값이 SQL 구조를 못 바꿈 (안전)

### ResultSet 읽기
```java
while (rs.next()) {              // 다음 행으로 이동 (있으면 true)
    int num = rs.getInt("num");        // 컬럼 이름으로
    int num = rs.getInt(1);            // 또는 컬럼 번호(1부터)로
    String name = rs.getString("name");
}
```

### count(*) 처럼 결과가 1행 1열일 때
```java
rs = pstmt.executeQuery();
if (rs.next()) {                 // while 아니라 if (한 줄뿐)
    count = rs.getInt(1);
}
```

---

## 5. MySQL vs Oracle — 접속 URL 문법

| DB | URL 형태 |
|---|---|
| MySQL | `jdbc:mysql://호스트:3306/DB이름?옵션` |
| Oracle (SID) | `jdbc:oracle:thin:@호스트:1521:SID` ← **콜론** |
| Oracle (서비스명) | `jdbc:oracle:thin:@호스트:1521/서비스명` ← **슬래시** |

### 오늘 삽질한 것
- `ch09.DBConnectionMgr2`의 URL: `jdbc:oracle:thin:@localhost:1521:xe`
- Oracle 23ai Free는 **SID가 `free`**, `xe`는 서비스명 → `:xe`(SID 문법)로는 접속 실패
- 접속 실패해도 `catch (Exception e)`가 조용히 삼켜서 → 결과가 `0` 으로 나옴
- **에러를 보려면 콘솔의 스택 트레이스를 봐야 함**
- 해결: URL을 `/xe` 로 바꾸거나, 리스너에 `USE_SID_AS_SERVICE_LISTENER = ON`

### Oracle 드라이버 없을 때
```
java.lang.ClassNotFoundException: oracle.jdbc.driver.OracleDriver
```
→ `pom.xml`에 `ojdbc11` 추가 + Maven Reload

---

## 6. DTO / DAO 역할 분리

```
[JSP 화면]          [DAO: TeamMgr]           [DB]
  │  폼 값                │
  ▼                       │
[DTO: TeamBean]  ────────▶ insertTeam(bean) ──▶ INSERT
                          listTeam() ◀──────── SELECT ──▶ Vector<TeamBean>
  ▲                       │
  │  화면에 출력           │
[JSP 화면] ◀───────────────┘
```

| 역할 | 클래스 | 하는 일 |
|---|---|---|
| **DTO** (그릇) | `TeamBean` | 필드 + getter/setter만. 로직 없음 |
| **DAO** (일꾼) | `TeamMgr` | DB 접속해서 CRUD. `TeamBean`을 주고받음 |

- JSP는 SQL을 모름 → DAO에게 시킴
- DAO는 화면을 모름 → DTO에 담아서 돌려줌
- 이렇게 나누면: SQL 바뀌어도 DAO만 고치면 됨, 화면 바뀌어도 JSP만 고치면 됨

---

## 7. Mgr 예제 — 개수 세기

### MysqlMgr.java
```java
public int getTeamCount() {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int count = 0;
    try {
        con = pool.getConnection();
        String sql = "select count(*) from tblTeam";
        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
        if (rs.next()) { count = rs.getInt(1); }   // 1행 1열 → if + getInt(1)
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        pool.freeConnection(con, pstmt, rs);
    }
    return count;
}

public static void main(String[] args) {           // 테스트용 main
    MysqlMgr mgr = new MysqlMgr();
    System.out.println(mgr.getTeamCount());         // 콘솔에 결과 확인
}
```

> DAO에 `main()`을 두고 `System.out.println()` 으로 결과를 찍어보는 것 = 화면(JSP) 없이 DB 로직만 빠르게 테스트하는 방법.

---

## 8. 이렇게 써야 한다 — DAO 메소드 템플릿

### SELECT (목록)
```java
public Vector<XxxBean> listXxx() {
    Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
    Vector<XxxBean> list = new Vector<>();
    try {
        con = pool.getConnection();
        pstmt = con.prepareStatement("select * from tblxxx order by num desc");
        rs = pstmt.executeQuery();
        while (rs.next()) {
            list.add(new XxxBean(rs.getInt(1), rs.getString(2), ...));
        }
    } catch (Exception e) { e.printStackTrace(); }
    finally { pool.freeConnection(con, pstmt, rs); }
    return list;
}
```

### SELECT (상세, 조건)
```java
public XxxBean getXxx(int num) {
    ...
    pstmt = con.prepareStatement("select * from tblxxx where num = ?");
    pstmt.setInt(1, num);
    rs = pstmt.executeQuery();
    XxxBean bean = null;
    if (rs.next()) { bean = new XxxBean(rs.getInt(1), ...); }
    ...
    return bean;
}
```

### INSERT / UPDATE / DELETE
```java
public void insertXxx(XxxBean bean) {
    Connection con = null; PreparedStatement pstmt = null;   // rs 없음!
    try {
        con = pool.getConnection();
        pstmt = con.prepareStatement("insert tblxxx values(null, ?, ?)");
        pstmt.setString(1, bean.getName());
        pstmt.setString(2, bean.getCity());
        pstmt.executeUpdate();                               // executeUpdate!
    } catch (Exception e) { e.printStackTrace(); }
    finally { pool.freeConnection(con, pstmt); }             // 2-arg!
}
```
