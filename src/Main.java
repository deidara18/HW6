import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Set<Notebook> set = new HashSet<>();
        set.add(new Notebook("Notebook 1", 8, "Windows 11", 25000, "HP"));
        set.add(new Notebook("Notebook 2", 16, "Ubuntu", 35000, "Asus"));
        set.add(new Notebook("Notebook 3", 32, "Windows 11", 62000, "Huawei"));
        set.add(new Notebook("Notebook 4", 32, "Windows 11", 56000, "Lenovo"));
        set.add(new Notebook("Notebook 5", 64, "linux", 150000, "Lenovo"));


        OperationWithNotebook operation = new OperationWithNotebook(set);
        operation.start();

    }
}