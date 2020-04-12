package cnv.g33;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WebServer {
	private int _port;
	private HttpServer _server;
	private HttpHandler _handler;

	public WebServer(int port, HttpHandler handler) {
		if (port < 1 || port > 65535) {
			throw new IllegalArgumentException("O numero do porto deve estar entre 1 e 65535");
		}

		if (handler == null) {
			throw new IllegalArgumentException("HttpHandler nao pode ser null");
		}

		_port = port;
		_handler = handler;
	}

	public void start() throws Exception {
		_server = HttpServer.create(new InetSocketAddress(_port), 0);

		_server.createContext("/sudoku", _handler);

		_server.setExecutor(Executors.newCachedThreadPool());
		_server.start();

		System.out.println(_server.getAddress().toString());
	}
}
