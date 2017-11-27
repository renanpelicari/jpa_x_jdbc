package poc.springboot.jpaxjdbc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import poc.springboot.jpaxjdbc.service.PersonJpaService;
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
public class PersonJpaController {

    private final PersonJpaService personJpaService;

    /**
     * Instantiates a new PersonJpa jpa controller.
     *
     * @param personJpaService  injection for {@link PersonJpaService}
     */
    public PersonJpaController(PersonJpaService personJpaService) {
        this.personJpaService = personJpaService;
    }

    /**
     * Register person.
     *
     * @param requestVo the {@link PersonRequestVo}
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Register new person using JPA", nickname = "register")
    public void register(@ApiParam(name = "PersonRequestVo", value = "Create PersonJpa Request Body")
                             @Valid @RequestBody final PersonRequestVo requestVo) {
        log.debug("BEGIN registerPerson, requestVo={}", requestVo);
        personJpaService.registerPerson(requestVo);
        log.debug("END registerPerson.");
    }

    /**
     * Find list of person
     *
     * @return the person response vo list
     */
    @GetMapping(value = "/findAll")
    @ApiOperation(value = "Find everyone", nickname = "findAll")
    public List<PersonResponseVo> findAll() {
        log.debug("BEGIN findAll");
        final List<PersonResponseVo> response = personJpaService.findAll();
        log.debug("END findAll, response={}.", response);
        return response;
    }
}
