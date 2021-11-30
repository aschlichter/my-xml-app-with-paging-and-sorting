package hu.aschlichter.myxmlapp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Person {

    @Id
    private Integer id;
    private String name;
    private Integer appearance;
}
