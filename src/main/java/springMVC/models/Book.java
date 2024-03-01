package springMVC.models;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min=2,max=100,message = "Название книги должно быть от 2 до 100 символов длиной")
    private String name;
    @NotEmpty(message = "Автор не должен быть пустым")
    @Size(min =2,max=100,message = "Имя автора должно быть от 2 до 100 символов длиной ")
    private String author;
    @Min(value = 1500, message = "Год должкен быть больне чем 1500г.")
    @Digits(integer = 4, fraction = 0, message = "Год рождения должен быть числом")
    private int year;
    public Book(){}
    public Book(int id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
