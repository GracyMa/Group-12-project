package com.gracyma.onlineshoppingproject.db.mappers;

import com.gracyma.onlineshoppingproject.db.po.OnlineShoppingCommodity;

import java.util.List;

public interface OnlineShoppingCommodityMapper {
    int deleteByPrimaryKey(Long commodityId);

    int insert(OnlineShoppingCommodity record);

    int insertSelective(OnlineShoppingCommodity record);

    OnlineShoppingCommodity selectByPrimaryKey(Long commodityId);

    int updateByPrimaryKeySelective(OnlineShoppingCommodity record);

    int updateByPrimaryKey(OnlineShoppingCommodity record);

    List<OnlineShoppingCommodity> listCommodities();

    List<OnlineShoppingCommodity> listCommoditiesByUserId(long userId);

    int deductStockWithCommodityId(long commodityId);

    int revertStockWithCommodityId(long commodityId);
}