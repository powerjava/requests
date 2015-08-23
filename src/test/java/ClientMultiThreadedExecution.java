import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.easy.request.handler.ResponseHandler;
import org.easy.request.response.Response;

import java.io.IOException;

/**
 * Created by hangu on 2015/8/22 0022.
 */
public class ClientMultiThreadedExecution {
    public static void main(String[] args) throws Exception {
        // Create an HttpClient with the ThreadSafeClientConnManager.
        // This connection manager must be used if more than one thread will
        // be using the HttpClient.
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setMaxTotal(800);
        cm.setDefaultMaxPerRoute(100);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        for (int i = 0; i < 5000; i++) {
            HttpGet httpget = new HttpGet("http://www.baidu.com");
            GetThread threads = new GetThread(httpclient, httpget, i + 1);
            threads.start();
        }
    }

    /**
     * A thread that performs a GET.
     */
    static class GetThread extends Thread {

        private final CloseableHttpClient httpClient;
        private final HttpContext context;
        private final HttpGet httpget;
        private final int id;

        public GetThread(CloseableHttpClient httpClient, HttpGet httpget, int id) {
            this.httpClient = httpClient;
            this.context = new BasicHttpContext();
            this.httpget = httpget;
            this.id = id;
        }

        /**
         * Executes the GetMethod and prints some status information.
         */
        @Override
        public void run() {
            System.out.println(id + " - about to get something from " + httpget.getURI());
            try {
                Response response1 = httpClient.execute(httpget, new ResponseHandler(), context);
                System.out.println(response1.getContentType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
