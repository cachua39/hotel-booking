/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

import luonglv.daos.OrderDAO;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

/**
 *
 * @author LeVaLu
 */
@ParentPackage(value = "json-default")
@Action("deleteHistory")
@ResultPath("/")
@Result(name = "success", type = "json")
public class DeleteHistoryAction {

    private static final String SUCCESS = "success";
    private int orderId;
    private String deleteResult;

    public DeleteHistoryAction() {
    }

    public String execute() throws Exception {
        OrderDAO orderDAO = new OrderDAO();
        if(orderDAO.deleteHistory(orderId)){
            deleteResult = "success";
        }else{
            deleteResult = "failed";
        }
        return SUCCESS;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDeleteResult() {
        return deleteResult;
    }

    public void setDeleteResult(String deleteResult) {
        this.deleteResult = deleteResult;
    }
    
    
}
