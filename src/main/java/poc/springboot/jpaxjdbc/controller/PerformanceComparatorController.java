package poc.springboot.jpaxjdbc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poc.springboot.jpaxjdbc.service.PerformanceComparatorService;
import poc.springboot.jpaxjdbc.vo.response.PerformanceComparatorResponseVo;

import java.util.List;

/**
 * The Performance Comparator Controller.
 * This endpoint compare the JPA and JDBC CRUD performance.
 * Since all the data (for PUT, POST and DELETE) is mocked, the only rest method at this endpoint is GET.
 */
@Slf4j
@RestController
@Api(value = "api-performance-comparator")
@RequestMapping(value = "/api/performance/comparator", produces = MediaType.APPLICATION_JSON_VALUE)
public class PerformanceComparatorController {

    private final PerformanceComparatorService performanceComparatorService;

    /**
     * Instantiates a new Performance comparator controller.
     *
     * @param performanceComparatorService the {@link PerformanceComparatorService} injection
     */
    public PerformanceComparatorController(final PerformanceComparatorService performanceComparatorService) {
        this.performanceComparatorService = performanceComparatorService;
    }

    /**
     * Performance Comparator - Find All.
     *
     * @return the performance comparator response vo
     */
    @GetMapping(value = "/{quantity}")
    @ApiOperation(value = "Compare performance between JPA and JDBC", nickname = "compare")
    public List<PerformanceComparatorResponseVo> compare(@PathVariable("quantity") final long quantity) {
        log.debug("BEGIN compare.");
        final List<PerformanceComparatorResponseVo> response = performanceComparatorService.compare(quantity);
        log.debug("END compare, response={}.", response);
        return response;
    }

}
