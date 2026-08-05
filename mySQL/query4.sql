--중복체크
SELECT COUNT(phone) FROM dblbember WHERE phone = '010-5555-8888'


--중복제거
SELECT distinct team FROM tblmember 