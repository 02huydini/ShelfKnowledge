package J2EE_Bai6.service;

import J2EE_Bai6.models.Account;
import J2EE_Bai6.models.Book;
import J2EE_Bai6.models.Order;
import J2EE_Bai6.models.OrderDetail;
import J2EE_Bai6.repository.AccountRepository;
import J2EE_Bai6.repository.BookRepository;
import J2EE_Bai6.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BookRepository bookRepository;

    public Order checkout(String username, Map<Integer, Integer> cartItems) {
        Account account = accountRepository.findByLoginName(username)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        Order order = new Order();
        order.setAccount(account);
        order.setOrderDate(LocalDateTime.now());

        long total = 0L;
        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
            int bookId = entry.getKey();
            int quantity = entry.getValue();
            if (quantity <= 0) continue;

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));

            long unitPrice = book.getPrice();
            long lineTotal = unitPrice * (long) quantity;
            total += lineTotal;

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setBook(book);
            detail.setQuantity(quantity);
            detail.setUnitPrice(unitPrice);
            detail.setLineTotal(lineTotal);

            order.getOrderDetails().add(detail);
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }
}

