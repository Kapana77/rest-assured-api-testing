package tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.models.books.Book;
import ge.tbc.testautomation.data.models.books.ResponseBook;
import ge.tbc.testautomation.steps.BookStorePOJOSteps;
import org.testng.annotations.Test;

import java.util.List;

public class BookStorePOJOTest {
    @Test
    public void testBookListAndAuthors() {
        BookStorePOJOSteps steps = new BookStorePOJOSteps();

        ResponseBook response = steps.getAllBooks();
        List<Book> books = response.getBooks();

        steps.validateAllBooksHaveLessThan(1000, books)
                .validateLastTwoAuthors(books, Constants.EXPECTED_LAST1, Constants.EXPECTED_LAST2);
    }
}
