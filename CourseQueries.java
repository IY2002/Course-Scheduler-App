
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author IsmaelYoussef
 */
public class CourseQueries {
    
    private static Connection connection;
    
    private static PreparedStatement addCourse;
    private static PreparedStatement dropCourse;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement getCourseCodes;
    private static PreparedStatement getCourseSeats;
    
    private static ResultSet resultSet;
    
    public static ArrayList<CourseEntry> getAllCourses(String semester){
        
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courseList = new ArrayList<>();
        String command = "select Semester, CourseCode, Description, Seats from app.Course where Semester = (?)";
        try{
            getAllCourses = connection.prepareStatement(command.toUpperCase());
            getAllCourses.setString(1, semester);
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next())
            {
                // string 1 is semester, string 2 is course code, string 3 is course description and int 4 is the number of seats
                courseList.add(new CourseEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
                
        return courseList;
    }
    
    public static void addCourse(CourseEntry course){
        connection = DBConnection.getConnection();
        String command = "insert into app.Course (Semester, CourseCode, Description, Seats) values (?, ?, ?, ?)"; 
        try
        {
            addCourse = connection.prepareStatement(command.toUpperCase());
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode()); 
            addCourse.setString(3, course.getDescription()); 
            addCourse.setInt(4, course.getSeats());
            
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester){
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<>();
        String command = "select CourseCode from app.Course where Semester = (?)";
        try{
            getCourseCodes = connection.prepareStatement(command.toUpperCase());
            getCourseCodes.setString(1, semester);
            resultSet = getCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                // string 1 is semester, string 2 is course code, string 3 is course description and int 4 is the number of seats
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
                
        return courseCodes;
    }
    
    public static int getCourseSeats(String semester, String courseCode){
        connection = DBConnection.getConnection();
        int seats = 0;
        String command = "select Seats from app.Course where Semester = (?) and CourseCode = (?)";
        try{
            getCourseSeats = connection.prepareStatement(command.toUpperCase());
            getCourseSeats.setString(1, semester);
            getCourseSeats.setString(2, courseCode);
            resultSet = getCourseSeats.executeQuery();
            
            while(resultSet.next())
            {
                seats = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
                
        return seats;
    }
    
    public static void dropCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        String command = "delete from app.course where semester = (?) and courseCode = (?)";
        try{
            dropCourse = connection.prepareStatement(command.toUpperCase());
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();    
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
}
