package com.core.net;

/**
 * Created by yangwenmin on 2017/10/28.
 */

public class HttpUrl {


    // 测试环境
    //public static final String BASE_URL = "http://192.168.0.111:80/mainintf";
    //public static final String BASE_URL = "http://192.168.1.215:8080/mainintf";

    //public static final String BASE_URL = "http://192.168.0.120:8080/mainintf";
    //public static final String BASE_URL = "http://218.107.155.100:8082/mainintf";
    //public static final String BASE_URL = "http://172.16.11.121:8080/mainintf";
    //public static final String BASE_URL = "http://172.16.11.94:8080/mainintf";
    // public static final String BASE_URL = "http://172.21.25.103:8080/mainintf";
    //public static final String BASE_URL = "http://192.168.1.242:8080/mainintf";
    //public static final String BASE_URL = "http://192.168.1.208:8080/mainintf";
    public static final String BASE_URL = "http://192.168.0.35:80/app-main-intf";
    //public static final String BASE_URL = "http://192.168.1.208:8080/app-main-intf";

    // 正式环境
     //public static final String BASE_URL = "http://172.16.1.95:8001/mainintf";

    public static final String API_HOST = BASE_URL+"/DdController/";

    public static final String IP_END = "method";


    // 登录
    public static final String LOGIN_API = BASE_URL+"/ComController/Api";
    // 业代数据
    public static final String DBTPLUS_DATA_API = BASE_URL+"/YdController/Api";
    // 督导数据
    public static final String DD_PUSH_DATA = BASE_URL+"/DdController/Api";


}
