/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevjava.services;

import pidevjava.entities.Admin;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pidevjava.utils.MyCnx;


/**
 *
 * @author Abirn
 */
public class AdminService {

    public void ajouterAdmin(Admin ad) {

        try {
            String req = "INSERT INTO admin (adminname,lastname,email,password,tel,birthday,gender,image)"
                    + "VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement pst = MyCnx.getInstance().getConnection().prepareStatement(req);

            pst.setString(1, ad.getAdminname());
            pst.setString(2, ad.getLastname());
            pst.setString(3, ad.getEmail());
            pst.setString(4, ad.getPassword());
            pst.setInt(5, ad.getTel());
            pst.setDate(6, ad.getBirthday());
            pst.setString(7, ad.getGender());
            pst.setString(8, ad.getImage());

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Admin added");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateAdmin(Admin ad) {
        try {
            String req = "UPDATE admin SET adminname=?,lastname=?,email=?,tel=?,password=?,birthday=?,gender=? WHERE id=?";

            PreparedStatement pst = MyCnx.getInstance().getConnection().prepareStatement(req);

            pst.setString(1, ad.getAdminname());
            pst.setString(2, ad.getLastname());
            pst.setString(3, ad.getEmail());
            pst.setInt(4, ad.getTel());
            pst.setString(5, ad.getPassword());
            pst.setDate(6, ad.getBirthday());
            pst.setString(7, ad.getGender());
            //pst.setString(9, ad.getImage());
            pst.setInt(8, ad.getId());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Admin updated");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimerAdmin(Admin ad) {
        try {
            String req = "DELETE FROM admin WHERE id=?";

            PreparedStatement pst = MyCnx.getInstance().getConnection().prepareStatement(req);
            pst.setInt(1, ad.getId());

            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Admin deleted");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Admin> displayAdmins() {
        
        List<Admin> adminList = new ArrayList<>();
        try {
            String req = "SELECT * FROM admin ORDER BY birthday,adminname";
            Statement st = MyCnx.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Admin ad = new Admin();

                ad.setId(rs.getInt("id"));
                ad.setAdminname(rs.getString("adminname"));
                ad.setLastname(rs.getString("lastname"));
                ad.setGender(rs.getString("gender"));
                ad.setTel(rs.getInt("tel"));
                ad.setEmail(rs.getString("email"));
                ad.setPassword(rs.getString("password"));
                ad.setBirthday(rs.getDate("birthday"));
                ad.setImage(rs.getString("image"));

                adminList.add(ad);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return adminList;
    }

    public List<Admin> RechercheAdmins(String rech) {

        List<Admin> adminList = new ArrayList<>();
        try {
            String req = "SELECT * FROM admin WHERE adminname LIKE '%" + rech + "%' OR lastname LIKE '%" + rech + "%' ";
            Statement st = MyCnx.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Admin ad = new Admin();
                ad.setId(rs.getInt("id"));
                ad.setAdminname(rs.getString("adminname"));
                ad.setLastname(rs.getString("lastname"));
                ad.setEmail(rs.getString("email"));
                ad.setPassword(rs.getString("password"));
                // u.setImage(rs.getString("image"));
                ad.setGender(rs.getString("gender"));
                ad.setTel(rs.getInt("tel"));
                ad.setBirthday(rs.getDate("birthday"));

                adminList.add(ad);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return adminList;
    }
}
