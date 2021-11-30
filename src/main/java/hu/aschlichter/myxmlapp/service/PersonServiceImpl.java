package hu.aschlichter.myxmlapp.service;

import hu.aschlichter.myxmlapp.model.Person;
import hu.aschlichter.myxmlapp.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public List<Person> getByKeyword(String keyword) {
        return personRepository.findByKeyWord(keyword);
    }

    @Override
    public Page<Person> getPaginated(int pageNumber, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return personRepository.findAll(pageable);
    }
}

