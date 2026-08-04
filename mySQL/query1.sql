--TABLE.sql 저장
--테이블 생성
--컬럼, 레코드(행의 저장 단위)
-- AUTO_INCREMENT: 자동증가
-- not null : 빈 값을 허용하지 않음
-- unique: 중복값 허용 불가
-- PRIMARY KEY : 중복을 허용하지 않고 인덱스가 만들어짐
CREATE TABLE tblMember(
	num INT AUTO_INCREMENT PRIMARY KEY,
	name CHAR(20) NOT null,
	phone CHAR(20) unique NOT null,
	address CHAR(70) NOT null,
	team CHAR(20) NOT null
);

tblmember

-- 입력
INSERT tblmember(NAME, phone, address, team)
VALUES ('홍길동','010-5555-8888','서울시 강남구','산적');

 --테이블 만들때 순서
INSERT tblmember
VALUES (NULL,'소지섭','010-7777-8888','서울시 강남구','김부장');

-- 가져오기 *은 모든 컬럼을 선택(테이블 만들때 순서대로 가져옴)
SELECT * FROM tblmember;

-- 필요한 컬럼 리턴
SELECT num,NAME,phone FROM tblmember;

-- 조건에 맞는 레코드 가져오기(행)
SELECT *
FROM tblMember
WHERE team = 'bts';

SELECT NAME, team
FROM tblMember
WHERE NAME = '홍길동' OR team = 'BTS';

-- 수정
UPDATE tblmember SET NAME = '홍길순', team = 'MC'
WHERE NAME = '홍길동';

-- 삭제
DELETE FROM tblMember
WHERE num = 2;

-- 테이블 전부 삭제(schema 는 유지)
TRUNCATE TABLE tblMember

--테이블 제거
DROP TABLE tblMember

SELECT * FROM tblmember;
