/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.DBUtil;

/**
 *
 * @author tuanv
 */
public class RecordDAO {

    public void recordUpdate(String userID, String productID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "INSERT tblRecoedUpdate(userID,productID,dateRecord,note)\n"
                    + "VALUES(?,?,?,?)";
            pst = conn.prepareStatement(url);
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            pst.setString(1, userID);
            pst.setString(2, productID);
            pst.setString(3, date.toString());
            pst.setString(4, "Update");
            pst.executeUpdate();
        }finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public void recordDelete(String userID, String productID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "INSERT tblRecoedUpdate(userID,productID,dateRecord,note)\n"
                    + "VALUES(?,?,?,?)";
            pst = conn.prepareStatement(url);
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            pst.setString(1, userID);
            pst.setString(2, productID);
            pst.setString(3, date.toString());
            pst.setString(4, "Delete");
            pst.executeUpdate();
        }finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
