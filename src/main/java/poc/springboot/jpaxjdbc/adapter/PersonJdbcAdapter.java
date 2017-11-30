package poc.springboot.jpaxjdbc.adapter;

import org.springframework.util.Assert;
import poc.springboot.jpaxjdbc.model.entity.PersonJdbc;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter for {@link PersonJdbc} and {@link PersonRequestVo}
 */
public class PersonJdbcAdapter {

    /**
     * Convert {@link PersonRequestVo} into {@link PersonJdbc}
     *
     * @param vo the PersonRequestVo
     * @return the person model
     */
    public static PersonJdbc voToModelJdbc(final PersonRequestVo vo) {
        Assert.notNull(vo, "The PersonRequestVo cannot be null!");
        Assert.notNull(vo.getFullName(), "The full name is required!");
        return PersonJdbc.builder().fullName(vo.getFullName()).age(vo.getAge()).build();
    }

    /**
     * Convert {@link PersonJdbc} to {@link PersonResponseVo}
     *
     * @param model the PersonJdbc
     * @return the PersonResponseVo
     */
    private static PersonResponseVo jdbcModelToVo(final PersonJdbc model) {
        return PersonResponseVo.builder()
            .id(model.getId())
            .fullName(model.getFullName())
            .age(model.getAge())
            .build();
    }

    /**
     * Convert {@link PersonJdbc} list to {@link PersonResponseVo}
     *
     * @param models a list of PersonJdbc
     * @return the list of PersonResponseVo
     */
    public static List<PersonResponseVo> jdbcModelToVos(final List<PersonJdbc> models) {
        return models.stream().map(PersonJdbcAdapter::jdbcModelToVo).collect(Collectors.toList());
    }

    /**
     * Update Current Person Jdbc based on New Person Values
     *
     * @param currentPerson the current person
     * @param newPerson     the PersonRequestVo with new values
     * @return the current person with new values
     */
    public static PersonJdbc updatePersonJdbc(final PersonJdbc currentPerson, final PersonRequestVo newPerson) {
        currentPerson.setAge(newPerson.getAge());
        currentPerson.setFullName(newPerson.getFullName());
        return currentPerson;
    }
}
