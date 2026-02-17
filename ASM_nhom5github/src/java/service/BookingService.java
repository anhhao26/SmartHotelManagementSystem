/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.BookingDAO;
import entity.Booking;

public class BookingService {

    BookingDAO dao = new BookingDAO();

    public String book(Booking b){

        if(b.getCheckIn()==null || b.getCheckOut()==null)
            return "Thiếu ngày";

        if(b.getCheckOut().before(b.getCheckIn()))
            return "Ngày checkout phải sau checkin";

        boolean ok = dao.saveBooking(b);

        if(!ok)
            return "Phòng đã có người đặt";

        return "OK";
    }
}


