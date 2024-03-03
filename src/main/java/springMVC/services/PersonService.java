package springMVC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springMVC.models.Book;
import springMVC.models.Person;
import springMVC.repositories.PersonRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> byPage(int page,int itemsPerPage){
        return personRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("name"))).getContent();
    }

    public int getAllCount(){
        return (int) personRepository.count();
    }

    public List<Book> findBooks (int id){
       List<Book> result = new ArrayList<>( personRepository.findById(id).orElse(null).getBookList());
        if (result==null) {result = new ArrayList<>();
        return result;}

        Date currentDate = new Date();
        Date tenDaysAgo = new Date(currentDate.getTime() - 10 * 24 * 60 * 60 * 1000L);
        for (Book book : result){
           book.setNeedReturn(book.getTimeOfSetOwner().before(tenDaysAgo));

        }
        for (Book book : result){
            System.out.println(book.getNeedReturn());
        }
        return result;
    }

    public List<Person> getAll(){
        return personRepository.findAll();
    }

    public Person getById(int id){
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void upDate(int id,Person person){
        person.setId(id);
        personRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }

    public Person getByName(String name){
        return personRepository.findByName(name);
    }

}
