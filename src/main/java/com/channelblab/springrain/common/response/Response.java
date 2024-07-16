package com.channelblab.springrain.common.response;

/**
 * 基础响应接口
 *
 * <p>一切响应都必须实现此接口
 *
 * @author dengyi (email:dengyi@dengyi.pro)
 * @date 2021-01-01
 */
public interface Response {

    Integer SUCCESS_CODE = 1;//成功code
    Integer LOGIN_EXPIRE_CODE = 2;//未登录或登录过期code
    Integer RATE_LIMIT_CODE = 3;//限流code
    Integer NO_PERMISSION = 4;//无权限
    Integer ERROR_CODE = 5;//通用异常code


    /**
     * 响应状态
     *
     * @return Boolean
     */
    Boolean getStatus();

    /**
     * 响应code
     *
     * @return Integer
     */
    Integer getCode();

    /**
     * 响应消息
     *
     * @return String
     */
    String getMessage();

    /**
     * 响应数据
     *
     * @return
     */
    Object getData();

}
