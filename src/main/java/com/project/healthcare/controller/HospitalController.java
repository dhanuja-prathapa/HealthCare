package com.project.healthcare.controller;

import com.project.healthcare.model.Hospital;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class HospitalController implements IHospitalController{

    List<Hospital> hospitals;

    Connection con = null;

    public HospitalController(){

        String url = "jdbc:mysql://127.0.0.1:3306/healthcare";
        String username = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public List<Hospital> getHospitals() {
        List<Hospital> hospitals = new ArrayList<>();
        String sql = "select * from hospital";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Hospital h = new Hospital();
                h.setId(rs.getInt(1));
                h.setName(rs.getString(2));
                h.setType(rs.getString(3));
                h.setDescription(rs.getString(4));
                h.setAddress(rs.getString(5));
                h.setPhone(rs.getString(6));
                hospitals.add(h);
            }
        } catch (SQLException e){
            System.out.println(e);
        }
        return hospitals;
    }

    @Override
    public void createHospital(Hospital h){
        String sql = "insert into hospital values (?,?,?,?,?,?)";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, h.getId());
            st.setString(2, h.getName());
            st.setString(3, h.getType());
            st.setString(4, h.getDescription());
            st.setString(5, h.getAddress());
            st.setString(6, h.getPhone());
            st.executeUpdate();
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public Hospital getHospital(int id) {
        String sql = "select * from hospital where id="+id;
        Hospital h = new Hospital();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                h.setId(rs.getInt(1));
                h.setName(rs.getString(2));
                h.setType(rs.getString(3));
                h.setDescription(rs.getString(4));
                h.setAddress(rs.getString(5));
                h.setPhone(rs.getString(6));
            }
        } catch (SQLException e){
            System.out.println(e);
        }
        return h;
    }

    @Override
    public void updateHospital(Hospital h) {
        String sql = "update hospital set name=?, type=?, description=?, address=?, phone=? where id=?";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, h.getName());
            st.setString(2, h.getType());
            st.setString(3, h.getDescription());
            st.setString(4, h.getAddress());
            st.setString(5, h.getPhone());
            st.setInt(6, h.getId());
            st.executeUpdate();
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public String deleteHospital(int id) {
        String sql = "delete from hospital where id=?";
        String output;
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            output = "Successfully Deleted";
        } catch (SQLException e){
            System.out.println(e);
            output = "Error";
        }
            return output;
    }
}
