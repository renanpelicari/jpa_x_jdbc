package poc.springboot.jpaxjdbc.adapter;

import org.springframework.util.Assert;
import poc.springboot.jpaxjdbc.model.entity.PersonJpa;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter for {@link PersonJpa} and {@link PersonRequestVo}
 */
public class PersonJpaAdapter {

    /**
     * Convert {@link PersonRequestVo} into {@link PersonJpa}
     *
     * @param vo the PersonRequestVo
     * @return the person model
     */
    public static PersonJpa voToModelJpa(final PersonRequestVo vo) {
        Assert.notNull(vo, "The PersonRequestVo cannot be null!");
        Assert.notNull(vo.getFullName(), "The full name is required!");
        return PersonJpa.builder().fullName(vo.getFullName()).age(vo.getAge()).build();
    }

    /**
     * Convert {@link PersonJpa} to {@link PersonResponseVo}
     *
     * @param model the PersonJpa
     * @return the PersonResponseVo
     */
    private static PersonResponseVo jpaModelToVo(final PersonJpa model) {
        return PersonResponseVo.builder()
            .id(model.getId())
            .fullName(model.getFullName())
            .age(model.getAge())
            .build();
    }

    /**
     * Convert {@link PersonJpa} list to {@link PersonResponseVo}
     *
     * @param models a list of PersonJpa
     * @return the list of PersonResponseVo
     */
    public static List<PersonResponseVo> jpaModelToVos(final List<PersonJpa> models) {
        return models.stream().map(PersonJpaAdapter::jpaModelToVo).collect(Collectors.toList());
    }

    /**
     * Update Current Person Jpa based on New Person Values
     *
     * @param currentPerson the current person
     * @param newPerson     the PersonRequestVo with new values
     * @return the current person with new values
     */
    public static PersonJpa updatePersonJpa(final PersonJpa currentPerson, final PersonRequestVo newPerson) {
        currentPerson.setAge(newPerson.getAge());
        currentPerson.setFullName(newPerson.getFullName());
        return currentPerson;
    }
}
