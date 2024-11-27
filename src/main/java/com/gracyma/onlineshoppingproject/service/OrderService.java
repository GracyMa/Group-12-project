package com.gracyma.onlineshoppingproject.service;

import com.alibaba.fastjson.JSON;
import com.gracyma.onlineshoppingproject.db.dao.OnlineShoppingCommodityDao;
import com.gracyma.onlineshoppingproject.db.dao.OnlineShoppingOrderDao;
import com.gracyma.onlineshoppingproject.db.po.OnlineShoppingCommodity;
import com.gracyma.onlineshoppingproject.db.po.OnlineShoppingOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {
    @Resource
    OnlineShoppingCommodityDao onlineShoppingCommodityDao;
    @Resource
    OnlineShoppingOrderDao onlineShoppingOrderDao;

    public OnlineShoppingOrder placeOrderOriginal(String userId, String commodityId) {
        OnlineShoppingCommodity commodityDetail = onlineShoppingCommodityDao.getCommodityDetail(Long.parseLong(commodityId));
        int availableStock = commodityDetail.getAvailableStock();
        int lockStock = commodityDetail.getLockStock();
        if(availableStock > 0) {
            availableStock--; // update availableStock and lockStock
            lockStock++;
            commodityDetail.setAvailableStock(availableStock);// set
            commodityDetail.setLockStock(lockStock);
            onlineShoppingCommodityDao.updateCommodity(commodityDetail); // and put set update to database
            OnlineShoppingOrder order = createOrder(commodityId, 1L, userId);
//            onlineShoppingOrderDao.insertOrder(order);
            log.info("Place order succesful, current availableStock:" +  availableStock);
            return order;
        } else {
            log.info("commodity out of stock, commodityId:" + commodityDetail.getCommodityId());
            return null;
        }
    }
    private OnlineShoppingOrder createOrder(String commodityId, long orderAmount, String userId) {
        OnlineShoppingOrder order = OnlineShoppingOrder.builder()
                .orderNo(UUID.randomUUID().toString())
                .commodityId(Long.valueOf(commodityId))
                .orderAmount(orderAmount)
                .orderStatus(1)
                // 0: invalid order
                // 1. pending payment
                // 2. finish payment
                // 99. overtime order
                .createTime(new Date())
                .userId(Long.valueOf(userId))
                .build();
        onlineShoppingOrderDao.insertOrder(order);
        return order;
    }

    public OnlineShoppingOrder getOrder(String orderNo) {
        return onlineShoppingOrderDao.getOrderDetailByOrderNo(orderNo);
    }
    public OnlineShoppingOrder queryOrderByOrderNo(String orderNo) {
        return onlineShoppingOrderDao.getOrderDetailByOrderNo(orderNo);
    }


}
