package Entities;

import javax.persistence.*;
import java.util.List;

@Table(name = "student")
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentId")
    private int studentId;

    @Column(name = "studentName",nullable = false)
    private String studentName;

    @Column(name ="studTeacher",nullable)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacherId")
    private Teacher studTeacher;

    @Column(name = "studentCourses")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "courseId")
    private List<Course> studentCourses;

    public Student(String studentName, int studentId, Teacher studTeacher, List<Course> studentCourses) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.studTeacher = studTeacher;
        this.studentCourses = studentCourses;
    }

    public Student(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public Student () {}

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Teacher getStudTeacher() {
        return studTeacher;
    }

    public void setStudTeacher(Teacher studTeacher) {
        this.studTeacher = studTeacher;
    }

    public List<Course> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<Course> studentCourses) {
        this.studentCourses = studentCourses;
    }
}
