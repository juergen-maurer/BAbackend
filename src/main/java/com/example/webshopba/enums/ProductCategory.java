package com.example.webshopba.enums;

public enum ProductCategory {
    ELECTRONICS("Elektronik"),
    BOOKS("Bücher"),
    HOME("Haushalt"),
    FASHION("Fashion"),;

    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
