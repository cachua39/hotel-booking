/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.actions;

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
@Action("searchHotel")
@ResultPath("/")
@Result(name = "success", type = "json")
public class SearchHotelAction {

    private static final String SUCCESS = "success";
    private List<HotelDTO> listHotels;
    private String checkIn, checkOut, area;
    private int quantity;

    public SearchHotelAction() {
    }

    public String execute() throws Exception {
        HotelDAO hotelDAO = new HotelDAO();
        HotelDTO searchDTO = new HotelDTO(area, quantity, checkIn, checkOut);
        listHotels = hotelDAO.Search(searchDTO);

        return SUCCESS;
    }

    public List<HotelDTO> getListHotels() {
        return listHotels;
    }

    public void setListHotels(List<HotelDTO> listHotels) {
        this.listHotels = listHotels;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
