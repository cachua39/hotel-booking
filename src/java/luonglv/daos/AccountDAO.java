/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.daos;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.NamingException;
import luonglv.db.DBConnection;
import luonglv.dtos.AccountDTO;
import luonglv.utils.PasswordUtils;

/**
 *
 * @author LeVaLu
 */
public class AccountDAO implements Serializable {

    public String checkLogin(String email, String password) throws NamingException, SQLException, NoSuchAlgorithmException {
        String roleName = "failed";

        String sql = "select tblRole.Name from tblRole "
                + "inner join tblAccount on tblRole.RoleId = tblAccount.RoleId "
                + "where Email = ? and Password = ? and Status='Active'";

        try (
                Connection con = DBConnection.makeConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            String encryptedPassword = PasswordUtils.encryptPassword(password);
            ps.setString(1, email);
            ps.setString(2, encryptedPassword);

            try (
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    roleName = rs.getString("Name");
                }
            }
        }
        return roleName;
    }

    public boolean insert(AccountDTO accountDTO) throws NamingException, SQLException, NoSuchAlgorithmException {
        boolean result = false;
        try (
                Connection con = DBConnection.makeConnection();
                Statement st = con.createStatement()) {
            con.setAutoCommit(false);

            String encryptPassword = PasswordUtils.encryptPassword(accountDTO.getPassword());

            String sqlAccount = "insert into tblAccount(Email, Password, RoleId, Status, CreatedDate) "
                    + "values('" + accountDTO.getEmail()
                    + "','" + encryptPassword
                    + "',(select RoleId from tblRole where Name='" + accountDTO.getRoleName() + "'),'Active',GETDATE())";

            String sqlUserInfo = "insert into tblUserInfo(Email, Fullname, Phone, Address) "
                    + "values('"
                    + accountDTO.getEmail() + "','"
                    + accountDTO.getFullname() + "','"
                    + accountDTO.getPhone() + "','"
                    + accountDTO.getAddress() + "')";

            st.addBatch(sqlAccount);
            st.addBatch(sqlUserInfo);

            int resultArr[] = st.executeBatch();
            result = resultArr[0] > 0 && resultArr[1] > 0;

            con.commit();
        }

        return result;
    }
}
