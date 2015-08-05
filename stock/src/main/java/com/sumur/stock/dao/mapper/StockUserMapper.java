package com.sumur.stock.dao.mapper;

import com.sumur.stock.entity.orm.StockUser;
import com.sumur.stock.entity.orm.StockUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockUserMapper {
    int countByExample(StockUserExample example);

    int deleteByExample(StockUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(StockUser record);

    int insertSelective(StockUser record);

    List<StockUser> selectByExample(StockUserExample example);

    StockUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") StockUser record, @Param("example") StockUserExample example);

    int updateByExample(@Param("record") StockUser record, @Param("example") StockUserExample example);

    int updateByPrimaryKeySelective(StockUser record);

    int updateByPrimaryKey(StockUser record);
}