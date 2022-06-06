package Entities;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;

@Table(name = "teacher")
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacherId")
    private int teacherId;

    @Column(name = "teacherName")
    private String teacherName;

    @Column(name = "teachStudents")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "studentId")
    private Student teachStudents;

    public Teacher() {
    }

    public Teacher(int teacherId, String teacherName, Student teachStudents) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teachStudents = teachStudents;
    }

    @JsonGetter
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @JsonGetter
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @JsonGetter
    public Student getTeachStudents() {
        return teachStudents;
    }

    public void setTeachStudents(Student teachStudents) {
        this.teachStudents = teachStudents;
    }
}
