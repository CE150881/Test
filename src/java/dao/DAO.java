/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Entity.Category;
import Entity.Food;
import Entity.User;
import context.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msi-pc
 */
public class DAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    //get list of user
    public List<User> getAllUser() {
        List<User> list = new ArrayList<>();
        ResultSet resultSet = DBConnection.querySet("select * from user");
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    list.add(new User(
                            
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            resultSet.getInt(7)
                    ));
                }
            } catch (Exception e) {
            }
        }
        return list;

    }

    //get list of food
    public List<Food> getAllFood() {
        List<Food> list = new ArrayList<>();
        ResultSet resultSet = DBConnection.querySet("select * from food");
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    list.add(new Food(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getInt(5)
                    ));
                }
            } catch (Exception e) {
            }
        }
        return list;

    }

    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        ResultSet resultSet = DBConnection.querySet("select * from category");
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    list.add(new Category(
                            resultSet.getInt(1),
                            resultSet.getString(2)
                    ));
                }
            } catch (Exception e) {
            }
        }
        return list;

    }
    //get food with id

    public Food getFoodByID(String id) {
        String query = "SELECT * FROM Food WHERE FoodID = ?";
        try {
            conn = new DBConnection().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Food(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5)
                );
            }
        } catch (Exception e) {
        }
        return null;
    }

    public List<Food> getFoodByCID(String categoryID) {
        List<Food> list = new ArrayList<>();
        String query = "SELECT * FROM Food WHERE categoryID = ?";
        try {
            conn = new DBConnection().getConnection(); // call function form DBconnection
            ps = conn.prepareStatement(query);
            ps.setString(1, categoryID);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Food(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5)
                ));
            }
        } catch (Exception e) {
        }
        return list;
    }
    // search name of food

    public List<Food> searchFoodByName(String txtSearch) {
        List<Food> list = new ArrayList<>();
        String query = "SELECT * FROM Food WHERE foodName LIKE ?";
        try {
            conn = new DBConnection().getConnection();   // call function form DBconnection
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + txtSearch + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Food(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5)
                ));
            }
        } catch (Exception e) {
        }
        return list;
    }
    //search name of user

    public List<User> searchUserByName(String txtSearch) {
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM User WHERE userName LIKE ?";
        try {
            conn = new DBConnection().getConnection();   // call function form DBconnection
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + txtSearch + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(
                        
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7)));
            }
        } catch (Exception e) {
        }
        return list;
    }
    // get newest food

    public List<Food> getLastFood() {
        List<Food> list = new ArrayList<>();
        ResultSet resultSet = DBConnection.querySet("SELECT * FROM `Food` ORDER BY foodID DESC limit 2");
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    list.add(new Food(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getInt(5)
                    ));
                }
            } catch (Exception e) {
            }
        }
        return list;

    }

    // login to webpage
    public User login(String user, String pass) {
        String query = "SELECT * FROM User WHERE userName = ? AND password =?";
        try {
            conn = new DBConnection().getConnection();       // call function form DBconnection
            ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new User(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7));
            }
        } catch (Exception e) {

        }
        return null;
    }

    //create new account
    public void signup(String userName, String password, String userPhone, String userEmail, String userGender) {
        String query = "INSERT INTO `user` (`userName`, `password`, `userPhone`, `userEmail`, `userGender`, `isSelf`, `isAdmin`) VALUES ( ?, ?, ?, ?, ?, '1', '0')";
        try {
            conn = new DBConnection().getConnection();       // call function form DBconnection
            ps = conn.prepareStatement(query);
            ps.setString(1, userName);
            ps.setString(2, password);
            ps.setString(3, userPhone);
            ps.setString(4, userEmail);
            ps.setString(5, userGender);
            ps.executeUpdate();

        } catch (Exception e) {

        }
    }

    // check if username exist
    public User checkAccountExist(String user) {
        String query = "SELECT * FROM user WHERE userName = ?";
        try {
            conn = new DBConnection().getConnection();       // call function form DBconnection
            ps = conn.prepareStatement(query);
            ps.setString(1, user);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new User(                        
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7));
            }
        } catch (Exception e) {

        }
        return null;
    }

    public void insertBill(String userName, double total, String address) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            String sql = "Insert into bill "
                    + "(userName, total, address) "
                    + "Values (?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setDouble(2, total);
            ps.setString(3, address);
            ps.execute();
        } finally {
            if (con != null) {
                con.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }
// get telephone 
    public String getTelephone(String username) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = DBConnection.getConnection();
            String sql = "Select userPhone "
                    + "From user "
                    + "Where userName = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString(1);
            }
        } finally {
            if (rs != null){
                rs.close();
            }
            if (ps != null){
                ps.close();
            }
            if (con != null){
                con.close();
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        DAO dao = new DAO();

        System.out.println(dao.getFoodByID("1")); // list user
//        System.out.println(dao.getAllFood()); // list food        
//        System.out.println(dao.searchFoodByName("Food"));  //Search Food name
//        System.out.println(dao.searchUserByName("Test"));  //Search User name
//        System.out.println(dao.getLatestFood()); //list of latest food
        // dao.signup("admin", "123", "0913888777", "gg32131@gmail.com", "Nam", "dhdh"); 
        // User a = dao.login("Tester", "123456");
        //     System.out.println(a.getUserName());
    }
}
