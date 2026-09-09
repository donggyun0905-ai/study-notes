/*실전문제*/
/*1번 ANSI문을 이용하여 '3월'에 제품을 주문받은 판매자명(aname),
주문번호(ordno)와 주문한 고객명(cname)을 구하시오.*/
select a.aname as 판매자명, o.ordno as 주문번호, c.cname as 고객명
from orders o join customers c on o.cid = c.cid join agents a on o.aid = a.aid
where o.month = '3월';

/*2번 조인 연산자를 두 개를 사용하여  '3월'에 제품을 주문받은 판매자명(aname),
주문번호(ordno)와 주문한 고객명(cname)을 구하시오.*/
select a.aname as 판매자명, o.ordno as 주문번호, c.cname as 고객명
from orders o, agents a, customers c
where o.cid = c.cid and o.aid = a.aid and o.month = '3월';


/*3번 주문가격(wons)이 500,000원 이하 제품을 주문받은 '부산'에 살고 있는
판매자명(aname)을 모두 구하시오*/
select distinct aname as 판매자명
from orders o, agents a
where o.wons <= 500000 and a.acity = '부산';


/*4번 '3월'에 제품을 주문하고 제품보관도시(pcity)가 '대전'인 모든
제품의 이름(pname)을 구하시오.*/
select distinct p.pname as 제품명
from orders o, products p
where o.month = '3월' and p.pcity = '대전' and o.pid = p.pid;

/*5번 고객 'c002'로부터 주문을 받은 판매자가 거주하는 도시(acity)를 구하시오.*/
select distinct a.acity as 거주지
from orders o,agents a
where o.cid = 'c002' and o.aid = a.aid;

/*6번 '대전'에 거주하는 최소한 한 명 이상의 고객이 '경주'에 거주하는
판매자(aid)에게 제품 주문을 하였을 경우 이 제품의 이름(pname)을 구하시오.*/
select distinct p.pname as 제품명
from orders o join customers c on o.cid = c.cid join agents a on o.aid = a.aid join products p on o.pid = p.pid
where c.city = '대전' and a.acity = '경주';
/*---------------------------------------*/
select distinct p.pname as 제품명
from orders o, customers c, agents a, products p
where o.cid = c.cid
  and o.aid = a.aid
  and o.pid = p.pid
  and c.city = '대전'
  and a.acity = '경주';


/*7번 가장 높은 할인율(discnt)을 가진 고객의 이름(cname)과 가장 낮은 할인율을 가진
고객의 이름을 구하시오.
결과
최대값|최소값
------------
백남호|이재성
*/

--오답
select cname , discnt
from customers
where discnt in (select max(discnt) from customers) or
    discnt in (select min(discnt) from customers);


select (select cname from customers where discnt = (select max(discnt) from customers)) as 최대값,
       (select cname from customers where discnt = (select min(discnt) from customers)) as 최소값
from dual;



/*8번 판매자 'a06'을 통해 제품을 주문하지 않고, 판매자 'a03'을 통해 제품을 주문한 경우의 제품번호(pid)를 구하시오.*/
select distinct o.pid
from orders o
where o.aid = 'a03' and pid not in (select pid from orders where aid = 'a06');


/*9번 고객 'c006'에 의해 주문한  제품(pid)을 모두 판매하는 판매자의 이름(aname)을 구하시오.*/
--hint SQL문
--c006 주문한 제품 : p01(가위), p07(싸인펜) => a01(김수현)
select pid,aid from orders where pid in ('p01', 'p07') order by 1;
select distinct pid from orders where cid = 'c006';

select a.aname
from agents a
where a.aid in (select o.aid from orders o where o.pid in (select pid from orders where cid ='c006')
                group by o.aid
                having count(distinct o.pid) = (select count(distinct pid) from orders where cid = 'c006'));


/*10번 '충주'에 살고 있는 고객으로부터 500,000원 이상인 주문을 받은 판매자의
판매자번호(aid)와 판매자명을 구하시오.*/
select distinct a.aid, a.aname
from agents a join orders o on a.aid = o.aid join customers c on o.cid = c.cid
where c.city = '충주' and  o.wons >= 500000;

/*11번 고객에 따라 평균 주문 수량(qty)이 적어도 900이상 주문한 고객의 고객번호(cid)와
고객명을 검색하시오.*/

select o.cid, c.cname
from orders o join customers c on o.cid = c.cid
group by o.cid, c.cname
having avg(o.qty) >= 900;

/*12번 제품 보관도시(pcity)가 '광주'인 제품을 판매하지 않은 '부산'에 거주하는
모든 판매자의 판매번호(aid)를 검색하시오.*/
--hint SQL문
--광주 제품 : p02 p07
select pid, pcity from products where pcity = '광주';
--부산 거주 : a01, a04
select aid from agents where acity = '부산';
--a04만이 p02,p07를 판매하지 않음.
select pid, aid from orders where aid = 'a01' or aid = 'a04' order by 2;

select aid
from agents
where acity = '부산'
  and aid not in (
    select o.aid
    from orders o
    where o.pid in (select pid from products where pcity = '광주')
);


/*13번 '서울' 또는 '충주'에 거주하는 각 고객(cid)들이 한 개의 공통된 제품(pid)을
주문한 경우 주문받은 판매자의 판매번호(aid)를 검색하시오.*/
--hint SQL문
--서울, 충주 : c001, c004, c006
select cid from customers where city in ('서울', '충주');
--pid : p01
select cid, pid from orders where cid in ('c001','c004', 'c006');
--aid : a01, a06
select aid, pid, cid from orders where pid in ('p01');

select distinct o.aid
from orders o
where o.cid in (select cid from customers where city in ('서울', '충주'))
  and o.pid in (
    select pid
    from orders
    where cid in (select cid from customers where city in ('서울', '충주'))
    group by pid
    having count(distinct cid) = (select count(*) from customers where city in ('서울', '충주'))
);

/*14번 판매자 'a03' 또는 'a05'만을 주문한 고객의 고객번호(cid)를 검색하시오.*/
--cid : c001(X), c002(O), c003(O), c006(x)
--hint SQL문
select cid, aid from orders where aid in ('a03', 'a05');
select cid, aid from orders where cid in ('c001', 'c002', 'c003', 'c006');

select distinct cid
from orders
where cid not in (
    select cid from orders where aid not in ('a03','a05')
);

/*15번 '대전'에 거주하는 모든 고객(cid)들이 주문한 제품의 제품번호(pid)와 제품명을 검색하시오.*/
select p.pid, p.pname
from products p
where p.pid in (
    select pid
    from orders
    where cid in (select cid from customers where city = '대전')
    group by pid
    having count(distinct cid) = (select count(*) from customers where city = '대전')
);

/*16번 판매자 'a01'에게 적어도 한 개 이상의 제품을 주문한 고객에 대해
각 고객별로 고객번호(cid)와 'a01'에게 주문한 전체 주문액수(wons)를 검색하는 질의문을 작성하시오.*/
select cid, sum(wons) as 전체주문액수
from orders
where aid = 'a01'
group by cid;