/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

import luonglv.daos.AccountDAO;
import luonglv.dtos.AccountDTO;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

/**
 *
 * @author LeVaLu
 */
@ResultPath("/")
@Action("adminRegister")
@Results({
    @Result(name = "success", location = "admin.jsp")
    ,
    @Result(name = "invalid", location = "admin_register.jsp")
    ,
    @Result(name = "failed", location = "error.jsp")
})
@ExceptionMappings({
    @ExceptionMapping(exception = "java.sql.SQLException", result = "invalid")
})
public class AdminRegisterAction {

    private static final String SUCCESS = "success";
    private static final String FAILED = "failed";
    private String email, password, fullname, phone, address;

    public AdminRegisterAction() {
    }

    public String execute() throws Exception {
        String url = FAILED;

        AccountDTO accountDTO = new AccountDTO(email, password, fullname, phone, address);
        accountDTO.setRoleName("Admin");

        AccountDAO accountDAO = new AccountDAO();
        if (accountDAO.insert(accountDTO)) {
            url = SUCCESS;
        }

        return url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
}
