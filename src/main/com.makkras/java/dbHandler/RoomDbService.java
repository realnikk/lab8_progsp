package dbHandler;

import exception.CustomException;

import java.sql.*;

public class RoomDbService {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/hotel";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "msqlga2023";
    public static void addRoomToDb(String name,String password,int roomNumber) throws CustomException {
        try {
            int usId = UserDbService.getIdByNameAndPassword(name,password);
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            PreparedStatement ps = con.prepareStatement("INSERT INTO hotel .\"room\""+"(us_id,room_number) " +"VALUES (?,?);");
            ps.setInt(1,usId);
            ps.setInt(2,roomNumber);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException /*| ClassNotFoundException*/ e) {
            throw new CustomException(e.getMessage());
        }
    }
    public static Integer getIdByNumber(Integer number) throws CustomException {
        int resId =0;
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM hotel .\"room\" ;");
            while (rs.next()){
                if(rs.getInt(3)==number){
                    resId = rs.getInt(2);
                    break;
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage());
        }
        return resId;
    }
    public static void delAnyRoomFromDb(int roomNumber) throws CustomException {
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            PreparedStatement ps = con.prepareStatement("DELETE FROM hotel .\"room\""+"WHERE room_number = ?;");
            ps.setInt(1,roomNumber);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage());
        }
    }
    public static boolean checkIfRoomIsOrdered(int roomNumber) throws CustomException {
        boolean roomExists = false;
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            ResultSet rs = con.createStatement().executeQuery("SELECT room_number FROM hotel .\"room\" ;");
            while (rs.next()){
                if(rs.getInt(1) ==roomNumber){
                    roomExists=true;
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage());
        }

        return roomExists;
    }
    public static ResultSet showAllDataForAdm() throws CustomException {
        ResultSet rs = null;
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            rs = con.createStatement().executeQuery("SELECT * FROM hotel .\"room\" INNER JOIN hotel .\"user\" ON " +
                    "hotel .\"room\".us_id =hotel .\"user\".id;");
            con.close();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage());
        }

        return rs;
    }
}
