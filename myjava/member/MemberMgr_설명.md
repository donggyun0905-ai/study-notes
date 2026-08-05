# MemberMgr.java 코드 설명

`MemberMgr`는 `tblmember` 테이블에 대한 DB 작업(조회/저장/수정/삭제 등)을 모아둔 클래스입니다.
모든 메소드가 공통적으로 다음 패턴을 따릅니다.

```
Connection con     → DB 연결 객체
PreparedStatement pstmt → SQL 실행 객체 (? 파라미터 바인딩 가능)
ResultSet rs        → SELECT 결과 (조회가 있을 때만 사용)
try { ... } catch { ... } finally { pool.freeConnection(...); }
```

`finally`에서 항상 `pool.freeConnection()`을 호출하는 이유: DB 연결은 개수가 제한된 자원이라
쓰고 나면 반드시 pool에 반납해야 다음 요청이 연결을 재사용할 수 있기 때문입니다.
예외가 발생하든 안 하든 `finally`는 무조건 실행되므로 여기서 반납하는 게 안전합니다.

---

## 1. 생성자 `MemberMgr()`

```java
public MemberMgr() {
    pool = DBConnectionMgr.getInstance();
}
```

- `DBConnectionMgr`는 싱글턴(singleton)으로 구현된 커넥션 풀 관리자입니다.
- `getInstance()`를 호출하면 이미 만들어진 풀 객체를 가져오거나, 없으면 새로 만듭니다.
- `MemberMgr`는 직접 DB에 연결하지 않고, 이 `pool`을 통해서만 커넥션을 빌리고 반납합니다.

---

## 2. `selectCnt()` — 전체 레코드 개수

```java
sql = "select count(*) from tblMember";
pstmt = con.prepareStatement(sql);
rs = pstmt.executeQuery();
if(rs.next()) cnt = rs.getInt(1);
```

- `count(*)`는 결과가 항상 "행 1개, 컬럼 1개"로 나옵니다. (예: `5`)
- `rs.next()`로 그 한 줄로 커서를 이동시키고, `rs.getInt(1)`로 **1번째 컬럼 값**을 꺼냅니다.
- DB 연동이 잘 되는지 확인하는 용도로 자주 쓰입니다 (연결만 되면 무조건 결과가 나오므로).

---

## 3. `insertMember(MemberBean bean)` — 회원 등록

```java
sql = "insert tblmember values (null,?,?,?,?)";
pstmt.setString(1,bean.getName());
pstmt.setString(2,bean.getPhone());
pstmt.setString(3,bean.getAddress());
pstmt.setString(4,bean.getTeam());
int cnt = pstmt.executeUpdate();
if(cnt==1) flag = true;
```

- 테이블 컬럼 순서가 `num, name, phone, address, team`이므로, 첫 번째 값은 `null`을 넣습니다.
  (`num`이 `AUTO_INCREMENT`라 DB가 알아서 번호를 채워줌)
- `?`는 자리표시자(placeholder)이고, `setString(순번, 값)`으로 실제 값을 바인딩합니다.
  → SQL 인젝션 방지 + 문자열 특수문자(`'` 등) 자동 이스케이프 처리됨.
- `executeQuery()`가 아니라 **`executeUpdate()`**를 쓰는 이유: INSERT/UPDATE/DELETE처럼
  "결과 테이블을 리턴하지 않는" DML은 executeUpdate를 쓰고, 반영된 행(row) 개수를 int로 리턴받습니다.
- 1건 성공적으로 들어갔으면 `cnt == 1`이라 `flag = true`가 됩니다.

---

## 4. `listMember()` — 전체 회원 목록

```java
sql = "select * from tblmember";
rs = pstmt.executeQuery();
while(rs.next()){
    MemberBean bean = new MemberBean();
    bean.setNum(rs.getInt("num"));
    ...
    vlist.add(bean);
}
```

- `if(rs.next())`가 아니라 **`while(rs.next())`**를 쓰는 이유: 결과가 여러 줄일 수 있으므로,
  더 이상 다음 행이 없을 때(`false`)까지 반복하면서 한 줄씩 꺼내야 합니다.
- 컬럼을 꺼낼 때 `rs.getInt("num")`처럼 **컬럼명**으로 꺼내면, 나중에 SELECT 절의 컬럼 순서가
  바뀌어도 코드가 안 깨진다는 장점이 있습니다 (인덱스 번호 방식보다 안전).
- 한 줄(레코드) = `MemberBean` 객체 하나로 변환해서 `Vector`에 차곡차곡 담아 리턴합니다.
  (Vector는 옛날 스타일 컬렉션이지만, 여러 개의 빈(Bean)을 담아 화면에 리스트로 뿌릴 때 씁니다.)

---

## 5. `getMember(int num)` — 회원 1명 조회

