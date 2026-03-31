package J2EE_Bai6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import J2EE_Bai6.models.Book;
import J2EE_Bai6.repository.BookRepository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() { return bookRepository.findAll(); }
    public List<Book> searchBooksByKeyword(String keyword) {
        return bookRepository.findByNameContainingIgnoreCase(keyword);
    }
    public Page<Book> searchBooksByKeyword(String keyword, @NonNull Pageable pageable) {
        return bookRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }
    public Page<Book> getAllBooks(@NonNull Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    public Page<Book> filterBooksByCategory(Integer categoryId, @NonNull Pageable pageable) {
        return bookRepository.findByCategory_Id(categoryId, pageable);
    }
    public Page<Book> searchBooksByCategoryAndKeyword(Integer categoryId, String keyword, @NonNull Pageable pageable) {
        return bookRepository.findByCategory_IdAndNameContainingIgnoreCase(categoryId, keyword, pageable);
    }
    @SuppressWarnings("null")
    public Book saveBook(Book book) { return bookRepository.save(book); }
    public Book getBookById(int id) { return bookRepository.findById(id).orElse(null); }
    @SuppressWarnings("null")
    public Book updateBook(Book book) { return bookRepository.save(book); }
    public void deleteBook(int id) { bookRepository.deleteById(id); }
}