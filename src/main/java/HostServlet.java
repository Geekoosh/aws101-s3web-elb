import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        URL url = new URL("http://169.254.169.254/latest/meta-data/hostname");
        URLConnection conn = url.openConnection();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            resp.getWriter().println(inputLine);
        }
        br.close();

        resp.getWriter().flush();
    }
}
