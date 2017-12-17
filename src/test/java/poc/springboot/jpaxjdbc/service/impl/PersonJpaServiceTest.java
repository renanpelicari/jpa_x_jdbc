package poc.springboot.jpaxjdbc.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import poc.springboot.jpaxjdbc.fixtures.model.entity.PersonJpaFixture;
import poc.springboot.jpaxjdbc.fixtures.vo.request.PersonRequestVoFixture;
import poc.springboot.jpaxjdbc.model.entity.PersonJpa;
import poc.springboot.jpaxjdbc.model.repository.PersonJpaRepository;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link PersonJpaService}
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonJpaServiceTest {

    @InjectMocks
    private PersonJpaService personJpaService;

    @Mock
    private PersonJpaRepository personJpaRepository;

    @Captor
    private ArgumentCaptor<PersonJpa> personJpaCaptor;

    @Test
    public void testRegisterSuccess() throws Exception {
        final PersonRequestVo requestVo = PersonRequestVoFixture.getPersonRequestVo();

        personJpaService.register(requestVo);
        verify(personJpaRepository).save(personJpaCaptor.capture());

        final PersonJpa personJpa = personJpaCaptor.getValue();

        assertNotNull(personJpa);
        assertEquals(requestVo.getAge(), personJpa.getAge());
        assertEquals(requestVo.getFullName(), personJpa.getFullName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterErrorNullObject() throws Exception {
        try {
            personJpaService.register(null);
        } catch (final IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "PersonRequestVo cannot be null!");
            verify(personJpaRepository, never()).save(any(PersonJpa.class));
            throw ex;
        }
        fail("Should throw IllegalArgumentException!");
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        final PersonRequestVo requestVo = PersonRequestVoFixture.getPersonRequestVo();
        final long id = 1;
        final PersonJpa personJpa = PersonJpa.builder()
            .age(requestVo.getAge()).fullName(requestVo.getFullName()).id(id).build();
        final Optional<PersonJpa> mockedPersonJpa = Optional.ofNullable(personJpa);

        given(personJpaRepository.findById(id)).willReturn(mockedPersonJpa);

        personJpaService.update(id, requestVo);
        verify(personJpaRepository).save(personJpaCaptor.capture());

        final PersonJpa savedPersonJpa = personJpaCaptor.getValue();
        assertNotNull(savedPersonJpa);
        assertEquals(Long.valueOf(id), savedPersonJpa.getId());
        assertEquals(requestVo.getAge(), savedPersonJpa.getAge());
        assertEquals(requestVo.getFullName(), savedPersonJpa.getFullName());
    }

    @Test
    public void testUpdateNotFoundIdRegisterNewSuccess() throws Exception {
        final PersonRequestVo requestVo = PersonRequestVoFixture.getPersonRequestVo();
        final long id = 1;

        personJpaService.update(id, requestVo);
        verify(personJpaRepository).save(personJpaCaptor.capture());

        final PersonJpa savedPersonJpa = personJpaCaptor.getValue();
        assertNotNull(savedPersonJpa);
        assertNull(savedPersonJpa.getId());
        assertEquals(requestVo.getAge(), savedPersonJpa.getAge());
        assertEquals(requestVo.getFullName(), savedPersonJpa.getFullName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateErrorMissingRequestVoParam() throws Exception {
        try {
            personJpaService.update(1, null);
        } catch (final IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "PersonRequestVo cannot be null!");
            verify(personJpaRepository, never()).save(personJpaCaptor.capture());
            throw ex;
        }
        fail("Should throw IllegalArgumentException!");
    }

    @Test
    public void testFindAllSuccess() throws Exception {
        given(personJpaRepository.findAll()).willReturn(PersonJpaFixture.getPersonJpaList(5));

        final List<PersonResponseVo> response = personJpaService.findAll();
        verify(personJpaRepository).findAll();

        assertEquals(response.size(), 5);
        IntStream.range(0, response.size()).forEach(counter -> {
            assertEquals(response.get(counter).getId(), Long.valueOf(counter));
            assertEquals(response.get(counter).getFullName(), "Mocked User " + counter);
            assertEquals(response.get(counter).getAge(), Integer.valueOf(20 + counter));
        });
    }

    @Test
    public void testFindAllEmptyList() throws Exception {
        given(personJpaRepository.findAll()).willReturn(Collections.emptyList());
        final List<PersonResponseVo> response = personJpaService.findAll();
        verify(personJpaRepository).findAll();
        assertEquals(response.size(), 0);
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        final long id = 7;
        personJpaService.delete(id);
        verify(personJpaRepository).deleteById(id);
    }

    @Test(expected = DataAccessException.class)
    public void testDeleteDataAccessException() throws Exception {
        final String mockedException = "Could not found delete ID, ex={Mocked Exception}";
        doThrow(new DataAccessException(mockedException) {
        }).when(personJpaRepository).deleteById(anyLong());
        final long id = 7;
        try {
            personJpaService.delete(id);
        } catch (final DataAccessException ex) {
            verify(personJpaRepository).deleteById(id);
            assertEquals(ex.getMessage(), mockedException);
            throw ex;
        }
        fail("Should throw an DataAccessException!");
    }

}
