package poc.springboot.jpaxjdbc.vo.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Person Request VO
 */
@Data
@Builder
@JsonDeserialize(builder = PersonRequestVo.PersonRequestVoBuilder.class)
@ApiModel("PersonRequest")
public class PersonRequestVo {

    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "Full fullName", required = true)
    private String fullName;

    @NotNull
    @ApiModelProperty(value = "Age", required = true)
    private Integer age;

    /**
     * Support to Jackson deserialization.
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static final class PersonRequestVoBuilder {
    }

}

