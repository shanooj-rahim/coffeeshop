package charlenescoffeecorner.model;

import java.util.List;

public class Customer {
    private final Long customerStampCard;
    private final List<Product> product;

    public Customer(Builder builder) {
        this.customerStampCard = builder.customerStampCard;
        this.product = builder.product;
    }

    public Long getCustomerStampCard() {
        return customerStampCard;
    }

    public List<Product> getProduct() {
        return product;
    }

    public static class Builder {
        private Long customerStampCard;
        private List<Product> product;

        public Builder customerStampCard(Long customerStampCard) {
            this.customerStampCard = customerStampCard;
            return this;
        }

        public Builder order(List<Product> product) {
            this.product = product;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }

    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerStampCard=" + customerStampCard +
                ", order=" + product +
                '}';
    }
}
