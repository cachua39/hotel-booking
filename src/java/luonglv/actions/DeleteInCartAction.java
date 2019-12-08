/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import luonglv.shoppingcart.ShoppingCart;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

/**
 *
 * @author LeVaLu
 */
@Action("deleteInCart")
@ResultPath("/")
@Result(name = "success", location = "customer_cart.jsp")
public class DeleteInCartAction {

    private static final String SUCCESS = "success";
    private int hotelId, typeId;
    private String checkIn, checkOut;

    public DeleteInCartAction() {

    }

    public String execute() throws Exception {
         Map session = ActionContext.getContext().getSession();
        // Get shopping cart from session
        ShoppingCart shoppingCart = (ShoppingCart) session.get("CART");
        if(shoppingCart != null) {
            shoppingCart.remove(hotelId, typeId, checkIn, checkOut);
        }
        return SUCCESS;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    
    
}
