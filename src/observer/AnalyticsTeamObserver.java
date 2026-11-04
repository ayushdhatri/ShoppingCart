package observer;


import models.CartItem;
import services.Cart;
public class AnalyticsTeamObserver implements CartObserver {
    @Override
    public void notifyOnItemAdded(Cart cart, CartItem cartItem) {
        System.out.println("[LOG] Item added to cart " + cart.getId() + ": "
                + cartItem.getQuantity() + "x " + cartItem.getProduct().getName()
                + " @ $" + String.format("%.2f", cartItem.getPriceAtAddition()));
    }

    @Override
    public void notifyOnItemRemoved(Cart cart, CartItem cartItem) {
        System.out.println("[LOG] Item removed from cart " + cart.getId() + ": "
                + cartItem.getProduct().getName());
    }

    @Override
    public void onCartCheckout(Cart cart) {
        System.out.println("[LOG] services.Cart " + cart.getId() + " checked out. Total: $"
                + String.format("%.2f", cart.getTotal()));
    }
}
