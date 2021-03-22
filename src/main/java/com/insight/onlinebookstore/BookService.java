package com.insight.onlinebookstore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight.onlinebookstore.model.Book;
import com.insight.onlinebookstore.model.TotalPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    @Value("${book.store.delivery.fee.minimum.threshold}")
    private double deliveryFeeMinimumThreshold;

    @Value("${book.store.delivery.fee}")
    private double deliveryFee;

    @Value("${book.store.gst.percent}")
    private double gstPercent;

    /**
     * Calculates total book prices including and excluding GST
     * @param books
     * @return total price
     * @throws IOException
     */
    public TotalPrice calculateTotalPrice(List<Book> books) throws IOException {

        double totalBasePrice = 0.00;
        Map<String, Double> discountMap = getDiscountMap();

        // adding base price of all books in the cart
        for(Book book: books) {
            totalBasePrice += book.getDiscountPrice(discountMap);
        }

        // calculating the total price excluding GST
        double totalBasePriceWithDeliveryFee = totalBasePrice < deliveryFeeMinimumThreshold ? totalBasePrice + deliveryFee : totalBasePrice;

        // calculating GST
        double gst = (gstPercent / 100) * totalBasePrice;

        return new TotalPrice(totalBasePriceWithDeliveryFee + gst, totalBasePriceWithDeliveryFee);

    }

    /**
     * Gets the all the books from books.json in resources folder.
     * Books can be added or removed from books.json as required providing flexibility.
     *
     * @return a list of books
     * @throws IOException
     */
    public List<Book> getStock() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/books.json");
        List<Book> books = objectMapper.readValue(file, new TypeReference<List<Book>>(){});
        return books;
    }

    /**
     * Gets discount map from discount.json from resources folder.
     * New discount percentages can be added to different genres as additional flexibility.
     *
     * @return a map of genres and their respective discounts
     * @throws IOException
     */
    public Map<String, Double> getDiscountMap() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/discount.json");
        Map<String, Double> discountMap = objectMapper.readValue(file, new TypeReference<Map<String, Double>>(){});
        return discountMap;
    }
}