```java
sql = "select * from tblmember where num = ?";
pstmt.setInt(1,num);
rs = pstmt.executeQuery();
if(rs.next()){
    bean.setNum(rs.getInt(1));
    bean.setName(rs.getString(2));
    ...
}
```

- `where num = ?` 조건으로 특정 회원 1명만 조회하므로, 결과는 최대 1줄 → `if(rs.next())`만으로 충분.
- 이번엔 컬럼을 **인덱스 번호**(`1,2,3...`)로 꺼냈습니다. 테이블 생성 순서(`num,name,phone,address,team`)와
  SELECT * 순서가 같기 때문에 가능한 방식이지만, `listMember()`의 컬럼명 방식보다는 순서에 의존적이라 덜 안전합니다.

---

## 6. `updateMember(MemberBean bean)` — 회원 정보 수정

```java
sql = "update tblmember set name = ? , phone = ?, address = ?, team = ? where num = ?";
pstmt.setString(1,bean.getName());
...
pstmt.setInt(5,bean.getNum());
int cnt = pstmt.executeUpdate();
if(cnt==1) flag = true;
```

- `set`에 들어갈 4개 컬럼 값 + `where`의 `num` 조건값까지 총 5개의 `?`를 순서대로 바인딩합니다.
  (문자열은 `setString`, 정수인 `num`은 `setInt`로 타입을 맞춰야 함)
- 어떤 회원(`num`)의 정보를 새 값으로 덮어쓰는 구조입니다. 1건 수정되면 `true`.

---

## 7. `deleteMember(int num)` — 회원 삭제

```java
sql = "delete from tblmember where num = ?";
pstmt.setInt(1,num);
if(pstmt.executeUpdate() == 1) flag = true;
```

- `num`으로 지정한 회원 한 명만 삭제합니다. `where` 조건이 없으면 테이블 전체가 삭제되므로
  반드시 조건절이 있어야 하는 위험한 SQL 중 하나입니다.
- `executeUpdate()`가 삭제된 행 개수(0 또는 1)를 바로 리턴하므로, `if`문 안에서 바로 비교해서 사용했습니다.
- ✅ 실제 DB로 테스트해서 정상 동작 확인됨 (4건 → 삭제 후 3건 → 복구 후 4건).

---

## 8. `isDuplicatePhone(String phone)` — 전화번호 중복 체크

```java
sql = "select phone from tblmember where phone = ?";
pstmt.setString(1,phone);
rs = pstmt.executeQuery();
if(rs.next()) check = true;
```

- 같은 전화번호를 가진 회원이 이미 있는지 확인하는 메소드입니다.
- `rs.next()`가 `true`라는 건 "조건에 맞는 행이 1개 이상 있다"는 뜻이므로, 곧 "이미 등록된 번호"라는 의미가 됩니다.
- 회원 등록(`insertMember`) 전에 이 메소드로 먼저 검사해서, 중복이면 등록을 막는 용도로 쓸 수 있습니다.
- `unique` 제약(`table.sql`의 `phone CHAR(20) unique`)이 DB 레벨에서도 걸려있어서, 설령 이 체크를 건너뛰어도
  DB가 중복 INSERT 자체를 막아주지만, 이 메소드는 그 전에 미리 사용자에게 안내(alert)를 띄우기 위한 용도입니다.

---

## 9. `getTeamList()` — 팀 이름 목록 (중복 제거)

```java
sql = "select distinct team from tblmember";
rs = pstmt.executeQuery();
while(rs.next()) vlist.add(rs.getString(1));
```

- `distinct` 키워드가 SQL 레벨에서 이미 중복을 제거해줍니다. 즉 team이
  `['배우','배우','가수']`처럼 겹쳐 있어도 `distinct team`은 `['배우','가수']`만 리턴합니다.
- 자바 코드에서 따로 중복 제거 로직(Set 등)을 짤 필요 없이, SQL 한 줄로 처리한 것이 핵심입니다.
- 여러 줄이 나올 수 있으므로 `while(rs.next())`로 반복하면서 팀 이름만 `Vector<String>`에 담아 리턴합니다.
  (콤보박스 등에 팀 목록을 채울 때 사용)

---

## 자주 나오는 패턴 정리

| 상황 | 사용 메소드 | 이유 |
|---|---|---|
| 결과가 없거나 1줄 확인 | `if(rs.next())` | 조건에 맞는 행이 있는지 여부만 판단 |
| 결과가 여러 줄 | `while(rs.next())` | 마지막 행까지 반복 처리 |
| INSERT/UPDATE/DELETE | `executeUpdate()` | 영향받은 행 개수(int)를 리턴 |
| SELECT | `executeQuery()` | 조회 결과(ResultSet)를 리턴 |
| 컬럼 꺼내기 | `getString("컬럼명")` vs `getString(순번)` | 컬럼명이 더 안전, 순번은 SELECT 순서에 의존 |
