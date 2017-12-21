package poc.springboot.jpaxjdbc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import poc.springboot.jpaxjdbc.fixtures.vo.response.PerformanceComparatorResponseVoFixture;
import poc.springboot.jpaxjdbc.service.PerformanceComparatorService;
import poc.springboot.jpaxjdbc.vo.response.PerformanceComparatorResponseVo;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Rest Controller Tests for {@link PerformanceComparatorController}
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = PerformanceComparatorController.class, secure = false)
public class PerformanceComparatorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PerformanceComparatorService performanceComparatorService;

    private final String ENDPOINT_URL = "/api/performance/comparator";

    @Test
    public void testCompareSuccess() throws Exception {
        final int quantity = 10;
        final List<PerformanceComparatorResponseVo> responseVo = PerformanceComparatorResponseVoFixture
            .getPerformanceComparatorResponseVos(4);
        given(performanceComparatorService.compare(quantity)).willReturn(responseVo);

        mvc.perform(get(ENDPOINT_URL + "/" + quantity)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(responseVo)));
        verify(performanceComparatorService).compare(quantity);
    }

    @Test
    public void testCompareWithoutQuantity() throws Exception {
        mvc.perform(get(ENDPOINT_URL)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        verify(performanceComparatorService, never()).compare(anyInt());
    }

    @Test
    public void testCompareNotAcceptableParam() throws Exception {
        mvc.perform(get(ENDPOINT_URL + "/error")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        verify(performanceComparatorService, never()).compare(anyInt());
    }
}
