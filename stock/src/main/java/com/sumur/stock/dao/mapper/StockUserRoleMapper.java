package com.sumur.stock.dao.mapper;

import com.sumur.stock.entity.orm.StockUserRole;
import com.sumur.stock.entity.orm.StockUserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockUserRoleMapper {
    int countByExample(StockUserRoleExample example);

    int deleteByExample(StockUserRoleExample example);

    int insert(StockUserRole record);

    int insertSelective(StockUserRole record);

    List<StockUserRole> selectByExample(StockUserRoleExample example);

    int updateByExampleSelective(@Param("record") StockUserRole record, @Param("example") StockUserRoleExample example);

    int updateByExample(@Param("record") StockUserRole record, @Param("example") StockUserRoleExample example);
}