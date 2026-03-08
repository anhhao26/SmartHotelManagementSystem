package service;

import dao.BookingDAO;
import model.Booking;

public class BookingService {
    private final BookingDAO dao = new BookingDAO();

    public String book(Booking b){
        if(b.getCheckInDate() == null || b.getCheckOutDate() == null)
            return "Thiếu ngày nhận/trả phòng";

        if(b.getCheckOutDate().before(b.getCheckInDate()))
            return "Ngày trả phòng phải sau ngày nhận phòng";

        boolean ok = dao.saveBooking(b);

        if(!ok)
            return "Rất tiếc, phòng đã có người đặt trong thời gian này!";

        return "OK";
    }
}