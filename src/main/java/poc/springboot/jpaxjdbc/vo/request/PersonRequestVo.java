package poc.springboot.jpaxjdbc.vo.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The PersonJpa Request VO
 */
@Data
@Builder
@ApiModel("PersonRequest")
@JsonDeserialize(builder = PersonRequestVo.PersonRequestVoBuilder.class)
public class PersonRequestVo {

    @NotNull
    @ApiModelProperty(value = "Full fullName", required = true)
    @Size(max = 100)
    private String fullName;

    @NotNull
    @ApiParam(value = "Age", required = true)
    private Integer age;

    /**
     * Support to Jackson deserialization.
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static final class PersonRequestVoBuilder {
    }

}

