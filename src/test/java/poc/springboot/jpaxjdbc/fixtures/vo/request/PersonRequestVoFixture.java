package poc.springboot.jpaxjdbc.fixtures.vo.request;

import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;

/**
 * Class Helper to generate {@link PersonRequestVo} fixtures
 */
public class PersonRequestVoFixture {

    public static PersonRequestVo getPersonRequestVo() {
        return PersonRequestVo.builder().age(29).fullName("Renan Peliçari Rodrigues").build();
    }
}
