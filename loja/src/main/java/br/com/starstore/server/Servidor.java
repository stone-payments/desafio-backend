package br.com.starstore.server;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

	private static HttpServer server;

	public static void iniciarServidor() {
		ResourceConfig config = new ResourceConfig().packages("br.com.starstore.controller");
		URI uri = URI.create("http://localhost:8080/");
		server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
	}
	
	public static void pararServidor() throws Exception {
		if(server != null) { 
			server.stop(); 
			server = null;
		}	
		else throw new Exception("Servidor off!");
	}

	public static void main(String[] args) throws IOException {
		iniciarServidor();
		System.out.println("Servidor iniciado: Aperte ENTER para parar.");
		System.in.read();
		try {
			pararServidor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
