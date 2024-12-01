package com.gracyma.onlineshoppingproject.controller;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gracyma.onlineshoppingproject.db.dao.OnlineShoppingCommodityDao;
import com.gracyma.onlineshoppingproject.db.po.OnlineShoppingCommodity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class CommodityController {
    @Resource OnlineShoppingCommodityDao onlineShoppingCommodityDao;
    //查看商品列表
    @RequestMapping("/listItems/{sellerId}")
    public String listItems( @PathVariable("sellerId") String sellerId,
                             Map<String, Object> resultMap ) {
        List<OnlineShoppingCommodity> onlineShoppingCommodities = onlineShoppingCommodityDao.listCommoditiesByUserId(Long.parseLong(sellerId));
        resultMap.put("itemList", onlineShoppingCommodities);
        return "list_items";
    }

    // get commodities by userID
    @GetMapping("/online_users/{userId}")
    public String getCommodityByUserId(@PathVariable ("userId") String userId,
                                       Map<String, Object> resultMap) {
        List<OnlineShoppingCommodity> onlineShoppingCommodities = onlineShoppingCommodityDao.listCommoditiesByUserId(Long.parseLong(userId));
        resultMap.put("itemList", onlineShoppingCommodities);
        return "list_items";
    }

    //查看商品详情
    @RequestMapping("/item/{commodityId}")
    public String itemPage( @PathVariable("commodityId") long commodityId,
                            Map<String, Object> resultMap ) {
        OnlineShoppingCommodity onlineShoppingCommodity = onlineShoppingCommodityDao.getCommodityDetail(commodityId);
        resultMap.put("commodity", onlineShoppingCommodity);
        return "item_detail";
    }

    @RequestMapping("/addItem")
    public String addCommodity() {

        return "add_commodity";
    }

    @GetMapping({"sellers/{sellerId}/commodities/", "commodities/{sellerId}" })
    public String listCommoditiesBySellerId(@PathVariable("sellerId") String sellerId,
                                            Map<String, Object> resultMap){
        try (Entry entry = SphU.entry("listCommoditiesRule", EntryType.IN, 1, sellerId)) {
            // set flow control for request list commodities for specific seller, only 1 request allowed
            List<OnlineShoppingCommodity> onlineShoppingCommodities =
                    onlineShoppingCommodityDao.listCommoditiesByUserId(Long.parseLong(sellerId));
            resultMap.put("itemList", onlineShoppingCommodities);
            return "list_items";
        } catch (BlockException e) {
            // if exceed threshold
            log.error("ListItems got throttled{}", e.toString());
            return "wait";
        }
    }

    @PostMapping("/commodities")
    public String createCommodity(@RequestParam("commodityId") long commdityId,
                                  @RequestParam("commodityName") String commodityName,
                                  @RequestParam("commodityDesc") String commodityDesc,
                                  @RequestParam("price") int price,
                                  @RequestParam("availableStock") int availableStock,
                                  @RequestParam("creatorUserId") long creatorUserId,
                                  Map<String, Object> resultMap) {
        OnlineShoppingCommodity commodity = OnlineShoppingCommodity.builder()
                .commodityId(commdityId)
                .commodityName(commodityName)
                .commodityDesc(commodityDesc)
                .price(price)
                .availableStock(availableStock)
                .creatorUserId(creatorUserId)
                .lockStock(0)
                .totalStock(availableStock)
                .build();
        onlineShoppingCommodityDao.insertCommodity(commodity);
//        esService.addCommodityToEs(commodity);
        resultMap.put("Item", commodity);
        return "add_commodity_success";
    }
}

