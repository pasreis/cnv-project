package cnv.g33;

/**
 * Hello world!
 *
 */
public class LoadBalancerApp {
    public static void main( String[] args ) {
        RequestHandler requestHandler = new RequestHandler();

        WebServer ws = new WebServer(7000, requestHandler);

        try {
            ws.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
