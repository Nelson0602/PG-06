package model;

import java.time.LocalDate;


public class Product implements Comparable<Product> {
    private int id;
    private String name;
    private double price;
    private int stock;
    private String type; // electrónicos, tecnológicos, línea blanca, comestible, médico, suministros
    private LocalDate date; // fecha de registro

    public Product(int id, String name, double price, int stock, String type, LocalDate date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.type = type;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public int compareTo(Product other) {
        return Double.compare(this.price, other.price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product p = (Product) o;
        return this.id == p.id;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price +
               ", stock=" + stock + ", type='" + type + "', registerDate=" + date + "}";
    }
}
