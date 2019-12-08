/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import luonglv.db.DBConnection;
import luonglv.dtos.HotelDTO;

/**
 *
 * @author LeVaLu
 */
public class HotelDAO implements Serializable {

    public List<HotelDTO> Search(HotelDTO searchHotelDTO) throws NamingException, SQLException {
        List<HotelDTO> result = null;
        HotelDTO hotelDTO = null;
        String name, address, type, photo;
        float price;
        int hotelId, typeId, availableRoom, maxPeople;
        
        String sql = "exec searchRoomAvailable @CheckIn = '" + searchHotelDTO.getCheckIn() + "', "
                + "@CheckOut = '" + searchHotelDTO.getCheckOut() + "', "
                + "@Quantity = " + searchHotelDTO.getQuantity()+ ", @Area='" + searchHotelDTO.getArea() + "'";

        try (
                Connection con = DBConnection.makeConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            result = new ArrayList<>();
            while (rs.next()) {
                hotelId = rs.getInt("HotelId");
                typeId = rs.getInt("TypeId");
                name = rs.getString("HotelName");
                address = rs.getString("Address");
                photo = rs.getString("Photo");
                type = rs.getString("TypeName");
                price = rs.getFloat("Price");
                availableRoom = rs.getInt("AvailableRoom");
                maxPeople = rs.getInt("MaxPeople");

               
                
                hotelDTO = new HotelDTO(hotelId, typeId, name, address, type, photo, price, availableRoom,
                        maxPeople, searchHotelDTO.getCheckIn(), searchHotelDTO.getCheckOut(), searchHotelDTO.getQuantity());
                result.add(hotelDTO);
            }
        }

        return result;
    }
    
    public HotelDTO findByRoomTypeId(int hotelId, int typeId) throws NamingException, SQLException {
        HotelDTO result = null;
        String name, address, type, photo;
        float price;
        
        String sql = "select tblHotel.Name as HotelName, Address, tblType.Name as TypeName, Photo, Price from tblHotel "
                + "inner join tblRoom on tblHotel.HotelId = tblRoom.HotelId "
                + "inner join tblType on tblRoom.TypeId = tblType.TypeId "
                + "where tblRoom.HotelId= "+ hotelId +" and tblRoom.TypeId = "+  typeId +"";
        try(
                Connection con = DBConnection.makeConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            if(rs.next()){
                name = rs.getString("HotelName");
                address = rs.getString("Address");
                type = rs.getString("TypeName");
                photo = rs.getString("Photo");
                price = rs.getFloat("Price");
                result = new HotelDTO(hotelId, typeId, name, address, type, photo, price);
            }
        }
        return result;
    }
}
