package luonglv.paypal;


import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import luonglv.dtos.HotelDTO;
import luonglv.dtos.OrderDTO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LeVaLu
 */
public class PaypalServices implements Serializable {

    private static final String CLIENT_ID = "AQyqxQr4IDcfaBCvKQz1FlZY1Py0S02oJiiBrsE_PAzarCICp4_DPKzgwRzzeI3Ad56pV3edXRg9IgGa";
    private static final String CLIENT_SECRET = "EKUG7OFfshMGT2mtR7CL3p3sbGw6hk9q00EhuXdFlKhgI5iMo1khq6VyBY6ZI74VivhwZfyYfREOSuhZ";
    private static final String MODE = "sandbox";
    
    private static final String URL_RETURN = "http://localhost:8084/J3.L.P0003/executePayment";
    private static final String URL_CANCEL = "http://localhost:8084/J3.L.P0003/customer_cart.jsp";
    
    private static final String CURRENCY = "USD";
    private static final String INTENT = "sale";
    
    public String createPayment(OrderDTO orderDTO) throws PayPalRESTException {
        Payer payer = getPayer();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransactions = getTransactions(orderDTO);
        
        // Create request payment
        Payment requestPayment = new Payment();
        requestPayment.setPayer(payer);
        requestPayment.setTransactions(listTransactions);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setIntent(INTENT);
        
        // Create approved payment 
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        Payment approvedPayment = requestPayment.create(apiContext);
        
        return getApprovalLink(approvedPayment);
    }
    
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        
        Payment payment = new Payment().setId(paymentId);
        
        APIContext apiContent = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        
        return payment.execute(apiContent, paymentExecution);
    }
    
    // Get Payer with paypal method
    private Payer getPayer(){
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        
        return payer;
    }
    
    // Set redirect urls when payer accept or cancel payment
    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(URL_RETURN);
        redirectUrls.setCancelUrl(URL_CANCEL);
        
        return redirectUrls;
    }
    
    // Get approval url after created payment
    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;
        
        for(Links link : links) {
            if(link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }
        return approvalLink;
    }
    
    
    // Get Transtion list
    private List<Transaction> getTransactions(OrderDTO orderDTO) {
        Details details = new Details();
        details.setShipping("0.00");
        details.setSubtotal(String.format("%.2f", orderDTO.getTotal()));
        details.setTax("0.00");
        
        Amount amount = new Amount();
        amount.setCurrency(CURRENCY);
        amount.setTotal(String.format("%.2f", orderDTO.getTotal()));
        amount.setDetails(details);
        
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        Item item;
        
        for(HotelDTO hotel : orderDTO.getListHotels()){
            item = new Item();
            item.setCurrency(CURRENCY);
            item.setName(hotel.getName() + " - " + hotel.getType() + " Room - x"+hotel.getQuantityCart()+" - From " + hotel.getCheckIn() + " to " + hotel.getCheckOut());
            item.setPrice(String.format("%.2f", hotel.getSubTotalCart()));
            item.setQuantity(String.valueOf(hotel.getQuantityCart()));
            item.setTax("0.0");
            items.add(item);
        }
        
        if(orderDTO.getCouponCode() != null && !orderDTO.getCouponCode().isEmpty()) {
           item = new Item();
           item.setCurrency(CURRENCY);
           item.setName("Discount");
           item.setPrice(String.format("%.2f", (0-orderDTO.getCouponValue())));
           item.setTax("0.0");
           item.setQuantity("1");
           items.add(item);
        }
        
        itemList.setItems(items);
        
        
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setItemList(itemList);
        
        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);
        
        return listTransaction;
    }
    
}