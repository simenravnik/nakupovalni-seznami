package si.fri.prpo.nakupovanje.zrna;

import si.fri.prpo.nakupovanje.entitete.Artikel;
import si.fri.prpo.nakupovanje.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovanje.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeNakupovalnihSeznamovZrno {

    private Logger log = Logger.getLogger(UpravljanjeNakupovalnihSeznamovZrno.class.getName());

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private NakupovalniSeznamZrno nakupovalniSeznamZrno;

    @Inject
    private ArtikelZrno artikelZrno;

    @PostConstruct
    private void init() {

        log.info("Inicializacija zrna " + UpravljanjeNakupovalnihSeznamovZrno.class.getSimpleName());

    }

    @PreDestroy
    private void destroy() {

        log.info("Deinicializacija zrna " + UpravljanjeNakupovalnihSeznamovZrno.class.getSimpleName());

    }

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    public NakupovalniSeznam ustvariNakupovalniSeznam(NakupovalniSeznamDto nakupovalniSeznamDto) {

        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(nakupovalniSeznamDto.getUporabnikId());

        if (uporabnik == null) {

            log.info("Uporabnik ne obstaja. Ne morem ustvariti novega nakupovalnega seznama.");
            return null;

        }

        NakupovalniSeznam nakupovalniSeznam = new NakupovalniSeznam();

        nakupovalniSeznam.setUporabnik(uporabnik);
        nakupovalniSeznam.setNaziv(nakupovalniSeznamDto.getNaziv());
        nakupovalniSeznam.setOpis(nakupovalniSeznamDto.getOpis());
        nakupovalniSeznam.setUstvarjen(Instant.now());

        return nakupovalniSeznamZrno.insertNakupovalniSeznam(nakupovalniSeznam);

    }

    public Artikel ustvariArtikel(ArtikelDto artikelDto) {

        NakupovalniSeznam nakupovalniSeznam = nakupovalniSeznamZrno.pridobiNakupovalniSeznam(artikelDto.getSeznamId());

        if (nakupovalniSeznam == null) {

            log.info("Nakupovalni seznam ne obstaja. Ne morem ustvariti novega artikla.");
            return null;

        }

        Artikel artikel = new Artikel();

        artikel.setNakupovalniSeznam(nakupovalniSeznam);
        artikel.setImeArtikla(artikelDto.getImeArtikla());

        return artikelZrno.insertArtikel(artikel);

    }

    public Uporabnik ustvariUporabnika(UporabnikDto uporabnikDto) {

        if(uporabnikZrno.pridobiUporabnikaByUsername(uporabnikDto.getUporabniskoIme()).isEmpty()) {
            Uporabnik uporabnik = new Uporabnik();

            uporabnik.setIme(uporabnikDto.getIme());
            uporabnik.setPriimek(uporabnikDto.getPriimek());
            uporabnik.setEmail(uporabnikDto.getEmail());
            uporabnik.setUporabniskoIme(uporabnikDto.getUporabniskoIme());
            uporabnik.setGeslo(uporabnikDto.getGeslo());

            return uporabnikZrno.insertUporabnik(uporabnik);

        } else {
            log.info("Uporabnik s tem uporabniskim imenom ze obstaja, kreiranje neuspesno.");
            return null;
        }

    }
}
