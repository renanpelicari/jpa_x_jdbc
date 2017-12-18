package poc.springboot.jpaxjdbc.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import poc.springboot.jpaxjdbc.fixtures.model.entity.PersonJdbcFixture;
import poc.springboot.jpaxjdbc.fixtures.vo.request.PersonRequestVoFixture;
import poc.springboot.jpaxjdbc.model.entity.PersonJdbc;
import poc.springboot.jpaxjdbc.model.repository.PersonJdbcRepository;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link PersonJdbcService}
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonJdbcServiceTest {

    @InjectMocks
    private PersonJdbcService personJdbcService;

    @Mock
    private PersonJdbcRepository personJdbcRepository;

    @Captor
    private ArgumentCaptor<PersonJdbc> personJdbcCaptor;

    @Test
    public void testRegisterSuccess() throws Exception {
        final PersonRequestVo requestVo = PersonRequestVoFixture.getPersonRequestVo();

        personJdbcService.register(requestVo);
        verify(personJdbcRepository).insert(personJdbcCaptor.capture());

        final PersonJdbc personJdbc = personJdbcCaptor.getValue();

        assertNotNull(personJdbc);
        assertEquals(requestVo.getAge(), personJdbc.getAge());
        assertEquals(requestVo.getFullName(), personJdbc.getFullName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterErrorNullObject() throws Exception {
        try {
            personJdbcService.register(null);
        } catch (final IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "PersonRequestVo cannot be null!");
            verify(personJdbcRepository, never()).insert(any(PersonJdbc.class));
            throw ex;
        }
        fail("Should throw IllegalArgumentException!");
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        final PersonRequestVo requestVo = PersonRequestVoFixture.getPersonRequestVo();
        final long id = 1;
        final PersonJdbc personJdbc = PersonJdbc.builder()
            .age(requestVo.getAge()).fullName(requestVo.getFullName()).id(id).build();

        given(personJdbcRepository.findById(id)).willReturn(personJdbc);

        personJdbcService.update(id, requestVo);
        verify(personJdbcRepository).update(personJdbcCaptor.capture());

        final PersonJdbc savedPersonJdbc = personJdbcCaptor.getValue();
        assertNotNull(savedPersonJdbc);
        assertEquals(Long.valueOf(id), savedPersonJdbc.getId());
        assertEquals(requestVo.getAge(), savedPersonJdbc.getAge());
        assertEquals(requestVo.getFullName(), savedPersonJdbc.getFullName());
    }

    @Test
    public void testUpdateNotFoundIdRegisterNewSuccess() throws Exception {
        final PersonRequestVo requestVo = PersonRequestVoFixture.getPersonRequestVo();
        final long id = 1;

        personJdbcService.update(id, requestVo);
        verify(personJdbcRepository).insert(personJdbcCaptor.capture());

        final PersonJdbc savedPersonJdbc = personJdbcCaptor.getValue();
        assertNotNull(savedPersonJdbc);
        assertNull(savedPersonJdbc.getId());
        assertEquals(requestVo.getAge(), savedPersonJdbc.getAge());
        assertEquals(requestVo.getFullName(), savedPersonJdbc.getFullName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateErrorMissingRequestVoParam() throws Exception {
        try {
            personJdbcService.update(1, null);
        } catch (final IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "PersonRequestVo cannot be null!");
            verify(personJdbcRepository, never()).insert(personJdbcCaptor.capture());
            throw ex;
        }
        fail("Should throw IllegalArgumentException!");
    }

    @Test
    public void testFindAllSuccess() throws Exception {
        given(personJdbcRepository.findAll()).willReturn(PersonJdbcFixture.getPersonJdbcList(5));

        final List<PersonResponseVo> response = personJdbcService.findAll();
        verify(personJdbcRepository).findAll();

        assertEquals(response.size(), 5);
        IntStream.range(0, response.size()).forEach(counter -> {
            assertEquals(response.get(counter).getId(), Long.valueOf(counter));
            assertEquals(response.get(counter).getFullName(), "Mocked User " + counter);
            assertEquals(response.get(counter).getAge(), Integer.valueOf(20 + counter));
        });
    }

    @Test
    public void testFindAllEmptyList() throws Exception {
        given(personJdbcRepository.findAll()).willReturn(Collections.emptyList());
        final List<PersonResponseVo> response = personJdbcService.findAll();
        verify(personJdbcRepository).findAll();
        assertEquals(response.size(), 0);
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        final long id = 7;
        personJdbcService.delete(id);
        verify(personJdbcRepository).deleteById(id);
    }

    @Test(expected = DataAccessException.class)
    public void testDeleteDataAccessException() throws Exception {
        final String mockedException = "Could not found delete ID, ex={Mocked Exception}";
        doThrow(new DataAccessException(mockedException) {
        }).when(personJdbcRepository).deleteById(anyLong());
        final long id = 7;
        try {
            personJdbcService.delete(id);
        } catch (final DataAccessException ex) {
            verify(personJdbcRepository).deleteById(id);
            assertEquals(ex.getMessage(), mockedException);
            throw ex;
        }
        fail("Should throw an DataAccessException!");
    }

}
