import java.util.*;

public class OperationWithNotebook {

    private final Set<Notebook> notebooks;
    private final List<Criterion> criterionList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public void printList() {
        for (Notebook notebook : notebooks) {
            if (notebookIsCorrect(notebook)) {
                System.out.println(notebook);
            }
        }
    }

    public boolean notebookIsCorrect(Notebook notebook) {

        for (Criterion criterion : criterionList) {
            Object valueNotebook;

            switch (criterion.property) {
                case "name" -> valueNotebook = notebook.name();
                case "amountRAM" -> valueNotebook = notebook.amountRAM();
                case "OS" -> valueNotebook = notebook.OS();
                case "price" -> valueNotebook = notebook.price();
                case "model" -> valueNotebook = notebook.model();
                default -> {
                    continue;
                }
            }

            if (criterion.value != null && !criterion.value.equals(valueNotebook)) {
                return false;
            }

            if (criterion.maxValue != null && criterion.maxValue < Double.parseDouble(Objects.toString(valueNotebook))) {
                return false;
            }

            if (criterion.minValue != null && criterion.minValue > Double.parseDouble(Objects.toString(valueNotebook))) {
                return false;
            }
        }

        return true;
    }

    public OperationWithNotebook(Set<Notebook> notebooks) {
        scanner = new Scanner(System.in);
        this.notebooks = notebooks;
    }


    public int getCriteria() {
        StringBuilder text = new StringBuilder("Введите цифру, соответствующую необходимому критерию: ");

        List<String> properties = propertiesForFilter();

        for (int i = 0; i < properties.size(); i++) {
            text.append("\n").append(i + 1).append(". ").append(getPropertyDescription(properties.get(i)));
        }

        System.out.println(text);

        int value = scanner.nextInt();

        return value;
    }

    public String getPropertyDescription(String property) {

        Map<String, String> descriptionsProperties = descriptionsProperties();

        return descriptionsProperties.get(property);

    }

    public Map<String, String> descriptionsProperties() {
        Map<String, String> map = new HashMap<>();

        map.put("name", "Наименование");
        map.put("amountRAM", "Объем оперативной памяти");
        map.put("OS", "Операционная система");
        map.put("price", "цена");
        map.put("model", "модель");

        return map;

    }

    public List<String> propertiesForFilter() {
        List<String> list = new ArrayList<>();
        list.add("name");
        list.add("amountRAM");
        list.add("OS");
        list.add("price");
        list.add("model");

        return list;
    }

    public String getOperations() {

        String text = """
                Выберите опрерацию:\s
                 1. Добавить критерий\s
                 2. Вывести список\s
                 3. Завершить""";

        System.out.println(text);

        String answer = scanner.next();

        return answer;
    }

    public Set<String> quantitativeSelection() {
        Set<String> set = new HashSet<>();
        set.add("amountRAM");
        set.add("price");

        return set;
    }


    public void start() {

        boolean flag = true;
        while (flag) {

            String operation = getOperations();
            switch (operation) {
                case "3" -> {
                    flag = false;
                    scanner.close();
                }
                case "1" -> {
                    int criterion = getCriteria();
                    List<String> properties = propertiesForFilter();
                    if (criterion - 1 < 0 || criterion - 1 > properties.size() - 1) {
                        System.out.println("Введено некорректное значение");
                        continue;
                    }
                    String property = properties.get(criterion - 1);
                    Criterion criterionObject;
                    try {
                        if (quantitativeSelection().contains(property)) {
                            criterionObject = Criterion.startGetting(scanner, property, true);
                        } else {
                            criterionObject = Criterion.startGetting(scanner, property, false);
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка при выборе критерия");
                        continue;
                    }
                    if (criterionObject != null) {
                        System.out.println("Критерий добавлен");
                        criterionList.add(criterionObject);
                    }
                }
                case "2" -> printList();
            }
        }
    }
}


class Criterion {

    Object value;
    Double minValue;
    Double maxValue;
    boolean isQuantitative;
    String property;

    public Criterion(String property, boolean isQuantitative, Object value, Double minValue, Double maxValue) {
        this.property = property;
        this.isQuantitative = isQuantitative;
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public static Criterion startGetting(Scanner scanner, String property, boolean isQuantitative) {

        if (isQuantitative) {

            String quest = """
                    Выберите тип криетрия:\s
                     1. Значение
                     2. Меньше
                     3. Больше
                     4. Интервал""";
            System.out.println(quest);

            String text = scanner.next();

            Criterion criterion = null;

            switch (text) {
                case "1" -> {
                    System.out.println("Введите значение поиска: ");
                    int getValue = scanner.nextInt();
                    criterion = new Criterion(property, true, getValue, null, null);
                }
                case "2" -> {
                    System.out.println("Введите максимальное предельное значение: ");
                    double getValue = scanner.nextDouble();
                    criterion = new Criterion(property, true, null, null, getValue);
                }
                case "3" -> {
                    System.out.println("Введите минимальное предельное значение: ");
                    double getValue = scanner.nextDouble();
                    criterion = new Criterion(property, true, null, getValue, null);
                }
                case "4" -> {
                    System.out.println("Введите минимальное предельное значение: ");
                    double getMin = scanner.nextDouble();
                    System.out.println("Введите максимальное предельное значение: ");
                    double getMax = scanner.nextDouble();
                    criterion = new Criterion(property, true, null, getMin, getMax);
                }
            }

            return criterion;
        }

        System.out.println("Введите значение поиска: ");
        String getValue = scanner.next();
        return new Criterion(property, false, getValue, null, null);
    }

}
