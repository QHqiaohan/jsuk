package com.jh.jsuk.conf;

public class Constant {
    /**
     * 数据请求返回码
     */
    public static final long RESCODE_SUCCESS = 200;                //成功(默认success)
//    public static final long RESCODE_SUCCESS_MSG = 200;            //成功(msg)
//    public static final long RESCODE_SUCCESS_DATA = 200;            //成功(默认success 自定义data)
//    public static final long RESCODE_SUCCESS_MSG_DATA = 200;    //成功(自定义msg data)
//    public static final long RESCODE_EXCEPTION = 500;            //请求抛出异常
    public static final long RESCODE_NOLOGIN = 203;                //未登陆状态
    public static final long RESCODE_NOAUTH = 401;                //未登陆状态
    public static final long RESCODE_REQUEST_ERROR = 400;                //未登陆状态
//    public static final long RESCODE_LOGIN_OVERDUE = -100;        //登陆过期
//    public static final long RESCODE_NOFOUND = -101;                //没有找到该用户
    public static final long RESCODE_ERROR = -11;                //无操作权限
//    public static final long BIND_QQ_NOAUTH = -1001;                //未绑定QQ
//    public static final long BIND_WX_NOAUTH = -1002;                //未绑定微信
//    public static final long BIND_WB_NOAUTH = -1003;                //未绑定微博
//    public static final long RESCODE_NOEXIST = -10;                //查询结果为空

    /**
     * jwt
     */
    public static final String JWT_ID = "jwt";
    public static final String JWT_SECRET = "5bc9a23bb5994ef8565ed1e657192fee";
    public static final String JWT_HEADER = "access-token";
    public static final int JWT_TTL = 60 * 60 * 1000;  //millisecond
    public static final int JWT_REFRESH_INTERVAL = 55 * 60 * 1000;  //millisecond
    public static final int JWT_REFRESH_TTL = 12 * 60 * 60 * 1000;  //millisecond
    /**
     * 天气接口地址
     */
    public static final String MEIZU_WEATHER_URL = "http://aider.meizu.com/app/weather/listWeather";

}
