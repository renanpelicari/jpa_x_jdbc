package poc.springboot.jpaxjdbc.adapter;

import org.springframework.util.Assert;
import poc.springboot.jpaxjdbc.model.entity.PersonJdbc;
import poc.springboot.jpaxjdbc.model.entity.PersonJpa;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter for {@link PersonJpa} and {@link PersonRequestVo}
 */
public class PersonAdapter {

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
        return models.stream().map(PersonAdapter::jpaModelToVo).collect(Collectors.toList());
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
        return models.stream().map(PersonAdapter::jdbcModelToVo).collect(Collectors.toList());
    }
}
