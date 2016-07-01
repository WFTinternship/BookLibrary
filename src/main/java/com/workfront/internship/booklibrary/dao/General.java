package com.workfront.internship.booklibrary.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Workfront on 7/1/2016.
 */
public class General {
    protected void closeConnection(ResultSet rs, Statement st, Connection c){
        try{
            rs.close();
            st.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(st!=null)
                    st.close();
            }catch(SQLException se2){
            }
            try{
                if(c!=null)
                    c.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
    //todo close-nery gral
}
