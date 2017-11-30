package poc.springboot.jpaxjdbc.controller;

import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.util.List;

/**
 * The Person Interface Controller
 */
public interface PersonController {

    /**
     * Register person.
     *
     * @param personRequestVo the {@link PersonRequestVo}
     */
    void register(final PersonRequestVo personRequestVo);

    /**
     * Delete person based on ID
     *
     * @param id the Person Id
     */
    void delete(final long id);

    /**
     * Update person based on ID
     *
     * @param id the Person Id
     * @param personRequestVo the {@link PersonRequestVo}
     */
    void update(final long id, final PersonRequestVo personRequestVo);

    /**
     * Find list of person
     *
     * @return the person response vo list
     */
    List<PersonResponseVo> findAll();
}
