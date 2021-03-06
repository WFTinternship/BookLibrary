package com.workfront.internship.booklibrary.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


abstract public class General {
    protected void closeConnection(Connection c){
        closeConnection(null, null, c);
    }

    protected void closeConnection(Statement st, Connection c) {
        closeConnection(null, st, c);
    }

    protected void closeConnection(ResultSet rs, Statement st, Connection c) {
        try {
            if (st != null) st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (c != null) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
