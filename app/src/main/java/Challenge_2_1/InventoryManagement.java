package Challenge_2_1;

import java.util.ArrayList;
import java.util.List;

public class InventoryManagement {
    private List<Product> products;

    public InventoryManagement() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }
    //Calculate total inventory value
    public double calculateTotalInventoryValue() {
        double totalValue = 0.0;
        for (Product product : products) {
            totalValue += product.getPrice() * product.getQuantity();
        }
        totalValue = Math.round(totalValue * 100.0) / 100.0;
        return totalValue;
    }
    //Find most expensive product
    public String findMostExpensiveProduct() {
        Product mostExpensiveProduct = products.get(0);
        for (Product product : products) {
            if (product.getPrice() > mostExpensiveProduct.getPrice()) {
                mostExpensiveProduct = product;
            }
        }
        return mostExpensiveProduct.getName();
    }
    //Find if product exists in inventory
    public boolean isExistProduct(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    //Sort product by price ascending
    public void sortProductsByPriceAscending() {
        products.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
    }
    //Sort product by price descending
    public void sortProductsByPriceDescending() {
        products.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
    }
    //Sort product by quantity ascending
    public void sortProductsByQuantityAscending() {
        products.sort((p1, p2) -> Integer.compare(p1.getQuantity(), p2.getQuantity()));
    }
    //Sort product by quantity descending
    public void sortProductsByQuantityDescending() {
        products.sort((p1, p2) -> Integer.compare(p2.getQuantity(), p1.getQuantity()));
    }

    public static void main(String[] args) {
        InventoryManagement inventory = new InventoryManagement();
        inventory.addProduct(new Product("Laptop", 999.99, 5));
        inventory.addProduct(new Product("Smartphone", 499.99, 10));
        inventory.addProduct(new Product("Tablet", 299.99, 0));
        inventory.addProduct(new Product("Smartwatch", 199.99, 3));

        //Total inventory value
        System.out.println("Total inventory value: " + inventory.calculateTotalInventoryValue());

        //Most expensive product
        System.out.println("Most expensive product: " + inventory.findMostExpensiveProduct());

        //Check if product exists in inventory
        System.out.println("Headphones exists in inventory? " + inventory.isExistProduct("Headphones"));
        System.out.println();

        //Sort products by ascending and descending price and quantity
        inventory.sortProductsByPriceAscending();
        System.out.println("Products sorted ascending by price:");
        for (Product product : inventory.products) {
            System.out.println(product.getName() + " - " + product.getPrice());
        }
        System.out.println();

        inventory.sortProductsByPriceDescending();
        System.out.println("Products sorted descending by price:");
        for (Product product : inventory.products) {
            System.out.println(product.getName() + " - " + product.getPrice());
        }
        System.out.println();

        inventory.sortProductsByQuantityAscending();
        System.out.println("Products sorted ascending by quantity:");
        for (Product product : inventory.products) {
            System.out.println(product.getName() + " - " + product.getQuantity());
        }
        System.out.println();

        inventory.sortProductsByQuantityDescending();
        System.out.println("Products sorted descending by quantity:");
        for (Product product : inventory.products) {
            System.out.println(product.getName() + " - " + product.getQuantity());
        }

    }
}