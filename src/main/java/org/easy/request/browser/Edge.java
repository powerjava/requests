package org.easy.request.browser;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.easy.request.Broswer;

/**
 * Edge.
 *
 * @author Kuo Hong on 2015/8/22 0022.
 */
public class Edge extends AbstractBroswer implements Broswer {


    public Edge(CloseableHttpClient closeableHttpClient) {
        super(closeableHttpClient);
    }

    protected HttpUriRequest setHeader(HttpUriRequest request) {
        request.removeHeaders("User-Agent");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240");
        return request;

    }


}
