package si.fri.prpo.nakupovanje.servleti;

import si.fri.prpo.nakupovanje.zrna.ApplicationScopedDemo;
import si.fri.prpo.nakupovanje.zrna.RequestScopedDemo;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet("/demo")
public class DemoServlet extends HttpServlet {

    @Inject
    private ApplicationScopedDemo as;

    @Inject
    private RequestScopedDemo rs;

    private static final Logger log = Logger.getLogger(DemoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        out.print("<h1>Demo</h1>");

        as.pridobiUporabnike();
        rs.pridobiUporabnike();

        out.print("Glej konzolo");
    }
}
