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
@Table(name = "T_PERSON_JPA")
public class PersonJpa implements Serializable {

    @Id
    @SequenceGenerator(sequenceName = "SEQ_PERSON_JPA", name = "personJpaSG", allocationSize = 1)
    @GeneratedValue(generator = "personJpaSG", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false, length = 100)
    private String fullName;

    @Column(name = "AGE", nullable = false)
    private Integer age;
}
