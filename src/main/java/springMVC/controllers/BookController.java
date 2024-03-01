package springMVC.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springMVC.dao.BookDAO;
import springMVC.dao.PersonDAO;
import springMVC.models.Book;
import springMVC.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books",bookDAO.index());
        return "/books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "/books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") Book book){
        bookDAO.save(book);
        return "redirect:books";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,Model model,@ModelAttribute("person")Person person){
        Book book= bookDAO.show(id).orElse(new Book());
        model.addAttribute("book",book);

      Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if (bookOwner.isPresent())
            model.addAttribute("owner",bookOwner.get());
        else
            model.addAttribute("people",personDAO.index());
        return "/books/show";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        Book book = bookDAO.show(id).orElse(new Book());
        model.addAttribute("book",book);
        return "/books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("id") int id){
        bookDAO.updateFull(id,book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookDAO.release(id);
        return "redirect:/books/"+id;
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        bookDAO.assign(id,person);
        return "redirect:/books/"+id;
    }

}
