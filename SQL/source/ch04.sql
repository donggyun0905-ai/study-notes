--ch04.sql

--ch04.Function 단일행 함수 vs 그룹함수

select sysdate from dual;

select count(*) from employees;

--자동 타입변환
select 1+'2' from dual;

--수동 타입변환
select
    to_char(sysdate,'YY'),
    to_char(sysdate,'YYYY'),
    to_char(sysdate,'MM'),
    to_char(sysdate,'HH:MI:SS PM')
from dual;

select TO_NUMBER('123') + TO_NUMBER('456')
from dual;

--null 처리
select salary * nvl(commission_pct, 1)
from employees;

select count(nvl(commission_pct,1))
from employees;

--decode : 조건 논리(if else)
--department_id = 60 모든 직원 10% 인상
select first_name,department_id,salary 원래급여,
    decode(department_id,60,salary * 1.1, salary) as 인상급여,
    decode(department_id,60,'10% 인상','동결') as 인상여부
from employees
order by department_id;

--case when else end: 다중조건
--9000 이상: 상위급여
--6000 이상: 중위급여
--나머지: 하위급여
select first_name, salary,
    case
    when salary >= 9000 THEN '상위급여'
    when salary >= 6000 THEN '중위급여'
    else '하위급여'
    end as 급여등급
from employees
where job_id = 'IT_PROG';

--급여순위
select
ROWNUM as 가상번호,
employee_id,
salary,
first_name
from employees
order by 3 desc;

--동점처리 함수
select employee_id,salary,
    rank() over(order by salary desc) rank_급여, --동점에게 같은 순위 부여
    dense_rank() over(order by salary desc) dense_rank_급여, --동점에게 같은 순위 부여, 건너뛰지 않고 연속으로 부여하는게 특징
    row_number() over(order by salary desc) row_number_급여 --동점이여도 무조건 고유번호를 부여
from employees;

--그룹함수
select count(salary) 행수
from employees;

--합계, 평균
select to_char(sum(salary), '999,999,999,99') 합계,
    to_char(avg(salary), '999,999,999,99') 평균,
    to_char(sum(salary)/count(salary), '999,999,999,99') 계산된평균
from employees

--문제, 급여를 최대값과 최소값을 받는 사람의 이름을 리턴
select first_name||' '||last_name as 성명, salary
from employees
where salary = (select max(salary) from employees)or salary = (select min(salary) from employees);

select 성명, salary
from(
    select first_name||' '||last_name as 성명, salary,
    rank() over(order by salary asc) as 최저순위,
    rank() over(order by salary desc) as 최고순위
    from employees
    )
where 최저순위 = 1 or 최고순위 = 1;

--group by (~별로) : 그룹 함수
--job_id별로 그룹화 하여 총 급여 및 평균 급여를 내림차순 리턴
select job_id, sum(salary) 총급여, avg(salary) 직급별_평균, count(job_id) 인원
from employees
group by job_id
order by 3 desc;

--다중 그룹
select job_id,manager_id,sum(salary), avg(salary),count(job_id) 인원
from employees
group by job_id, manager_id
order by 1,2;

--having : group by에 대한 조건
--employee_id가 10인 이상 직원(where)
--job_id별로 평균급여 리턴
--평균 급여가 10000이상 리턴
select job_id, avg(salary)
from employees
where employee_id >= 10
group by job_id
having avg(salary) >= 10000
order by 1;















