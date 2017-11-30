package poc.springboot.jpaxjdbc.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import poc.springboot.jpaxjdbc.controller.PersonController;
import poc.springboot.jpaxjdbc.service.impl.PersonJdbcService;
import poc.springboot.jpaxjdbc.vo.request.PersonRequestVo;
import poc.springboot.jpaxjdbc.vo.response.PersonResponseVo;

import javax.validation.Valid;
import java.util.List;

/**
 * The PersonJpa JPA Controller
 */
@Slf4j
@RestController
@Api(value = "api-person-jdbc")
@RequestMapping(value = "/api/person/jdbc", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonJdbcController implements PersonController {

    private final PersonJdbcService personJdbcService;

    /**
     * Instantiates a new PersonJpa jpa controller.
     *
     * @param personJdbcService  injection for {@link PersonJdbcService}
     */
    public PersonJdbcController(final PersonJdbcService personJdbcService) {
        this.personJdbcService = personJdbcService;
    }

    /**
     * @see PersonController#register(PersonRequestVo)
     */
    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Register new person using JPA", nickname = "register")
    public void register(@ApiParam(name = "PersonRequest", value = "Create PersonJpa Request Body")
                             @RequestBody @Valid final PersonRequestVo personRequest) {
        log.debug("BEGIN register, requestVo={}", personRequest);
        personJdbcService.register(personRequest);
        log.debug("END register.");
    }

    /**
     * @see PersonController#delete(long)
     */
    @Override
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete person using JDBC", nickname = "delete")
    public void delete(@PathVariable final long id) {
        log.debug("BEGIN delete personJdbc, id={}", id);
        personJdbcService.delete(id);
        log.debug("END delete personJdbc.");
    }

    /**
     * @see PersonController#update(long, PersonRequestVo)
     */
    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update person using JDBC", nickname = "update")
    public void update(@PathVariable final long id,
                       @ApiParam(name = "PersonRequest", value = "Update PersonJdbc Request Body")
                       @RequestBody @Valid final PersonRequestVo requestVo) {
        log.debug("BEGIN update personJdbc, id={} requestVo={}", id, requestVo);
        personJdbcService.update(id, requestVo);
        log.debug("END update personJdbc.");
    }

    /**
     * @see PersonController#findAll()
     */
    @Override
    @GetMapping(value = "/findAll")
    @ApiOperation(value = "Find everyone", nickname = "findAll")
    public List<PersonResponseVo> findAll() {
        log.debug("BEGIN findAll");
        final List<PersonResponseVo> response = personJdbcService.findAll();
        log.debug("END findAll, response={}.", response);
        return response;
    }
}
