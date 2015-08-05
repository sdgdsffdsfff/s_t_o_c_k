package com.sumur.stock.entity.other;

import java.io.Serializable;

import com.sumur.stock.exception.ResponseCode;


/**
 * 该数据体用于进行http协议json格式接口的返回信息描述。
 * @author yinwenjie
 * 
 */
public class JsonEntity implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2456254862958624358L;

	public JsonEntity() {
    }

    public JsonEntity(Object data, String errorinfo, ResponseCode responseCode)
    {
        this.data = data;
        this.responsecode = responseCode;
        this.errorinfo = errorinfo;
    }

    /**
     * 返回的请求结果查询
     */
    private Object data;

    /**
     * 接口请求的返回代码，从这个代码中可以识别接口调用是否成功，以及调用失败时的错误类型。 具体的code定义参见技术文档
     */
    private ResponseCode responsecode;

    /**
     * 错误的中文描述，错误信息的详细中文描述
     */
    private String errorinfo = "";

    /**
     * @return the data
     */
    public Object getData()
    {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data)
    {
        this.data = data;
    }

    /**
     * @return the responsecode
     */
    public ResponseCode getResponsecode()
    {
        return responsecode;
    }

    /**
     * @param responsecode the responsecode to set
     */
    public void setResponsecode(ResponseCode responsecode)
    {
        this.responsecode = responsecode;
    }

    /**
     * @return the errorinfo
     */
    public String getErrorinfo()
    {
        return errorinfo;
    }

    /**
     * @param errorinfo the errorinfo to set
     */
    public void setErrorinfo(String errorinfo)
    {
        this.errorinfo = errorinfo;
    }
}