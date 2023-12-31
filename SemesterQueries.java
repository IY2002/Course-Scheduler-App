/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author IsmaelYoussef
 */
public class SemesterQueries {
    
    private static Connection connection;
       
    private static PreparedStatement addSemester;
    private static PreparedStatement getSemesterList;
    
    private static ResultSet resultSet;
    
    public static void addSemester(String name)
    {
        connection = DBConnection.getConnection();
        String command = "insert into app.Semester (Semester) values (?)";
        try
        {
            addSemester = connection.prepareStatement(command.toUpperCase());
            addSemester.setString(1, name);
            addSemester.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getSemesterList()
    {
        connection = DBConnection.getConnection();
        ArrayList<String> semester = new ArrayList<String>();
        String command = "select Semester from app.Semester order by Semester";
        try
        {
            getSemesterList = connection.prepareStatement(command.toUpperCase());
            resultSet = getSemesterList.executeQuery();
            
            while(resultSet.next())
            {
                semester.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return semester;
        
    }
    
    
}
