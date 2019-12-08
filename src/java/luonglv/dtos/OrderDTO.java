/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author LeVaLu
 */
public class OrderDTO implements Serializable{
    private String email, couponCode; 
    private float total, couponValue;
    private List<HotelDTO> listHotels;
    private int orderId;

    public OrderDTO() {
        couponCode = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(float couponValue) {
        this.couponValue = couponValue;
    }

    public List<HotelDTO> getListHotels() {
        return listHotels;
    }

    public void setListHotels(List<HotelDTO> listHotels) {
        this.listHotels = listHotels;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    
}
