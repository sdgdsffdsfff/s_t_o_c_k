package com.sumur.stock.dao.mapper;

import com.sumur.stock.entity.orm.StockCompany;
import com.sumur.stock.entity.orm.StockCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockCompanyMapper {
    int countByExample(StockCompanyExample example);

    int deleteByExample(StockCompanyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(StockCompany record);

    int insertSelective(StockCompany record);

    List<StockCompany> selectByExample(StockCompanyExample example);

    StockCompany selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") StockCompany record, @Param("example") StockCompanyExample example);

    int updateByExample(@Param("record") StockCompany record, @Param("example") StockCompanyExample example);

    int updateByPrimaryKeySelective(StockCompany record);

    int updateByPrimaryKey(StockCompany record);
}