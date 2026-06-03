package model;

public class Employee extends Person{
    private String jobPosition;

    public Employee(String id, String name, int age, double heigth, double weight, String jobPosition) {
        super(id, name, age, heigth, weight);
        this.jobPosition = jobPosition;
    }

    @Override
    public String getRoleDescription() {
        return "";
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + getRoleDescription();
    }
}