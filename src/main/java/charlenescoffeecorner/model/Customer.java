package charlenescoffeecorner.model;

import java.util.List;

public class Customer {
    private final Long customerStampCard;
    private final List<Order> order;

    public Customer(Builder builder) {
        this.customerStampCard = builder.customerStampCard;
        this.order = builder.order;
    }

    public Long getCustomerStampCard() {
        return customerStampCard;
    }

    public List<Order> getOrder() {
        return order;
    }

    public static class Builder {
        private Long customerStampCard;
        private List<Order> order;

        public Builder customerStampCard(Long customerStampCard) {
            this.customerStampCard = customerStampCard;
            return this;
        }

        public Builder order(List<Order> order) {
            this.order = order;
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
                ", order=" + order +
                '}';
    }
}
