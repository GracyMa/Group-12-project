package com.gracyma.onlineshoppingproject.db.dao.impl;

import com.gracyma.onlineshoppingproject.db.dao.OnlineShoppingCommodityDao;
import com.gracyma.onlineshoppingproject.db.mappers.OnlineShoppingCommodityMapper;
import com.gracyma.onlineshoppingproject.db.po.OnlineShoppingCommodity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class OnlineShoppingCommodityDaoMySqlImpl implements OnlineShoppingCommodityDao {

    @Resource
    OnlineShoppingCommodityMapper onlineShoppingCommodityMapper;

    @Override
    public int insertCommodity(OnlineShoppingCommodity commodity) {
        return onlineShoppingCommodityMapper.insert(commodity);
    }

    @Override
    public List<OnlineShoppingCommodity> listCommodities() {
        return onlineShoppingCommodityMapper.listCommodities();
    }

    @Override
    public List<OnlineShoppingCommodity> listCommoditiesByUserId(long userId) {
        return onlineShoppingCommodityMapper.listCommoditiesByUserId(userId);
    }

    @Override
    public OnlineShoppingCommodity getCommodityDetail(long commodityId) {
        return onlineShoppingCommodityMapper.selectByPrimaryKey(commodityId);
    }

    @Override
    public int updateCommodity(OnlineShoppingCommodity commodityDetail) {
        return onlineShoppingCommodityMapper.updateByPrimaryKey(commodityDetail);
    }

    @Override
    public int deductStockWithCommodityId(long commodityId) {
        return onlineShoppingCommodityMapper.deductStockWithCommodityId(commodityId);
    }

//    @Override
//    public int revertStockWithCommodityId(long commodityId) {
//        return onlineShoppingCommodityMapper.revertStockWithCommodityId(commodityId);
//    }
//
//    @Override
//    public List<OnlineShoppingCommodity> searchCommodityByKeyWord(String keyWord) {
//        String keyWordPattern = "%" + keyWord + "%";
//        return onlineShoppingCommodityMapper.searchCommodityByKeyWord(keyWordPattern);
//    }

}

