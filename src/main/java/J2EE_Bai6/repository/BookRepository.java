package J2EE_Bai6.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import J2EE_Bai6.models.Book;
import java.util.List;
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameContainingIgnoreCase(String keyword);
    Page<Book> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Book> findByCategory_Id(Integer categoryId, Pageable pageable);
    Page<Book> findByCategory_IdAndNameContainingIgnoreCase(Integer categoryId, String keyword, Pageable pageable);
}