package poc.springboot.jpaxjdbc.fixtures.vo.response;

import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class Helper to generate {@link PersonResponseVo} fixtures
 */
public class PersonResponseVoFixture {

    public static List<PersonResponseVo> getPersonResponseVos(final int size) {
        return IntStream.range(1, size).mapToObj(PersonResponseVoFixture::getPersonResponseVo)
            .collect(Collectors.toList());
    }

    private static PersonResponseVo getPersonResponseVo(final int id) {
        return PersonResponseVo.builder().id((long) id).age(20 + id).fullName("Mocked User " + id).build();
    }
}
