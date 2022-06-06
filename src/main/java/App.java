import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Dao.*;
import Entities.*;
import java.util.*;

public class App {
    private static final int OPTION_REGISTER_STUDENT = 1;
    private static final int OPTION_REGISTER_COURSES = 2;
    private static final int OPTION_ASSIGN_TEACHER = 3;
    private static final int OPTION_ADD_COURSES = 4;
    private static final int OPTION_ADD_TEACHER = 5;
    private static final int OPTION_VIEW_ALL_REGISTERED_COURSES_BY_STUDENT = 6;
    private static final int OPTION_VIEW_ASSIGNED_TEACHER_TO_STUDENT = 7;
    private static final int OPTION_QUIT = 8;

    private static final Scanner scanner;
    private static final ObjectMapper objectMapper;

    static {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        objectMapper=new ObjectMapper();
        StudentDao.getAllStudents();
    }


    public static void main( String[] args )
    {

        boolean quitter=false;
        while(!quitter)
        {
            System.out.println();
            int option = homeOptions();
            System.out.println();

            if (option == OPTION_QUIT)
            {
                quitter = true;
                System.out.println("Good bye");
            }
            else if (option == OPTION_REGISTER_STUDENT)
            {
                registerStudent();
            }
            else if(option == OPTION_REGISTER_COURSES)
            {
                registerCourses();
            }
            else if(option == OPTION_ASSIGN_TEACHER)
            {
                assignTeacher();
            }
            else if (option == OPTION_ADD_COURSES)
            {
                addCourse();
            }
            else if(option == OPTION_ADD_TEACHER)
            {
                addTeacher();
            }
            else if(option == OPTION_VIEW_ALL_REGISTERED_COURSES_BY_STUDENT)
            {
                viewAllRegisteredCoursesByStudent();
            }
            else if(option == OPTION_VIEW_ASSIGNED_TEACHER_TO_STUDENT)
            {
                viewAssignedTeacherToStudent();
            }
            else
            {       System.out.println("Please enter a valid entry");

            }

        }
    }

    private static int homeOptions() {
        int option;

        while (true) {

            System.out.printf("%d -> Register Student%n", OPTION_REGISTER_STUDENT);
            System.out.printf("%d -> Register Courses%n", OPTION_REGISTER_COURSES);
            System.out.printf("%d -> Register Courses%n", OPTION_ASSIGN_TEACHER);
            System.out.printf("%d -> Add Courses%n", OPTION_ADD_COURSES);
            System.out.printf("%d -> Add Teacher%n", OPTION_ADD_TEACHER);
            System.out.printf("%d -> View All Registered Courses By Student%n", OPTION_VIEW_ALL_REGISTERED_COURSES_BY_STUDENT);
            System.out.printf("%d -> View Assigned Teacher TO A Student%n", OPTION_VIEW_ASSIGNED_TEACHER_TO_STUDENT);
            System.out.printf("%d -> Quit%n", OPTION_QUIT);
            System.out.print("Please choose one to continue: ");

            String input = scanner.next();

            try {
                option = Integer.parseInt(input);
                break;
            } catch (Exception e) {
                System.out.println(input + " is not a valid entry (i.e integer)");
            }

            System.out.println();
        }

        return option;
    }

    private static void registerStudent() {
        int studId;
        String studName;
        boolean studentExist=false;

        while(!studentExist)
        {
            System.out.print("Enter your matric number: ");
            studId=scanner.nextInt();
            System.out.print("Enter your name: ");
            studName=scanner.next().trim();

            Student student=StudentDao.getStudent(studId);
            if(student!=null)
            {
                System.out.println("Matric Number Exists");
                registerStudent();
            }
            else
            {
                StudentDao.addStudent(new Student(studId,studName));
                System.out.println("Student successfully registered");
                studentExist=true;
            }
        }
    }

    private static void registerCourses()
    {

        int studId;
        System.out.print("Enter the matric number of the student: ");
        studId = scanner.nextInt();

        Student student = StudentDao.getStudent(studId);
        if(student ==  null)
        {
            System.out.println("Student not registered");
            return;
        }

        Range<Integer> range = Range.closed(5,7);

        boolean quitter=true;
        while(quitter)
        {
            int listSize =StudentDao.getAllRegisteredCourses(student).size();
            String courseCode ;
            if(listSize < range.lowerEndpoint())
            {
                System.out.print("Enter the course code for the course: ");
                courseCode = scanner.next().trim();
                if(CourseDao.isCourseExist(courseCode)) {
                    StudentDao.registerCourse(student, courseCode);
                    System.out.print("course successfully registered \n");
                }

            }
            else if(listSize < range.upperEndpoint())
            {
                System.out.print("Enter quit in lowercase to stop registering or tap enter to continue: ");
                String option= scanner.next().trim().toLowerCase(Locale.ROOT);
                if(option.equals("quit"))
                {
                    quitter=false;
                }
                else
                {
                    System.out.print("Enter the course code for the course: ");
                    courseCode = scanner.next().trim();
                    if(CourseDao.isCourseExist(courseCode)) {
                        StudentDao.registerCourse(student, courseCode);
                        System.out.print("course successfully registered \n");
                    }

                }
            }
            else
            {
                System.out.println("You have reached the limit of courses possible to be registered");
                quitter=false;
            }

        }
    }

    private static boolean assignTeacher()
    {
        System.out.print("Enter the matric number of the student: ");
        int studId= scanner.nextInt();
        Student student = StudentDao.getStudent(studId);
        Teacher teacher=TeacherDao.getRandomTeacher();
        StudentDao.assignTeacherToStudent(student,teacher);
        return true;
    }


    private static boolean addCourse()
    {
        String courseName;
        String courseCode;
        System.out.print("Enter the name of the course: ");
        courseName = scanner.next().trim();
        System.out.print("Enter the course code of the course: ");
        courseCode = scanner.next().trim();

        Course course = CourseDao.getCourse(courseCode);
        if(course != null)
        {

            System.out.println("course already exists");
            return false;
        }
        else {
            Boolean success = CourseDao.addCourse(new Course(courseName));
            if (success) {
                System.out.println("Course successful added");
                return true;
            } else {
                System.out.println("Course not added to db");
                return false;
            }
        }
    }

    private static boolean addTeacher()
    {
        String teacherName;
        System.out.print("Enter the name of the Teacher: ");
        teacherName = scanner.next().trim();

        Boolean success = TeacherDao.addTeacher(new Teacher(teacherName));
        if(success)
        {
            System.out.println("Teacher successful added");
            return true;
        }
        else
        {
            System.out.println("Course not added to db");
            return false;
        }
    }

    private static void viewAllRegisteredCoursesByStudent()
    {
        String matricNumber;
        System.out.print("Enter the matric number of the student: ");
        matricNumber = scanner.next().trim();

        Student student = StudentDao.getStudent(matricNumber);
        if(student ==  null)
        {
            System.out.println("Student not registered");
        }
        else
        {

            try {
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(StudentDao.getAllRegisteredCourses(student)));
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private static void viewAssignedTeacherToStudent()
    {
        String matricNumber;
        System.out.print("Enter the matric number of the student: ");
        matricNumber = scanner.next().trim();

        Student student = StudentDao.getStudent(matricNumber);
        if(student ==  null)
        {
            System.out.println("Student not registered");
        }
        else
        {
            try {
                System.out.println(StudentDao.getAssignedTeacher(student));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

}
