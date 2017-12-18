package poc.springboot.jpaxjdbc.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import poc.springboot.jpaxjdbc.model.entity.PersonJdbc;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Unit test for {@link PersonJdbcRepository} class
 */
@RunWith(SpringRunner.class)
@JdbcTest
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts =
        "classpath:fixtures/person-jdbc-repository.sql")
})
public class PersonJdbcRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private PersonJdbcRepository personJdbcRepository;

    @Before
    public void setup() {
        personJdbcRepository = new PersonJdbcRepository(jdbcTemplate);
    }

    @After
    public void tearDown() {
        jdbcTemplate.execute("DELETE FROM T_PERSON_JDBC");
        jdbcTemplate.execute("DROP SEQUENCE T_PERSON_JDBC_ID_SEQ");
    }

    @Test
    public void testSaveSuccess() throws Exception {
        final PersonJdbc personJdbc = PersonJdbc.builder().fullName("Renan P Rodrigues").age(29).build();
        personJdbcRepository.insert(personJdbc);

        final PersonJdbc savedPerson = personJdbcRepository.findById(6);
        assertNotNull(savedPerson);
        assertEquals(savedPerson.getId(), Long.valueOf(6));
        assertEquals(savedPerson.getAge(), Integer.valueOf(29));
        assertEquals(savedPerson.getFullName(), "Renan P Rodrigues");

        final List<PersonJdbc> list = personJdbcRepository.findAll();
        Assertions.assertThat(list).hasSize(6).extracting(PersonJdbc::getId).contains(1L, 2L, 3L, 4L, 5L, 6L);
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        final PersonJdbc personJdbc = PersonJdbc.builder().fullName("Renan P Rodrigues").age(29).id(1L).build();
        personJdbcRepository.update(personJdbc);
        final PersonJdbc savedPerson = personJdbcRepository.findById(1);

        assertNotNull(savedPerson);
        assertEquals(savedPerson.getId(), Long.valueOf(1));
        assertEquals(savedPerson.getAge(), Integer.valueOf(29));
        assertEquals(savedPerson.getFullName(), "Renan P Rodrigues");

        final List<PersonJdbc> list = personJdbcRepository.findAll();
        Assertions.assertThat(list).hasSize(5).extracting(PersonJdbc::getId).contains(1L, 2L, 3L, 4L, 5L);
    }

    @Test
    public void testFindAllSuccess() throws Exception {
        final List<PersonJdbc> personList = personJdbcRepository.findAll();
        assertNotNull(personList);
        IntStream.range(0, personList.size()).forEach(counter -> {
            final int i = counter + 1;
            assertEquals(personList.get(counter).getId(), Long.valueOf(i));
            assertEquals(personList.get(counter).getFullName(), "Mocked Person " + i);
            assertEquals(personList.get(counter).getAge(), Integer.valueOf(20 + i));
        });
    }

    @Test
    public void testFindByIdSuccess() throws Exception {
        final PersonJdbc personJdbc = personJdbcRepository.findById(1L);
        assertNotNull(personJdbc);
        assertEquals(personJdbc.getId(), Long.valueOf(1));
        assertEquals(personJdbc.getFullName(), "Mocked Person 1");
        assertEquals(personJdbc.getAge(), Integer.valueOf(21));
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        final PersonJdbc personJdbc = personJdbcRepository.findById(98L);
        assertNull(personJdbc);
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        personJdbcRepository.deleteById(1L);
        final List<PersonJdbc> list = personJdbcRepository.findAll();
        Assertions.assertThat(list).hasSize(4).extracting(PersonJdbc::getId).contains(2L, 3L, 4L, 5L);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteFailedIdNotExists() throws Exception {
        try {
            personJdbcRepository.deleteById(11L);
        } catch (final EmptyResultDataAccessException ex) {
            assertEquals(ex.getMessage(), "Could not delete person because id={11} was not found.");
            throw ex;
        }
        fail("Should throw an EmptyResultDataAccessException!");
    }
}
