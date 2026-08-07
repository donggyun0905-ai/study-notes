CREATE TABLE tblRegister(
    id   CHAR(30) PRIMARY KEY,
    pw   CHAR(30) NOT NULL,
    name CHAR(30) NOT NULL
);

CREATE TABLE tblMessage(
    num         INT AUTO_INCREMENT PRIMARY KEY,
    sender_id   CHAR(30) NOT NULL,
    receiver_id CHAR(30) NOT NULL,
    content     VARCHAR(500) NOT NULL,
    send_date   DATETIME NOT NULL
);

-- tblRegister 3개의 레코드 저장
INSERT tblRegister VALUES ('user1','1234','홍길동');
INSERT tblRegister VALUES ('user2','1234','김철수');
INSERT tblRegister VALUES ('user3','1234','이영희');

SELECT * FROM tblRegister;
SELECT * FROM tblMessage;
