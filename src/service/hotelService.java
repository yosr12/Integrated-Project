/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.hotel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DataSource;

/**
 *
 * @author user
 */
public class hotelService implements IService <hotel>{

    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    private Connection conn;

    public hotelService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(hotel h) {

        String req = "insert into hotel (nom,adresse,price,image,description,datedebut,datefin) values "
                + "('" + h.getNom() + "','" + h.getAdresse() + "','" + h.getPrice() + "','" + h.getImage() + "','" + h.getDescription() + "','" + h.getDatedebut() + "','" + h.getDatefin() + "')";

        try {
            ste = conn.createStatement();
            ste.executeUpdate(req);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void update(hotel h) {
        
        String requeteUpdate = "UPDATE  `hotel` set `nom`='" + h.getNom() + "',`adresse`='" + h.getAdresse() + "',`price`='" + h.getPrice() + "',`image`='" + h.getImage() + "',`description`='" + h.getDescription() + "',`datedebut`='" + h.getDatedebut() + "',`datefin`='" + h.getDatefin() + "' where `hotel`.`id`='" + h.getId() + "' ";

        try {
            ste = conn.createStatement();
             ste.executeUpdate(requeteUpdate);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String Req = " delete from hotel where id="+id+" ";
        try {
            ste = conn.createStatement();
            ste.execute(Req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List readAll() {
        String req = "select * from hotel order by price ASC";

        List<hotel> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new hotel(rs.getString("nom"), rs.getString("adresse"), rs.getDouble("price"), rs.getString("image"), rs.getString("description"), rs.getDate("datedebut"), rs.getDate("datefin")));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public hotel readById(int id) {
        hotel h = new hotel();
        String Req = "select * from hotel where id=" + id + "";
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(Req);
            while (rs.next()) {
                h = new hotel(rs.getString("nom"),rs.getString("adresse"),rs.getDouble("price"),rs.getString("image"),  rs.getString("description"), rs.getDate("datedebut"),rs.getDate("datefin"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return h;
    }
    
    
}
