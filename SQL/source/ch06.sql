--ch06.sql

--join: 두 개 이상의 테이블을 특정 컬럼(pk-fk)을 기준으로 연결해서 조회하는 방법

--전통적 조인 and ANSI
select first_name, e.department_id, department_name
from employees e, departments d
where e.department_id = d.department_id;

--ANSI
select first_name, e.department_id, department_name
from employees e join departments d on e.department_id = d.department_id;

--3-way join
--Sales 부서(departments)에 소속 직원(employees)의 직책(jobs)을 리턴하는 문제
select first_name,job_title
from departments d join employees e on d.department_id = e.department_id
    join jobs j on e.job_id = j.job_id
where department_name = 'Sales';

select first_name,job_title
from departments d, employees e, jobs j
where department_name = 'Sales' and d.department_id = e.department_id and e.job_id = j.job_id;

--self join
--직원별 담당 매니저 이름 조회
select e1.employee_id, e1.first_name, e1.manager_id, e2.first_name
from employees e1 join employees e2 on e1.manager_id = e2.employee_id
order by e1.employee_id;

--집합연산자(union,union all, intersect, minus)
--위에 SQL과 밑에 SQL은 컬럼의 개수와 타입이 일치
select department_id from employees
UNION --합집합, 중복제거 + 정렬됨
select department_id from departments

select department_id from employees
UNION ALL  --합집합, 중복제거(정렬 X,더 빠름)
select department_id from departments;

select department_id from employees
intersect -- 교집합: 양쪽에 공통으로 존재하는 값만 리턴이 된다.
select department_id from departments;

select department_id from departments --27개 부서
minus -- 차집합 : 앞 결과 - 뒤 결과
select department_id from employees;
where department_id is not null; --결과: 직원이 한 명도 없는 부서 목록

