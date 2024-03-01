package springMVC.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springMVC.models.Book;
import springMVC.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("select * from Person", new BeanPropertyRowMapper<>(Person.class));
    }
    public Optional<Person> show (int id){
        return jdbcTemplate.query("select * from person where id =?",new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }
    public Optional<Person> show (String name){
        return jdbcTemplate.query("select * from person where name=?",new Object[]{name},new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person){
        jdbcTemplate.update("insert into Person(name,birth_year) values (?,?)",person.getName(),person.getBirthYear());

    }
    public void updateName(int id, Person updatedPerson){
        jdbcTemplate.update("update Person set name=? where id=?",updatedPerson.getName(),id);
    }
    public void updateBirthYear(int id, Person updatedPerson){
        jdbcTemplate.update("update Person set birth_year=? where id=?",updatedPerson.getBirthYear(),id);
    }
    public void updateFull(int id, Person updatedPerson){
        jdbcTemplate.update("update Person set name=?,birth_year=? where id=?",updatedPerson.getName(),updatedPerson.getBirthYear(),id);
    }
    public void delete(int id){
        jdbcTemplate.update("delete from person where id=?",id);
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("select * from book where person_id=?" , new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
