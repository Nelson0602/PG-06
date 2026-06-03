package model;

public class Student extends Person {
    private String carne;

    public Student(String id, String name, int age, double height, double weight, String carne) {
        super(id, name, age, height, weight);
        this.carne = carne;
    }

    public String getCarne() { return carne; }
    public void setCarne(String carne) { this.carne = carne; }

    @Override
    public String getRoleDescription() {
        return "Rol: Estudiante, carne=" + carne;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", height=" + getHeight() +
                ", weight=" + getWeight() +
                ", carne='" + carne + '\'' +
                '}';
    }
}