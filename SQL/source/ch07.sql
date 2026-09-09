--ch07.sql

--Sub Query(하위질의)
-- 'De Haan' 과 같은 급여를 받는 직원

select *
from employees
where last_name = 'De Haan'; --17000

select *
from employees
where salary = 17000;

select *
from employees
where salary = (select salary from employees where last_name = 'De Haan');

-- 'Taylor'과 같은 급여를 받는 직원
-- 단일행 연산자 (=,>,<): 서브쿼리 결과가 반드시 1개의 행이 리턴
select *
from employees
where salary = (select salary from employees where last_name = 'Taylor');

--서브쿼리 결과 개수가 불확실하면 in 연산자 사용
select *
from employees
where salary in (select salary from employees where last_name = 'Taylor');

--다중컬럼 서브쿼리
-- job_id 별로 가장 작은 급여를 받는 사람 리턴
select first_name, job_id, salary
from employees
where (job_id,salary) in (select job_id, min(salary)
                          from employees
                          group by job_id
                          )
order by salary;

--인라인 뷰(inline view)
create view vEmp
as
select first_name, last_name, salary
from EMPLOYEES
where salary >= 5000;

select *
from vEmp;

--인라인 뷰(inline view)
select *
from employees A, (select department_id
                    from departments
                    where department_name='IT') B
where A.department_id = B.department_id;
