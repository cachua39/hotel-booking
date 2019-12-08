/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

import com.opensymphony.xwork2.ActionContext;
import java.util.List;
import java.util.Map;
import luonglv.daos.OrderDAO;
import luonglv.dtos.AccountDTO;
import luonglv.dtos.OrderDTO;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

/**
 *
 * @author LeVaLu
 */
@ParentPackage(value = "json-default")
@Action("loadHistory")
@ResultPath("/")
@Result(name = "success", type = "json")
public class LoadHistoryAction {

    private static final String SUCCESS = "success";
    private List<OrderDTO> listOrders;

    public LoadHistoryAction() {
    }

    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        AccountDTO accountDTO = (AccountDTO) session.get("ACCOUNT");
        OrderDAO orderDAO = new OrderDAO();
        listOrders = orderDAO.loadHistory(accountDTO.getEmail());
        
        return SUCCESS;
    }

    public List<OrderDTO> getListOrders() {
        return listOrders;
    }

    public void setListOrders(List<OrderDTO> listOrders) {
        this.listOrders = listOrders;
    }
    
}
