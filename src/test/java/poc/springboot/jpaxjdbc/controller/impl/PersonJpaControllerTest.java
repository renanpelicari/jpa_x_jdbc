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
import poc.springboot.jpaxjdbc.service.impl.PersonJpaService;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Rest Controller Tests for {@link PersonJpaController}
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = PersonJpaController.class, secure = false)
public class PersonJpaControllerTest {

    //TODO: Missing findAll tests and some cases of Delete. Also missing test with not exists ID
    // to perform: mock return, and check endpoint behavior
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PersonJpaService personJpaService;

    private final String ENDPOINT_URL = "/api/person/jpa";

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
        verify(personJpaService).register(personRequestVo);
    }

    @Test
    public void testRegisterErrorMissingAge() throws Exception {
        mvc.perform(post(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(PersonRequestVo.builder().fullName("Renan Rodrigues").build()))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        verify(personJpaService, never()).register(any());
    }

    @Test
    public void testRegisterErrorMissingName() throws Exception {
        mvc.perform(post(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(PersonRequestVo.builder().age(87).build()))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        verify(personJpaService, never()).register(any());
    }

    @Test
    public void testRegisterErrorMediaTypeNotAcceptable() throws Exception {
        mvc.perform(post(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_XML_VALUE))
            .andExpect(status().isNotAcceptable());
        verify(personJpaService, never()).register(any());
    }

    @Test
    public void testRegisterErrorMethodGetNotAcceptable() throws Exception {
        mvc.perform(get(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed());
        verify(personJpaService, never()).register(any());
    }

    @Test
    public void testRegisterErrorMethodDeleteNotAcceptable() throws Exception {
        mvc.perform(delete(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed());
        verify(personJpaService, never()).register(any());
    }

    @Test
    public void testRegisterErrorMethodPutNotAcceptable() throws Exception {
        mvc.perform(put(ENDPOINT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed());
        verify(personJpaService, never()).register(any());
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        mvc.perform(put(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().bytes(new byte[0]));
        verify(personJpaService, never()).register(personRequestVo);
        verify(personJpaService).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testUpdateErrorMissingAge() throws Exception {
        mvc.perform(put(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(PersonRequestVo.builder().fullName("Renan Rodrigues").build()))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        verify(personJpaService, never()).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testUpdateErrorMissingName() throws Exception {
        mvc.perform(put(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(PersonRequestVo.builder().age(87).build()))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        verify(personJpaService, never()).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testUpdateErrorMediaTypeNotAcceptable() throws Exception {
        mvc.perform(put(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_XML_VALUE))
            .andExpect(status().isNotAcceptable());
        verify(personJpaService, never()).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testUpdateErrorMethodGetNotAcceptable() throws Exception {
        mvc.perform(get(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed());
        verify(personJpaService, never()).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testUpdateErrorMethodPostNotAcceptable() throws Exception {
        mvc.perform(post(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personRequestVo))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isMethodNotAllowed());
        verify(personJpaService, never()).update(PERSON_ID, personRequestVo);
    }

    @Test
    public void testFindAllSuccess() throws Exception {
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        mvc.perform(delete(ENDPOINT_URL + "/" + PERSON_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().bytes(new byte[0]));
        verify(personJpaService, never()).register(any());
        verify(personJpaService, never()).update(anyInt(), any());
        verify(personJpaService).delete(PERSON_ID);
    }

}
