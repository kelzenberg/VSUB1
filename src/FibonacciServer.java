//package de.VS.FibonacciServer

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class FibonacciServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/fibonacci", new MyHandler());
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            long input = Long.parseLong(t.getRequestURI().getQuery(), 10);
            long result = fibonacci(input);

            String response = String.valueOf(result);
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static long fibonacci(long val) {
        if (val == 1 || val == 0) {
            return val;
        } else {
            return fibonacci(val - 1) + fibonacci(val - 2);
        }
    }

}