package poc.springboot.jpaxjdbc.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import poc.springboot.jpaxjdbc.fixtures.vo.response.PerformanceComparatorResponseVoFixture;
import poc.springboot.jpaxjdbc.model.repository.PersonJdbcRepository;
import poc.springboot.jpaxjdbc.model.repository.PersonJpaRepository;
import poc.springboot.jpaxjdbc.service.impl.PersonJdbcService;
import poc.springboot.jpaxjdbc.service.impl.PersonJpaService;
import poc.springboot.jpaxjdbc.vo.response.PerformanceComparatorResponseVo;

import java.util.List;

/**
 * Unit test for {@link PerformanceComparatorService}
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
//TODO: Missing tests for PerformanceComparatorService
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
        final List<PerformanceComparatorResponseVo> responseVo = PerformanceComparatorResponseVoFixture
            .getPerformanceComparatorResponseVos(4);

        performanceComparatorService.compare(1);
    }

}
