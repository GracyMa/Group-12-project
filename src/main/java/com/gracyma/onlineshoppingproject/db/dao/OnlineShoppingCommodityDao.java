package com.gracyma.onlineshoppingproject.db.dao;

import com.gracyma.onlineshoppingproject.db.po.OnlineShoppingCommodity;

import java.util.List;

public interface OnlineShoppingCommodityDao {
    int insertCommodity(OnlineShoppingCommodity commodity);
    List<OnlineShoppingCommodity> listCommodities();
    List<OnlineShoppingCommodity> listCommoditiesByUserId(long userId);
    OnlineShoppingCommodity getCommodityDetail(long commodityId);

    int updateCommodity(OnlineShoppingCommodity onlineShoppingCommodity);
    // int because default return value is 1/0 success/fail
    int deductStockWithCommodityId(long commodityId);

//    int revertStockWithCommodityId(long commodityId);

//    List<OnlineShoppingCommodity>searchCommodityByKeyWord(String keyWord);
}
