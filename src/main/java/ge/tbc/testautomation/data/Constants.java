package ge.tbc.testautomation.data;

public class Constants {
    public static final String BOOK_URL = "https://bookstore.toolsqa.com";
    public static final String BOOKS_BASEPATH = "/BookStore/v1/Books";
    public static final String BOOKSTORE = "/BookStore/v1/Book";
    public static final String UNAUTHORIZED_ERROR = "User not authorized!";
    public static final String BOOK_FULL = "https://bookstore.toolsqa.com/BookStore/v1/Books";
    public static final String ISBN = "ISBN";
    public static final String PATH_AUTHOR = "author";
    public static final String PATH_TITLE = "title";
    public static final String PATH_ISBN = "isbn";
    public static final String PATH_DATE = "publish_date";
    public static final String PATH_PAGES = "pages";
    public static final String ORDER_BODY =  """
                {
                    "id": 999,
                    "petId": 888,
                    "quantity": 2,
                    "shipDate": "2025-06-09T00:00:00.000Z",
                    "status": "placed",
                    "complete": true
                }
                """;
    public static final String PATH_ORDER = "/store/order";
    public static final int EXPECTED_ID = 999;
    public static final int EXPECTED_PET_ID = 888;
    public static final String EXPECTED_STATUS = "placed";
    public static final String EXPECTED_DATE = "2025-06-09T00:00:00.000+0000";
    public static final String PATH_ID = "id";
    public static final String PATH_PETID = "petId";
    public static final String PATH_STATUS = "status";
    public static final String PATH_SHIPDATE = "shipDate";
    public static final String PETSTORE_BASEURI ="https://petstore.swagger.io/v2";
    public static final String PARAM_NAME = "randomname";
    public static final String PARAM_STATUS = "available";
    public static final String EXPECTED_TYPE = "unknown";
    public static final String EXPECTED_MSG = "not found";
    public static final int INVALID_PETID = 998989;
    public static final int VALID_PETID = 3;
    public static final String PETNAME = "Diasy";
    public static final String STATUS = "6000";
    public static final String INVALID_GET = "https://petstore.swagger.io/v2/pet/999999999";
    public static final String NOT_FOUND_MSG = "Pet not found";
    public static final String JSONBODY = "{\"isbn\":\"69999996\"}";
    public static final String USERNAME = "someuser";
    public static final String PASSWORD = "pass123";
    public static final String LOGIN = "https://petstore.swagger.io/v2/user/login";
    public static final String SEARCH_KEYWORD = "Harry Potter";
    public static final String LIBRARY = "https://openlibrary.org/search.json";
    public static final String EXPECTED_TITLE = "Harry Potter and the Philosopher's Stone";
    public static final String EXPECTED_AUTHOR = "J. K. Rowling";
    public static final String EXPECTED_PLACE1 = "England";
    public static final String EXPECTED_PLACE2 = "Hogwarts School of Witchcraft and Wizardry";
    public static final String EXPECTED_PLACE3 = "Platform Nine and Three-quarters";
    public static final String DRIVERS_BASE = "https://api.jolpi.ca";
    public static final String EXPETCED_VERSTAPPEN = "Verstappen";
    public static final String BRAZIL = "Brazilian";
    public static final String CANADIAN = "Canadian";
    public static final String EXPECTED_ITEM = "Lance Stroll";
    public static final String DRIVERS_ERGAST = "/ergast/f1/2025/drivers";
    public static final String EXP_AUTHOR1 = "Richard E. Silverman";
    public static final String EXP_AUTHOR2 = "Addy Osmani";
    public static final String BOOKSTORE_URL = "https://bookstore.toolsqa.com/BookStore/v1";
    public static final String NO_PET_ERROR = "pet not found";
    public static final String BOOKER_URL = "https://restful-booker.herokuapp.com";
    public static final String NAME = "randomname";
    public static final String LAST_NAME = "randomlastname";
    public static final String NEEDS = "breakfast";
    public static final String CHECKIN = "2025-06-12";
    public static final String CHECKOUT = "2025-06-30";
    public static final String UPDATED_NAME = "changedname";
    public static final String UPDATED_LAST_NAME = "changedlastname";
    public static final String UPDATED_NEEDS = "updated additionalneeds";
    public static final String UPDATED_CHECKIN = "2025-06-13";
    public static final String UPDATED_CHECKOUT = "2025-07-03";
    public static final String DUTCH = "Dutch";







}
