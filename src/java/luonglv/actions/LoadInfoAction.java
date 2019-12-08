/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import luonglv.daos.UserInfoDAO;
import luonglv.dtos.AccountDTO;
import luonglv.dtos.UserInfoDTO;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

/**
 *
 * @author LeVaLu
 */
@Action("loadInfo")
@ResultPath("/")
@Result(name = "success", location = "customer_checkout.jsp")
public class LoadInfoAction {

    private static final String SUCCESS = "success";
    private UserInfoDTO userInfo;

    public LoadInfoAction() {
    }

    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        AccountDTO accountDTO = (AccountDTO) session.get("ACCOUNT");
        if (accountDTO != null) {
            UserInfoDAO userInfoDAO = new UserInfoDAO();
            userInfo = userInfoDAO.findByEmail(accountDTO.getEmail());
        }
        return SUCCESS;
    }

    public UserInfoDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDTO userInfo) {
        this.userInfo = userInfo;
    }

}
