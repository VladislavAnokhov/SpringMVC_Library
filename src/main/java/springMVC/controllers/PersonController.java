package springMVC.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springMVC.dao.PersonDAO;
import springMVC.models.Person;
import springMVC.validator.PersonValidator;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index (Model model){
        model.addAttribute("people", personDAO.index());
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
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id){
        Person person = personDAO.show(id).orElse(new Person());
        model.addAttribute("person",person);
        return "/people/edit";
    }

    @GetMapping("/{id}")
    public String show (@PathVariable("id") int id,Model model){
        Person person = personDAO.show(id).orElse(new Person());
        model.addAttribute("person",person);
        model.addAttribute("books",personDAO.getBooksByPersonId(id));

        return "/people/show";
    }

    @PatchMapping("/{id}")
    public String update (@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,@PathVariable("id") int id){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return  "people/edit";
        Person currentPerson = personDAO.show(id).orElse(null);
        if (currentPerson != null) {
            boolean nameChanged = !currentPerson.getName().equals(person.getName());
            boolean birthYearChanged = currentPerson.getBirthYear() != person.getBirthYear();

            if (nameChanged && birthYearChanged) {
                personDAO.updateFull(id, person);
            } else if (nameChanged) {
                personDAO.updateName(id, person);
            } else if (birthYearChanged) {
                personDAO.updateBirthYear(id, person);
            }
        }
    return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
