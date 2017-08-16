package com.example.a.party_booker;

/**
 * Created by Dhruv on 09/05/2017.
 */

public class BeanHistroy {
    String bookingid="";
    String status="";
    String bookingdate="";
    String price="";
    String leavingdate="";
    String bookingfor="";
    String address="";
    String venuename="";

    public BeanHistroy() {
    }

    public BeanHistroy(String bookingid, String status, String bookingdate, String price, String leavingdate, String bookingfor, String address, String venuename) {
        this.bookingid = bookingid;
        this.status = status;
        this.bookingdate = bookingdate;
        this.price = price;
        this.leavingdate = leavingdate;
        this.bookingfor = bookingfor;
        this.address = address;
        this.venuename = venuename;
    }

    public String getBookingid() {
        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.bookingdate = bookingdate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLeavingdate() {
        return leavingdate;
    }

    public void setLeavingdate(String leavingdate) {
        this.leavingdate = leavingdate;
    }

    public String getBookingfor() {
        return bookingfor;
    }

    public void setBookingfor(String bookingfor) {
        this.bookingfor = bookingfor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVenuename() {
        return venuename;
    }

    public void setVenuename(String venuename) {
        this.venuename = venuename;
    }
}
