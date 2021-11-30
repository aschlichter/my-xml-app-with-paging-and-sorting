package hu.aschlichter.myxmlapp.service;

import hu.aschlichter.myxmlapp.model.Person;
import org.springframework.data.domain.Page;


import java.util.List;

public interface PersonService {

    List<Person> getAllPersons();

    List<Person> getByKeyword(String keyword);

    Page<Person> getPaginated(int pageNumber, int pageSize, String sortField, String sortDirection);
}
