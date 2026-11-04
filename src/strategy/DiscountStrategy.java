package strategy;

import models.CartItem;

import java.util.List;

public interface DiscountStrategy {
    double calculateDiscount(List<CartItem> items);
}
