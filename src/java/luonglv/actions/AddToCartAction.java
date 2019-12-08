/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import luonglv.daos.HotelDAO;
import luonglv.dtos.HotelDTO;
import luonglv.shoppingcart.ShoppingCart;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

/**
 *
 * @author LeVaLu
 */
@ParentPackage(value = "json-default")
@Action("addToCart")
@ResultPath("/")
@Result(name = "success", type = "json")
public class AddToCartAction {

    private static final String SUCCESS = "success";
    private int hotelId, typeId, quantity;
    private String checkIn, checkOut;
    private String addResultMsg;

    public AddToCartAction() {
    }

    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        // Get shopping cart from session
        ShoppingCart shoppingCart = (ShoppingCart) session.get("CART");
        if(shoppingCart != null) {
            HotelDAO hotelDAO = new HotelDAO();
            HotelDTO hotelDTO = hotelDAO.findByRoomTypeId(hotelId, typeId);
            hotelDTO.setCheckIn(checkIn);
            hotelDTO.setCheckOut(checkOut);
            hotelDTO.setQuantityCart(quantity);
            
            shoppingCart.add(hotelDTO);
            addResultMsg = "success";
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

    public String getAddResultMsg() {
        return addResultMsg;
    }

    public void setAddResultMsg(String addResultMsg) {
        this.addResultMsg = addResultMsg;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    
}
