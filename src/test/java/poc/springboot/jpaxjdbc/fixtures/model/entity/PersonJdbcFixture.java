package poc.springboot.jpaxjdbc.fixtures.model.entity;

import poc.springboot.jpaxjdbc.model.entity.PersonJdbc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class Helper to generate {@link PersonJdbc} fixtures
 */
public class PersonJdbcFixture {

    private static PersonJdbc getPersonJdbc(final int size) {
        return PersonJdbc.builder().id((long) size).age(20 + size).fullName("Mocked User " + size).build();
    }

    public static List<PersonJdbc> getPersonJdbcList(final int size) {
        return IntStream.range(0, size).mapToObj(PersonJdbcFixture::getPersonJdbc).collect(Collectors.toList());
    }
}
