/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.transport;
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
public class transportService implements IService<transport> {

    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    private Connection conn;

    public transportService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(transport t) {

        String req = "insert into transport (description,disponibilite,price,type) values "
                + "('" + t.getDescription() + "','" + t.getDisponibilite() + "','" + t.getPrice() + "','" + t.getType() + "')";

        try {
            ste = conn.createStatement();
            ste.executeUpdate(req);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(transport t) {
        String requeteUpdate = "UPDATE  `transport` set `description`='" + t.getDescription() + "',`disponibilite`='" + t.getDisponibilite() + "',`price`='" + t.getPrice() + "',`type`='" + t.getType() + "' where id=" + t.getId() + " ";

        try {
            ste = conn.createStatement();
            ste.executeUpdate(requeteUpdate);
            System.out.println("Transport modifié");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String Req = " delete from transport where id=" + id + " ";
        try {
            ste = conn.createStatement();
            ste.execute(Req);
            System.out.println("Transport supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List readAll() {
        String req = "select * from transport order by description ASC";

        List<transport> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new transport(rs.getInt("id"), rs.getString("description"), rs.getString("disponibilite"), rs.getDouble("price"), rs.getString("type")));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public transport readById(int id) {
        transport t = null;
        String Req = "select * from transport where id=" + id + "";
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(Req);
            while (rs.next()) {
                t = new transport(rs.getInt("id"), rs.getString("description"), rs.getString("description"), rs.getDouble("price"), rs.getString("type"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return t;
    }

   
    public List<transport> RechercheTransport(String rech) {

        List<transport> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM transport WHERE type LIKE '%" + rech +"%' ";
            ste = conn.createStatement();
            rs = ste.executeQuery(req);

            while (rs.next()) {
                transport t = new transport();
                t.setId(rs.getInt("id"));
                t.setDescription(rs.getString("description"));
                t.setDisponibilite(rs.getString("disponibilite"));
                t.setPrice(rs.getDouble("price"));
                t.setType(rs.getString("type"));
               
                list.add(t);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

}
