package misc;

import lombok.Data;

/**
 * @author Student
 */
@Data
public class ClassA {
    public ClassA(String name) {
        this.name = name;
    }

    private String name;

}
