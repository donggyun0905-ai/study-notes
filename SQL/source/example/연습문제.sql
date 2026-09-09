/*1번 모든 4개 테이블을 리턴하시오*/

select c.cid as "고객번호",
       c.cname as "고객이름",
       c.city as "고객거주지",
       c.discnt as "고객할인율"
 from CUSTOMERS c;

select a.aid as "판매자번호",
        a.aname as "판매자이름",
        a.acity as "판매자거주지",
        a.apercent as "판매자수수료"
 from AGENTS a;
 
select p.pid as "상품번호",
        p.pname as "상품이름",
        p.pcity as "상품보관도시",
        p.quantity as "상품수량",
        p.price as "상품가격"
 from PRODUCTS p;
 
select o.ordno as "주문번호",
        o.month as "주문월",
        o.cid as "고객번호",
        o.aid as "판매자번호",
        o.pid as "상품번호",
        o.qty as "주문수량",
        o.wons as "주문액수"
 from ORDERS o;

/*2번 고객중에 서울에 사는 고객을 검색하시오.*/
select *
from customers
where city = '서울';

/*3번 고객중에 서울에 살고 있지 않는 고객을 검색하시오.*/ 
select *
from customers
where city != '서울';

/*4번 고객중에 서울에 살고 할인율이 10%이상을 검색하시오.*/
select *
from customers
where city = '서울' and discnt >= 10;

/*5번 고객중에 대전에 살고 있지 않거나 또는 할인율이 8%이상을 검색하시오.*/
select *
from customers
where city != '대전' or discnt >= 8;

/*6번 판매자 중에 부산, 대전에 살고 있는 사람의 이름을 검색하시오.*/
select aname
from agents
where acity = '부산' or acity = '대전';

/*7번 판매자 중에 수수료가 가장 높은 사람의 수수료 구하시오.*/
select max(apercent)
from agents;

/*8번 부산에 살고 성씨가 김씨인 사람의 판매자 번호를 검색하시오*/
select aid
from agents
where acity = '부산' and aname like '김%';
      
/*9번 상품가격이 1000원 이상인 상품이름과 재고수량을 검색하시오.*/
select pname, quantity
from products
where price >= 1000;

/*10번. 제품보관도시가 대전이지 않거나 또는 가격이 500원에서 1500원 
사이의 제품번호를 검색하시오.*/
select pid
from products
where pcity != '대전' or (price > 500 and price < 1500);

/*11번 주문수량이 1000개를 초과한 상품번호를 검색하시오.(중복은 제거)*/
select distinct pid
from orders
where qty > 1000;

/*12번 주문날짜가 1월 아니고 주문액수가 500,000원에서 800,000원사이에
주문한 고객번호를 검색하시오.*/
select distinct cid
from orders
where month != '1월'
  and wons between 500000 and 800000;

/*13번 가위(p01)를 주문한 고객번호를 검색하시오. (하위질의, 조인 각각)*/
--하위질의
select distinct cid
from orders
where pid = (select pid from products where pname = '가위');

--join
select distinct o.cid
from orders o
join products p on o.pid = p.pid
where p.pname = '가위';

/*14번 고객들중에 사는곳이 동일한 사람들의 할인율 평균을 구하시오.*/
select city, avg(discnt) as 평균할인율
from customers
group by city
having count(*) > 1;

select city, avg(discnt)
from customers
group by city
having count(*) > 1;

/*15번 제품보관도시별로 가격의 평균값을 평균값이 높은 순서대로 가져오시오.*/
select pcity, round(avg(price), 2) as 평균가격
from products
group by pcity
order by avg(price) desc;

/*16번 제품가격이 700원 이상인 제품중에 제품 보관도시별로 재고수량을 합을 구하시오.*/
select pcity, sum(quantity) as 재고수량합
from products
where price >= 700
group by pcity;

/*17번 고객별로 주문 평균가격과 합계를 cid 오름차순로 가져오시오.*/
select cid,
       avg(wons) as 평균주문액,
       to_char(sum(wons),'999,999,999') as 주문총액
from orders
group by cid
order by cid asc;

/*18번 제품가격이 1000원 이하의 제품중에 제품 보관도시별로 제품 평균가격이 800원
이상인것만 가져오시오.*/
select pcity, avg(price) as 평균가격
from products
where price <= 1000
group by pcity
having avg(price) >= 800;

/*orders 테이블 FK 삭제*/
ALTER TABLE orders DROP CONSTRAINT FK_CID;
ALTER TABLE orders DROP CONSTRAINT FK_AID;
ALTER TABLE orders DROP CONSTRAINT FK_PID;

/*19번 서울에 사는 고객의 정보를 삭제하라 (rollback)*/

delete from customers
where city = '서울';

select  * from customers;
rollback;


