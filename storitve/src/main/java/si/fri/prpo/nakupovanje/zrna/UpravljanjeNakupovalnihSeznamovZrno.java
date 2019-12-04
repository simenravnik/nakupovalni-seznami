package si.fri.prpo.nakupovanje.zrna;

import si.fri.prpo.nakupovanje.dto.NakupovalniSeznamDto;
import si.fri.prpo.nakupovanje.dto.UporabnikDto;
import si.fri.prpo.nakupovanje.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovanje.entitete.Uporabnik;
import si.fri.prpo.nakupovanje.izjeme.UporabniskoImeObstajaException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeNakupovalnihSeznamovZrno {

    private Logger log = Logger.getLogger(UpravljanjeNakupovalnihSeznamovZrno.class.getName());

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private NakupovalniSeznamZrno nakupovalniSeznamZrno;


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

    public List<NakupovalniSeznam> pridobiNakupovalniSeznameUporabnika(Long uporabnikId) {

        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(uporabnikId);

        if (uporabnik == null) {

            log.info("Uporabnik ne obstaja. Ne morem vrniti nakupovalnih seznamov.");
            return null;

        }

        return nakupovalniSeznamZrno.pridobiNakupovalneSeznameUporabnika(uporabnik);

    }

    public NakupovalniSeznam pridobiNakupovalniSeznam(Long seznamId) {

       NakupovalniSeznam nakupovalniSeznam = nakupovalniSeznamZrno.pridobiNakupovalniSeznam(seznamId);

       if(nakupovalniSeznam == null) {

           log.info("Seznam z id = " + seznamId + " ne obstaja.");
           return null;

       }

        return nakupovalniSeznam;

    }

    public NakupovalniSeznam posodobiNakupovalniSeznam(Long seznamId, NakupovalniSeznamDto nakupovalniSeznamDto) {

        NakupovalniSeznam nakupovalniSeznam = pridobiNakupovalniSeznam(seznamId);

        if(nakupovalniSeznam == null) {

            log.info("Seznam z id = " + seznamId + " ne obstaja. Nemorem ga posodobiti.");
            return null;
        }

        nakupovalniSeznam.setNaziv(nakupovalniSeznamDto.getNaziv());
        nakupovalniSeznam.setOpis(nakupovalniSeznamDto.getOpis());

        nakupovalniSeznamZrno.updateNakupovalniSeznam(seznamId, nakupovalniSeznam);

        return nakupovalniSeznam;

    }

    public NakupovalniSeznam odstraniNakupovalniSeznam(Long seznamId) {

        NakupovalniSeznam nakupovalniSeznam = nakupovalniSeznamZrno.pridobiNakupovalniSeznam(seznamId);

        if (nakupovalniSeznam == null) {

            log.info("Seznam z id = " + seznamId + " ne obstaja. Nemorem ga izbrisati.");
            return null;

        }

        nakupovalniSeznamZrno.deleteNakupovalniSeznam(seznamId);

        return nakupovalniSeznam;

    }

    public Uporabnik ustvariUporabnika(UporabnikDto uporabnikDto) throws RuntimeException {

        if(uporabnikZrno.pridobiUporabnikaByUsername(uporabnikDto.getUporabniskoIme()).isEmpty()) {
            Uporabnik uporabnik = new Uporabnik();

            uporabnik.setIme(uporabnikDto.getIme());
            uporabnik.setPriimek(uporabnikDto.getPriimek());
            uporabnik.setEmail(uporabnikDto.getEmail());
            uporabnik.setUporabniskoIme(uporabnikDto.getUporabniskoIme());
            uporabnik.setGeslo(uporabnikDto.getGeslo());

            return uporabnikZrno.insertUporabnik(uporabnik);

        } else {
            String msg = "Uporabnik s tem uporabniskim imenom ze obstaja, kreiranje neuspesno.";
            log.info(msg);
            throw new UporabniskoImeObstajaException(msg);
        }

    }

    public Uporabnik posodobiUporabnika(Long uporabnikId, UporabnikDto uporabnikDto) {

        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(uporabnikId);

        if(uporabnik == null) {

            log.info("Uporabnik z id = " + uporabnikId + " ne obstaja. Nemorem ga posodobiti.");
            return null;

        }

        uporabnik.setIme(uporabnikDto.getIme());
        uporabnik.setPriimek(uporabnikDto.getPriimek());
        uporabnik.setEmail(uporabnikDto.getEmail());
        uporabnik.setUporabniskoIme(uporabnikDto.getUporabniskoIme());
        uporabnik.setGeslo(uporabnikDto.getGeslo());

        uporabnikZrno.updateUporabnik(uporabnikId, uporabnik);

        return uporabnik;

    }
}
