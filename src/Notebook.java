public record Notebook(String name, int amountRAM, String OS, int price, String model) {


    public String toString() {
        return "Ноутбук: (" + name + "): " +
                "количество оперативной памяти:" + amountRAM +
                ", операционная система: " + OS + '\'' +
                ", цена: " + price +
                ", модель: " + model;
    }
}