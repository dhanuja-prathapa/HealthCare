package com.project.healthcare.controller;

import com.project.healthcare.model.Hospital;
import com.project.healthcare.utils.Constants;
import com.project.healthcare.utils.DBConnection;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;


public class HospitalController implements IHospitalController{

    List<Hospital> hospitals;

    public static Connection connecton;

    public static Statement st;

    private static PreparedStatement pt;

    public HospitalController() throws SQLException, ClassNotFoundException {

        connecton = DBConnection.getDBConnection();
    }

    @Override
    public List<Hospital> getHospitals() {
        List<Hospital> hospitals = new ArrayList<>();
        String sql = "select * from hospital";
        try {
            st = connecton.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Hospital h = new Hospital();
                mapObject(rs, h);
                hospitals.add(h);
            }
        } catch (SQLException e){
            System.out.println(e);
        } finally {

            stClose(st);
        }
        return hospitals;
    }

    @Override
    public void createHospital(Hospital h) {
        String sql = "insert into hospital values (?,?,?,?,?,?)";

        try {
            pt = connecton.prepareStatement(sql);
            pt.setInt(1, h.getId());
            pt.setString(2, h.getName());
            pt.setString(3, h.getType());
            pt.setString(4, h.getDescription());
            pt.setString(5, h.getAddress());
            pt.setString(6, h.getPhone());
            pt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            ptClose(pt);
        }
    }

    @Override
    public Hospital getHospital(int id) {
        String sql = "select * from hospital where id="+id;
        Hospital h = new Hospital();
        try {
            st = connecton.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                mapObject(rs, h);
            }
        } catch (SQLException e){
            System.out.println(e);
        } finally {
            stClose(st);
        }
        return h;
    }

    @Override
    public void updateHospital(Hospital h) {
        String sql = "update hospital set name=?, type=?, description=?, address=?, phone=? where id=?";

        try {
            pt = connecton.prepareStatement(sql);
            pt.setString(1, h.getName());
            pt.setString(2, h.getType());
            pt.setString(3, h.getDescription());
            pt.setString(4, h.getAddress());
            pt.setString(5, h.getPhone());
            pt.setInt(6, h.getId());
            pt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e);
        }finally {
            ptClose(pt);
        }
    }

    @Override
    public String deleteHospital(int id) {
        String sql = "delete from hospital where id=?";
        String output;
        try {
            pt = connecton.prepareStatement(sql);
            pt.setInt(1, id);
            pt.executeUpdate();
            output = "Successfully Deleted";
        } catch (SQLException e){
            System.out.println(e);
            output = "Error";
        }finally {
            ptClose(pt);
        }
            return output;
    }

//Close Statement
    private void stClose(Statement st) {
        try {
            if(st != null) {
                st.close();
            }
            if(connecton != null) {
                connecton.close();
                System.out.println("DB Connection Closed");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void ptClose(PreparedStatement pt) {
        try {
            if(pt != null) {
                pt.close();
            }
            if(connecton != null) {
                connecton.close();
                System.out.println("DB Connection Closed");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

//Map Object
    private void mapObject(ResultSet rs, Hospital h) throws SQLException {
        h.setId(rs.getInt(Constants.COLUMN_INDEX_ONE));
        h.setName(rs.getString(Constants.COLUMN_INDEX_TWO));
        h.setType(rs.getString(Constants.COLUMN_INDEX_THREE));
        h.setDescription(rs.getString(Constants.COLUMN_INDEX_FOUR));
        h.setAddress(rs.getString(Constants.COLUMN_INDEX_FIVE));
        h.setPhone(rs.getString(Constants.COLUMN_INDEX_SIX));
    }
}