/*20번 대전에 살고 있지 않고 '김,이,박' 성을 가진 고객을 삭제하라. (rollback)*/
delete from customers
where city != '대전' and (cname like '김%' or cname like '이%' or cname like '박%');

/*21번 주문수량이 400개이하의 주문은 모두 삭제 하시오., (rollback)*/
delete from orders
where qty <= 400;

/*22번 주문날짜가 4월이고 주문액수가 720000원 주문을 삭제하라. (rollback)*/
delete from orders
where month = '4월' and wons = 720000;

/*23번 대전에 살고 있는 고객의 거주지를 부산으로 변경하라. (rollback)*/
update customers set city = '부산' where city='대전';
/*24번 할인율이 12% 이상인 고객의 고객번호를 c007로 변경하라 (rollback)*/
update customers set cid='c007' where discnt >= 12;

/*25번 고객테이블에서 이름은 오름차순 그리고 거주지는 내림차순으로 가져오시오.*/
select * from customers
order by cname asc, city desc;

/*26번 제품 보관도시가 서울이지 않은 상품을 수량은 많고, 가격은 낮은 순으로 상품번호를 가져오시오.*/
select pid
from products
where pcity != '서울'
order by quantity desc, price asc;

/*27번 홀수달에 주문한 제품을 수량이 많은 순으로 가져오시오.
hint : mod, substr 함수*/
select *
from orders
where  mod(substr(month,0,1), 2) = 1
order by qty desc;

/*28번 고객테이블의 cid를 외래키로 참조 하면서 테이블명은 hobby이고 컬럼은 hid 주키 자동증가(시퀀스 적용),
data 추가 : h_name 취미명 : char(10), cid 고객번호 이렇게 테이블을 만드시오.
 '인터넷','c001'
 '수영','c001'
 '낚시','c001'
 '독서','c002'*/

create table hobby(
                      hid number primary key,
                      hname char(10),
                      cid char(10),
                      constraint fk_hid foreign key(cid)
                          references customers(cid)
);
create sequence hobby_seq; --자동증가 기능 mysql : auto_increment

insert into hobby(hid,hname,cid)
values (hobby_seq.nextval, '인터넷','c001');

insert into hobby(hid,hname,cid)
values (hobby_seq.nextval,'수영','c001');

insert into hobby(hid, hname,cid)
values (hobby_seq.nextval,'낚시','c001');

insert into hobby(hid,hname,cid)
values (hobby_seq.nextval,'독서','c002');


/*29번 고객명이 김동길인 사람의 취미를 모두 가져오시오. ANSI, 조인, 하위질의(Sub Query)*/

--ANSI 조인
select c.cname, h.hname
from customers c join hobby h on c.cid = h.cid
where c.cname = '김동길';
--오라클 일반 조인
select c.cname, h.hname
from customers c, hobby h
where c.cid = h.cid
and c.cname = '김동길';

--Sub Query
select hname
from hobby
where cid = (select cid from customers where cname = '김동길');

/*30번 제품 보관도시가 광주인 제품의 주문수량을 내림차순으로 가져오시오.(조인, 하위질의 각각)*/
--조인
select p.pcity, o.qty
from orders o join products p on o.pid = p.pid
where p.pcity = '광주'
order by o.qty desc;

--하위질의
select qty
from orders
where pid in (select pid from products where pcity = '광주')
order by qty desc;

/*31번 가위를 주문한 고객번호를 오름차순으로 가져오시오.(조인, 하위질의 각각)*/
--조인
select distinct c.cid
from customers c join orders o on c.cid = o.cid join products p on o.pid = p.pid
where p.pname = '가위'
order by cid asc;

--하위질의
select cid
from customers
where cid in(select cid from orders where pid in (select pid from products where pname='가위'))
order by cid asc;

/*32번 제품보관도시가 광주인 제품을 주문한 고객명을 가져오시오.(조인, 하위질의 각각)*/
--조인
select distinct c.cname
from customers c join orders o on c.cid = o.cid join products p on o.pid = p.pid
where p.pcity = '광주';

--조인+Subquery
select distinct cname
from customers c join orders o on c.cid = o.cid
where o.pid in (select pid from products where pcity='광주');

--only subquery
select cname
from customers
where cid in (select cid from orders where pid in (select pid from products where pcity='광주'));


/*orders 테이블 FK 추가 코드*/
ALTER TABLE orders
    ADD CONSTRAINT FK_CID
        FOREIGN KEY (cid)
            REFERENCES customers(cid);

ALTER TABLE orders
    ADD CONSTRAINT FK_AID
        FOREIGN KEY (aid)
            REFERENCES agents(aid);

ALTER TABLE orders
    ADD CONSTRAINT FK_PID
        FOREIGN KEY (pid)
            REFERENCES products(pid);