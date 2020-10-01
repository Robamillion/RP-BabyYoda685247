package com.ibm.inventory_management.models;
import java.io.Serializable;
public class StockItem implements Serializable {
    private String name;
    private String id = null;
    private int stock = 0;
    private double price = 0.0;
    private String manufacturer = "";
    public StockItem() {
        super();
    }
    public StockItem(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public StockItem withName(String name) {
        this.setName(name);
        return this;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public StockItem withId(String id) {
        this.setId(id);
        return this;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public StockItem withStock(int stock) {
        this.setStock(stock);
        return this;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public StockItem withPrice(double price) {
        this.setPrice(price);
        return this;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public StockItem withManufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }
}





2:50
StockItemApi.java
New
2:50
package com.ibm.inventory_management.services;
import java.util.List;
import com.ibm.inventory_management.models.StockItem;
public interface StockItemApi {
    List<StockItem> listStockItems();
}
2:51
StockItemService.java
2:51
package com.ibm.inventory_management.services;
import static java.util.Arrays.asList;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.ibm.inventory_management.models.StockItem;
@Service
public class StockItemService implements StockItemApi {
    @Override
    public List<StockItem> listStockItems() {
        return asList(
                new StockItem("1")
                        .withName("Item 1")
                        .withStock(100)
                        .withPrice(10.5)
                        .withManufacturer("Sony"),
                new StockItem("2")
                        .withName("Item 2")
                        .withStock(150)
                        .withPrice(100.0)
                        .withManufacturer("Insignia"),
                new StockItem("3")
                        .withName("Item 3")
                        .withStock(10)
                        .withPrice(1000.0)
                        .withManufacturer("Panasonic")
        );
    }
}
2:55
StockItemControllerTest.java
2:55
package com.ibm.inventory_management.controllers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.ibm.inventory_management.models.StockItem;
import com.ibm.inventory_management.services.StockItemApi;
@DisplayName("StockItemController")
public class StockItemControllerTest {
    StockItemController controller;
    StockItemApi service;
    MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        service = mock(StockItemApi.class);
        controller = spy(new StockItemController(service));
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Nested
    @DisplayName("Given [GET] /stock-items")
    public class GivenGetStockItems {
        @Test
        @DisplayName("When called then it should return a 200 status")
        public void when_called_should_return_200_status() throws Exception {
            mockMvc.perform(get("/stock-items"))
                    .andExpect(status().isOk());
        }
        @Test
        @DisplayName("When called then it should return an empty array")
        public void when_called_then_return_an_empty_array() throws Exception {
            mockMvc.perform(get("/stock-items").accept("application/json"))
                    .andExpect(content().json("[]"));
        }
        @Test
        @DisplayName("When called then it should return the results of the StockItemService")
        public void when_called_then_return_the_results_of_the_stockitemservice() throws Exception {
            final List<StockItem> expectedResult = Arrays.asList(new StockItem());
            when(service.listStockItems()).thenReturn(expectedResult);
            mockMvc.perform(get("/stock-items").accept("application/json"))
                    .andExpect(content().json("[{}]"));
        }
    }
}
