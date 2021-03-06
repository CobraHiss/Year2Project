package database.operations;


import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import database.ConnectionDB;
import gui.PasswordGenerator;
import model.Employee;

/*
IT Tallaght - 2015, S2
Computing - Year 2, Project
Group 17
George
David Lawlor x00107563
*/

public class EmployeeOperations {

    Statement stmt;
    PreparedStatement pstmt;
    ResultSet rset;

    public ResultSet getEmployees(){
        try {
            String sqlQuery = "SELECT * FROM employee";
            stmt = ConnectionDB.getConn().createStatement();
            rset = stmt.executeQuery(sqlQuery);
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return rset;
    }

    public Employee validatePassword(String uname, char[] pword){
        Employee x = new Employee();
        try{
            String sql = "SELECT empId, deptId, empFName, empLName, position, empStreet, empCity, empCounty, empDOBd, " +
                    "empDOBm, empDOBy, empEmail, salary, empUsername, empPassword, empPic FROM EMPLOYEE " +
                    "WHERE empUsername = ? AND empPassword = ?";
            pstmt = ConnectionDB.getConn().prepareStatement(sql);
            //https://www.owasp.org/index.php/Preventing_SQL_Injection_in_Java
            pstmt.setString(1, uname);
            pstmt.setString(2, PasswordGenerator.hashPassword(new String(pword)));
            rset = pstmt.executeQuery();

            while (rset.next()){
                x = new Employee(rset.getInt(1), rset.getInt(2), rset.getString(3),rset.getString(4),rset.getString(5),
                        rset.getString(6),rset.getString(7),rset.getString(8),rset.getString(9),rset.getString(10),
                        rset.getString(11),rset.getString(12),rset.getDouble(13), rset.getString(14),rset.getString(15),
                        rset.getBytes(16));
            }


        }catch (SQLException sqlE){
            System.out.println("Error in the validate password method");
        }
        return x;
    }

    public Employee getEmployeeOb(int id){
        Employee x = null;
        try{
            String sql = "SELECT empId, deptId, empFName, empLName, position, empStreet, empCity, empCounty, empDOBd, " +
                    "empDOBm, empDOBy, empEmail, salary, empUsername, empPassword, empPic FROM EMPLOYEE " +
                    "WHERE empid = '" +id+ "'";
            stmt = ConnectionDB.getConn().createStatement();
            rset = stmt.executeQuery(sql);

            while (rset.next()) {
                x = new Employee(rset.getInt(1), rset.getInt(2), rset.getString(3), rset.getString(4), rset.getString(5),
                        rset.getString(6), rset.getString(7), rset.getString(8), rset.getString(9), rset.getString(10),
                        rset.getString(11), rset.getString(12), rset.getDouble(13), rset.getString(14), rset.getString(15),
                        rset.getBytes(16));
            }
        }catch (SQLException sqlE){
            System.out.println("Error in the validate password method");
        }
        return x;
    }

    public void updateEmployee(int empId, int dId, String fName, String lName, String position, String street, String city, String county,
                               int day, String month, String year, String email, double salary, File pic, String  uname, String pass) {
        try {
            String sql = "Update employee SET  deptId = ?, empFName = ?, empLName = ?, position = ?, " +
                    "empStreet = ?, empCity = ?, empCounty = ?, empDOBd = ?, empDOBm = ?, empDOBy = ?, empEmail = ?," +
                    " salary = ?, empPic = ?, empUsername = ?, empPassword = ? WHERE empid = '" + empId + "'";
            pstmt = ConnectionDB.getConn().prepareStatement(sql);

            // employee id SEQUENCE
            pstmt.setInt(1, dId); // department id
            pstmt.setString(2, fName); // employee first name
            pstmt.setString(3, lName); // employee last name
            pstmt.setString(4, position); // employee position
            pstmt.setString(5, street); // employee street
            pstmt.setString(6, city); // employee city
            pstmt.setString(7, county); // employee county
            pstmt.setInt(8, day); // employee dob day
            pstmt.setString(9, month); // employee dob month
            pstmt.setString(10, year); // employee dob year
            pstmt.setString(11, email);  //employee email
            pstmt.setDouble(12, salary); // employee salary
            pstmt.setBinaryStream(13, savePic2DB(pic)); // employee picture BLOB
            pstmt.setString(14, uname); // employee username
            pstmt.setString(15, PasswordGenerator.hashPassword(pass)); // employee password
            pstmt.execute();
            System.out.println("Update Successful");
        }
        catch (SQLException sqlE){
            System.out.println(sqlE.getMessage());
        }
    }

    public void updateAdmin(int empId, String fName, String lName, String uName, String pwd){
        try {
            String sql = "Update employee SET  empFName = ?, empLName = ?, empUsername = ?, empPassword = ? WHERE empid = '" + empId + "'";
            pstmt = ConnectionDB.getConn().prepareStatement(sql);

            // employee id SEQUENCE
            pstmt.setString(1, fName); // employee first name
            pstmt.setString(2, lName); // employee last name
            pstmt.setString(3, uName); // employee username
            pstmt.setString(4, pwd); // employee password
            pstmt.execute();
            System.out.println("alright!");
        }
        catch (SQLException sqlE){
            System.out.println(sqlE.getMessage());
        }
    }

    public void addEmployee(int dId, String fName, String lName, String position, String street, String city, String county,
                               int day, String month, String year, String email, double salary, File pic, String  uname, String pass) {
        try {
            String sql = "INSERT INTO employee (empId, deptId, empFName, empLName, position, empStreet, empCity, empCounty, empDOBd, empDOBm, empDOBy, empEmail, salary, empPic, empUsername, empPassword)" +
                    "VALUES (empSeq.nextVal,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = ConnectionDB.getConn().prepareStatement(sql);

            // employee id SEQUENCE
            pstmt.setInt(1, dId); // department id
            pstmt.setString(2, fName); // employee first name
            pstmt.setString(3, lName); // employee last name
            pstmt.setString(4, position); // employee position
            pstmt.setString(5, street); // employee street
            pstmt.setString(6, city); // employee city
            pstmt.setString(7, county); // employee county
            pstmt.setInt(8, day); // employee dob day
            pstmt.setString(9, month); // employee dob month
            pstmt.setString(10, year); // employee dob year
            pstmt.setString(11, email);  //employee email
            pstmt.setDouble(12, salary); // employee salary
            pstmt.setBinaryStream(13, savePic2DB(pic)); // employee picture BLOB
            pstmt.setString(14, uname); // employee username
            pstmt.setString(15, PasswordGenerator.hashPassword(pass)); // employee password
            pstmt.execute();
            System.out.println("Insert Successful");
        }
        catch (SQLException sqlE){
            System.out.println(sqlE.getMessage());
        }
    }

    public void deleteEmployee(int id){
        String sql = "DELETE FROM Employee WHERE empid = '"+id+"'";
        try{
            stmt = ConnectionDB.getConn().createStatement();
            stmt.execute(sql);
        }catch (SQLException sqlE){
            System.out.println(sqlE.getMessage());
        }
    }


    public int getNextID(){
        int max = 0;
        try{
            stmt = ConnectionDB.getConn().createStatement();
            rset = stmt.executeQuery("SELECT MAX(empid) FROM employee");
            while (rset.next()){
                max = rset.getInt(1);
            }
        }catch (SQLException sqlE){
            System.out.println("Error getting the maximum id");
        }
        return (max +1);
    }

    public FileInputStream savePic2DB(File pic) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(pic);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return in;
    }
}