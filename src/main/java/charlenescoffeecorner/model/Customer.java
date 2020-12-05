package charlenescoffeecorner.model;

import java.util.List;

public class Customer {
    private final Long customerStampCard;
    private final List<Item> items;

    public Customer(Builder builder) {
        this.customerStampCard = builder.customerStampCard;
        this.items = builder.items;
    }

    public Long getCustomerStampCard() {
        return customerStampCard;
    }

    public List<Item> getItems() {
        return items;
    }

    public static class Builder {
        private Long customerStampCard;
        private List<Item> items;

        public Builder customerStampCard(Long customerStampCard) {
            this.customerStampCard = customerStampCard;
            return this;
        }

        public Builder items(List<Item> items) {
            this.items = items;
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
                ", items=" + items +
                '}';
    }
}
