package poc.springboot.jpaxjdbc.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * PersonJpa Entity
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_PERSON_JDBC")
public class PersonJdbc implements Serializable {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false, length = 100)
    private String fullName;

    @Column(name = "AGE", nullable = false)
    private Integer age;
}
