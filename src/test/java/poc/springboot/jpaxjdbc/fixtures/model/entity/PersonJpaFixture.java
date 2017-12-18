package poc.springboot.jpaxjdbc.fixtures.model.entity;

import poc.springboot.jpaxjdbc.model.entity.PersonJpa;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class Helper to generate {@link PersonJpa} fixtures
 */
public class PersonJpaFixture {

    public static PersonJpa getPersonJpa(final int id) {
        return PersonJpa.builder().id((long) id).age(20 + id).fullName("Mocked User " + id).build();
    }

    public static List<PersonJpa> getPersonJpaList(final int size) {
        return IntStream.range(0, size).mapToObj(PersonJpaFixture::getPersonJpa).collect(Collectors.toList());
    }
}
