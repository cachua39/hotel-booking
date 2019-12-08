/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
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
@Action("createPayment")
@ResultPath("/")
@Results({
    @Result(name = "success", location = "customer_confirm.jsp")
    ,
    @Result(name = "failed", location = "customer_checkout.jsp")
    ,
    @Result(name = "error", location = "error.jsp")
})
public class CreatePaymentAction {

    private static final String FAILED = "failed";
    private static final String ERROR = "error";
    private String couponCode;
    private OrderDTO orderDTO;
    private OrderError orderError;

    public CreatePaymentAction() {
    }

    public String execute() throws Exception {
        String url = ERROR;
        boolean outOfRoom = false;
        boolean codeIsUsed = false;

        // Get cart
        Map session = ActionContext.getContext().getSession();
        ShoppingCart shoppingCartDTO = (ShoppingCart) session.get("CART");

        if (shoppingCartDTO != null) {
            orderDTO = new OrderDTO();
            orderDTO.setEmail(shoppingCartDTO.getCustomerEmail());
            orderDTO.setListHotels(new ArrayList<>(shoppingCartDTO.getCart().values()));

            OrderDAO orderDAO = new OrderDAO();
            HotelDTO hotelDTO = orderDAO.loadRoomNotAvailable(orderDTO);
            if (hotelDTO != null) {
                HttpServletRequest req = ServletActionContext.getRequest();
                req.setAttribute("ERROR", "Hotel:  " + hotelDTO.getName() + " - " + hotelDTO.getType() + " "
                        + "Room , " + hotelDTO.getCheckIn() + " - " + hotelDTO.getCheckOut() + " is not available now!");

                outOfRoom = true;
            }

            if (!outOfRoom) {
                if (couponCode != null && !couponCode.isEmpty()) {
                    CouponDAO couponDAO = new CouponDAO();
                    if (!couponDAO.check(couponCode)) {
                        orderError = new OrderError();
                        orderError.setOrderError("Discount Code is invalid!");
                        url = FAILED;
                        codeIsUsed = true;
                    } else {
                        CouponDTO couponDTO = couponDAO.findByCode(couponCode);
                        orderDTO.setCouponCode(couponCode);
                        orderDTO.setCouponValue(couponDTO.getValue());
                        float subtotal = shoppingCartDTO.getTotal() - couponDTO.getValue();
                        orderDTO.setTotal(subtotal < 0 ? 0 : subtotal);
                    }
                } else {
                    orderDTO.setTotal(shoppingCartDTO.getTotal());
                }
                if (!codeIsUsed) {
                    session.put("ORDER_PAYMENT", orderDTO);

                    PaypalServices paypalServices = new PaypalServices();
                    String approvalLink = paypalServices.createPayment(orderDTO);

                    HttpServletResponse response = ServletActionContext.getResponse();
                    response.sendRedirect(approvalLink);
                }
            }
        }
        return url;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public OrderError getOrderError() {
        return orderError;
    }

    public void setOrderError(OrderError orderError) {
        this.orderError = orderError;
    }

}
