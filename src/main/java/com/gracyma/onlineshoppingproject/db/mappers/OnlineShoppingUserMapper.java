package com.gracyma.onlineshoppingproject.db.mappers;

import com.gracyma.onlineshoppingproject.db.po.OnlineShoppingUser;

public interface OnlineShoppingUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(OnlineShoppingUser record);

    int insertSelective(OnlineShoppingUser record);

    OnlineShoppingUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(OnlineShoppingUser record);

    int updateByPrimaryKey(OnlineShoppingUser record);
}