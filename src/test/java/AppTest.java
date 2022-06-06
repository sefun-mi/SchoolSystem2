import junit.framework.TestCase;
import org.junit.Test;

public class AppTest extends TestCase {
    @Test
    public void testRegisterCoursesAndGetRegisterdCourses() throws JsonProcessingException {
        StudentDao.registerCourse(std,"csc111");
        StudentDao.registerCourse(std,"csc222");
        StudentDao.registerCourse(std,"csc333");

        //System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(StudentDao.getAllRegisteredCourses(std)));
        assertEquals(3,StudentDao.getAllRegisteredCourses(std).size());

        Range<Integer> range = Range.closed(5,7);

        System.out.println(range.lowerEndpoint());
        int listSize =StudentDao.getAllRegisteredCourses(std).size();
        System.out.println(listSize);
    }
    ObjectMapper objectMapper = new ObjectMapper();
    static Student std=new Student("19cg026489","bebs");
    static Teacher teacher1 = new Teacher("Tobi");
    static Teacher teacher2 = new Teacher("Ecentrals");


    @BeforeClass
    public static void setup() {
        //Add Courses To DB
        CourseDao.addCourse(new Course("intro to java","csc111"));
        CourseDao.addCourse(new Course("intro to c#","csc222"));
        CourseDao.addCourse(new Course("intro to python","csc333"));

        //Add Teachers To DB
        TeacherDao.addTeacher(teacher1);
        TeacherDao.addTeacher(teacher2);
        StudentDao.addStudent(std);
    }
    @Test
    public void testRegisterCoursesAndGetRegisterdCourses() throws JsonProcessingException {
        StudentDao.registerCourse(std,"csc111");
        StudentDao.registerCourse(std,"csc222");
        StudentDao.registerCourse(std,"csc333");

        //System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(StudentDao.getAllRegisteredCourses(std)));
        assertEquals(3,StudentDao.getAllRegisteredCourses(std).size());

        Range<Integer> range = Range.closed(5,7);

        System.out.println(range.lowerEndpoint());
        int listSize =StudentDao.getAllRegisteredCourses(std).size();
        System.out.println(listSize);
    }

    @Test
    public void testGetAllStudentsAndGetStudent()
    {
        StudentDao.addStudent(new Student("1234567890","ecentrals"));
        assertEquals(2,StudentDao.getAllStudents().size());

        Student student = StudentDao.getStudent("19cg026489");
        assertEquals(std.getStudentId(),student.getStudentId());
    }

    @Test
    public void testAssignTeacherAndGetAssignedTeacher() throws JsonProcessingException {

        assertTrue(StudentDao.assignTeacherToStudent(std,teacher1));
        assertEquals(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(teacher1),StudentDao.getAssignedTeacher(std));
    }

}