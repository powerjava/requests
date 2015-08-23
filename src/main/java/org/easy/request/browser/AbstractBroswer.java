package org.easy.request.browser;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.easy.request.Broswer;
import org.easy.request.handler.ResponseHandler;
import org.easy.request.response.Response;

import java.io.IOException;

/**
 * AbstractBroswer.
 *
 * @author Kuo Hong on 2015/8/23 0023.
 */
public abstract class AbstractBroswer implements Broswer {

    protected CloseableHttpClient closeableHttpClient;
    protected CloseableHttpClient httpClient = getHttpClient();

    public AbstractBroswer(CloseableHttpClient closeableHttpClient) {
        this.closeableHttpClient = closeableHttpClient;
    }

    protected CloseableHttpClient getHttpClient() {
        return closeableHttpClient;
    }

    protected abstract HttpUriRequest setHeader(HttpUriRequest request);

    /**
     * GET Method.
     *
     * @param url     URL
     * @param context context
     * @param pairs   params
     * @return response
     */
    @Override
    public Response get(String url, HttpContext context, NameValuePair... pairs) {
        Response response = null;
        HttpUriRequest request = RequestBuilder.get()
                .get(url)
                .addParameters(pairs).build();
        try {
            response = getHttpClient().execute(this.setHeader(request), new ResponseHandler(), context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    @Override
    public Response get(String url, HttpContext context, Header header, NameValuePair... pairs) {
        Response response = null;
        HttpUriRequest request = RequestBuilder.get(url).addHeader(header)
                .addParameters(pairs).build();
        try {
            response = getHttpClient().execute(this.setHeader(request), new ResponseHandler(), context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response post(String url, HttpContext context, Header header, NameValuePair... pairs) {
        Response response = null;
        HttpUriRequest request = RequestBuilder.post(url).addHeader(header)
                .addParameters(pairs).build();
        try {
            response = getHttpClient().execute(this.setHeader(request), new ResponseHandler(), context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response post(String url, HttpContext context, NameValuePair... pairs) {
        Response response = null;
        HttpUriRequest request = RequestBuilder.post(url)
                .addParameters(pairs).build();
        try {
            response = getHttpClient().execute(this.setHeader(request), new ResponseHandler(), context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    @Override
    public Response post(String url, HttpContext context, HttpEntity entity) {
        Response response = null;
        HttpUriRequest request = RequestBuilder.post(url)
                .setEntity(entity).build();
        try {
            response = getHttpClient().execute(this.setHeader(request), new ResponseHandler(), context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response exec(HttpUriRequest request, HttpContext context) {
        Response response = null;
        try {
            response = getHttpClient().execute(this.setHeader(request), new ResponseHandler(), context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void close() {
        try {
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
