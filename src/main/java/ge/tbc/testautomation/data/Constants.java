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
    public static final String ORDER_BODY = """
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
    public static final String PETSTORE_BASEURI = "https://petstore.swagger.io/v2";
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
    public static final String UPDATED_LAST_NAME = "changedlastname";
    public static final String UPDATED_NEEDS = "updated additionalneeds";
    public static final String UPDATED_CHECKIN = "2025-06-13";
    public static final String UPDATED_CHECKOUT = "2025-07-03";
    public static final String DUTCH = "Dutch";
    public static final String EXPECTED_LAST1 = "Nicholas C. Zakas";
    public static final String EXPECTED_LAST2 = "Marijn Haverbeke";
    public static final String ERR_MSG = "deserialization failed";
    public static final String BREAKFST = "Breakfast";
    public static final String ESC_BASEURI = "https://api.escuelajs.co/api";
    public static final String AVATAR = "https://random.com/fake-avatar";
    public static final String CATEGORY = "Dogs";
    public static final String EXPECTED_NAME = "Kamino";
    public static final String EXPECTED_TERRAIN = "ocean";
    public static final String EXPECTED_URL = "https://www.swapi.tech/api/planets/10";
    public static final String NAMES = "Names of Planets: ";
    public static final String PASS_ERROR = "Must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.";
    public static final String EXP_PETNAME = "Doggo";
    public static final String READ = "READ_PRIVILEGE";
    public static final String WRITE = "WRITE_PRIVILEGE";
    public static final String DELETE = "DELETE_PRIVILEGE";
    public static final String UPDATE = "UPDATE_PRIVILEGE";
    public static final String ADM = "ROLE_ADMIN";
    public static final long TESTID = 99L;
    public static final String VALIDATION_MSG = "Hello, you have access to a protected resource";
    public static final String LOCAL_URL = "http://localhost:8087/ws";
    public static final String ADD_ACT = "interfaces.soap.springboot.example.com/exampleSoapHttp/addEmployeeRequest";
    public static final String RAND_ADDR = "address";
    public static final String DEP = "dep";
    public static final String ADDED_MESSAGE = "Content Added Successfully";
    public static final String GETBYID_ACT = "interfaces.soap.springboot.example.com/exampleSoapHttp/getEmployeeByIdRequest";
    public static final String UPDATED_NAME = "newname";
    public static final String UPDATED_DEP = "newdep";
    public static final String UPDATED_ADDRESS = "newaddress";
    public static final String UPDATE_ACT = "interfaces.soap.springboot.example.com/exampleSoapHttp/updateEmployeeRequest";
    public static final String UPDATED_MESSAGE = "Content Updated Successfully";
    public static final long NEW_TESTID = 77L;
    public static final String DELETE_ACT = "interfaces.soap.springboot.example.com/exampleSoapHttp/deleteEmployeeRequest";
    public static final String DELETED_MESSAGE = "Content Deleted Successfully";
    public static final String AFRICA = "Africa";
    public static final String ANTARCTICA = "Antarctica";
    public static final String ASIA = "Asia";
    public static final String EUROPE = "Europe";
    public static final String OCENANIA = "Ocenania";
    public static final String THE_AMERICAS = "The Americas";
    public static final String NO_OCENANIA_ERROR = "No ocenania found";
    public static final String C_BASEURI = "http://webservices.oorsprong.org";
    public static final String COUNTRY_BASEPATH = "/websamples.countryinfo/CountryInfoService.wso";


}
