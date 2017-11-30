package poc.springboot.jpaxjdbc.service;

import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.util.List;

public interface PersonService {

    /**
     * Register new person.
     *
     * @param personRequestVo the person request vo
     */
    void register(final PersonRequestVo personRequestVo);

    /**
     * Update personJpa based on Id. If not exists, create a new one.
     *
     * @param id              the person id
     * @param personRequestVo the person request vo with new values
     */
    void update(final long id, final PersonRequestVo personRequestVo);

    /**
     * Find all person list.
     *
     * @return the list of Person
     */
    public List<PersonResponseVo> findAll();

    /**
     * Delete person based on ID.
     *
     * @param id the person id
     */
    public void delete(final long id);
}
