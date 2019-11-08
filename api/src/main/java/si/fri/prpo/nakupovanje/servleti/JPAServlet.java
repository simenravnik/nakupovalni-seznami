package si.fri.prpo.nakupovanje.servleti;

import si.fri.prpo.nakupovanje.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovanje.entitete.Uporabnik;
import si.fri.prpo.nakupovanje.zrna.ArtikliZrno;
import si.fri.prpo.nakupovanje.zrna.SeznamiZrno;
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

    @Inject
    private ArtikliZrno artikliZrno;

    private static final Logger log = Logger.getLogger(JPAServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        // printing the users
        out.append("<br><h1>Uporabniki:</h1>");
        uporabnikiZrno.pridobiUporabnike().stream().forEach(u -> out.append(u.toString() + "<br><br>"));

        out.append("<br><h1>Uporabniki z Criteria api:</h1>");
        uporabnikiZrno.pridobiUporabnike().stream().forEach(u -> out.append(u.toString() + "<br><br>"));

        // printanje vseh artiklov
        out.append("<br><h1>Artikli:</h1>");
        artikliZrno.pridobiArtikle().stream().forEach(u -> out.append(u.toString() + "<br><br>"));
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
