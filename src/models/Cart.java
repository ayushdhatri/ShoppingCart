package models;

import exceptions.ItemAlreadyInCart;
import exceptions.ProductOutOfStock;
import strategy.DiscountStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cart {
    private final String id;

    private Map<String, CartItem> cartItems;

    private CartStatus status;

    private DiscountStrategy activeDiscount;

    Cart(String id){
        this.id = id;
        this.cartItems = new ConcurrentHashMap<>();
        this.status = CartStatus.ACTIVE;
        this.activeDiscount = null;
    }

    public void applyDiscount(DiscountStrategy strategy){
        validateActive();
        this.activeDiscount = strategy;
    }

    public void removeDiscount(){
        validateActive();
        this.activeDiscount = null;
    }

    void addItem(CartItem cartItem){
        validateActive();
        if(cartItems.containsKey(cartItem.getId())){
            throw new ItemAlreadyInCart("Product with id" + cartItem.getProduct().getId() + "already exist in the cart");
        }
        cartItems.put(cartItem.getId(), cartItem);
    }

    void removeItem(CartItem cartItem){
        if(cartItems.containsKey(cartItem.getId())){
            throw new IllegalArgumentException("CartItem with id + " + cartItem.getId() + " does not exist!");
        }
        cartItems.put(cartItem.getId(), cartItem);
    }

    void increaseQuantityByOne(String cartItemId){
        validateActive();
        CartItem cartItem = cartItems.get(cartItemId);
        if(cartItem == null){
            throw new IllegalArgumentException("Cart item does not exist");
        }
        int currentQuantity = cartItem.getQuantity();
        int maxQuantityCanBeAdded = cartItem.getProduct().getMaxQuantity();
        if((currentQuantity + 1) <= maxQuantityCanBeAdded){
            // we can successfully update the cart
            cartItem.updateQuantity(currentQuantity + 1);
        }
        else{
            throw new ProductOutOfStock("You cannot add one more unit! Product out of stock");
        }
        this.cartItems.put(cartItem.getId(), cartItem);

    }

    void decreaseQuantityByOne(String cartItemId){
        validateActive();
        CartItem cartItem = cartItems.get(cartItemId);
        if(cartItem == null){
            throw new IllegalArgumentException("Cart item does not exist");
        }
        int currentQuantity = cartItem.getQuantity();
        int maxQuantityCanBeAdded = cartItem.getProduct().getMaxQuantity();
        if((currentQuantity - 1) >=1){
            // we can successfully update the cart
            cartItem.updateQuantity(currentQuantity - 1);
            System.out.println("Cart Item reduced successfully");
        }
        else if(currentQuantity - 1 == 0 ){
           removeItem(cartItem);
            System.out.println("Cart Item removed successfully");
        }

        this.cartItems.put(cartItem.getId(), cartItem);

    }

    void checkout(){

    }

    void getSubTotal(){

    }

    void getTotal(){

    }

    public void validateActive(){
        if(status != CartStatus.ACTIVE){
            throw new IllegalStateException("Cart is not active");
        }
    }


}
