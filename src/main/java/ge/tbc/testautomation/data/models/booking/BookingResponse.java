package ge.tbc.testautomation.data.models.booking;

public class BookingResponse {
    private int bookingid;
    private ge.tbc.testautomation.data.models.responses.booking.BookingResponse booking;

    public BookingResponse() {}

    public int getBookingid() { return bookingid; }
    public void setBookingid(int bookingid) { this.bookingid = bookingid; }

    public ge.tbc.testautomation.data.models.responses.booking.BookingResponse getBooking() { return booking; }
    public void setBooking(ge.tbc.testautomation.data.models.responses.booking.BookingResponse booking) { this.booking = booking; }
}
