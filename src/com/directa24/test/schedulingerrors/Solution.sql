
-- Using MySQL Dialect


-- CREATE TABLE PROFESSOR (ID INTEGER PRIMARY KEY AUTO_INCREMENT, NAME TEXT, DEPARTMENT_ID INTEGER, SALARY INTEGER);
-- CREATE TABLE DEPARTMENT (ID INTEGER PRIMARY KEY AUTO_INCREMENT, NAME TEXT);
-- CREATE TABLE COURSE (ID INTEGER PRIMARY KEY AUTO_INCREMENT, NAME TEXT, DEPARTMENT_ID INTEGER, CREDITS INTEGER);
-- CREATE TABLE SCHEDULE (PROFESSOR_ID INTEGER, COURSE_ID INTEGER, SEMESTER INTEGER, YEAR INTEGER);

-- INSERT INTO PROFESSOR (NAME, DEPARTMENT_ID, SALARY)
-- VALUES('Nancy Daniels', 4, 7169),
-- ('Billy Knight', 1, 9793),
-- ('Harry Myers', 4, 25194),
-- ('Antonio Rodriguez', 3, 9686),
-- ('Nicole Gome', 2, 30860),
-- ('Eugene George', 5, 10487),
-- ('Gloria Vasquez', 4, 6353),
-- ('Joyce Flores', 1, 25796),
-- ('Daniel Gilbert', 5, 35678),
-- ('Matthew Stevens', 2, 26648)

-- INSERT INTO DEPARTMENT(ID, NAME)
-- VALUES(3, 'Biological Science'),
-- (5, 'Technology'),
-- (6, 'Humanities and social science'),
-- (2, 'Clinical Medicine'),
-- (4, 'Arts and Humanities'),
-- (1, 'Physical Science');

-- INSERT INTO COURSE (ID, NAME, DEPARTMENT_ID, CREDITS)
-- VALUES(9, 'Clinical Biochemistry', 2, 3),
-- (4, 'Astronomy', 1, 6),
-- (10, 'Clinical Neuroscience', 2, 5),
-- (1, 'Pure Mathematics and Mathematical Statistics', 1, 3),
-- (6, 'Geography', 1, 7),
-- (8, 'Chemistry', 1, 1),
-- (5, 'Physics', 1, 8),
-- (3, 'Earth Science', 1, 7),
-- (7, 'Materials Science and Metallurgy', 1, 5),
-- (2, 'Applied Mathematics and Theoretical Physics', 1, 5);


-- INSERT INTO SCHEDULE (PROFESSOR_ID, COURSE_ID, SEMESTER, YEAR)
-- VALUES (4,4,3,2003),
-- (3,3,1,2011),
-- (1,7,5,2011),
-- (7,7,1,2010),
-- (4,6,1,2001),
-- (9,3,1,2012),
-- (10,2,4,2009),
-- (1,1,3,2014),
-- (1,2,3,2008),
-- (1,7,5,2007);

SELECT DISTINCT p.name AS 'Professor.Name', c.name AS 'Course.Name'
FROM Professor p
         LEFT JOIN Schedule s ON s.professor_id = p.id
         INNER JOIN Course c on c.id = s.course_id
WHERE c.department_id <> p.department_id;