package org.yearup.data;

import org.yearup.models.Product;
import java.math.BigDecimal;
import java.util.List;

public interface ProductDao
{
    List<Product> search(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String color);
    List<Product> listByCategoryId(int categoryId);
    Product getById(int productId);
    Product create(Product product);
    void update(Product product);  // <-- added update method here
    void delete(int productId);
}