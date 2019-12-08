/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.shoppingcart;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 *
 * @author LeVaLu
 */
public class CartKey implements Serializable {

    private final int hotelId, typeId;
    private final String checkIn, checkOut;

    public CartKey(int hotelId, int typeId, String checkIn, String checkOut) {
        this.hotelId = hotelId;
        this.typeId = typeId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CartKey)) {
            return false;
        }

        CartKey cartKey = (CartKey) obj;
        return cartKey.hotelId == hotelId && cartKey.typeId == typeId
                && cartKey.checkIn.equals(checkIn) && cartKey.checkOut.equals(checkOut);
    }

    @Override
    public int hashCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int result = hotelId;
        result = 31 * result + typeId + checkIn.hashCode() + checkOut.hashCode();

        return result;
    }

}
