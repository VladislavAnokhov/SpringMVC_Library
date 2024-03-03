package springMVC.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Не должно быть пустым")
    @Pattern(regexp ="^(?:[А-ЯЁ][а-яё]+)\\s(?:[А-ЯЁ][а-яё]+)\\s(?:[А-ЯЁ][а-яё]+)$",message = "Фамилия Имя Отчество")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Не должно быть пустым")
    @Min(value = 1920,message = "Год рождения не позже 1920")
    @Column(name="birth_year")
    private int birthYear;

    @OneToMany(mappedBy = "owner" )
    private List<Book> bookList;

    public Person (){
    }

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthYear(int birthYear){
       this.birthYear= birthYear;
    }
    public int getBirthYear() {
        return birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setbirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
    //   private static Pattern patternPointInTheEnd = Pattern.compile("[$А-ЯЁ].+[\\.!?]");

}
