package dbHandler;

import exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class UserDbService {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/hotel";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "msqlga2023";
    private static Logger logger = LogManager.getLogger();
    public static boolean checkIfUsExists(String name,String password) throws CustomException {
        boolean usExists = false;
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM hotel .\"user\" ;");
            while (rs.next()){
                if(rs.getString(2).equals(name) && rs.getString(3).equals(password)){
                    usExists=true;
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage());
        }

        return usExists;
    }
    public static boolean checkIfUsIsAdm(String name,String password, boolean isAdm) throws CustomException {
        boolean isAdmRes = false;
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM hotel .\"user\" ;");
            while (rs.next()){
                if(rs.getString(2).equals(name) && rs.getString(3).equals(password) && rs.getBoolean(4) == isAdm){
                    isAdmRes=true;
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage());
        }

        return isAdmRes;
    }
    public static ResultSet getUsersFromDb() throws CustomException {
        ResultSet rs = null;
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            rs = con.createStatement().executeQuery("SELECT * FROM hotel .\"user\" ;");
            con.close();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage());
        }

        return rs;
    }
    public static Integer getIdByNameAndPassword(String name,String password) throws CustomException {
        int resId =0;
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM hotel .\"user\" ;");
            while (rs.next()){
                if(rs.getString(2).equals(name) && rs.getString(3).equals(password)){
                    resId = rs.getInt(1);
                    break;
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage());
        }

        return resId;
    }
    public static void addUsToDb(String name,String password, boolean isAdm) throws CustomException {
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            PreparedStatement ps = con.prepareStatement("INSERT INTO hotel .\"user\""+"(name,password,is_admin) " +"VALUES (?,?,?);");
            ps.setString(1,name);
            ps.setString(2,password);
            ps.setBoolean(3,isAdm);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage());
        }
    }
    public static void delUserFromDb(String name,String password) throws CustomException {
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            PreparedStatement ps = con.prepareStatement("DELETE FROM hotel .\"user\""+"WHERE name = ? AND password = ?;");
            ps.setString(1,name);
            ps.setString(2,password);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage());
        }
    }
}
