---ch03.sql

SELECT object_name, object_type
FROM user_objects
WHERE object_type IN ('TABLE')


---서브쿼리(sub query), 조인(join)
select *
from employees A
join (select *
        from departments
        where department_id = 20) B
on a.department_id=B.department_id;

--select 기본 문법
select *
from employees;

--테이블 구조
desc employees;

select employee_id,first_name, last_name
from employees
order by employee_id desc; --asc 생략가능(오른차순)/desc(내림차순)

--중복제거
select distinct job_id
from employees
order by 1 desc;

--컬럼명 변경(임시적)
select employee_id as 사원번호, first_name 이름, last_name "마지막 이름"
from employees;

--데이터 값 연결하기
select employee_id, first_name||last_name as 성명
from employees;


select employee_id,
        first_name || ' ' || last_name as 성명,
        email || '@' || 'walmart.com' as 이메일
from employees;

--산술처리
select employee_id as 사원번호,
        salary as 급여,
        salary+500 as 추가급여,
        salary-100 as 인하급여,
        (salary*1.1)/2 as 급여조정
from employees;

--where 비교연산자
--대소문자 구분이 되는 case
select *
from employees
where first_name = 'David';

select *
from employees
where employee_id >= 105;

select *
from employees
where salary BETWEEN 10000 and 20000
order by salary desc;

--IN(다중행 연산자)
select *
from employees
where salary in (10000,17000,24000);

--검색 필요한 like: % ~라는
select *
from employees
where job_id like 'AD%';

--조회 조건
select *
from employees
where job_id like 'AD___'; --3개의 길이값

--is null
select *
from employees
where manager_id is null; --manager_id = null

--논리연산자(and or not)
select *
from employees
where salary > 4000 and job_id = 'IT_PROG';

select *
from employees
where salary > 4000 and (job_id = 'IT_PROG' or job_id = 'FI_ACCOUNT');

select *
from employees
--where employee_id <> 105 (동일 조건, 다른 표기)
where employee_id != 105;

select *
from employees
where manager_id is not null;

--연습문제
--1. 1급여(salary)가 8000 이상 12000 이하인 직원을 급여 내림차순으로 조회하시오.
select *
from employees
where salary >= 8000 and salary <=12000
order by salary desc;

--2. job_id가 'SA_'로 시작하는 직원의 사원번호, 이름, job_id를 조회하시오.
select employee_id, job_id, first_name||' '||last_name as name
from employees
--where job_id like 'SA_%' (와일드카드로 오인식되어 SA로 시작하는 모든 job도 걸림)
where job_id like 'SA\_%' escape '\'; --와일드 카드 목적이 아니고 문자 그대로 언더스코어를 찾으려면 escape 처리

--3. manager_id가 없는(NULL) 직원의 이름과 부서번호를 조회하시오.
select employee_id,first_name||' '||last_name as name
from employees
where manager_id is null;

--4. job_id가 'IT_PROG' 또는 'SA_REP'이면서 급여가 5000을 초과하는 직원을 조회하시오. (괄호 사용 필수)
select *
from employees
where job_id = 'IT_PROG' or (job_id = 'SA_REP' and salary > 5000);

INSERT INTO jobs VALUES
    ( 'SAXREP'
    , 'Sales X Representative (Test)'
    , 4000
    , 9000
    );

INSERT INTO employees VALUES
    ( 300
    , 'Test'
    , 'Wildcard'
    , 'TWILDCAR'
    , '650.999.0000'
    , TO_DATE('01-01-2024', 'dd-MM-yyyy')
    , 'SAXREP'
    , 5000
    , NULL
    , 145
    , 80
    );

--테스트 데이터 지우기
DELETE FROM employees WHERE employee_id = 300;

DELETE FROM jobs WHERE job_id = 'SAXREP';



