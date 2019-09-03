package Oplog;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Data
@Getter
@Setter
@ToString
public class InputParameters {

    private final String migrationType;

    public InputParameters(String... aInputs) {
        migrationType = aInputs[0];
    }
}
