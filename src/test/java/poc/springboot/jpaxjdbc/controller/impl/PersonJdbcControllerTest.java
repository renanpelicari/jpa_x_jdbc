package poc.springboot.jpaxjdbc.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import poc.springboot.jpaxjdbc.fixtures.vo.request.PersonRequestVoFixture;
import poc.springboot.jpaxjdbc.fixtures.vo.response.PersonResponseVoFixture;
import poc.springboot.jpaxjdbc.service.impl.PersonJdbcService;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Rest Controller Tests for {@link PersonJdbcController}
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = PersonJdbcController.class, secure = false)
public class PersonJdbcControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PersonJdbcService personJdbcService;

    private final String ENDPOINT_URL = "/api/person/jdbc";

    private PersonRequestVo personRequestVo;

    private final int PERSON_ID = 1;

    @Before
    public void setUp() {
        personRequestVo = PersonRequestVoFixture.getPersonRequestVo();
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        mvc.perform(post(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().bytes(new byte[0]));
        verify(personJdbcService).register(personRequestVo);
    }

    @Test
    public void testRegisterErrorMissingAge() throws Exception {
        mvc.perform(post(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(PersonRequestVo.builder().fullName("Renan Rodrigues").build()))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        verify(personJdbcService, never()).register(any());
    }

    @Test
    public void testRegisterErrorMissingName() throws Exception {
        mvc.perform(post(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(PersonRequestVo.builder().age(87).build()))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        verify(personJdbcService, never()).register(any());
    }

    @Test
    public void testRegisterErrorMediaTypeNotAcceptable() throws Exception {
        mvc.perform(post(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_XML_VALUE))
            .andExpect(status().isNotAcceptable());
        verify(personJdbcService, never()).register(any());
    }

    @Test
    public void testRegisterErrorMethodGetNotAcceptable() throws Exception {
        mvc.perform(get(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed());
        verify(personJdbcService, never()).register(any());
    }

    @Test
    public void testRegisterErrorMethodDeleteNotAcceptable() throws Exception {
        mvc.perform(delete(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed());
        verify(personJdbcService, never()).register(any());
    }

    @Test
    public void testRegisterErrorMethodPutNotAcceptable() throws Exception {
        mvc.perform(put(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed());
        verify(personJdbcService, never()).register(any());
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        mvc.perform(put(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().bytes(new byte[0]));
        verify(personJdbcService, never()).register(personRequestVo);
        verify(personJdbcService).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testUpdateErrorMissingRequestVo() throws Exception {
        mvc.perform(put(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().bytes(new byte[0]));
        verify(personJdbcService, never()).register(personRequestVo);
        verify(personJdbcService, never()).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testUpdateErrorMissingAge() throws Exception {
        mvc.perform(put(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(PersonRequestVo.builder().fullName("Renan Rodrigues").build()))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        verify(personJdbcService, never()).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testUpdateErrorMissingName() throws Exception {
        mvc.perform(put(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(PersonRequestVo.builder().age(87).build()))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        verify(personJdbcService, never()).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testUpdateErrorMediaTypeNotAcceptable() throws Exception {
        mvc.perform(put(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_XML_VALUE))
            .andExpect(status().isNotAcceptable());
        verify(personJdbcService, never()).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testUpdateErrorMethodGetNotAcceptable() throws Exception {
        mvc.perform(get(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed());
        verify(personJdbcService, never()).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testUpdateErrorMethodPostNotAcceptable() throws Exception {
        mvc.perform(post(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed());
        verify(personJdbcService, never()).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testFindAllSuccess() throws Exception {
        final List<PersonResponseVo> responseVo = PersonResponseVoFixture.getPersonResponseVos(5);
        given(personJdbcService.findAll()).willReturn(responseVo);

        mvc.perform(get(ENDPOINT_URL + "/findAll")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(responseVo)));
        verify(personJdbcService).findAll();
    }

    @Test
    public void testFindAllErrorMediaTypeNotAcceptable() throws Exception {
        mvc.perform(get(ENDPOINT_URL + "/findAll")
            .accept(MediaType.APPLICATION_XML_VALUE))
            .andExpect(status().isNotAcceptable());
        verify(personJdbcService, never()).findAll();
    }

    @Test
    public void testFindAllErrorMethodPutNotAcceptable() throws Exception {
        mvc.perform(put(ENDPOINT_URL + "/findAll")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnsupportedMediaType());
        verify(personJdbcService, never()).findAll();
    }

    @Test
    public void testFindAllErrorMethodPostNotAcceptable() throws Exception {
        mvc.perform(post(ENDPOINT_URL + "/findAll")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed());
        verify(personJdbcService, never()).findAll();
    }

    @Test
    public void testFindAllErrorMethodDeleteNotAcceptable() throws Exception {
        mvc.perform(delete(ENDPOINT_URL + "/findAll")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        verify(personJdbcService, never()).findAll();
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        mvc.perform(delete(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().bytes(new byte[0]));
        verify(personJdbcService, never()).register(any());
        verify(personJdbcService, never()).update(anyInt(), any());
        verify(personJdbcService).delete(PERSON_ID);
    }

    @Test
    public void testDeleteErrorMethodGetNotAcceptable() throws Exception {
        mvc.perform(get(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed())
            .andExpect(content().bytes(new byte[0]));
        verify(personJdbcService, never()).register(any());
        verify(personJdbcService, never()).update(anyInt(), any());
        verify(personJdbcService, never()).delete(PERSON_ID);
    }

    @Test
    public void testDeleteErrorMethodPostNotAcceptable() throws Exception {
        mvc.perform(post(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed())
            .andExpect(content().bytes(new byte[0]));
        verify(personJdbcService, never()).register(any());
        verify(personJdbcService, never()).update(anyInt(), any());
        verify(personJdbcService, never()).delete(PERSON_ID);
    }

}
