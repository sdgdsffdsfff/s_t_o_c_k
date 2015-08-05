package com.sumur.stock.dao.mapper;

import com.sumur.stock.entity.orm.StockRole;
import com.sumur.stock.entity.orm.StockRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockRoleMapper {
    int countByExample(StockRoleExample example);

    int deleteByExample(StockRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(StockRole record);

    int insertSelective(StockRole record);

    List<StockRole> selectByExample(StockRoleExample example);

    StockRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") StockRole record, @Param("example") StockRoleExample example);

    int updateByExample(@Param("record") StockRole record, @Param("example") StockRoleExample example);

    int updateByPrimaryKeySelective(StockRole record);

    int updateByPrimaryKey(StockRole record);
}