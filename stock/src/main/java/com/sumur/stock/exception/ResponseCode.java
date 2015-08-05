package com.sumur.stock.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应编码，记录了所有执行者向调用者返回的处理结果编码
 * @author yinwenjie
 */
/**
 * 错误代码描述
 * <pre>
 * 200 请求成功
 * 301 请求次数已经超过本周期设置的最大值
 * 302 请求频率已超过设定的最大值。
 * 303 该接口工作繁忙，产生拥堵，请稍后再试。
 * 304 
 * 401 规定的必传入项没有传入
 * 402 传入的参数项格式不符合规定
 * 403 传入参数项至少有一项超出规定的长度
 * 404 没有查询到符合条件的数据
 * 405 查询到重复数据
 * 406 传入的参数编码格式失效
 * 407 未查询到指定信息
 * 501 服务器内部错误
 * 502 插入操作错误
 * 503 更新操作错误
 * 504 Fastdfs服务器错误
 * 
 * ===========================================    	
 * 对于一些具体业务逻辑错误进行根据情况进行定义
 * 1000 可以根据错误信息显示 供前端使用
 * 1001 jid格式错误
 * 1002 调用三方接口thrift错误
 * </pre>
 * @author wenjie
 */
public enum ResponseCode
{
    _200("200"), _301("301"), _302("302"), _303("303"), _304("304"), _401("401"), _402("402"), _403(
            "403"), _404("404"), _405("405"), _406("406"), _407("407"), _501("501"), _502("502"), _503("503"), _504("504"),        
    _1000("1000"),  _1001("1001"), _1002("1002");
    
    private String code;

    ResponseCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return this.code;
    }

    /**
     * 该私有静态方法用于映射字符串和枚举信息的关系
     */
    private static final Map<ResponseCode, String> stringToEnum = new HashMap<ResponseCode, String>();
    static
    {
        for (ResponseCode blah : values())
        {
            stringToEnum.put(blah, blah.toString());
        }
    }

    /**
     * @param symbol
     * @return
     */
    public static String fromString(ResponseCode symbol)
    {
        return stringToEnum.get(symbol);
    }

    @Override
    public String toString()
    {
        return code;
    }
}
