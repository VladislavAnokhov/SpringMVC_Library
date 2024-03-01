package springMVC.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Person {
    private int id;
    @NotEmpty(message = "Не должно быть пустым")
    @Pattern(regexp ="^(?:[А-ЯЁ][а-яё]+)\\s(?:[А-ЯЁ][а-яё]+)\\s(?:[А-ЯЁ][а-яё]+)$",message = "Фамилия Имя Отчество")
    private String name;

    @NotNull(message = "Не должно быть пустым")
    @Min(value = 1920,message = "Год рождения не позже 1920")
    private int birthYear;

    public Person (){
    }

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
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
