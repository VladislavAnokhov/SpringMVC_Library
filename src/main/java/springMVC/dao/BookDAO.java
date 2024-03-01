package springMVC.dao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springMVC.models.Book;
import springMVC.models.Person;

import java.util.List;
import java.util.Optional;

@Component

public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("select * from book",
                new BeanPropertyRowMapper<>(Book.class));
    }
    public Optional<Book> show (int id){
        return jdbcTemplate.query("select * from book where id =?",
                        new Object[]{id},new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny();
    }
    public Optional<Book> show (String name){
        return jdbcTemplate.query("select * from book where name=?",
                new Object[]{name},new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public void save(Book book){
        jdbcTemplate.update("insert into book(name,author,year) values (?,?,?)",
                book.getName(),book.getAuthor(),book.getYear());

    }
    public void updateFull(int id, Book updatedBook){
        jdbcTemplate.update("update book set name=?,author=?,year=? where id=?",
                updatedBook.getName(),updatedBook.getAuthor(),updatedBook.getYear(),id);
    }
    public void delete(int id){
        jdbcTemplate.update("delete from book where id=?",id);
    }
    public void deleteUser(int personID){
        jdbcTemplate.update("update book set person_id=null where person_id=?",personID);}

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("select person.* from book join person on book.person_id=person.id " +
                        "where book.id=?",new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void release(int id) {
     jdbcTemplate.update("update book set person_id=NULL where id=?",id);
    }
    public void assign(int id, Person person) {
        jdbcTemplate.update("update book set person_id=? where id=?",person.getId(),id);
    }
}
