package poc.springboot.jpaxjdbc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import poc.springboot.jpaxjdbc.controller.enums.CrudMethod;
import poc.springboot.jpaxjdbc.model.repository.PersonJdbcRepository;
import poc.springboot.jpaxjdbc.model.repository.PersonJpaRepository;
import poc.springboot.jpaxjdbc.service.impl.PersonJdbcService;
import poc.springboot.jpaxjdbc.service.impl.PersonJpaService;
import poc.springboot.jpaxjdbc.vo.response.PerformanceComparatorResponseVo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link PerformanceComparatorService}
 */
@RunWith(MockitoJUnitRunner.class)
public class PerformanceComparatorServiceTest {

    @InjectMocks
    private PerformanceComparatorService performanceComparatorService;

    @Mock
    private PersonJpaRepository personJpaRepository;

    @Mock
    private PersonJdbcRepository personJdbcRepository;

    @Mock
    private PersonJpaService personJpaService;

    @Mock
    private PersonJdbcService personJdbcService;

    @Test
    public void testCompareSuccess() throws Exception {
        final List<PerformanceComparatorResponseVo> responseVo = performanceComparatorService.compare(11);
        assertThat(responseVo).hasSize(4).extracting(PerformanceComparatorResponseVo::getMethod)
            .containsExactly(CrudMethod.CREATE, CrudMethod.READ, CrudMethod.UPDATE, CrudMethod.DELETE);
    }
}
