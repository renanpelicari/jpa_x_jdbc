package poc.springboot.jpaxjdbc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import poc.springboot.jpaxjdbc.adapter.PersonJdbcAdapter;
import poc.springboot.jpaxjdbc.model.entity.PersonJdbc;
import poc.springboot.jpaxjdbc.model.repository.PersonJdbcRepository;
import poc.springboot.jpaxjdbc.service.PersonService;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.util.List;
import java.util.Optional;

/**
 * The PersonJpa JPA service.
 */
@Slf4j
@Service
public class PersonJdbcService implements PersonService {

    private PersonJdbcRepository personJdbcRepository;

    /**
     * Instantiates a new PersonJpa jpa service.
     *
     * @param personJdbcRepository injection of {@link PersonJdbcRepository}
     */
    public PersonJdbcService(final PersonJdbcRepository personJdbcRepository) {
        this.personJdbcRepository = personJdbcRepository;
    }

    /**
     * @see PersonService#register(PersonRequestVo)
     */
    @Override
    public void register(final PersonRequestVo personRequestVo) {
        log.debug("BEGIN register personRequestVo={}", personRequestVo);
        personJdbcRepository.insert(PersonJdbcAdapter.voToModelJdbc(personRequestVo));
        log.debug("END register.");
    }

    /**
     * @see PersonService#update(long, PersonRequestVo)
     */
    @Override
    public void update(final long id, final PersonRequestVo personRequestVo) {
        log.debug("BEGIN update personRequestVo={}", personRequestVo);
        final PersonJdbc personJdbc = personJdbcRepository.findById(id);
        if (Optional.ofNullable(personJdbc).isPresent()) {
            personJdbcRepository.update(PersonJdbcAdapter.updatePersonJdbc(personJdbc, personRequestVo));
        } else {
            personJdbcRepository.insert(PersonJdbcAdapter.updatePersonJdbc(personJdbc, personRequestVo));
        }
        log.debug("END update.");
    }

    /**
     * @see PersonService#findAll()
     */
    @Override
    public List<PersonResponseVo> findAll() {
        log.debug("BEGIN findAll");
        final List<PersonResponseVo> response = PersonJdbcAdapter.jdbcModelToVos(personJdbcRepository.findAll());
        log.debug("END findAll, response={}", response);
        return response;
    }

    /**
     * @see PersonService#delete(long)
     */
    @Override
    public void delete(final long id) {

    }
}