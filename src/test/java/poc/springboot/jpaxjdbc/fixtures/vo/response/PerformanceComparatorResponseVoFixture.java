package poc.springboot.jpaxjdbc.fixtures.vo.response;

import org.apache.commons.lang.math.RandomUtils;
import poc.springboot.jpaxjdbc.controller.enums.CrudMethod;
import poc.springboot.jpaxjdbc.vo.response.PerformanceComparatorResponseVo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class Helper to generate {@link PerformanceComparatorResponseVo} fixtures
 */
public class PerformanceComparatorResponseVoFixture {

    public static List<PerformanceComparatorResponseVo> getPerformanceComparatorResponseVos(final int size) {
        return IntStream.range(1, size + 1)
            .mapToObj(PerformanceComparatorResponseVoFixture::getPerformanceComparatorResponseVo)
            .collect(Collectors.toList());
    }

    private static PerformanceComparatorResponseVo getPerformanceComparatorResponseVo(final int id) {
        return PerformanceComparatorResponseVo
            .builder()
            .method(CrudMethod.valueOf(id))
            .jdbcElapsedTimeInMillis(RandomUtils.nextInt(4))
            .jpaElapsedTimeInMillis(RandomUtils.nextInt(4))
            .build();
    }
}
