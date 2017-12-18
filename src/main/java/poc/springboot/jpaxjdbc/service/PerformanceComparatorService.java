package poc.springboot.jpaxjdbc.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poc.springboot.jpaxjdbc.controller.enums.CrudMethod;
import poc.springboot.jpaxjdbc.service.impl.PersonJdbcService;
import poc.springboot.jpaxjdbc.service.impl.PersonJpaService;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PerformanceComparatorResponseVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * The Performance Comparator service.
 */
@Slf4j
@Service
public class PerformanceComparatorService {

    private final PersonJpaService personJpaService;

    private final PersonJdbcService personJdbcService;

    private static final String MOCKED_NAME = "Massive Person ";

    @Getter
    @Setter
    private long maxRecords = 1000;

    /**
     * Instantiates a new Performance comparator service.
     *
     * @param personJpaService  the {@link PersonJpaService} injection
     * @param personJdbcService the {@link PersonJdbcService} injection
     */
    public PerformanceComparatorService(final PersonJpaService personJpaService,
                                        final PersonJdbcService personJdbcService) {
        this.personJpaService = personJpaService;
        this.personJdbcService = personJdbcService;
    }

    /**
     * Performance comparator - Read Method
     *
     * @param quantity of elements will be create, update and delete
     * @return the {@link PerformanceComparatorResponseVo}
     */
    @Transactional
    public List<PerformanceComparatorResponseVo> compare(final long quantity) {
        log.debug("BEGIN compare.");
        setMaxRecords(quantity);

        final List<PersonResponseVo> responseJpa = personJpaService.findAll();
        final List<Long> jpaIds = getIds(responseJpa);

        final List<PersonResponseVo> responseJdbc = personJdbcService.findAll();
        final List<Long> jdbcIds = getIds(responseJdbc);

        final PerformanceComparatorResponseVo createMethodResult = getCreateMethodResult();
        final PerformanceComparatorResponseVo readMethodResult = getReadMethodResult();
        final PerformanceComparatorResponseVo updateMethodResult = getUpdateMethodResult(jpaIds, jdbcIds);
        final PerformanceComparatorResponseVo deleteMethodResult = getDeleteMethodResult(jpaIds, jdbcIds);

        final List<PerformanceComparatorResponseVo> response = Arrays.asList(createMethodResult, readMethodResult,
            updateMethodResult, deleteMethodResult);

        log.debug("END compare, response={}.", response);
        return response;
    }

    private PerformanceComparatorResponseVo getCreateMethodResult() {
        final Instant jpaStart = Instant.now();
        setMassivePersonJpa();
        final long jpaElapsedTime = Duration.between(jpaStart, Instant.now()).toMillis();
        log.info("Create JPA | Start={} | Duration={}", jpaStart, jpaElapsedTime);

        final Instant jdbcStart = Instant.now();
        setMassivePersonJdbc();
        final long jdbcElapsedTime = Duration.between(jdbcStart, Instant.now()).toMillis();
        log.info("Create JDBC | Start={} | Duration={}", jdbcStart, jdbcElapsedTime);

        return PerformanceComparatorResponseVo.builder()
            .jpaElapsedTimeInMillis(jpaElapsedTime)
            .jdbcElapsedTimeInMillis(jdbcElapsedTime)
            .method(CrudMethod.CREATE)
            .build();
    }

    private PerformanceComparatorResponseVo getReadMethodResult() {
        final Instant jpaStart = Instant.now();
        personJpaService.findAll();
        final long jpaElapsedTime = Duration.between(jpaStart, Instant.now()).toMillis();
        log.info("Read JPA | Start={} | Duration={}", jpaStart, jpaElapsedTime);

        final Instant jdbcStart = Instant.now();
        personJdbcService.findAll();
        final long jdbcElapsedTime = Duration.between(jdbcStart, Instant.now()).toMillis();
        log.info("Read JDBC | Start={} | Duration={}", jdbcStart, jdbcElapsedTime);

        return PerformanceComparatorResponseVo.builder()
            .jpaElapsedTimeInMillis(jpaElapsedTime)
            .jdbcElapsedTimeInMillis(jdbcElapsedTime)
            .method(CrudMethod.READ)
            .build();
    }

    private PerformanceComparatorResponseVo getUpdateMethodResult(final List<Long> jpaIds, final List<Long> jdbcIds) {
        final Instant jpaStart = Instant.now();
        updateMassivePersonJpa(jpaIds);
        final long jpaElapsedTime = Duration.between(jpaStart, Instant.now()).toMillis();
        log.info("Updae JPA | Start={} | Duration={}", jpaStart, jpaElapsedTime);

        final Instant jdbcStart = Instant.now();
        updateMassivePersonJdbc(jdbcIds);
        final long jdbcElapsedTime = Duration.between(jdbcStart, Instant.now()).toMillis();
        log.info("Update JDBC | Start={} | Duration={}", jdbcStart, jdbcElapsedTime);

        return PerformanceComparatorResponseVo.builder()
            .jpaElapsedTimeInMillis(jpaElapsedTime)
            .jdbcElapsedTimeInMillis(jdbcElapsedTime)
            .method(CrudMethod.UPDATE)
            .build();
    }

    private PerformanceComparatorResponseVo getDeleteMethodResult(final List<Long> jpaIds, final List<Long> jdbcIds) {
        final Instant jpaStart = Instant.now();
        jpaIds.forEach(personJpaService::delete);
        final long jpaElapsedTime = Duration.between(jpaStart, Instant.now()).toMillis();
        log.info("Delete JPA | Start={} | Duration={}", jpaStart, jpaElapsedTime);

        final Instant jdbcStart = Instant.now();
        jdbcIds.forEach(personJdbcService::delete);
        final long jdbcElapsedTime = Duration.between(jdbcStart, Instant.now()).toMillis();
        log.info("Delete JDBC | Start={} | Duration={}", jdbcStart, jdbcElapsedTime);

        return PerformanceComparatorResponseVo.builder()
            .jpaElapsedTimeInMillis(jpaElapsedTime)
            .jdbcElapsedTimeInMillis(jdbcElapsedTime)
            .method(CrudMethod.DELETE)
            .build();
    }

    private List<Long> getIds(final List<PersonResponseVo> personList) {
        return personList.stream().filter(person -> person.getFullName().startsWith(MOCKED_NAME))
            .map(PersonResponseVo::getId).collect(Collectors.toList());
    }

    private void setMassivePersonJpa() {
        LongStream.range(0, getMaxRecords()).forEach(counter ->
            personJpaService.register(PersonRequestVo.builder()
                .fullName(MOCKED_NAME + counter)
                .age(RandomUtils.nextInt(99))
                .build())
        );
    }

    private void setMassivePersonJdbc() {
        LongStream.range(0, getMaxRecords()).forEach(counter ->
            personJdbcService.register(PersonRequestVo.builder()
                .fullName(MOCKED_NAME + counter)
                .age(RandomUtils.nextInt(99))
                .build())
        );
    }

    private void updateMassivePersonJpa(final List<Long> ids) {
        ids.forEach(id -> personJpaService.update(id,
            PersonRequestVo.builder().fullName(MOCKED_NAME + "Changed " + id).age(RandomUtils.nextInt(99)).build()));
    }

    private void updateMassivePersonJdbc(final List<Long> ids) {
        ids.forEach(id -> personJdbcService.update(id,
            PersonRequestVo.builder().fullName(MOCKED_NAME + "Changed " + id).age(RandomUtils.nextInt(99)).build()));
    }
}
