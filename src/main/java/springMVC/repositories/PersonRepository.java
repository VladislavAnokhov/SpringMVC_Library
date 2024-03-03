package springMVC.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springMVC.models.Book;
import springMVC.models.Person;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

Person findByName(String name);
}
