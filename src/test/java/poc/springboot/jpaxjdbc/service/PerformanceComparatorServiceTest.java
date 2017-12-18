package poc.springboot.jpaxjdbc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import poc.springboot.jpaxjdbc.model.repository.PersonJdbcRepository;
import poc.springboot.jpaxjdbc.model.repository.PersonJpaRepository;
import poc.springboot.jpaxjdbc.service.impl.PersonJdbcService;
import poc.springboot.jpaxjdbc.service.impl.PersonJpaService;

/**
 * Unit test for {@link PersonJpaService}
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
        performanceComparatorService.compare();
    }

}
