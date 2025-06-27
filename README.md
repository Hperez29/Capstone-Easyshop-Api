# Capstone-Easyshop-Api
YearUp Capstone 

🔍 Challenging Code Snippet: Flexible Product Search
@GetMapping("")
@PreAuthorize("permitAll()")
public List<Product> search(@RequestParam(name = "cat", required = false) Integer categoryId,
                            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
                            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
                            @RequestParam(name = "color", required = false) String color)
{
    try
    {
        return productDao.search(categoryId, minPrice, maxPrice, color);
    }
    catch (Exception ex)
    {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
    }
}

Why it was challenging:
Dynamic filtering logic: You had to account for different combinations of filters (category, price range, color) without hardcoding every scenario.

DAO query flexibility: The corresponding SQL in MySqlProductDao needed to dynamically build a query depending on which filters were non-null.

Testing edge cases: It was tricky to ensure it returned accurate results for all combinations (e.g. only color, only price range, etc.).


EasyShop is a Java-based e-commerce backend built with Spring Boot and MySQL. It provides a secure RESTful API for:

User registration & login (with JWT tokens)

Browsing and searching products by category, price, and color

Admin tools to create, update, and delete products & categories

Shopping cart features for logged-in users

All data operations are handled using a DAO pattern with JDBC, and access is restricted using Spring Security roles (USER, ADMIN).

Working on EasyShop was both a rewarding and challenging experience.


The API involved a wide range of concepts: from building DAOs using JDBC, to securing endpoints with Spring Security, and implementing JWT authentication. One of the hardest parts was debugging a series of bugs in the ProductController, where product updates were mistakenly creating duplicates. Another major issue was resolving environment-specific datasource bugs related to ${datasource.url} not being injected correctly during test execution.

It took a lot of trial and error, careful debugging, and reading stack traces to resolve classpath issues, broken test configs, and circular dependencies. In the end, it taught me a lot about building real-world backends, managing configuration profiles, and writing maintainable controller logic.
