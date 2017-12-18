package poc.springboot.jpaxjdbc.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import poc.springboot.jpaxjdbc.model.entity.PersonJpa;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Unit test for {@link PersonJpaRepository} class
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts =
        "classpath:fixtures/person-jpa-repository.sql")
})
public class PersonJpaRepositoryTest {

    @Autowired
    private PersonJpaRepository personJpaRepository;

    @Test
    public void testSaveSuccess() throws Exception {
        final PersonJpa personJpa = PersonJpa.builder().fullName("Renan P Rodrigues").age(29).build();
        final PersonJpa savedPerson = personJpaRepository.saveAndFlush(personJpa);
        assertNotNull(savedPerson);
        assertEquals(savedPerson.getId(), Long.valueOf(6));
        assertEquals(savedPerson.getAge(), Integer.valueOf(29));
        assertEquals(savedPerson.getFullName(), "Renan P Rodrigues");

        final List<PersonJpa> list = personJpaRepository.findAll();
        Assertions.assertThat(list).hasSize(6).extracting(PersonJpa::getId).contains(1L, 2L, 3L, 4L, 5L, 6L);
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        final PersonJpa personJpa = PersonJpa.builder().fullName("Renan P Rodrigues").age(29).id(1L).build();
        final PersonJpa savedPerson = personJpaRepository.saveAndFlush(personJpa);
        assertNotNull(savedPerson);
        assertEquals(savedPerson.getId(), Long.valueOf(1));
        assertEquals(savedPerson.getAge(), Integer.valueOf(29));
        assertEquals(savedPerson.getFullName(), "Renan P Rodrigues");

        final List<PersonJpa> list = personJpaRepository.findAll();
        Assertions.assertThat(list).hasSize(5).extracting(PersonJpa::getId).contains(1L, 2L, 3L, 4L, 5L);
    }

    @Test
    public void testFindAllSuccess() throws Exception {
        final List<PersonJpa> personList = personJpaRepository.findAll();
        assertNotNull(personList);
        IntStream.range(0, personList.size()).forEach(counter -> {
            final int i = counter + 1;
            assertEquals(personList.get(counter).getId(), Long.valueOf(i));
            assertEquals(personList.get(counter).getFullName(), "Mocked Person " + i);
            assertEquals(personList.get(counter).getAge(), Integer.valueOf(20 + i));
        });
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        personJpaRepository.deleteById(1L);
        final List<PersonJpa> list = personJpaRepository.findAll();
        Assertions.assertThat(list).hasSize(4).extracting(PersonJpa::getId).contains(2L, 3L, 4L, 5L);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteFailedIdNotExists() throws Exception {
        try {
            personJpaRepository.deleteById(11L);
        } catch (final EmptyResultDataAccessException ex) {
            assertEquals(ex.getMessage(),
                "No class poc.springboot.jpaxjdbc.model.entity.PersonJpa entity with id 11 exists!");
            throw ex;
        }
        fail("Should throw an EmptyResultDataAccessException!");
    }

}
