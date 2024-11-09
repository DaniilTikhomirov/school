SELECT student.name, age, faculty.name FROM student
INNER JOIN public.faculty ON faculty.id = student.facultyid
WHERE faculty.name = 'hogwarts';

SELECT student.* FROM student
INNER JOIN public.avatar on avatar.student_id = student.id
