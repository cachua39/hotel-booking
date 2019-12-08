/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.NamingException;
import luonglv.db.DBConnection;
import luonglv.dtos.UserInfoDTO;

/**
 *
 * @author LeVaLu
 */
public class UserInfoDAO implements Serializable{
    public UserInfoDTO findByEmail(String email) throws SQLException, NamingException {
        UserInfoDTO result = null;
        String fullname, phone, address;
        
        String sql = "select Fullname, Phone, Address from tblUserInfo where Email = '"+email+"'";
        try(
                Connection con = DBConnection.makeConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            if(rs.next()) {
                fullname = rs.getString("Fullname");
                phone = rs.getString("Phone");
                address = rs.getString("Address");
                result = new UserInfoDTO(email, fullname, phone, address);
            } 
        }
        return result;
    }
}
