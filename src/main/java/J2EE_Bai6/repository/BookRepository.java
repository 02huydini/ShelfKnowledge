package J2EE_Bai6.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import J2EE_Bai6.models.Book;
public interface BookRepository extends JpaRepository<Book, Integer> {
}