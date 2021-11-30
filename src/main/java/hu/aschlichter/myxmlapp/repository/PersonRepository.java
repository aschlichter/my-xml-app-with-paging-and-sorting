package hu.aschlichter.myxmlapp.repository;

import hu.aschlichter.myxmlapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query(value = "select * from person p where lower(p.name) like lower(concat('%', :keyword, '%'))",
            nativeQuery = true)
    List<Person> findByKeyWord(@Param("keyword") String keyword);
}
