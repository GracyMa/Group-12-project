package com.gracyma.onlineshoppingproject.service;

import com.gracyma.onlineshoppingproject.db.dao.OnlineShoppingCommodityDao;
import com.gracyma.onlineshoppingproject.db.dao.OnlineShoppingOrderDao;
import com.gracyma.onlineshoppingproject.db.po.OnlineShoppingCommodity;
import com.gracyma.onlineshoppingproject.db.po.OnlineShoppingOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OnlineShoppingCommodityDao commodityDao;

    @Mock
    private OnlineShoppingOrderDao orderDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrderOriginal_Success() {
        // Mock commodity details
        OnlineShoppingCommodity mockCommodity = new OnlineShoppingCommodity();
        mockCommodity.setCommodityId(1L);
        mockCommodity.setAvailableStock(10);
        mockCommodity.setLockStock(0);

        when(commodityDao.getCommodityDetail(1L)).thenReturn(mockCommodity);
        when(commodityDao.updateCommodity(any(OnlineShoppingCommodity.class))).thenReturn(1);
        when(orderDao.insertOrder(any(OnlineShoppingOrder.class))).thenReturn(1);
        // Test the method
        OnlineShoppingOrder order = orderService.placeOrderOriginal("1", "1");

        assertNotNull(order, "Order should not be null");
        assertEquals(1L, order.getCommodityId(), "Commodity ID should match");
        assertEquals(1L, order.getOrderAmount(), "Order amount should match");
        assertEquals(1, order.getOrderStatus(), "Order status should be '1' (Pending payment)");
    
        // Verify interactions
        verify(commodityDao, times(1)).getCommodityDetail(1L);
        verify(commodityDao, times(1)).updateCommodity(mockCommodity);
        verify(orderDao, times(1)).insertOrder(any(OnlineShoppingOrder.class));
    }

    @Test
    void testPlaceOrderOriginal_OutOfStock() {
        // Mock commodity details
        OnlineShoppingCommodity mockCommodity = new OnlineShoppingCommodity();
        mockCommodity.setCommodityId(1L);
        mockCommodity.setAvailableStock(0);
        mockCommodity.setLockStock(0);

        when(commodityDao.getCommodityDetail(1L)).thenReturn(mockCommodity);

        // Test the method
        OnlineShoppingOrder order = orderService.placeOrderOriginal("1", "1");

        assertNull(order);

        // Verify interactions
        verify(commodityDao, times(1)).getCommodityDetail(1L);
        verify(commodityDao, never()).updateCommodity(any());
        verify(orderDao, never()).insertOrder(any());
    }

    @Test
    void testGetOrder() {
        // Mock order details
        OnlineShoppingOrder mockOrder = OnlineShoppingOrder.builder()
                .orderNo(UUID.randomUUID().toString())
                .commodityId(1L)
                .orderAmount(1L)
                .orderStatus(1)
                .createTime(new Date())
                .userId(1L)
                .build();

        when(orderDao.getOrderDetailByOrderNo("order-1")).thenReturn(mockOrder);

        // Test the method
        OnlineShoppingOrder order = orderService.getOrder("order-1");

        assertNotNull(order);
        assertEquals(1L, order.getCommodityId());
        assertEquals(1L, order.getOrderAmount());
        assertEquals(1, order.getOrderStatus());

        // Verify interactions
        verify(orderDao, times(1)).getOrderDetailByOrderNo("order-1");
    }
}
