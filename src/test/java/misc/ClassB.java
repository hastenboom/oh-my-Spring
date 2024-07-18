package misc;

import lombok.Data;

/**
 * @author Student
 */
@Data
public class ClassB implements IClassB {

    public ClassA a;
    @Override
    public void setClassA(ClassA a) {
        this.a = a;
    }

    @Override
    public ClassA getClassA() {
        return a;
    }
}
