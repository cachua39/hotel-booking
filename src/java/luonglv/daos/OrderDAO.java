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
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import luonglv.db.DBConnection;
import luonglv.dtos.HotelDTO;
import luonglv.dtos.OrderDTO;

/**
 *
 * @author LeVaLu
 */
public class OrderDAO implements Serializable {

    public HotelDTO loadRoomNotAvailable(OrderDTO orderDTO) throws NamingException, SQLException {
        HotelDTO result = null;

        for (HotelDTO hotelDTO : orderDTO.getListHotels()) {
            String sql = "exec searchRoomCheckOut "
                    + "@CheckIn = '" + hotelDTO.getCheckIn() + "', "
                    + "@CheckOut = '" + hotelDTO.getCheckOut() + "', "
                    + "@HotelId = " + hotelDTO.getHotelId() + ", "
                    + "@TypeId=" + hotelDTO.getTypeId() + ", "
                    + "@Quantity= "+ hotelDTO.getQuantity() +"";

            try (
                    Connection con = DBConnection.makeConnection();
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql)) {
                if (rs.next()) {
                    result = new HotelDTO();
                    result.setHotelId(rs.getInt("HotelId"));
                    result.setTypeId(rs.getInt("TypeId"));
                    result.setName(rs.getString("HotelName"));
                    result.setType(rs.getString("TypeName"));
                    result.setCheckIn(hotelDTO.getCheckIn());
                    result.setCheckOut(hotelDTO.getCheckOut());
                }
            }
            if(result != null){
                return result;
            }
        }

