package poc.springboot.jpaxjdbc.fixtures.model.entity;

import poc.springboot.jpaxjdbc.model.entity.PersonJpa;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class Helper to generate {@link PersonJpa} fixtures
 */
public class PersonJpaFixture {

    private static PersonJpa getPersonJpa(final int size) {
        return PersonJpa.builder().id((long) size).age(20 + size).fullName("Mocked User " + size).build();
    }

    public static List<PersonJpa> getPersonJpaList(final int size) {
        return IntStream.range(0, size).mapToObj(PersonJpaFixture::getPersonJpa).collect(Collectors.toList());
    }
}
