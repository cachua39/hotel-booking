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
@Action("searchHistory")
@ResultPath("/")
@Result(name = "success", type = "json")
public class SearchHistoryAction {
    private static final String SUCCESS = "success";
    private List<OrderDTO> listOrders;
    private String hotelName;
    public SearchHistoryAction() {
    }
    
    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        AccountDTO accountDTO = (AccountDTO) session.get("ACCOUNT");
        
        OrderDAO orderDAO = new OrderDAO();
        listOrders = orderDAO.searchHistory(accountDTO.getEmail(), hotelName);
        
        return SUCCESS;
    }

    public List<OrderDTO> getListOrders() {
        return listOrders;
    }

    public void setListOrders(List<OrderDTO> listOrders) {
        this.listOrders = listOrders;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
    
    
}
