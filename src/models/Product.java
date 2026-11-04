package models;

public class Product {
    private final String id;
    private final String name;
    private double price;
    private int maxQuantity;
    private ProductCategory category;

    Product(String id, String name, double price, int maxQuantity, ProductCategory category){
        this.id = id;
        this.name = name;
        this.price = price;
        this.maxQuantity = maxQuantity;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public ProductCategory getCategory(){
        return this.category;
    }

    public void setPrice(double newPrice){
        this.price = newPrice;
    }

    public void changeMaxQuantity(int newQuantity){
        this.maxQuantity = newQuantity;
    }
}
