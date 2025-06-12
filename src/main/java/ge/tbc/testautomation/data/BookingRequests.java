package ge.tbc.testautomation.data;

import org.json.JSONObject;

public class BookingRequests {
//    public JSONObject buildAuth(String username, String password) {
//        JSONObject auth = new JSONObject();
//        auth.put("username", username);
//        auth.put("password", password);
//        return auth;
//    }

    public JSONObject buildBookingDates(String checkin, String checkout) {
        JSONObject dates = new JSONObject();
        dates.put("checkin", checkin);
        dates.put("checkout", checkout);
        return dates;
    }

    public JSONObject buildBooking(String firstname, String lastname, int totalPrice, boolean depositPaid, String needs, JSONObject bookingDates) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstname", firstname)
                .put("lastname", lastname)
                .put("totalprice", totalPrice)
                .put("depositpaid", depositPaid)
                .put("additionalneeds", needs)
                .put("bookingdates", bookingDates);
        return jsonObject;
    }
}
