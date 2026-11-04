package strategy;

import models.CartItem;

import java.util.List;

public class PercentageDiscount implements DiscountStrategy{

    private final double percentage;

    PercentageDiscount(double percentage){
        if(percentage < 0 || percentage > 100){
            throw new IllegalArgumentException("Percentage cannot be less than 0 or more than 100");
        }
        this.percentage = percentage;
    }


    @Override
    public double calculateDiscount(List<CartItem> items) {
        double totalAmount = 0.0;
        for(CartItem item : items){
            totalAmount += item.getSubTotal();
        }
        return totalAmount - ((totalAmount * percentage)/100.00);
    }
}
