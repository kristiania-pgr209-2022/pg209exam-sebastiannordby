package no.kristiania.messenger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MessengerServer {
    private static final Logger logger = LoggerFactory.getLogger(MessengerServer.class);
    private final Server server;

    public MessengerServer(int port) throws IOException {
        this.server = new Server(port);

        var webContext = new WebAppContext();
        webContext.setContextPath("/");
        var baseResource = org.eclipse.jetty.util.resource.Resource.newClassPathResource("/webapp");
        org.eclipse.jetty.util.resource.Resource sourceResource = getSourceResource(baseResource);

        if (sourceResource != null) {
            webContext.setBaseResource(sourceResource);
            webContext.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            webContext.setBaseResource(baseResource);
        }

        var servlet = new ServletHolder(new ServletContainer(
                new MessengerServerConfig()
        ));

        servlet.setInitOrder(0);

        webContext.addServlet(servlet, "/api/*");
        server.setHandler(webContext);
    }

    public void start() throws Exception {
        logger.info("Starting server..");
        server.start();
        logger.info("Server started at: ", server.getURI());
    }

    public void stop() throws Exception {
        logger.info("Stopping server..");
        server.stop();
        logger.info("Server stopped..");
    }

    public URL getUrl() throws MalformedURLException {
        return server.getURI().toURL();
    }

    private org.eclipse.jetty.util.resource.Resource getSourceResource(org.eclipse.jetty.util.resource.Resource baseResource) throws IOException {
        var file = baseResource.getFile();
        if (file == null) {
            return null;
        }
        var sourceDirectory = new File(file.toString()
                .replace('\\', '/')
                .replace("target/classes", "src/main/resources"));
        if (sourceDirectory.exists()) {
            return org.eclipse.jetty.util.resource.Resource.newResource(sourceDirectory);
        } else {
            return null;
        }
    }
}
