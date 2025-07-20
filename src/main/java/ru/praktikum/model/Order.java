package ru.praktikum.model;

import java.util.List;
import java.util.Objects;

public class Order {
    private List<String> ingredients;
    public Order() {}
    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(ingredients, order.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredients);
    }

    @Override
    public String toString() {
        return "Order{" +
                "ingredients=" + ingredients +
                '}';
    }
}
