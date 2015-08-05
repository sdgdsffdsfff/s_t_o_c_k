package com.sumur.stock.dao.mapper;

import com.sumur.stock.entity.orm.StockData;
import com.sumur.stock.entity.orm.StockDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockDataMapper {
    int countByExample(StockDataExample example);

    int deleteByExample(StockDataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(StockData record);

    int insertSelective(StockData record);

    List<StockData> selectByExample(StockDataExample example);

    StockData selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") StockData record, @Param("example") StockDataExample example);

    int updateByExample(@Param("record") StockData record, @Param("example") StockDataExample example);

    int updateByPrimaryKeySelective(StockData record);

    int updateByPrimaryKey(StockData record);
}