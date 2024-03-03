package springMVC.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import springMVC.models.Book;
import springMVC.models.Person;
import springMVC.services.BookService;
import springMVC.services.PersonService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/books")
public class BookController {

    private final PersonService personService;
    private final BookService bookService;
    @Autowired
    public BookController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }
    @GetMapping("/search")
    public String searchBooks(@RequestParam(name = "bookName", required = false) String bookName, Model model) {

        List<Book> foundBooks = bookService.searchByTitle(bookName);
        model.addAttribute("foundBooks", foundBooks);
        return "/books/search";
    }


    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(value = "page",required = false,defaultValue = "1")int page,
                        @RequestParam(value = "limit",required = false,defaultValue = "10")int limit){
        List<Book> books = bookService.byPage(page-1,limit);
        model.addAttribute("books",books);
        model.addAttribute("current_page",page);
        int totalPages = (int) Math.ceil(1.0* bookService.getAllCount()/ limit);
        List<Integer> pageNumbers= null;
        if(totalPages>1)
             pageNumbers= IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers",pageNumbers);
        return "/books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "/books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") Book book){
        bookService.save(book);
        return "redirect:books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,Model model,@ModelAttribute("person")Person person){
        model.addAttribute("book",bookService.getById(id));
        Person owner = bookService.getBookOwner(id);
        if (owner!=null)
            model.addAttribute("owner",owner);
        else
            model.addAttribute("people",personService.getAll());
        return "/books/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book",bookService.getById(id));
        return "/books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("id") int id){
        bookService.update(id,book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookService.release(id);
        return "redirect:/books/"+id;
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        bookService.assign(id,person);
        return "redirect:/books/"+id;
    }

}
