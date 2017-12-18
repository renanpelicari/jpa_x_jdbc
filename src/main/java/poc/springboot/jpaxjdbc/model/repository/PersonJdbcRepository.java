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

    /**
     * Find person by id
     *
     * @param personId the person id
     * @return the optional<PersonJdbc>
     */
    public PersonJdbc findById(final long personId) {
        final String sql = "SELECT ID, NAME, AGE FROM T_PERSON_JDBC WHERE ID = ?";
        try {
            return this.jdbcTemplate.queryForObject(sql, new Object[]{personId}, personJdbcRowMapper);
        } catch (final EmptyResultDataAccessException ex) {
            log.error("Empty result data.", ex);
            return null;
        }
    }

    /**
     * Insert new PersonJdbc.
     *
     * @param personJdbc a new {@link PersonJdbc} entity
     */
    public void insert(final PersonJdbc personJdbc) {
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
     * Update Person By Id with value
     *
     * @param personJdbc the entity PersonJdbc
     */
    public void update(final PersonJdbc personJdbc) {
        try {
            this.jdbcTemplate.update(
                "UPDATE T_PERSON_JDBC SET AGE = ?, NAME = ? WHERE ID = ?",
                personJdbc.getAge(),
                personJdbc.getFullName(),
                personJdbc.getId());
        } catch (final DataAccessException ex) {
            log.error("Could not update data, ex={}", ex);
            throw ex;
        }
    }

    /**
     * Delete Person By Id with value
     *
     * @param id the person id
     */
    public void deleteById(final Long id) {
        if (findById(id) == null) {
            final String errorMessage = "Could not delete person because id={" + id + "} was not found.";
            throw new EmptyResultDataAccessException(errorMessage, 1);
        }
        this.jdbcTemplate.update("DELETE T_PERSON_JDBC WHERE ID = ?", id);
    }
}
