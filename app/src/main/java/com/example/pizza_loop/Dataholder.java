package com.example.pizza_loop;

public class Dataholder {
    public static int pizzaId;
    public static String name;
    public static String details;
    public static float price;
    public static String imageURL;

    public static int getPizzaId() {
        return pizzaId;
    }

    public static void setPizzaId(int pizzaId) {
        Dataholder.pizzaId = pizzaId;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Dataholder.name = name;
    }

    public static String getDetails() {
        return details;
    }

    public static void setDetails(String details) {
        Dataholder.details = details;
    }

    public static float getPrice() {
        return price;
    }

    public static void setPrice(float price) {
        Dataholder.price = price;
    }

    public static String getImageURL() {
        return imageURL;
    }

    public static void setImageURL(String imageURL) {
        Dataholder.imageURL = imageURL;
    }
}
