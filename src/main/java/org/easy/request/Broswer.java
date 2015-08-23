package org.easy.request;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;
import org.easy.request.response.Response;

/**
 * Broswer.
 *
 * @author Kuo Hong on 2015/8/22 0022.
 */
public interface Broswer {
    /**
     * GET Method.
     *
     * @param url     URL
     * @param context context
     * @param pairs   params
     * @return response
     */
    Response get(String url, HttpContext context, NameValuePair... pairs);

    /**
     * GET Method.
     *
     * @param url     URL
     * @param context context
     * @param header  header
     * @param pairs   params
     * @return response
     */
    Response get(String url, HttpContext context, Header header, NameValuePair... pairs);

    /**
     * POST Method.
     *
     * @param url     URL
     * @param context context
     * @param header  header
     * @param pairs   params
     * @return response
     */
    Response post(String url, HttpContext context, Header header, NameValuePair... pairs);

    /**
     * POST Method.
     *
     * @param url     URL
     * @param context context
     * @param pairs   params
     * @return response
     */
    Response post(String url, HttpContext context, NameValuePair... pairs);

    /**
     * POST Method.
     *
     * @param url     URL
     * @param context context
     * @param entity  httpRequestEntity
     * @return response
     */
    Response post(String url, HttpContext context, HttpEntity entity);

    /**
     * Execute Method.
     *
     * @param request Request
     * @param context context
     * @return response
     */
    Response exec(HttpUriRequest request, HttpContext context);

    void close();

}
