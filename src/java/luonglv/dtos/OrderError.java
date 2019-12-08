/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.dtos;

import java.io.Serializable;

/**
 *
 * @author LeVaLu
 */
public class OrderError implements Serializable{
    private String orderError;

    public OrderError() {
    }

    public String getOrderError() {
        return orderError;
    }

    public void setOrderError(String orderError) {
        this.orderError = orderError;
    }
}
