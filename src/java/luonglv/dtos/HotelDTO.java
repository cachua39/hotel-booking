/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.dtos;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;

/**
 *
 * @author LeVaLu
 */
public class HotelDTO implements Serializable {

    private int hotelId, typeId;
    private String name, address, type, area, photo;
    private float price, subTotal, subTotalCart, subTotalHistory;
    private int quantity, availableRoom, maxPeople, quantityCart;
    private long rentDay;
    private String checkIn, checkOut;

    public HotelDTO() {
    }

    /**
     * Constructor for loading hotels from database
     */
    public HotelDTO(int hotelId, int typeId, String name, String address, String type, String photo, 
            float price, int availableRoom, int maxPeople, String checkIn, String checkOut, int quantity) {
        this.hotelId = hotelId;
        this.typeId = typeId;
        this.name = name;
        this.address = address;
        this.type = type;
        this.photo = photo;
        this.price = price;
        this.availableRoom = availableRoom;
        this.maxPeople = maxPeople;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.quantity = quantity;
    }

    /**
     * Constructor for search hotel
     */
    public HotelDTO(String area, int quantity, String checkIn, String checkOut) {
        this.area = area;
        this.quantity = quantity;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
    
    // Constructor for add to cart

    public HotelDTO(int hotelId, int typeId, String name, String address, String type, String photo, float price) {
        this.hotelId = hotelId;
        this.typeId = typeId;
        this.name = name;
        this.address = address;
        this.type = type;
        this.photo = photo;
        this.price = price;
    }
    

    public long getRentDay() {
        LocalDate in = LocalDate.parse(checkIn);
        LocalDate out = LocalDate.parse(checkOut);
        Duration longDiff = Duration.between(in.atStartOfDay(), out.atStartOfDay());
        return longDiff.toDays();
    }

    public float getSubTotal() {
        return price * getRentDay();
    }

    public float getSubTotalCart() {
        return price * getRentDay() * quantityCart;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAvailableRoom() {
        return availableRoom;
    }

    public void setAvailableRoom(int availableRoom) {
        this.availableRoom = availableRoom;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
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

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityCart() {
        return quantityCart;
    }

    public void setQuantityCart(int quantityCart) {
        this.quantityCart = quantityCart;
    }

    public float getSubTotalHistory() {
        return subTotalHistory;
    }

    public void setSubTotalHistory(float subTotalHistory) {
        this.subTotalHistory = subTotalHistory;
    }
    

}
