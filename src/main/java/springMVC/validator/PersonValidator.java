package springMVC.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import springMVC.models.Person;
import springMVC.services.PersonService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
       // Optional<Person>  = personDAO.show(person.getName());
        Person existingPerson =  personService.getByName(person.getName());
        if (existingPerson!=null && (person.getId() != existingPerson.getId())) {
            errors.rejectValue("name", "", "Человек с таким ФИО уже зарегистрирован");
        }

    }
}
