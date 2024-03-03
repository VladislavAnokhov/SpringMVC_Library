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
import springMVC.validator.PersonValidator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;

    private final PersonValidator personValidator;
    private final BookService bookService;
    @Autowired
    public PersonController(PersonService personService, PersonValidator personValidator, BookService bookService) {
        this.personService = personService;
        this.personValidator = personValidator;
        this.bookService = bookService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String index (Model model,
                         @RequestParam(value = "page",required = false,defaultValue = "1")int page,
                         @RequestParam(value = "limit",required = false,defaultValue = "10")int limit){
        List<Person> people = personService.byPage(page-1,limit);
        model.addAttribute("people",people);
        model.addAttribute("current_page",page);
        int totalPages = (int) Math.ceil(1.0* personService.getAllCount()/ limit);
        List<Integer> pageNumbers= null;
        if(totalPages>1) {
            pageNumbers= IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        }
        model.addAttribute("page_numbers",pageNumbers);

        return "/people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "/people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person")@Valid Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()){
            return "/people/new";
        }
        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("person",personService.getById(id));
        return "/people/edit";
    }

    @GetMapping("/{id}")
    public String show (@PathVariable("id") int id,Model model){
        model.addAttribute("person",personService.getById(id));
        model.addAttribute("books",personService.findBooks(id));
        return "/people/show";
    }

    @PatchMapping("/{id}")
    public String update (@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,@PathVariable("id") int id){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return  "people/edit";
        personService.upDate(id,person);
    return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personService.delete(id);
        return "redirect:/people";
    }
}
