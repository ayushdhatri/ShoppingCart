package strategy;

import models.CartItem;

import java.util.List;

public class FlatAmountDiscount implements DiscountStrategy{
    private final double flatAmount;

    FlatAmountDiscount(double flatAmount){
        if(flatAmount < 0){
            throw new IllegalArgumentException("Discount cannot be negative");
        }
        this.flatAmount = flatAmount;
    }

    @Override
    public double calculateDiscount(List<CartItem> items) {
        double totalSum = 0.0;
        for(CartItem item : items){
            totalSum += item.getSubTotal();
        }
        return Math.min(totalSum - flatAmount, 0);
    }
}
