package springMVC.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springMVC.models.Book;
import springMVC.models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
List<Book> getBooksByOwner_Id(int id);
List<Book> findByNameStartingWith(String string);
}
