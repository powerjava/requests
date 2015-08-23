import org.easy.request.Broswer;
import org.easy.request.Requests;
import org.easy.request.response.Response;

import java.util.concurrent.TimeUnit;

/**
 * Created by hangu on 2015/8/23 0023.
 */
public class EdgeTest {
    public static void main(String[] args) {
        Requests r = new Requests();
        Broswer edge = Requests.Edge();
        EdgeTest test = new EdgeTest();
        for (int i = 0; i < 1; i++) {
            Thread t = test.new Test(edge);
            t.start();
        }
    }

    class Test extends Thread {
        private Broswer broswer;

        public Test(Broswer edge) {
            this.broswer = edge;
        }

        @Override
        public void run() {
            Response response2 = broswer.get("http://www.ithome.com/", null);
            System.out.println(response2.getContentType());
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //edge.close();
        }
    }
}
