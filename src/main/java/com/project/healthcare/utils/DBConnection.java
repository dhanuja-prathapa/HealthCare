package com.project.healthcare.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.project.healthcare.utils.Constants;

public class DBConnection {

    //If there is any issue you can change it accordingly
    //Implemented with Singleton Pattern

    private static Connection con;

    private DBConnection() {

    }

    public static Connection getDBConnection() throws ClassNotFoundException, SQLException {

        if(con==null ||con.isClosed()) {

            try {
                Class.forName(Constants.DB_DRIVER_NAME);
                con = DriverManager.getConnection(Constants.DBLOCATION_STRING, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            }catch (Exception e){
                System.out.println(e);
            }

            System.out.println("Connected to DB");
        }

        return con;
    }
}