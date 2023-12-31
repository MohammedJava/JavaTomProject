package SOEN387.services;

import SOEN387.DAOs.CartDAO;
import SOEN387.DAOs.ProductDAO;
import SOEN387.DAOs.UserDAO;
import SOEN387.models.CartItem;
import SOEN387.models.Product;

import java.util.List;

public class CartService {

    private CartDAO cartDAO;
    private ProductDAO productDAO;
    private UserDAO userDAO;

    public CartService() {
        cartDAO = new CartDAO();
        productDAO = new ProductDAO();
        userDAO = new UserDAO();
    }


    public List<CartItem> getCart(String user) {
        int userId = (user != null) ? userDAO.getUserIDByPasscode(user) : -1; // Use -1 for anonymous users
        return cartDAO.getCartItems(userId);
    }

    public void SetProductQuantityInCart(String user, String sku, int Quantity) {
		/*
		 * Sets the quantity of a given product in the cart
           associated with the user. If the quantity is greater than zero and if no cart is associated with the
           user, create one first.
		 * */

        int userId = (user != null) ? userDAO.getUserIDByPasscode(user) : -1; // Use -1 for anonymous users
        Product product = productDAO.getProduct(sku);
        int productId = productDAO.getProductIDByName(product.getName());
        if (product != null && productId >= 0) {
            cartDAO.UpdateQuantity(userId, productId, Quantity);
        }


    }

    public void addProductToCart(String user, String sku) {

        int userId = (user != null) ? userDAO.getUserIDByPasscode(user) : -1; // Use -1 for anonymous users
        Product product = productDAO.getProduct(sku);
        int productId = productDAO.getProductIDByName(product.getName());

        if (product != null && productId >= 0) {
            cartDAO.addToCart(userId, productId);
        }
    }

    public void removeProductFromCart(String user, String sku) {

        int userId = (user != null) ? userDAO.getUserIDByPasscode(user) : -1; // Use -1 for anonymous users
        Product product = productDAO.getProduct(sku);
        int productId = productDAO.getProductIDByName(product.getName());

        if (product != null && productId >= 0) {
            cartDAO.removeFromCart(userId, productId);
        }
    }

    public void clearCart(String username) {
        int userId = userDAO.getUserIDByPasscode(username);
            System.out.println("Clearing cart for user ID: " + userId);
            cartDAO.clearCartItems(userId);
    }

    public void removeSingleItemFromCart(String user, String sku) {
        int userId = userDAO.getUserIDByPasscode(user);
        Product product = productDAO.getProduct(sku);
        int productId = productDAO.getProductIDByName(product.getName());

        if (product != null && userId >= 0 && productId >= 0) {
            cartDAO.removeSingleItemFromCart(userId, productId);
        }
    }

    public int getProductQuantityInCart(String user, String sku) {
        int userId = (user != null) ? userDAO.getUserIDByPasscode(user) : -1; // Use -1 for anonymous users

        List<CartItem> cartItems = cartDAO.getCartItems(userId);
        if (cartItems == null) {
            return 0;
        }

        for (CartItem item : cartItems) {
            if (item.getProduct().getSku().equals(sku)) {
                return item.getQuantity();
            }
        }

        return 0;
    }

}
