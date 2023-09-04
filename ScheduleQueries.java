
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.apache.derby.vti.ForeignTableVTI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author IsmaelYoussef
 */
public class ScheduleQueries {
    
    private static Connection connection;
    
    private static PreparedStatement addSchedule;
    private static PreparedStatement getScheduleByID;    
    private static PreparedStatement getStudentCount;
    private static PreparedStatement updateScheduleEntry;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement getScheduleStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentsByCourse;
    
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
        String command = "insert into app.Schedule (Semester, CourseCode, StudentID, Status, Timestamp) values (?, ?, ?, ?, ?)";
        try
        {
            addSchedule = connection.prepareStatement(command.toUpperCase());
            addSchedule.setString(1, entry.getSemester());
            addSchedule.setString(2, entry.getCourseCode());
            addSchedule.setString(3, entry.getStudentID());
            addSchedule.setString(4, entry.getStatus());
            addSchedule.setTimestamp(5, entry.getTimestamp());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
        ArrayList<ScheduleEntry> studentScheduleList = new ArrayList<>();
        connection = DBConnection.getConnection();
        String command = "select Semester, CourseCode, StudentID, Status, Timestamp from app.Schedule where Semester = (?) and StudentID = (?)";
        try{
            getScheduleByID = connection.prepareStatement(command.toUpperCase());
            getScheduleByID.setString(1, semester);
            getScheduleByID.setString(2, studentID);
            resultSet = getScheduleByID.executeQuery();
            
            while(resultSet.next())
            {
                // string 1 is semester, string 2 is course code, string 3 is student ID, String 4 is status and timestamp 5 is the timestamp
                studentScheduleList.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
                
        return studentScheduleList;
    }
    
    public static int getScheduledStudentCount(String semester, String courseCode, String status){
        int studentCount = 0;
        connection = DBConnection.getConnection();
        String command = "select count(StudentID)  from app.Schedule where Semester = (?) and CourseCode = (?) and status = (?)";
        try{
            getStudentCount = connection.prepareStatement(command.toUpperCase());
            getStudentCount.setString(1, semester);
            getStudentCount.setString(2, courseCode);
            getStudentCount.setString(3, "S");
            resultSet = getStudentCount.executeQuery();
            while(resultSet.next()){
                studentCount = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        return studentCount;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode){
        ArrayList<ScheduleEntry> scheduledStudents = new ArrayList<>();
        connection = DBConnection.getConnection();
        String command = "select Semester, CourseCode, StudentID, Status, Timestamp from app.Schedule where Semester = (?) and coursecode = (?) and status = (?)";
        try{
            getScheduleStudentsByCourse = connection.prepareStatement(command.toUpperCase());
            getScheduleStudentsByCourse.setString(1, semester);
            getScheduleStudentsByCourse.setString(2, courseCode);
            getScheduleStudentsByCourse.setString(3, "S");
            resultSet = getScheduleStudentsByCourse.executeQuery();
            
            while(resultSet.next())
            {
                // string 1 is semester, string 2 is course code, string 3 is student ID, String 4 is status and timestamp 5 is the timestamp
                scheduledStudents.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
                
        return scheduledStudents;     
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode){
        ArrayList<ScheduleEntry> waitlistedStudents = new ArrayList<>();
        connection = DBConnection.getConnection();
        String command = "select Semester, CourseCode, StudentID, Status, Timestamp from app.Schedule where Semester = (?) and coursecode = (?) and status = (?)";
        try{
            getWaitlistedStudentsByCourse = connection.prepareStatement(command.toUpperCase());
            getWaitlistedStudentsByCourse.setString(1, semester);
            getWaitlistedStudentsByCourse.setString(2, courseCode);
            getWaitlistedStudentsByCourse.setString(3, "W");
            resultSet = getWaitlistedStudentsByCourse.executeQuery();
            
            while(resultSet.next())
            {
                // string 1 is semester, string 2 is course code, string 3 is student ID, String 4 is status and timestamp 5 is the timestamp
                waitlistedStudents.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
                
        return waitlistedStudents;     
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
        connection = DBConnection.getConnection();
        String command = "delete from app.schedule where semester = (?) and studentID = (?) and courseCode = (?)";
        try{
            dropStudentScheduleByCourse = connection.prepareStatement(command.toUpperCase());
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        String command = "delete from app.schedule where semester = (?) and courseCode = (?)";
        try{
            dropScheduleByCourse = connection.prepareStatement(command.toUpperCase());
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        String command = "update app.schedule set status = (?) where semester = (?) and studentid = (?) and coursecode = (?)";
        try{
            updateScheduleEntry = connection.prepareStatement(command.toUpperCase());
            updateScheduleEntry.setString(1, "S");
            updateScheduleEntry.setString(2, semester);
            updateScheduleEntry.setString(4, entry.getStudentID());
            updateScheduleEntry.setString(3, entry.getCourseCode());
            updateScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
