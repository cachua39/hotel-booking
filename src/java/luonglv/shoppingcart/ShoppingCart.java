/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.shoppingcart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import luonglv.dtos.HotelDTO;

/**
 *
 * @author LeVaLu
 */
public class ShoppingCart implements Serializable {

    private String customerEmail;
    private Map<CartKey, HotelDTO> cart;

    public ShoppingCart(String customerEmail) {
        this.customerEmail = customerEmail;
        cart = new HashMap<>();
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Map<CartKey, HotelDTO> getCart() {
        return cart;
    }

    public void add(HotelDTO hotelDTO) {
        CartKey cartKey = new CartKey(hotelDTO.getHotelId(), hotelDTO.getTypeId(), hotelDTO.getCheckIn(), hotelDTO.getCheckOut());
        if (cart.containsKey(cartKey)) {
            int quantity = cart.get(cartKey).getQuantityCart();
            hotelDTO.setQuantityCart(quantity + 1);
        }
        cart.put(cartKey, hotelDTO);
    }

    public void update(int hotelId, int typeId, String checkIn, String checkOut, int quantity) {
        CartKey cartKey = new CartKey(hotelId, typeId, checkIn, checkOut);
        if (cart.containsKey(cartKey)) {
            cart.get(cartKey).setQuantityCart(quantity);
        }
    }

    public void remove(int hotelId, int typeId, String checkIn, String checkOut) {
        CartKey cartKey = new CartKey(hotelId, typeId, checkIn, checkOut);
        if (cart.containsKey(cartKey)) {
            cart.remove(cartKey);
        }
    }

    public void removeAll() {
        cart.clear();
    }

    public float getTotal() {
        Float total = 0F;

        for (HotelDTO hotelDTO : cart.values()) {
            total += (hotelDTO.getQuantityCart() * hotelDTO.getSubTotal());
        }
        return total;
    }
}
