package Dao;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import Entities.Student;
import com.querydsl.jpa.impl.JPAQuery;

import Entities.Course;
import Entities.QCourse;
import Utils.HibernateUtil;

public class CourseDao {
    public static void addCourse(Course course){
        HibernateUtil.doTransaction(session -> session.save(course));
    }

    public static List<Course> getStudentCourses(String studentId){
        AtomicReference<List<Course>> courseList = new AtomicReference<>();
        HibernateUtil.doTransaction(session -> {
            JPAQuery<Course> query = new JPAQuery<>(session.getEntityManagerFactory().createEntityManager());
            QCourse qcourse = QCourse.course;
            List<Course> courses = query.select(qcourse)
                    .from(qcourse).where(qcourse.student.studentId.eq(studentId)).fetch();
            courseList.set(courses);
        });
        return courseList.get();
    }

    public static Course getCourse(String courseCode)
    {
        Preconditions.checkNotNull(courseCode,"Course code cant be null");
        AtomicReference<Course> reference=new AtomicReference<>();
        HibernateUtil.doTransaction(session ->
        {
            JPAQuery<Course> query = new JPAQuery<>(session.getSessionFactory().createEntityManager());
            QCourse qCourse = QCourse.course;
            Course course = query.select(qCourse).
                    from(qCourse).
                    where(qCourse.courseId.eq(courseId)).
                    fetchOne();
            reference.set(course);
        });
        return reference.get();
    }

}
