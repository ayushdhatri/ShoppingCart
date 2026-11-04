package models;

public class CartItem {
    private final String id;

    private final Product product;

    private final double priceAtAddition;

    private int quantity;

    CartItem(String id, Product product, double priceAtAddition, int quantity){
        this.id = id;
        this.product = product;
        this.priceAtAddition = priceAtAddition;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public double getPriceAtAddition() {
        return priceAtAddition;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubTotal(){
        return quantity * priceAtAddition;
    }

    public void updateQuantity(int newQuantity){
        this.quantity = newQuantity;
    }
}
