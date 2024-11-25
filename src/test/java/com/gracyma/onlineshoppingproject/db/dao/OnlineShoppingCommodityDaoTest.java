package com.gracyma.onlineshoppingproject.db.dao;

import com.gracyma.onlineshoppingproject.db.dao.impl.OnlineShoppingCommodityDaoMySqlImpl;
import com.gracyma.onlineshoppingproject.db.po.OnlineShoppingCommodity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class OnlineShoppingCommodityDaoTest {
    @Resource
    OnlineShoppingCommodityDaoMySqlImpl dao;

    @Test
    void insertCommodity() {
        OnlineShoppingCommodity commodity = OnlineShoppingCommodity.builder()
                .commodityName("TestCommodity")
                .commodityDesc("desc")
                .availableStock(111)
                .totalStock(111)
                .price(999)
                .lockStock(0)
                .creatorUserId(124L)
                .build();
        dao.insertCommodity(commodity);
    }

    @Test
    void listCommodities() {
        List<OnlineShoppingCommodity> onlineShoppingCommodities = dao.listCommodities();
        log.info(onlineShoppingCommodities.toString());
    }

    @Test
    void listCommoditiesByUserId() {
        List<OnlineShoppingCommodity> onlineShoppingCommodities = dao.listCommoditiesByUserId(124L);
        log.info(onlineShoppingCommodities.toString());
    }

    @Test
    void getCommodityDetail() {
        OnlineShoppingCommodity commodityDetail = dao.getCommodityDetail(1002);
        log.info(commodityDetail.toString());

    }

}