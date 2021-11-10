
import java.beans.XMLDecoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.Format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
 
public class HelloWorld extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {
        StringWriter out = new StringWriter();
        if (request.getMethod() == "POST") {
            String text = request.getParameter("xml");
            InputStream xml = new ByteArrayInputStream(request.getParameter("xml").getBytes());
            XMLDecoder decoder = new XMLDecoder(xml); 
            decoder.readObject();
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            response.getWriter().println("<h1>Hello Post!" + text + "</h1>");
        } else {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>Hello World</h1>");
        }
    }
 
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new HelloWorld());
 
        server.start();
        server.join();
    }
}