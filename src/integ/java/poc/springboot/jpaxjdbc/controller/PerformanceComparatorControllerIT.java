package poc.springboot.jpaxjdbc.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import poc.springboot.jpaxjdbc.JpaXJdbcApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Integration test class for {@link PerformanceComparatorController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaXJdbcApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PerformanceComparatorControllerIT {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private final HttpHeaders headers = new HttpHeaders();

    @Test
    public void testCompareSuccess() throws Exception {
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);

        final ResponseEntity<String> response = restTemplate.exchange(getUrl("/api/performance/comparator/5"),
            HttpMethod.GET, entity, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());

        final RegularExpressionValueMatcher<Object> regex = new RegularExpressionValueMatcher<>("\\d+");
        final Customization[] customizations = new Customization[2];
        customizations[0] = new Customization("*.jpaElapsedTimeInMillis", regex);
        customizations[1] = new Customization("*.jdbcElapsedTimeInMillis", regex);

        JSONAssert.assertEquals(getExpectedMessage(), response.getBody(), new CustomComparator(JSONCompareMode.LENIENT,
            customizations));
    }

    private String getUrl(final String uri) {
        return "http://localhost:" + port + uri;
    }

    private String getExpectedMessage() {
        return "[" +
            "{\"jpaElapsedTimeInMillis\":x," +
            "\"jdbcElapsedTimeInMillis\":x," +
            "\"method\":\"CREATE\"}," +
            "{\"jpaElapsedTimeInMillis\":x," +
            "\"jdbcElapsedTimeInMillis\":x," +
            "\"method\":\"READ\"}," +
            "{\"jpaElapsedTimeInMillis\":x," +
            "\"jdbcElapsedTimeInMillis\":x," +
            "\"method\":\"UPDATE\"}," +
            "{\"jpaElapsedTimeInMillis\":x," +
            "\"jdbcElapsedTimeInMillis\":x," +
            "\"method\":\"DELETE\"}]";
    }

}
