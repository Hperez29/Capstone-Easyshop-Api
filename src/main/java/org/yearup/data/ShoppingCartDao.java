package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    // Get the full cart for a user
    ShoppingCart getByUserId(int userId);

    // Add a product to the cart (with default quantity 1)
    void addProductToCart(int userId, int productId);

    // Update the quantity of an item in the cart
    void updateQuantity(int userId, int productId, int quantity);

    // Remove a specific product from the cart
    void removeProduct(int userId, int productId);

    // Clear the entire cart
    void clearCart(int userId);
}