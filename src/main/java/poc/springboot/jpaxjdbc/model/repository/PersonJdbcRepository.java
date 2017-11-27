package poc.springboot.jpaxjdbc.model.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import poc.springboot.jpaxjdbc.model.entity.PersonJdbc;
import poc.springboot.jpaxjdbc.model.entity.PersonJpa;

import java.util.List;

/**
 * The JDBC Repository {@link PersonJpa} entity
 */
@Slf4j
@Repository
public class PersonJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<PersonJdbc> personJdbcRowMapper = (rs, rowNum) ->
        PersonJdbc.builder()
        .id(rs.getLong("id"))
        .fullName(rs.getString("name"))
        .age(rs.getInt("age"))
        .build();

    /**
     * Instantiates a new PersonJpa JDBC repository.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} injection
     */
    @Autowired
    public PersonJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Insert PersonJpa.
     */
    public void insertPerson(final PersonJdbc personJdbc) {
        try {
            this.jdbcTemplate.update(
                "INSERT INTO T_PERSON_JDBC (NAME, AGE) VALUES (?, ?)",
                personJdbc.getFullName(),
                personJdbc.getAge());
        } catch (final DataAccessException ex) {
            log.error("Could not insert Person.", ex);
            throw ex;
        }
    }

    /**
     * Find a list of all PersonJpa
     *
     * @return the PersonJpa list
     */
    public List<PersonJdbc> findAll() {
        try {
            return this.jdbcTemplate.query("SELECT ID, NAME, AGE FROM T_PERSON_JDBC", personJdbcRowMapper);
        } catch (final EmptyResultDataAccessException ex) {
            log.error("Empty result data.", ex);
            return null;
        }
    }
}
