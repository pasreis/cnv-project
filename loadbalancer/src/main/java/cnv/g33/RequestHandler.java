package cnv.g33;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RequestHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		URIArgumentParser parser = new URIArgumentParser();

		String[] args = parser.parseCompleteQuery(httpExchange);

		for (String arg : args) {
			System.out.println(arg);
		}
	}
}
