package poc.springboot.jpaxjdbc.controller.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Enum with Crud Methods
 */
@Getter
@AllArgsConstructor
public enum CrudMethod {
    CREATE(1),
    READ(2),
    UPDATE(3),
    DELETE(4);

    private final long id;

    public static final String STRING_VALUES = "CREATE,READ,UPDATE,DELETE";

    /**
     * Verify if the id is valid.
     *
     * @param id the id to be validated
     * @return true if the id is represented on the enum, else false
     */
    public static boolean isValid(final long id) {
        return Arrays.stream(CrudMethod.values()).anyMatch(type -> type.getId() == id);
    }

    /**
     * Return the {@link CrudMethod} for the informed id.
     *
     * @param id the id of the enum to be returned
     * @return the {@link CrudMethod} of the id<br/> {@link IllegalArgumentException} if the id is invalid
     */
    public static CrudMethod valueOf(final long id) {
        return Arrays.stream(CrudMethod.values())
            .filter(type -> type.getId() == id).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid Crud Method id"));
    }
}
