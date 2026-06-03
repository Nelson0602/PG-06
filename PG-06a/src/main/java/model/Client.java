package model;

import java.time.LocalDate;

/**
 * Cliente bancario para la PriorityLinkedQueue
 * La prioridad se asigna automáticamente por edad:
 *   edad >= 65 → prioridad 1 (alta)
 *   edad >= 50 → prioridad 2 (media)
 *   default   → prioridad 3 (baja)
 */
public class Client {
    private int id;
    private String name;
    private int age;
    private String bankingService;
    private LocalDate date;

    public Client(int id, String name, int age, String bankingService, LocalDate date) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.bankingService = bankingService;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getBankingService() { return bankingService; }
    public void setBankingService(String bankingService) { this.bankingService = bankingService; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    /**
     * Calcula prioridad automáticamente según edad:
     * 1 = alta (>=65), 2 = media (>=50), 3 = baja (default)
     */
    public int getPriority() {
        if (age >= 65) return 1;
        if (age >= 50) return 2;
        return 3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client c = (Client) o;
        return this.id == c.id;
    }

    @Override
    public String toString() {
        return "Client{id=" + id + ", name=" + name + ", age=" + age +
               ", service name='" + bankingService + "', date=" + date + "}";
    }
}
