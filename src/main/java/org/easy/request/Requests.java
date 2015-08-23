package org.easy.request;


import org.apache.http.Header;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.easy.request.browser.Edge;
import org.easy.request.util.RequestsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Requests.
 *
 * @author Kuo Hong on 2015/8/22 0022.
 */
public class Requests {
    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = RequestsUtils.MAX_TOTAL_CONNECTIONS();
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = RequestsUtils.MAX_ROUTE_CONNECTIONS();
    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = RequestsUtils.WAIT_TIMEOUT();
    static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    private static Logger logger = LoggerFactory.getLogger(Requests.class);
    private static int SOCKET_TIME_OUT = RequestsUtils.SOCKET_TIME_OUT();
    /**
     * 连接超时时间
     */
    private static int CONNECTION_TIME_OUT = RequestsUtils.CONNECTION_TIME_OUT();
    /**
     *
     */
    private static int CONNECTION_REQUEST_TIME_OUT = RequestsUtils.CONNECTION_REQUEST_TIME_OUT();
    private static RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT)
            .setConnectTimeout(CONNECTION_TIME_OUT)
            .setSocketTimeout(SOCKET_TIME_OUT)
            .setCookieSpec(CookieSpecs.DEFAULT)
            .setExpectContinueEnabled(true)
            .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
            .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
            .build();

    static {
        cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        SocketConfig defaultSocketConfig = SocketConfig.custom()
                .setSoTimeout(WAIT_TIMEOUT)
                .build();
        cm.setDefaultSocketConfig(defaultSocketConfig);
    }


    public static Broswer Edge() {
        Collection<? extends Header> defaultHeaders = setDefaultHeaders();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultHeaders(defaultHeaders)
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();
        return new Edge(httpclient);
    }

    private static Collection<? extends Header> setDefaultHeaders() {
        Collection<Header> hreaders = new ArrayList<>();
        Header accept = new BasicHeader("Accept", "*/*");
        hreaders.add(accept);
        Header hreader = new BasicHeader("Accept-Lanauage", "zh-Hans-CN, zh-Hans; q=0.8, en-US; q=0.7, en; q=0.5, zh-Hant-HK; q=0.3, zh-Hant; q=0.2");
        hreaders.add(hreader);
        return hreaders;
    }


}
