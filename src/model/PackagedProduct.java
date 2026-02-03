package model;

public class PackagedProduct extends Product implements Discountable {

    private double weight;

    public PackagedProduct(String name, double price, double weight) {
        super(name, price);
        this.weight = weight;
    }

    @Override
    public String getType() {
        return "Packaged";
    }

    @Override
    public double getDiscountedPrice() {
        return price * 0.95;
    }
}
