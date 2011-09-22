package test.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class IDSMock extends HttpServlet {

	public void start() throws Exception {
		server = new Server(12345);
		server.setHandler(handler);
		server.start();
	}
	
	public void stop() throws Exception {
//		server.stop();
	}

	Handler handler = new AbstractHandler() {
		@Override
		public void handle(String target, Request request, HttpServletRequest httpRequest, HttpServletResponse response) throws IOException, ServletException {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
//			response.getWriter().println("<h1>Hello</h1>");
			((Request) request).setHandled(true);
		}

	};
	private Server server;

}
