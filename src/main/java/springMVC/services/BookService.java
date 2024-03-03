package springMVC.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springMVC.models.Book;
import springMVC.models.Person;
import springMVC.repositories.BookRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public int getAllCount(){
        return (int) bookRepository.count();
    }

    public List<Book> byPage(int page,int itemsPerPage){
        return bookRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("name"))).getContent();
    }

    public List<Book> getAll(){
        return  bookRepository.findAll();
    }

    public Book getById(int id){
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id,Book book){
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    public Person getBookOwner(int id){
        return bookRepository.findById(id).get().getOwner();
    }

    @Transactional
    public void release(int id){
       Book book= bookRepository.findById(id).orElse(null);
        book.setOwner(null);
        book.setTimeOfSetOwner(null);
        bookRepository.save(book);
    }

    @Transactional
    public void assign(int id,Person person){
        Book book= bookRepository.findById(id).orElse(null);
        book.setTimeOfSetOwner(new Date());
        book.setOwner(person);
        bookRepository.save(book);
    }

    public List<Book> searchByTitle(String bookName) {
        return bookRepository.findByNameStartingWith(bookName);
    }
}
