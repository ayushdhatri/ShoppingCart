package observer;


import models.CartItem;
import services.Cart;

public interface CartObserver {

    void notifyOnItemAdded(Cart cart, CartItem cartItem);

    void notifyOnItemRemoved(Cart cart, CartItem cartItem);

    void onCartCheckout(Cart cart);
}