        return result;
    }

    public boolean insert(OrderDTO orderDTO) throws NamingException, SQLException {
        boolean result = false;

        try (
                Connection con = DBConnection.makeConnection();
                Statement st = con.createStatement()) {
            con.setAutoCommit(false);

            // Add Order to database
            String sqlOrder;
            // Insert Order with coupon code
            if (orderDTO.getCouponCode() != null && !orderDTO.getCouponCode().isEmpty()) {
                sqlOrder = "insert into tblOrder(Email, Total, CouponCode, CouponValue, CreatedDate, Status) "
                        + "values('" + orderDTO.getEmail() + "',"
                        + "" + orderDTO.getTotal() + ","
                        + "'" + orderDTO.getCouponCode() + "',"
                        + "" + orderDTO.getCouponValue() + ","
                        + "GETDATE(), 'Active')";
            } // // Insert Order without coupon code
            else {
                sqlOrder = "insert into tblOrder(Email, Total, CreatedDate, Status) "
                        + "values('" + orderDTO.getEmail() + "',"
                        + "" + orderDTO.getTotal() + ","
                        + "GETDATE(), 'Active')";
            }
            result = st.executeUpdate(sqlOrder, Statement.RETURN_GENERATED_KEYS) > 0;

            int orderId = 0;
            try (
                    ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }

            // Add Detail to database and Subtract quantity
            String sqlDetail;
            for (HotelDTO hotelDTO : orderDTO.getListHotels()) {
                //float subTotal = book.getPrice() * book.getQuantityCart();
                sqlDetail = "insert into tblOrderDetail(OrderId, HotelId, TypeId, Quantity, CheckIn, CheckOut, SubTotal) "
                        + "values('" + orderId + "'," + hotelDTO.getHotelId() + "," + hotelDTO.getTypeId() + ","
                        + "" + hotelDTO.getQuantityCart() + ",'"
                        + "" + hotelDTO.getCheckIn() + "','" + hotelDTO.getCheckOut() + "'," + hotelDTO.getSubTotalCart() + ")";
                st.addBatch(sqlDetail);
            }

            if (orderDTO.getCouponCode() != null && !orderDTO.getCouponCode().isEmpty()) {
                // Updaet Discount Code
                String sqlDiscount = "update tblCoupon set Status='Used' "
                        + "where Code='" + orderDTO.getCouponCode() + "' "
                        + "and StartDate<=GETDATE() and EndDate>=GETDATE() "
                        + "and Status='NotUsed'";
                st.addBatch(sqlDiscount);
            }

            int resultArr[] = st.executeBatch();
            int i = 0;
            while (result && i < resultArr.length) {
                result = result && resultArr[i] > 0;
                i++;
            }
            con.commit();
        }

        return result;
    }

    public List<OrderDTO> loadHistory(String email) throws NamingException, SQLException {
        List<OrderDTO> result = null;
        List<HotelDTO> listHotel = null;
        OrderDTO orderDTO = null;
        HotelDTO hotelDTO = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String sqlOrder = "select distinct OrderId, Total, CouponValue from tblOrder where Email = ? and Status = 'Active'";
        try (
                Connection con = DBConnection.makeConnection();
                PreparedStatement ps = con.prepareStatement(sqlOrder)) {
            ps.setString(1, email);
            try (
                    ResultSet rs = ps.executeQuery()) {
                result = new ArrayList<>();
                while (rs.next()) {
                    orderDTO = new OrderDTO();
                    orderDTO.setEmail(email);
                    orderDTO.setOrderId(rs.getInt("OrderId"));
                    orderDTO.setTotal(rs.getFloat("Total"));
                    orderDTO.setCouponValue(rs.getFloat("CouponValue"));

                    String sqlDetail = "select tblHotel.Name as HotelName, tblType.Name as TypeName, Quantity, "
                            + "CheckIn, CheckOut, SubTotal from tblOrderDetail "
                            + "inner join tblHotel on tblHotel.HotelId = tblOrderDetail.HotelId "
                            + "inner join tblType on tblType.TypeId = tblOrderDetail.TypeId "
                            + "where OrderId=?";

                    try (
                            PreparedStatement ps2 = con.prepareStatement(sqlDetail)) {
                        ps2.setInt(1, orderDTO.getOrderId());
                        try (
                                ResultSet rsDetail = ps2.executeQuery()) {
                            listHotel = new ArrayList<>();
                            while (rsDetail.next()) {
                                hotelDTO = new HotelDTO();
                                hotelDTO.setName(rsDetail.getString("HotelName"));
                                hotelDTO.setType(rsDetail.getString("TypeName"));
                                hotelDTO.setQuantityCart(rsDetail.getInt("Quantity"));
                                hotelDTO.setCheckIn(sdf.format(rsDetail.getTimestamp("CheckIn")));
                                hotelDTO.setCheckOut(sdf.format(rsDetail.getTimestamp("CheckOut")));
                                hotelDTO.setSubTotalHistory(rsDetail.getFloat("SubTotal"));
                                listHotel.add(hotelDTO);
                            }
                        }
                    }
                    orderDTO.setListHotels(listHotel);
                    result.add(orderDTO);
                }
            }

        }
        return result;
    }

    public boolean deleteHistory(int orderId) throws NamingException, SQLException {
        boolean result = false;
        String sql = "update tblOrder set Status = 'Inactive' where OrderId = ?";
        try (
                Connection con = DBConnection.makeConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);

            result = ps.executeUpdate() > 0;
        }

        return result;
    }

    public List<OrderDTO> searchHistory(String email, String hotelName) throws NamingException, SQLException {
        List<OrderDTO> result = null;
        List<HotelDTO> listHotel = null;
        OrderDTO orderDTO = null;
        HotelDTO hotelDTO = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String sqlOrder = "select distinct OrderId, Total, CouponValue from tblOrder where Email = ? and Status = 'Active'";
        try (
                Connection con = DBConnection.makeConnection();
                PreparedStatement ps = con.prepareStatement(sqlOrder)) {
            ps.setString(1, email);
            try (
                    ResultSet rs = ps.executeQuery()) {
                result = new ArrayList<>();
                while (rs.next()) {
                    orderDTO = new OrderDTO();
                    orderDTO.setEmail(email);
                    orderDTO.setOrderId(rs.getInt("OrderId"));
                    orderDTO.setTotal(rs.getFloat("Total"));
                    orderDTO.setCouponValue(rs.getFloat("CouponValue"));

                    String sqlDetail = "select tblHotel.Name as HotelName, tblType.Name as TypeName, Quantity, "
                            + "CheckIn, CheckOut, SubTotal from tblOrderDetail "
                            + "inner join tblHotel on tblHotel.HotelId = tblOrderDetail.HotelId "
                            + "inner join tblType on tblType.TypeId = tblOrderDetail.TypeId "
                            + "where OrderId=? and tblHotel.Name like ?";

                    try (
                            PreparedStatement ps2 = con.prepareStatement(sqlDetail)) {
                        ps2.setInt(1, orderDTO.getOrderId());
                        ps2.setString(2, "%" + hotelName + "%");
                        try (
                                ResultSet rsDetail = ps2.executeQuery()) {
                            listHotel = new ArrayList<>();
                            while (rsDetail.next()) {
                                hotelDTO = new HotelDTO();
                                hotelDTO.setName(rsDetail.getString("HotelName"));
                                hotelDTO.setType(rsDetail.getString("TypeName"));
                                hotelDTO.setQuantityCart(rsDetail.getInt("Quantity"));
                                hotelDTO.setCheckIn(sdf.format(rsDetail.getTimestamp("CheckIn")));
                                hotelDTO.setCheckOut(sdf.format(rsDetail.getTimestamp("CheckOut")));
                                hotelDTO.setSubTotalHistory(rsDetail.getFloat("SubTotal"));
                                listHotel.add(hotelDTO);
                            }
                        }
                    }
                    if (!listHotel.isEmpty()) {
                        orderDTO.setListHotels(listHotel);
                        result.add(orderDTO);
                    }
                }
            }
        }
        return result;
    }
}
