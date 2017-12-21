package poc.springboot.jpaxjdbc.vo.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Value;
import poc.springboot.jpaxjdbc.controller.enums.CrudMethod;

/**
 * The Performance Comparator Vo
 */
@JsonDeserialize(builder = PerformanceComparatorResponseVo.PerformanceComparatorResponseVoBuilder.class)
@Builder
@Value
@ApiModel("PerformanceComparatorResponse")
public class PerformanceComparatorResponseVo {

    @ApiParam(value = "Total JPA Execution Time in milliseconds")
    final long jpaElapsedTimeInMillis;

    @ApiParam(value = "Total JDBC Execution Time in milliseconds")
    final long jdbcElapsedTimeInMillis;

    @ApiParam(value = "Which CRUD method was used")
    final CrudMethod method;

    /**
     * Support to Jackson deserialization.
     */
    @JsonPOJOBuilder(withPrefix = "")

    public static final class PerformanceComparatorResponseVoBuilder {
    }
}
