package dao;

import context.DBConnection;
import java.sql.*;

public class GuestDAO {

    public boolean registerGuest(String username, String password, String fullname,
                                 String phone, String email, String passport, String preference) {

        String checkSQL = "SELECT * FROM Accounts WHERE Username = ?";
        String insertAccount = "INSERT INTO Accounts (Username, Password, Role) VALUES (?, ?, 'Guest')";
        String insertCustomer = "INSERT INTO Customers (FullName, Phone, Email, Passport, Preference, MemberType, Points, TotalSpending) " +
                                "VALUES (?, ?, ?, ?, ?, 'Guest', 0, 0)";

        try(Connection conn = DBConnection.getConnection()){

            conn.setAutoCommit(false);

            // check username exists
            PreparedStatement psCheck = conn.prepareStatement(checkSQL);
            psCheck.setString(1, username);
            ResultSet rs = psCheck.executeQuery();
            if(rs.next()){
                return false;
            }

            // insert account
            PreparedStatement psAcc = conn.prepareStatement(insertAccount);
            psAcc.setString(1, username);
            psAcc.setString(2, password);
            psAcc.executeUpdate();

            // insert customer profile
            PreparedStatement psCus = conn.prepareStatement(insertCustomer);
            psCus.setString(1, fullname);
            psCus.setString(2, phone);
            psCus.setString(3, email);
            psCus.setString(4, passport);
            psCus.setString(5, preference);
            psCus.executeUpdate();

            conn.commit();
            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}