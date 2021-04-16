/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.maison;
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
public class maisonService implements IService<maison>{

    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    private Connection conn;

    public maisonService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(maison m) {

        String req = "insert into maisondhote (nom,adresse,price,image,description,datedebut,datefin) values "
                + "('" + m.getNom() + "','" + m.getAdresse() + "','" + m.getPrice() + "','" + m.getImage() + "','" + m.getDescription() + "','" + m.getDatedebut() + "','" + m.getDatefin() + "')";

        try {
            ste = conn.createStatement();
            ste.executeUpdate(req);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void update(maison m) {
        String requeteUpdate = "UPDATE  `maisondhote` set `nom`='" + m.getNom() + "',`adresse`='" + m.getAdresse() + "',`price`='" + m.getPrice() + "',`image`='" + m.getImage() + "',`description`='" + m.getDescription() + "',`datedebut`='" + m.getDatedebut() + "',`datefin`='" + m.getDatefin() + "' where id=" + m.getId()+ " ";

        try {
            ste = conn.createStatement();
             ste.executeUpdate(requeteUpdate);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String Req = " delete from maisondhote where id="+id+" ";
        try {
            ste = conn.createStatement();
            ste.execute(Req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List readAll() {
        String req = "select * from maisondhote";

        List<maison> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new maison(rs.getString("nom"), rs.getString("adresse"), rs.getDouble("price"), rs.getString("image"), rs.getString("description"), rs.getDate("datedebut"), rs.getDate("datefin")));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public maison readById(int id) {
        maison m = new maison();
        String Req = "select * from maisondhote where id=" + id + "";
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(Req);
            while (rs.next()) {
                m = new maison(rs.getString("nom"),rs.getString("adresse"),rs.getDouble("price"),rs.getString("image"),  rs.getString("description"), rs.getDate("datedebut"),rs.getDate("datefin"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return m;
    }
    

}
