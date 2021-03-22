package com.insight.onlinebookstore;

import com.insight.onlinebookstore.model.Book;
import com.insight.onlinebookstore.model.Cart;
import com.insight.onlinebookstore.model.TotalPrice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@SpringBootApplication
public class OnlineBookStoreApplication implements CommandLineRunner {
	private static final Logger LOG = LogManager.getLogger(OnlineBookStoreApplication.class);

	@Autowired
	BookService bookService;

	public static void main(String[] args) {
		LOG.info("STARTING ONLINE BOOK STORE");
		SpringApplication.run(OnlineBookStoreApplication.class, args);
		LOG.info("ONLINE BOOK STORE finished");
	}

	@Override
	public void run(String... args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int input = 0;
		List<Book> stock = bookService.getStock();
		String choiceTemplate = choiceTemplate(stock);
		Cart cart = new Cart();

		// Input 100 to terminate the application
		while (input != 100) {
			System.out.println(choiceTemplate);
			input = Integer.parseInt(br.readLine());

			// Input 99 to calculate total price
			if (input == 99) {
				TotalPrice totalPrice = bookService.calculateTotalPrice(cart.getBooks());
				System.out.println(String.format("Total Price (with GST): AU$ %.2f", totalPrice.getTotalPrice()));
				System.out.println(String.format("Total Price (without GST): AU$ %.2f", totalPrice.getTotalPriceWithoutGST()));
				break;
			}

			// any invalid input forces reiteration
			if (input > stock.size()) {
				System.out.println("Invalid choice!!");
				continue;
			}
			cart.addBook(stock.get(input - 1));
		}
	}

	private String choiceTemplate(List<Book> stock) throws IOException {
		StringBuilder template = new StringBuilder("Choice     \tTitle            \t\tPrice(AU$)\n");
		int choice = 1;
		for (Book book : stock) {
			template.append(choice + "        \t" + book.getTitle() + "\t\t" + book.getPrice() + "\n");
			choice++;
		}

		template.append("[99 - Checkout, 100 - Quit]\nPlease make a choice:   ");
		return template.toString();
	}

}
