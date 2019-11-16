package si.fri.prpo.nakupovanje.servleti;

import si.fri.prpo.nakupovanje.dto.ArtikelDto;
import si.fri.prpo.nakupovanje.dto.NakupovalniSeznamDto;
import si.fri.prpo.nakupovanje.dto.UporabnikDto;
import si.fri.prpo.nakupovanje.entitete.Artikel;
import si.fri.prpo.nakupovanje.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovanje.zrna.*;
import si.fri.prpo.nakupovanje.entitete.Uporabnik;

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
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeSeznamov;

    @Inject
    private UporabnikZrno uporabnikiZrno;

    @Inject
    private NakupovalniSeznamZrno nakupovalniSeznamZrno;

    @Inject
    private ArtikelZrno artikelZrno;

    private static final Logger log = Logger.getLogger(JPAServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        out.print("<h1>Nakupovalni seznami</h1>");

        // Creating shopping list for user
        NakupovalniSeznamDto nakupovalniSeznamDto = new NakupovalniSeznamDto(1L, "Fasunga", "Prinesi iz trgovine.");
        upravljanjeSeznamov.ustvariNakupovalniSeznam(nakupovalniSeznamDto);

        // Creating product for shopping list
        ArtikelDto artikelDto = new ArtikelDto(2L, "RTX 2070");
        upravljanjeSeznamov.ustvariArtikel(artikelDto);

        // Creating new user
        UporabnikDto uporabnikDto = new UporabnikDto("Mirko","Dostojevski","mirko.dosto12@hot.com","mirko13","noneofyourbusiness");
        upravljanjeSeznamov.ustvariUporabnika(uporabnikDto);

        // Creating shopping list for Mirko
        NakupovalniSeznamDto nakupovalniSeznamDto2 = new NakupovalniSeznamDto(3L, "Mirko Stuff", "nevem.");
        upravljanjeSeznamov.ustvariNakupovalniSeznam(nakupovalniSeznamDto2);

        printDataBase(resp);
    }

    private void printDataBase(HttpServletResponse resp) throws IOException{
        PrintWriter out = resp.getWriter();
        // print all users
        List<Uporabnik> uporabniki = uporabnikiZrno.pridobiUporabnike();
        uporabniki.forEach(u -> {
            out.append(u.toString());

            // with all shopping lists
            List<NakupovalniSeznam> seznami = nakupovalniSeznamZrno.pridobiNakupovalneSeznameUporabnika(u);
            seznami.forEach(s -> {
                out.append(s.toString());

                // with all products
                List<Artikel> artikli = artikelZrno.pridobiArtikleSeznama(s);
                artikli.forEach(a -> {
                    out.append(a.toString());
                });
            });

            out.append("<br>");
        });
    }
}
