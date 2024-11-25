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
                .commodityName("Dress")
                .commodityDesc("desc")
                .availableStock(20)
                .totalStock(20)
                .price(100)
                .lockStock(0)
                .creatorUserId(125L)
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
        List<OnlineShoppingCommodity> onlineShoppingCommodities = dao.listCommoditiesByUserId(125L);
        log.info(onlineShoppingCommodities.toString());
    }

    @Test
    void getCommodityDetail() {
        OnlineShoppingCommodity commodityDetail = dao.getCommodityDetail(1018);
        log.info(commodityDetail.toString());

    }

}