package model;

public class FreshProduct extends Product implements Discountable {

    private int daysToExpire;

    public FreshProduct(String name, double price, int daysToExpire) {
        super(name, price);
        this.daysToExpire = daysToExpire;
    }

    @Override
    public String getType() {
        return "Fresh";
    }

    @Override
    public double getDiscountedPrice() {
        return price * 0.9;
    }
}
