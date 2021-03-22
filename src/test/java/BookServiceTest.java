import com.insight.onlinebookstore.BookService;
import com.insight.onlinebookstore.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Jimy on 22/03/2021.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Test
    public void test_getStock() throws IOException {
        List<Book> books = bookService.getStock();
        assertNotNull(books);
    }
}
