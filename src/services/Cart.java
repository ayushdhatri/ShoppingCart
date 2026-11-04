package services;

import exceptions.ItemAlreadyInCart;
import exceptions.ProductOutOfStock;
import models.CartItem;
import models.CartStatus;
import strategy.DiscountStrategy;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cart {
    private final String id;

    private final Map<String, CartItem> cartItems;

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
            throw new IllegalArgumentException("services.Cart item does not exist");
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
            throw new IllegalArgumentException("services.Cart item does not exist");
        }
        int currentQuantity = cartItem.getQuantity();
        int maxQuantityCanBeAdded = cartItem.getProduct().getMaxQuantity();
        if((currentQuantity - 1) >=1){
            // we can successfully update the cart
            cartItem.updateQuantity(currentQuantity - 1);
            System.out.println("services.Cart Item reduced successfully");
        }
        else if(currentQuantity - 1 == 0 ){
           removeItem(cartItem);
            System.out.println("services.Cart Item removed successfully");
        }

        this.cartItems.put(cartItem.getId(), cartItem);

    }

    void markCheckout(){
        this.status = CartStatus.CART_CHECKOUT;
    }

    void checkout(){
        markCheckout();

    }

    double getSubTotal(){
        double subTotal = 0.0;
        for(Map.Entry<String, CartItem> item : cartItems.entrySet()){
            subTotal += item.getValue().getSubTotal();
        }
        return subTotal;
    }

    public double getTotal(){
        double totalAmount = getSubTotal();

        if(activeDiscount != null){
            totalAmount = Math.min(0, totalAmount - activeDiscount.calculateDiscount(new ArrayList<>(cartItems.values())));
        }
        return totalAmount;
    }

    public void validateActive(){
        if(status != CartStatus.ACTIVE){
            throw new IllegalStateException("services.Cart is not active");
        }
    }

    public String getId(){
        return this.id;
    }


}
