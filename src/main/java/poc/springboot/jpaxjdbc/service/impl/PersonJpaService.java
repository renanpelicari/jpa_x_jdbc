package poc.springboot.jpaxjdbc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import poc.springboot.jpaxjdbc.adapter.PersonJpaAdapter;
import poc.springboot.jpaxjdbc.model.entity.PersonJpa;
import poc.springboot.jpaxjdbc.model.repository.PersonJpaRepository;
import poc.springboot.jpaxjdbc.service.PersonService;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.util.List;

/**
 * The PersonJpa JPA service.
 */
@Slf4j
@Service
public class PersonJpaService implements PersonService {

    private final PersonJpaRepository personJpaRepository;

    /**
     * Instantiates a new PersonJpa jpa service.
     *
     * @param personJpaRepository injection of {@link PersonJpaRepository}
     */
    public PersonJpaService(final PersonJpaRepository personJpaRepository) {
        this.personJpaRepository = personJpaRepository;
    }

    /**
     * @see PersonService#register(PersonRequestVo)
     */
    @Override
    @Transactional
    public void register(final PersonRequestVo personRequestVo) {
        Assert.notNull(personRequestVo, "PersonRequestVo cannot be null!");
        log.debug("BEGIN register personRequestVo={}", personRequestVo);
        personJpaRepository.save(PersonJpaAdapter.voToModelJpa(personRequestVo));
        log.debug("END register.");
    }

    /**
     * @see PersonService#update(long, PersonRequestVo)
     */
    @Override
    @Transactional
    public void update(final long id, final PersonRequestVo personRequestVo) {
        Assert.notNull(personRequestVo, "PersonRequestVo cannot be null!");
        log.debug("BEGIN register personRequestVo={}", personRequestVo);
        final PersonJpa personJpa = personJpaRepository.findById(id).orElse(new PersonJpa());
        personJpaRepository.save(PersonJpaAdapter.updatePersonJpa(personJpa, personRequestVo));
        log.debug("END register.");
    }

    /**
     * @see PersonService#findAll()
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonResponseVo> findAll() {
        log.debug("BEGIN findAll");
        final List<PersonResponseVo> response = PersonJpaAdapter.jpaModelToVos(personJpaRepository.findAll());
        log.debug("END findAll, response={}", response);
        return response;
    }

    /**
     * @see PersonService#delete(long)
     */
    @Override
    @Transactional
    public void delete(final long id) {
        log.debug("BEGIN delete id={}", id);
        try {
            personJpaRepository.deleteById(id);
        } catch (final DataAccessException ex) {
            log.error("Could not found delete ID, ex={}", ex.getCause());
            throw ex;
        }
        log.debug("END delete.");
    }
}
