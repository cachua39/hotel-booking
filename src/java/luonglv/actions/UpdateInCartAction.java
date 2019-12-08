/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import luonglv.dtos.HotelDTO;
import luonglv.shoppingcart.ShoppingCart;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

/**
 *
 * @author LeVaLu
 */
@Action("updateInCart")
@ResultPath("/")
@Result(name = "success", location = "customer_cart.jsp")
public class UpdateInCartAction {
    private static final String SUCCESS = "success";
    
    private List<Integer> quantities = new ArrayList<>();
    
    public UpdateInCartAction() {
    }
    
    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        // Get shopping cart from session
        ShoppingCart shoppingCart = (ShoppingCart) session.get("CART");
        if(shoppingCart != null) {
            int i = 0;
           for(HotelDTO hotelDTO : shoppingCart.getCart().values()){
               hotelDTO.setQuantityCart(quantities.get(i));
               i++;
           }
        }
         return SUCCESS;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    

    
}
