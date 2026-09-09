--ch08.sql

--dml-데이터 조작어 insert,update,delete

--insert(value(mysql 가능) - 불가능, values)-컬럼명시를 하는게 가독성이 좋음
insert into DEPARTMENTS (DEPARTMENT_id, department_name,manager_id,location_id)
values (271,'Sample_Dept',200,1700);

insert into DEPARTMENTS
values (271,'Sample_Dept',200,1700);

select * from DEPARTMENTS;

--Transaction(트랜잭션)
begin transaction--자동

--DML 여러개 수행
COMMIT -- 모든 변경을 영구 저장
ROLLBACK -- 마지막 COMMIT 시점으로 전체 돌리기

--UPDATE
UPDATE DEPARTMENTS
set manager_id = 201,location_id=1800
where DEPARTMENT_id = 271;

--서브쿼리 결과로 여러 컬럼을 한번에 수정
update DEPARTMENTS
    set (manager_id, location_id) = (select manager_id, location_id
                                     from DEPARTMENTS
                                     where DEPARTMENT_id = 40)
where DEPARTMENT_name = 'Sample_Dept';

-- 특정 컬럼 값을 null로 리턴
update DEPARTMENTS
    set DEPARTMENT_name = null
    where DEPARTMENT_name = 'sample_dept';

rollback;

--delete
delete from DEPARTMENTS
    where DEPARTMENT_name = 'sample_dept';

--서브쿼리로 삭제대상 리턴
delete
from departments
where depart_name in (select department_name
                      from departments
                      where department_name = 'sample_dept');

--DDL - 구조에대한 정의
create table ex1(
    num int primary key,
    name char(10)
);

--복합키
create table ex2(
    num int,
    name char(10),
    primary key(num,name)
             );

insert into ex2 values(1,'aaa');
insert into ex2 values(2,'aaa');
insert into ex2 values(1,'bbb');

select * from ex2;

--자동증가(mysql auto increment mssql-identity)
create table sample_product(
    product_id number,
    product_name varchar(30),
    menu_date date
             );

create sequence sam_seq;

insert into sample_product values (sam_seq.nextval,'television',to_date('140101','YYMMDD'));
insert into sample_product values (sam_seq.nextval,'washer',to_date('150101','YYMMDD'));
insert into sample_product values (sam_seq.nextval,'CLEANER',to_date('160101','YYMMDD'));

select * from sample_product;

--ALTER TABLE - 테이블 구조 수정
--컬럼추가
alter table SAMPLE_PRODUCT
add(factory varchar(10));

--컬럼 자료형/길이 수정
alter table SAMPLE_PRODUCT
modify (product_name varchar(20));

desc SAMPLE_PRODUCT;

--컬럼명 변경
alter table SAMPLE_PRODUCT
rename column factory to factory_name;

--컬럼 삭제
alter table SAMPLE_PRODUCT
drop column factory_name;

--삭제 3형제 - delete, truncate, drop
delete from SAMPLE_PRODUCT; --rollback 가능

truncate table sample_product; -- rollback 불가능, 모든 행 삭제 <- 속도빠름

drop table sample_product; -- 테이블 구조 및 데이터 전체 삭제 <- 속도빠름