package hu.aschlichter.myxmlapp.controller;

import hu.aschlichter.myxmlapp.model.Person;
import hu.aschlichter.myxmlapp.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = {"/", "/search"})
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return getPaginated(1, model, "name", "asc");
    }


    @GetMapping("/search")
    public String showPersons(Model model, Person person, @Param("keyword") String keyword) {

        if (keyword != null) {
            List<Person> personList = personService.getByKeyword(keyword);
            model.addAttribute("personList", personList);
        } else {
            List<Person> personList = personService.getAllPersons();
            model.addAttribute("personList", personList);
        }
        return "search-list";
    }


    @GetMapping("/page/{pageNumber}")
    public String getPaginated(@PathVariable(value = "pageNumber") int pageNumber, Model model,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir) {
        int pageSize = 20;

        Page<Person> page = personService.getPaginated(pageNumber, pageSize, sortField, sortDir);
        List<Person> personList = page.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseDirection", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("personList", personList);

        return "index";
    }
}
