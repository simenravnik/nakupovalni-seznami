package si.fri.prpo.nakupovanje.servleti;

import si.fri.prpo.nakupovanje.entitete.Uporabnik;
import si.fri.prpo.nakupovanje.zrna.UporabnikiZrno;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    private static final Logger log = Logger.getLogger(JPAServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        // printing the users
        out.append("<br><h1>Uporabniki:</h1>");
        List<Uporabnik> uporabniki = uporabnikiZrno.pridobiUporabnike();
        izpisi(out, uporabniki);

        out.append("<br><h1>Uporabniki z Criteria api:</h1>");
        List<Uporabnik> uporabnikiCriteria = uporabnikiZrno.pridobiUporabnikiCriteria();
        izpisi(out, uporabnikiCriteria);

    }

    public static void izpisi(PrintWriter out, List<Uporabnik> uporabniki) {
        uporabniki.forEach(u -> {
            out.append("Ime: " + u.getIme() + "<br>");
            out.append("Priimek: " + u.getPriimek() + "<br>");
            out.append("Uporabnisko ime: " + u.getUporabniskoIme() + "<br>");
            out.append("Email: " + u.getEmail() + "<br>");
            out.append("<br>");
        });
    }

}
