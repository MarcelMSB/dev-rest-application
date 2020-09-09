package dev.marcel.application.web.resource.common.enterprise;

import java.io.*;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

@Provider
public class MyLoggingFilter implements ContainerRequestFilter {
    
    private static final Logger LOGGER = Logger.getLogger(MyLoggingFilter.class);
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.trace("#### RequestURL #### " + requestContext.getUriInfo().getPath());
        LOGGER.trace("#### Intercepted Method #### " + requestContext.getMethod());
      
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = requestContext.getEntityStream();
        final StringBuilder b = new StringBuilder(255);
        try {
            if (in.available() > 0) {
                IOUtils.copy(in, out);

                byte[] requestEntity = out.toByteArray();
                printEntity(b, requestEntity);

                requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
            }
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }
    
    private void printEntity(StringBuilder b, byte[] entity) throws IOException {
        if (entity.length == 0) {
            return;
        }
        b.append(new String(entity)).append("\n");
        LOGGER.trace("#### Intercepted Entity ####");
        LOGGER.trace(b.toString());
    }
}
