/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

import com.opensymphony.xwork2.ActionContext;
import com.paypal.api.payments.Payment;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import luonglv.daos.CouponDAO;
import luonglv.daos.OrderDAO;
import luonglv.dtos.CouponDTO;
import luonglv.dtos.HotelDTO;
import luonglv.dtos.OrderDTO;
import luonglv.dtos.OrderError;
import luonglv.paypal.PaypalServices;
import luonglv.shoppingcart.ShoppingCart;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

/**
 *
 * @author LeVaLu
 */
@Action("executePayment")
@ResultPath("/")
@Results({
    @Result(name = "success", location = "customer_confirm.jsp")
    ,
    @Result(name = "failed", location = "customer_checkout.jsp")
    ,
    @Result(name = "error", location = "error.jsp")
})
public class ExecutePaymentAction {

    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    private String paymentId;
    private String PayerID;
    private OrderDTO orderDTO;

    public ExecutePaymentAction() {
    }

    public String execute() throws Exception {
        String url = ERROR;
        // Get cart
        Map session = ActionContext.getContext().getSession();
        ShoppingCart shoppingCartDTO = (ShoppingCart) session.get("CART");
        orderDTO = (OrderDTO) session.get("ORDER_PAYMENT");

        if (orderDTO != null) {
            boolean outOfRoom = false;
            boolean codeIsUsed = false;
            
            OrderDAO orderDAO = new OrderDAO();
            HotelDTO hotelDTO = orderDAO.loadRoomNotAvailable(orderDTO);
            if (hotelDTO != null) {
                HttpServletRequest req = ServletActionContext.getRequest();
                req.setAttribute("ERROR", "Hotel:  " + hotelDTO.getName() + " - " + hotelDTO.getType() + " "
                        + "Room , " + hotelDTO.getCheckIn() + " - " + hotelDTO.getCheckOut() + " is not available now!");

                outOfRoom = true;
            }

            if (!outOfRoom) {
                String couponCode = orderDTO.getCouponCode();
                if (couponCode != null && !couponCode.isEmpty()) {
                    CouponDAO couponDAO = new CouponDAO();
                    if (!couponDAO.check(couponCode)) {
                        HttpServletRequest req = ServletActionContext.getRequest();
                        req.setAttribute("ERROR", "Discount Code is invalid!");
                        codeIsUsed = true;
                    }
                }

                if (!codeIsUsed) {
                    PaypalServices paypalServices = new PaypalServices();
                    Payment payment = paypalServices.executePayment(paymentId, PayerID);
                    if (payment != null) {
                        if (orderDAO.insert(orderDTO)) {
                            shoppingCartDTO.removeAll();
                            url = SUCCESS;
                        } else {
                            HttpServletRequest req = ServletActionContext.getRequest();
                            req.setAttribute("ERROR", "Order fail!");
                        }
                    }
                }
            }
        }
        return url;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPayerID() {
        return PayerID;
    }

    public void setPayerID(String PayerID) {
        this.PayerID = PayerID;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

}
