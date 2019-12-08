/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import luonglv.daos.HotelDAO;
import luonglv.dtos.HotelDTO;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

/**
 *
 * @author LeVaLu
 */
@ParentPackage(value = "json-default")
@ResultPath("/")
@Result(name = "success", type = "json")
public class LoadAllHotelAction extends ActionSupport {

    private static final String SUCCESS = "success";
    private List<HotelDTO> listHotels;

    public LoadAllHotelAction() {
    }

    @Action("loadAllHotel")
    public String execute() throws Exception {

        HotelDAO hotelDAO = new HotelDAO();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String checkIn = dateFormat.format(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        Date afterDate = calendar.getTime();
        String checkOut = dateFormat.format(afterDate);
        

        int quantity = 1;

        HotelDTO searchDTO = new HotelDTO("", quantity, checkIn, checkOut);
        listHotels = hotelDAO.Search(searchDTO);

        return SUCCESS;
    }

    public List<HotelDTO> getListHotels() {
        return listHotels;
    }

    public void setListHotels(List<HotelDTO> listHotels) {
        this.listHotels = listHotels;
    }
}
