# ch13-2. 파일 목록 + 체크박스 다중삭제

---

## 1. 목록 화면 — flist.jsp

```jsp
<jsp:useBean id="mgr" class="ch13.FileloadMgr"/>
<%Vector<FileloadBean> vlist = mgr.listFile();%>
```

`listFile()`은 ch09 Team CRUD 때와 똑같은 JDBC 4단계 패턴 — `select * from tblFileload` → `while(rs.next())`로 `FileloadBean` 하나씩 만들어 `Vector`에 담기.

## 2. 체크박스 전체선택 / 개별선택 — 자바스크립트 패턴

**체크박스가 1개일 때와 여러 개일 때 다르게 동작한다는 점이 핵심**입니다. HTML에서 `name`이 같은 요소가 **1개면 그 요소 자체**를, **2개 이상이면 배열(NodeList)**을 돌려줍니다.

```javascript
function allChk() {
    const f = document.frm;
    const chks = f.fch;                      // name="fch" 인 체크박스들
    const isAllChecked = f.allCh.checked;     // 맨 위 "전체선택" 체크박스 상태

    if(chks.length > 1){
        // 체크박스가 2개 이상 → chks는 배열처럼 동작
        for(i=0; i<chks.length; i++){
            chks[i].checked = isAllChecked;
        }
    }else{
        // 체크박스가 1개뿐 → chks 자체가 그 하나의 엘리먼트
        chks.checked = isAllChecked;
    }

    f.btn.disabled = !isAllChecked;
    f.btn.style.color = isAllChecked ? "blue" : "gray";   // 활성화 여부를 색으로도 표시
}
```

```javascript
function chk() {
    const f = document.frm;
    const chks = f.fch;
    isAnyChecked = false;

    if(chks.length > 1){
        for(i=0; i<chks.length; i++){
            if(chks[i].checked){
                isAnyChecked = true;
                break;              // 하나라도 찾으면 더 볼 필요 없음
            }
        }
    }else{
        if(chks.checked) isAnyChecked = true;
    }

    if(isAnyChecked){
        f.allCh.checked = false;    // 개별로 체크하면 "전체선택"은 자동 해제
    }

    f.btn.disabled = !isAnyChecked;
    f.btn.style.color = isAnyChecked ? "blue" : "gray";
}
```

### HTML 쪽 연결
```jsp
<td><input type="checkbox" name="allCh" onclick="allChk()"></td>
...
<td><input type="checkbox" name="fch" onclick="chk()" value="<%=num%>"></td>
...
<input type="submit" name="btn" value="DELETE" disabled>
```
- `value="<%=num%>"` — 체크박스 자체에 그 행의 PK(`num`)를 실어둠. 나중에 폼 제출하면 `request.getParameterValues("fch")`로 **체크된 것들의 num 값만** 배열로 받게 됨
- `disabled`는 **처음 상태(아무것도 선택 안 함)로는 맞는 값**. `chk()`/`allChk()`가 상황에 따라 풀고 잠급니다

## 3. 삭제 처리 — 한 개씩 vs 한 번에 (`IN` 연산자)

`FileloadMgr`에 같은 일을 하는 메소드가 **두 가지 방식**으로 들어있습니다. 성능 차이를 보여주는 좋은 비교 예제입니다.

### deleteFile() — 선택한 개수만큼 쿼리를 반복 실행

```java
public void deleteFile(int num[]){
    ...
    for(int i=0; i<num.length; i++) {
        String upFile = getFile(num[i]);
        File f = new File(SAVEFOLDER+upFile);
        if(f.exists()) f.delete();               // 실제 파일 삭제

        sql = "delete from tblFileload where num=?";
        pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, num[i]);
        pstmt.executeUpdate();                    // ← 선택한 파일 개수만큼 DB 왕복
    }
}
```
파일 5개를 골라 지우면 **DB에 쿼리를 5번** 날립니다. 개수가 많아지면 그만큼 느려짐(DB 왕복 = 네트워크 지연).

### deleteFile2() — `IN` 연산자로 한 번에

```java
public void deleteFile2(int num[]){
    // 실제 파일 삭제는 어차피 파일마다 따로 해야 함 (파일시스템 API 한계)
    for(int i=0; i<num.length; i++) {
        String upFile = getFile(num[i]);
        File f = new File(SAVEFOLDER+upFile);
        if(f.exists()) f.delete();
    }

    // DB 삭제는 딱 1번의 쿼리로
    con = pool.getConnection();
    sql = "delete from tblFileload where num in (" + MUtil.ph(num.length) + ")";
    //     → num in (?, ?, ?)  처럼 물음표가 선택한 개수만큼 자동으로 생성됨
    pstmt = con.prepareStatement(sql);
    for(int i = 0; i < num.length; i++) {
        pstmt.setInt(1, num[i]);   // ⚠️ 버그 — 아래 참고
    }
    pstmt.executeUpdate();          // ← 몇 개를 고르든 DB 왕복은 딱 1번
}
```

### `MUtil.ph()` — 물음표(`?`) 자동 생성 유틸

```java
// where num in (?,?,?);
public static String ph(int length) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++) {
        sb.append("?");
        if(i < length-1) sb.append(", ");
    }
    return sb.toString();
}
```
`ph(3)` → `"?, ?, ?"`. 선택한 개수가 3개면 `IN (?, ?, ?)`, 5개면 `IN (?, ?, ?, ?, ?)`가 자동으로 만들어집니다. **SQL 문자열에 값을 직접 이어붙이지 않고 물음표 개수만 맞춘 뒤 `setInt`로 채우는 방식** — SQL 인젝션 걱정 없이 가변 개수의 `IN` 절을 만드는 정석적인 방법입니다.

### ⚠️ 발견한 버그 — `deleteFile2`의 파라미터 인덱스

```java
for(int i = 0; i < num.length; i++) {
    pstmt.setInt(1, num[i]);   // ← 항상 "1번째 물음표"에만 넣고 있음
}
```

`setInt(인덱스, 값)`의 **첫 번째 인자는 "몇 번째 물음표냐"**인데, 반복문 안에서 **계속 `1`로 고정**돼 있습니다. 그래서:

- 파일 1개 선택 → 물음표도 1개뿐이라 **우연히 맞음** (그래서 지금까지 안 걸렸을 가능성이 큼)
- 파일 2개 이상 선택 → 2번째, 3번째... 물음표에는 아무 값도 안 들어간 채로 실행 → **"파라미터 값이 다 설정 안 됐다"는 예외** 발생 (`executeUpdate()`에서 터짐)

**고치려면:**
```java
pstmt.setInt(i + 1, num[i]);   // 1번째, 2번째, 3번째... 물음표에 순서대로
```

> 파일 하나만 지울 땐 안 걸리고 여러 개 동시에 지울 때만 터지는 전형적인 "숨어있는 버그" 패턴입니다. 체크박스로 2개 이상 골라서 DELETE 눌러보면 재현됩니다.

## 4. 언제 뭘 쓰나

| 상황 | 추천 |
|---|---|
| 선택 개수가 거의 항상 1~2개 | `deleteFile()`도 무방 (차이 미미) |
| 선택 개수가 많아질 수 있음(게시판 관리 화면 등) | `deleteFile2()` — DB 왕복 횟수를 줄이는 게 실무에서 중요 |
