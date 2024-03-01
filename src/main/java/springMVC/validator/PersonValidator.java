package springMVC.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import springMVC.dao.PersonDAO;
import springMVC.models.Person;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> existingPerson = personDAO.show(person.getName());
        if (personDAO.show(person.getName()).isPresent() && (person.getId() != existingPerson.get().getId())) {
            errors.rejectValue("name", "", "Человек с таким ФИО уже зарегистрирован");
        }

    }
}
