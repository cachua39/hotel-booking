/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

import java.util.Date;
import luonglv.daos.CouponDAO;
import luonglv.dtos.CouponDTO;
import luonglv.dtos.CouponInfo;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

/**
 *
 * @author LeVaLu
 */
@ParentPackage("json-default")
@Action("checkCoupon")
@ResultPath("/")
@Result(name = "success", type = "json")
public class CheckCouponAction {

    private static final String SUCCESS = "success";
    private String code;
    private CouponInfo couponInfo;

    public CheckCouponAction() {
    }

    public String execute() throws Exception {
            CouponDAO couponDAO = new CouponDAO();
            CouponDTO couponDTO = couponDAO.findByCode(code);
            couponInfo = new CouponInfo();
            if (couponDTO != null) {
                // Get current datetime
                java.sql.Timestamp currentDate = new java.sql.Timestamp(new Date().getTime());
                
                // Get started date and ended date 
                java.sql.Timestamp startDate = java.sql.Timestamp.valueOf(couponDTO.getStartDate());
                java.sql.Timestamp endDate = java.sql.Timestamp.valueOf(couponDTO.getEndDate());

                if (!couponDTO.getStatus().equals("NotUsed")) {
                    couponInfo.setCodeError("Code is used!");
                } else if (startDate.compareTo(currentDate) > 0) {
                    couponInfo.setCodeError("Code is unavailabe!");
                } else if (endDate.compareTo(currentDate) < 0) {
                    couponInfo.setCodeError("Code is expired!");
                } else {
                    couponInfo.setValue(Float.toString(couponDTO.getValue()));
                }
            }else{
                couponInfo.setCodeError("Code no existed!");
            }
            
            return SUCCESS;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CouponInfo getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(CouponInfo couponInfo) {
        this.couponInfo = couponInfo;
    }

    
}
