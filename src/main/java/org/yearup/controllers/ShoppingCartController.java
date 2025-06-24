package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;


@RequestMapping("/cart")
@CrossOrigin
@PreAuthorize("isAuthenticated()") // restrict to logged-in users
public class ShoppingCartController {

    private final ShoppingCartDao shoppingCartDao;
    private final UserDao userDao;
    private final ProductDao productDao;


    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // GET /cart
    @GetMapping
    public ShoppingCart getCart(Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            return shoppingCartDao.getByUserId(user.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not retrieve shopping cart.");
        }
    }

    // POST /cart/products/{productId}
    @PostMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addToCart(@PathVariable int productId, Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            shoppingCartDao.addProductToCart(user.getId(), productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not add product to cart.");
        }
    }

    // PUT /cart/products/{productId}
    @PutMapping("/products/{productId}")
    public void updateQuantity(@PathVariable int productId,
                               @RequestBody ShoppingCartItem item,
                               Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            shoppingCartDao.updateQuantity(user.getId(), productId, item.getQuantity());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update product quantity.");
        }
    }

    // DELETE /cart
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            shoppingCartDao.clearCart(user.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not clear cart.");
        }
    }
}