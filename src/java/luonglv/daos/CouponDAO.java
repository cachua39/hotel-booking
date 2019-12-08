/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import luonglv.db.DBConnection;
import luonglv.dtos.CouponDTO;

/**
 *
 * @author LeVaLu
 */
public class CouponDAO implements Serializable {

    public CouponDTO findByCode(String code) throws NamingException, SQLException {
        CouponDTO result = null;
        String startDate, endDate, status;
        float value;

        String sql = "select Value, StartDate, EndDate, Status from tblCoupon where Code=?";
        try (
                Connection con = DBConnection.makeConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, code);
            try (
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    value = rs.getFloat("Value");
                    startDate = rs.getTimestamp("StartDate").toString();
                    endDate = rs.getTimestamp("EndDate").toString();
                    status = rs.getString("Status");

                    result = new CouponDTO(code, value, startDate, endDate, status);
                }
            }
        }
        return result;
    }

    public boolean check(String code) throws NamingException, SQLException {
        boolean result = false;
        String sql = "update tblCoupon set Status='NotUsed' "
                + "where Code=? and StartDate<=GETDATE() and EndDate>=GETDATE() and Status='NotUsed'";
        try (
                Connection con = DBConnection.makeConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, code);

            result = ps.executeUpdate() > 0;
        }

        return result;
    }

    public boolean useCode(CouponDTO couponDTO) throws NamingException, SQLException {
        boolean result = false;

        String sql = "update tblCoupon set Status='Used' "
                + "where Code=? and StartDate<=GETDATE() and EndDate>=GETDATE() and Status='NotUsed'";

        try (
                Connection con = DBConnection.makeConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, couponDTO.getCode());

            result = ps.executeUpdate() > 0;
        }
        return result;
    }
}
