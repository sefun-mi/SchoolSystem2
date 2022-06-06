package Dao;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import com.google.common.base.Preconditions;

import Entities.Teacher;
import com.querydsl.jpa.impl.JPAQuery;

import Entities.Student;
import Entities.QStudent;
import Utils.HibernateUtil;

public class StudentDao {
    public static void addStudent (Student student) {
        HibernateUtil.doTransaction(session -> session.save(student));}

    public static Student getStudent(int studentId){
        AtomicReference<Student> student = new AtomicReference<>();
        HibernateUtil.doTransaction(session ->{
            JPAQuery<Student> query = new JPAQuery<>(session.getEntityManagerFactory().createEntityManager());
            QStudent qstudent = QStudent.student;
            Student stud = query.select(qstudent)
                    .from(qstudent)
                    .where(qstudent.studentId.eq(studentId))
                    .fetchOne();

            student.set(stud);
        });
        return student.get();
    }

    public static List<Student> getAllStudents(){
        AtomicReference<List<Student>> studentList = new AtomicReference<>();

        HibernateUtil.doTransaction(session -> {
            JPAQuery<Student> query = new JPAQuery<>(session.getEntityManagerFactory().createEntityManager());
            QStudent qstudent = QStudent.student;
            List<Student> students = query.select(qstudent).from(qstudent).fetch();
            studentList.set(students);
        });
        return studentList.get();
    }

    public static boolean assignTeacherToStudent(Student student,Teacher teacher)
    {
        Preconditions.checkNotNull(student,"Student can't be null");
        Preconditions.checkNotNull(teacher,"Teacher can't be null");

        AtomicReference<Teacher> reference = new AtomicReference<>();
        HibernateUtil.doTransaction(session ->
        {
            JPAQuery<Student> query = new JPAQuery<>(session.getSessionFactory().createEntityManager());
            QStudent qStudent = QStudent.student;
            Teacher teacher1 = query.select(qStudent.assignedTeacher).
                    from(qStudent).
                    where(qStudent.matNo.eq(student.getMatNo())).
                    fetchOne();
            reference.set(teacher1);
        });


        if (reference.get() == null) {
            student.assignedTeacher = teacher;
            return HibernateUtil.doTransaction(session -> session.update(student));
        }
        else
        {
            reference.set(null);
            Preconditions.checkNotNull(reference.get(),"Student has already been assigned a teacher");
            return false;
        }
    }
}
