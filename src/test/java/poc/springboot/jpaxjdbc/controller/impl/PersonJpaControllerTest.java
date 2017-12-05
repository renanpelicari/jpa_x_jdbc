package poc.springboot.jpaxjdbc.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import poc.springboot.jpaxjdbc.fixtures.vo.request.PersonRequestVoFixture;
import poc.springboot.jpaxjdbc.service.impl.PersonJpaService;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Rest Controller Tests for {@link PersonJpaController}
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = PersonJpaController.class, secure = false)
public class PersonJpaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PersonJpaService personJpaService;

    private final String ENDPOINT_URL = "/api/person/jpa";

    @Test
    public void testRegisterSuccess() throws Exception {
        final PersonRequestVo personRequestVo = PersonRequestVoFixture.getPersonRequestVo();

        mvc.perform(post(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(personJpaService).register(personRequestVo);
    }

    @Test
    public void testRegisterMediaTypeNotAccept() throws Exception {
        final PersonRequestVo personRequestVo = PersonRequestVoFixture.getPersonRequestVo();

        mvc.perform(post(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_XML_VALUE))
            .andExpect(status().is4xxClientError());

        verify(personJpaService, never()).register(any());
    }

    @Test
    public void testUpdateSuccess() throws Exception {
    }

    @Test
    public void testFindAllSuccess() throws Exception {
    }

    @Test
    public void testDeleteSuccess() throws Exception {
    }


}
