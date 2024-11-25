# OnlineShoppingProjectApplication Test Case

The test case for the `OnlineShoppingProjectApplication` class is a simple yet essential check to ensure that the Spring Boot application can start up and load its context without errors. It uses the `@SpringBootTest` annotation, which triggers Spring Boot to initialize the entire application context, just like when the application runs in a real environment.

The method `contextLoads()` is annotated with `@Test`, and while it doesn’t contain any logic or assertions, it serves as a health check for the application. If the application context fails to load due to misconfigured beans, missing dependencies, or invalid properties, this test will automatically fail. This ensures that the basic setup of the application is correct, including configurations, dependency injection, and initialization of components like MyBatis mappers (as specified by the `@MapperScan` annotation in the main class).

Although simple, this test is vital for catching configuration or startup issues early, especially in a CI/CD pipeline where quick feedback about application health is necessary. It forms the foundation for more complex tests by guaranteeing that the application context is stable and functional.

---

# OrderServiceTest Explanation

The `OrderServiceTest` class is designed to validate the functionality of the `OrderService` class, ensuring that its methods for placing and retrieving orders behave as expected. The class leverages **Mockito** to mock dependencies such as `OnlineShoppingCommodityDao` and `OnlineShoppingOrderDao`, simulating database interactions without requiring an actual database. **JUnit 5** is used for assertions and lifecycle management of the tests.

---

## 1. `testPlaceOrderOriginal_Success`

This test verifies that placing an order works correctly when the commodity has sufficient stock.

- **Setup**: A mock commodity with an available stock of 10 is created. The mocked methods are configured to:
  - Return the commodity details.
  - Simulate a successful stock update.
  - Simulate a successful order insertion.

- **Execution**: The method `placeOrderOriginal("1", "1")` is called.

- **Assertions**: The test checks that:
  - The returned order is not `null`.
  - The `commodityId`, `orderAmount`, and `orderStatus` match the expected values.

- **Verifications**: The test confirms that:
  - `commodityDao.getCommodityDetail()` is called once.
  - `commodityDao.updateCommodity()` and `orderDao.insertOrder()` are called the correct number of times.

---

## 2. `testPlaceOrderOriginal_OutOfStock`

This test checks the behavior when attempting to place an order for a commodity that is out of stock.

- **Setup**: A mock commodity with zero stock is created. The `getCommodityDetail` method is mocked to return this commodity.

- **Execution**: The method `placeOrderOriginal("1", "1")` is called.

- **Assertions**: The test ensures that:
  - The returned order is `null`, as no order should be placed if the stock is insufficient.

- **Verifications**: The test confirms that:
  - `commodityDao.getCommodityDetail()` is called once.
  - `commodityDao.updateCommodity()` and `orderDao.insertOrder()` are never called.

---

## 3. `testGetOrder`

This test validates the retrieval of an order using its order number.

- **Setup**: A mock order with valid details is created. The `getOrderDetailByOrderNo` method is mocked to return this order when called with a specific order number.

- **Execution**: The method `getOrder("order-1")` is called.

- **Assertions**: The test checks that:
  - The returned order is not `null`.
  - The `commodityId`, `orderAmount`, and `orderStatus` match the expected values.

- **Verifications**: The test confirms that:
  - `orderDao.getOrderDetailByOrderNo()` is called exactly once.

---

# Summary

These tests collectively ensure that the `OrderService` class handles both successful and failure scenarios robustly and interacts correctly with its dependencies. By testing various scenarios such as sufficient stock, out-of-stock, and retrieving an order, the functionality of the service is validated comprehensively.

---

# OnlineShoppingCommodityDaoTest Explanation
The OnlineShoppingCommodityDaoTest class is designed to validate the functionality of the OnlineShoppingCommodityDaoMySqlImpl class, ensuring that its methods for interacting with the database behave as expected
The tests cover key operations such as inserting commodities, retrieving commodity lists, filtering commodities by user ID, and retrieving detailed information about a commodity. JUnit 5 is used for assertions and lifecycle management, and Spring Boot Test is used for setting up the application context.

---

## 1. `insertCommodity`
This test verifies that a new commodity can be inserted into the database successfully.

- **Setup**:Creates a commodity object with the mock info attributes:
  Name: "TestCommodity"
  Description: "desc"
  Available Stock: 111
  Total Stock: 111
  Price: 999
  Locked Stock: 0
  Creator User ID: 124L
- **Execution**: Calls dao.insertCommodity(commodity) to persist the commodity.
- **Expected Return**: The commodity should be inserted into the database successfully. Also can be verified by fetching it in subsequent tests


## 2. ` listCommodities`
Ensures all commodities can be retrieved from the database.
- **Setup**: No additional setup. Assumes the database contains existing data.
- **Execution**: Calls dao.listCommodities() to fetch all available commodities.
- **Expected Return**: Should return a non-empty list of commodities. Each entry should match database entries, but this is logged for manual validation.

## 3. ` listCommoditiesByUserId`
Checks if commodities can be filtered by the creator's user ID.
- **Setup**: Use mock info from first test case of creatorUserId = 124L.
- **Execution**: Calls dao.listCommoditiesByUserId(124L) to fetch commodities created by this user.
- **Expected Return**: Returns a list of commodities where creatorUserId = 124L. The list should not be null or empty if the user has associated commodities.

## 3. ` getCommodityDetail` 
Verifies that detailed information about a specific commodity can be retrieved.
- **Setup**:Use mock info from first test case, and assume the commodity is in database with id = 1002
- **Execution**: Calls dao.getCommodityDetail(1002) to fetch the commodity's details.
- **Expected Return**:Returns a commodity object with details matching the database entry for id = 1002. Logs the result for validation.

---

# Summary

These tests collectively ensure that the OnlineShoppingCommodityDaoMySqlImpl class handles key database operations effectively. By testing insertion, retrieval of all commodities, filtering by user ID, and fetching detailed commodity information, the functionality of the DAO is validated comprehensively.

---