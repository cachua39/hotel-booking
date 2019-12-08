/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import luonglv.daos.AccountDAO;
import luonglv.dtos.AccountDTO;
import luonglv.dtos.AccountError;
import luonglv.shoppingcart.ShoppingCart;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

/**
 *
 * @author LeVaLu
 */
@ResultPath("/")
@Results({
    @Result(name = "admin", location = "admin.jsp")
    ,
    @Result(name = "customer", location = "index.jsp")
    ,
    @Result(name = "failed", location = "login.jsp")
})
public class LoginAction {

    private static final String ADMIN = "admin";
    private static final String CUSTOMER = "customer";
    private static final String FAIL = "failed";

    private String email, password;

    public LoginAction() {
    }

    public String execute() throws Exception {
        AccountDAO accountDAO = new AccountDAO();
        String roleName = accountDAO.checkLogin(email, password);

        String url = FAIL;
        if (roleName.equals("failed")) {
            HttpServletRequest request = ServletActionContext.getRequest();
            AccountError accountError = new AccountError();
            accountError.setAccountError("Invalid email or password!");
            request.setAttribute("INVALID", accountError);
        } else {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setEmail(email);
            accountDTO.setRoleName(roleName);
            Map session = ActionContext.getContext().getSession();
            session.put("ACCOUNT", accountDTO);
            
            if (roleName.equals("Customer")) {

                ShoppingCart shoppingCart = new ShoppingCart(email);
                session.put("CART", shoppingCart);

                url = CUSTOMER;
            } else if (roleName.equals("Admin")) {
                url = ADMIN;
            } else {
                HttpServletRequest request = ServletActionContext.getRequest();
                request.setAttribute("ERROR", "Role not supported!");
            }
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
}
