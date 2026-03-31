package J2EE_Bai6.controllers;

import J2EE_Bai6.models.Book;
import J2EE_Bai6.models.CartLine;
import J2EE_Bai6.models.Order;
import J2EE_Bai6.service.BookService;
import J2EE_Bai6.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {
    private static final String CART_KEY = "cart";

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        Map<Integer, Integer> cart = getCart(session);
        List<CartLine> cartLines = buildCartLines(cart);
        long totalAmount = cartLines.stream().mapToLong(CartLine::getLineTotal).sum();

        model.addAttribute("cartLines", cartLines);
        model.addAttribute("totalAmount", totalAmount);
        return "cart/cart";
    }
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Integer bookId,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session) {
        if (quantity < 1) quantity = 1;

        Map<Integer, Integer> cart = getCart(session);
        cart.merge(bookId, quantity, Integer::sum);
        session.setAttribute(CART_KEY, cart);

        return "redirect:/books";
    }
    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Integer bookId,
                             @RequestParam int quantity,
                             HttpSession session) {
        Map<Integer, Integer> cart = getCart(session);
        if (quantity < 1) {
            cart.remove(bookId);
        } else {
            cart.put(bookId, quantity);
        }
        session.setAttribute(CART_KEY, cart);
        return "redirect:/cart";
    }
    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Integer bookId,
                                 HttpSession session) {
        Map<Integer, Integer> cart = getCart(session);
        cart.remove(bookId);
        session.setAttribute(CART_KEY, cart);
        return "redirect:/cart";
    }
    @PostMapping("/checkout")
    public String checkout(HttpSession session,
                           Authentication authentication,
                           Model model) {
        Map<Integer, Integer> cart = getCart(session);
        if (cart.isEmpty()) {
            return "redirect:/cart";
        }

        String username = authentication.getName();
        Order order = orderService.checkout(username, cart);
        session.removeAttribute(CART_KEY);

        model.addAttribute("order", order);
        return "cart/order_confirmation";
    }
    @SuppressWarnings("unchecked")
    private Map<Integer, Integer> getCart(HttpSession session) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(CART_KEY);
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute(CART_KEY, cart);
        }
        return cart;
    }

    private List<CartLine> buildCartLines(Map<Integer, Integer> cart) {
        List<CartLine> lines = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Book book = bookService.getBookById(entry.getKey());
            if (book == null) continue;
            int qty = entry.getValue();
            CartLine line = new CartLine();
            line.setBook(book);
            line.setQuantity(qty);
            line.setLineTotal(book.getPrice() * qty);
            lines.add(line);
        }
        return lines;
    }
}