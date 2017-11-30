package poc.springboot.jpaxjdbc.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import poc.springboot.jpaxjdbc.controller.PersonController;
import poc.springboot.jpaxjdbc.service.impl.PersonJpaService;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import javax.validation.Valid;
import java.util.List;

/**
 * The PersonJpa JPA Controller
 */
@Slf4j
@RestController
@Api(value = "api-person-jpa")
@RequestMapping(value = "/api/person/jpa", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonJpaController implements PersonController {

    private final PersonJpaService personJpaService;

    /**
     * Instantiates a new PersonJpa jpa controller.
     *
     * @param personJpaService  injection for {@link PersonJpaService}
     */
    public PersonJpaController(final PersonJpaService personJpaService) {
        this.personJpaService = personJpaService;
    }

    /**
     * @see PersonController#register(PersonRequestVo)
     */
    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Register new person using JPA", nickname = "register")
    public void register(@ApiParam(name = "PersonRequest", value = "Create PersonJpa Request Body")
                             @RequestBody @Valid final PersonRequestVo requestVo) {
        log.debug("BEGIN register personJpa, requestVo={}", requestVo);
        personJpaService.register(requestVo);
        log.debug("END register personJpa.");
    }

    /**
     * @see PersonController#update(long, PersonRequestVo)
     */
    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update person using JPA", nickname = "update")
    public void update(@PathVariable final long id,
                       @ApiParam(name = "PersonRequest", value = "Update PersonJpa Request Body")
                       @RequestBody @Valid final PersonRequestVo requestVo) {
        log.debug("BEGIN update personJpa, id={} requestVo={}", id, requestVo);
        personJpaService.update(id, requestVo);
        log.debug("END update personJpa.");
    }

    /**
     * @see PersonController#findAll()
     */
    @Override
    @GetMapping(value = "/findAll")
    @ApiOperation(value = "Find everyone", nickname = "findAll")
    public List<PersonResponseVo> findAll() {
        log.debug("BEGIN findAll personJpa");
        final List<PersonResponseVo> response = personJpaService.findAll();
        log.debug("END findAll personJpa, response={}.", response);
        return response;
    }

    /**
     * @see PersonController#delete(long)
     */
    @Override
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete person using JPA", nickname = "delete")
    public void delete(@PathVariable final long id) {
        log.debug("BEGIN delete personJpa, id={}", id);
        personJpaService.delete(id);
        log.debug("END delete personJpa.");
    }
}
