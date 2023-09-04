
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
public class StudentQueries {
    
    private static Connection connection;
    
    private static PreparedStatement addStudent;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static PreparedStatement getStudentList;
    
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student){
        connection = DBConnection.getConnection();
        String command = "INSERT INTO APP.STUDENT (STUDENTID, FIRSTNAME, LASTNAME) VALUES (?, ?, ?)";
        try
        {
            addStudent = connection.prepareStatement(command.toUpperCase());
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents(){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> studentList = new ArrayList<>();
        String command = "select StudentID, FirstName, LastName from app.Student";
        try{
            getStudentList = connection.prepareStatement(command.toUpperCase());
            resultSet = getStudentList.executeQuery();
            
            while(resultSet.next())
            {
                // string 1 is semester, string 2 is course code, string 3 is course description and int 4 is the number of seats
                studentList.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
                
        return studentList;
    }
    
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        StudentEntry student;
        String command = "select StudentID, FirstName, LastName from app.Student where studentid = (?)";
        try{
            getStudent = connection.prepareStatement(command.toUpperCase());
            getStudent.setString(1, studentID.toUpperCase());
            resultSet = getStudent.executeQuery();
            if(resultSet.next()){
                student = new StudentEntry(studentID, resultSet.getString(2), resultSet.getString(3));
                return student;
            }
            return null;
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
                
        return null;
    }
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();

        String command = "delete from app.student where studentid = (?)";
        try{
            dropStudent = connection.prepareStatement(command.toUpperCase());
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
            }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
