package poc.springboot.jpaxjdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import poc.springboot.jpaxjdbc.adapter.PersonAdapter;
import poc.springboot.jpaxjdbc.model.repository.PersonJpaRepository;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.util.List;

/**
 * The PersonJpa JPA service.
 */
@Slf4j
@Service
public class PersonJpaService {

    private PersonJpaRepository personJpaRepository;

    /**
     * Instantiates a new PersonJpa jpa service.
     *
     * @param personJpaRepository injection of {@link PersonJpaRepository}
     */
    public PersonJpaService(PersonJpaRepository personJpaRepository) {
        this.personJpaRepository = personJpaRepository;
    }

    /**
     * Register new person.
     *
     * @param personRequestVo the person request vo
     */
    public void registerPerson(final PersonRequestVo personRequestVo) {
        log.debug("BEGIN registerPerson personRequestVo={}", personRequestVo);
        personJpaRepository.save(PersonAdapter.voToModelJpa(personRequestVo));
        log.debug("END registerPerson.");
    }

    /**
     * Find all person list.
     *
     * @return the list of Person
     */
    public List<PersonResponseVo> findAll() {
        log.debug("BEGIN findAll");
        final List<PersonResponseVo> response = PersonAdapter.jpaModelToVos(personJpaRepository.findAll());
        log.debug("END findAll, response={}", response);
        return response;
    }
}
