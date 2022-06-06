package Dao;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import Entities.Course;
import Entities.Student;
import com.querydsl.jpa.impl.JPAQuery;

import Entities.Teacher;
import Entities.QTeacher;
import Utils.HibernateUtil;

public class TeacherDao {
    public static void addTeacher(Teacher teacher){
        HibernateUtil.doTransaction(session -> session.save(teacher));
    }

    public static Teacher getStudentTeacher(String studentId){
        AtomicReference<Teacher> teacher = new AtomicReference<>();
        HibernateUtil.doTransaction(session -> {
            JPAQuery<Teacher> query = new JPAQuery<>(session.getEntityManagerFactory().createEntityManager());
            QTeacher qteacher = QTeacher.teacher;
            Teacher StudTeacher = query.select(qteacher)
                    .from(qteacher).where(qteacher.student.studentId.eq(studentId)).fetchOne();
            teacher.set(StudTeacher);
        });
        return teacher.get();
    }
    public static List<Teacher> getAllTeachers(){
        AtomicReference<List<Teacher>> teacherList = new AtomicReference<>();

        HibernateUtil.doTransaction(session -> {
            JPAQuery<Teacher> query = new JPAQuery<>(session.getEntityManagerFactory().createEntityManager());
            QTeacher qteacher = QTeacher.teacher;
            List<Teacher> teachers = query.select(qteacher).from(qteacher).fetch();
            teacherList.set(teachers);
        });
        return teacherList.get();

    }

    public static Teacher getRandomTeacher() {
        List<Teacher> teachers = getAllTeachers();
        Random rand = new Random();
        return teachers.get(rand.nextInt(teachers.size()));

    }

}
