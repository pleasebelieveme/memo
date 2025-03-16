use academy;

-- 1번
-- 수강생을 관리하는 MANAGER 테이블을 만들어보세요.
-- 컬럼은 총 id, name, student_code 입니다.
-- id는 bigint 타입이며 PK입니다.
-- name은 최소 2자 이상, varchar 타입, not null 입니다.
-- student_code는 STUDENT 테이블을 참조하는 FK이며 not null 입니다.
-- FK는 CONSTRAINT 이름을 ‘manager_fk_student_code’ 로 지정해야합니다.
CREATE TABLE IF NOT EXISTS MANAGER (
    id bigint primary key,
    name varchar(100) not null,
    student_code varchar(100) not null,
    constraint manager_fk_student_code foreign key(student_code) references STUDENT(student_code)
);


-- 2번
-- ALTER, MODIFY를 이용하여 MANAGER 테이블의 id 컬럼에 AUTO_INCREMENT 기능을 부여하세요.
ALTER TABLE MANAGER MODIFY COLUMN id bigint auto_increment;


-- 3번
-- INSERT를 이용하여 수강생 s1, s2, s3, s4, s5를 관리하는 managerA와 s6, s7, s8, s9를 관리하는 managerB를 추가하세요.
-- AUTO_INCREMENT 기능을 활용하세요
INSERT INTO MANAGER(name, student_code) VALUES('managerA', 's1');
INSERT INTO MANAGER(name, student_code) VALUES('managerA', 's2');
INSERT INTO MANAGER(name, student_code) VALUES('managerA', 's3');
INSERT INTO MANAGER(name, student_code) VALUES('managerA', 's4');
INSERT INTO MANAGER(name, student_code) VALUES('managerA', 's5');

INSERT INTO MANAGER(name, student_code) VALUES('managerB', 's6');
INSERT INTO MANAGER(name, student_code) VALUES('managerB', 's7');
INSERT INTO MANAGER(name, student_code) VALUES('managerB', 's8');
INSERT INTO MANAGER(name, student_code) VALUES('managerB', 's9');


-- 4번
-- JOIN을 사용하여 managerA가 관리하는 수강생들의 이름과 시험 주차 별 성적을 가져오세요.
SELECT s.name, e.exam_seq, e.score
FROM MANAGER m JOIN STUDENT S on m.student_code  = s.student_code
               JOIN EXAM e on m.student_code  = e.student_code WHERE m.name = 'managerA';


-- 5번
-- STUDENT 테이블에서 s1 수강생을 삭제했을 때 EXAM에 있는 s1수강생의 시험성적과 MANAGER의 managerA가 관리하는 수강생 목록에 자동으로 삭제될 수 있도록 하세요.
-- ALTER, DROP, MODIFY, CASCADE 를 사용하여 EXAM, MANAGER 테이블을 수정합니다.
ALTER TABLE EXAM DROP CONSTRAINT exam_fk_student_code;
ALTER TABLE EXAM ADD CONSTRAINT exam_fk_student_code FOREIGN KEY(student_code) REFERENCES STUDENT(student_code) ON DELETE CASCADE;
ALTER TABLE MANAGER DROP CONSTRAINT manager_fk_student_code;
ALTER TABLE MANAGER ADD CONSTRAINT manager_fk_student_code FOREIGN KEY(student_code) REFERENCES STUDENT(student_code) ON DELETE CASCADE;

DELETE FROM STUDENT WHERE student_code = 's1';