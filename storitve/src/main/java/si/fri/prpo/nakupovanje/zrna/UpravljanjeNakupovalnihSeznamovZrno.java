package si.fri.prpo.nakupovanje.zrna;

import si.fri.prpo.nakupovanje.dto.NakupovalniSeznamDto;
import si.fri.prpo.nakupovanje.dto.UporabnikDto;
import si.fri.prpo.nakupovanje.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovanje.entitete.Uporabnik;

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

            log.info("Uporabnik ne obstaja. Ne morem vrniti nakupovalnega seznama.");
            return null;

        }

        return nakupovalniSeznamZrno.pridobiNakupovalneSeznameUporabnika(uporabnik);

    }

    public NakupovalniSeznam pridobiNakupovalniSeznamUporabnika(Long uporabnikId, Integer seznamId) {

        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(uporabnikId);

        if (uporabnik == null) {

            log.info("Uporabnik ne obstaja. Ne morem vrniti nakupovalnega seznama.");
            return null;

        }

        if (uporabnik.getNakupovalniSeznami().size() < seznamId || seznamId < 1) {

            log.info("Nakupovalni seznam " + seznamId +  " uporabnika " + uporabnik.getIme() + " ne obstaja. Ne morem odstraniti nakupovalnega seznama.");
            return null;

        }

        NakupovalniSeznam nakupovalniSeznam = uporabnik.getNakupovalniSeznami().get(seznamId-1);

        return nakupovalniSeznam;

    }

    public NakupovalniSeznam posodobiNakupovalniSeznam(Long uporabnikId, Integer seznamId, NakupovalniSeznamDto nakupovalniSeznamDto) {

        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(uporabnikId);

        if (uporabnik == null) {

            log.info("Uporabnik ne obstaja. Ne morem ustvariti novega nakupovalnega seznama.");
            return null;

        }

        if (uporabnik.getNakupovalniSeznami().size() < seznamId || seznamId < 1) {

            log.info("Nakupovalni seznam " + nakupovalniSeznamDto.getNaziv() + " uporabnika " + uporabnik.getIme() + " ne obstaja. Ne morem posodobiti nakupovalnega seznama.");
            return null;

        }

        NakupovalniSeznam nakupovalniSeznam = uporabnik.getNakupovalniSeznami().get(seznamId-1);

        nakupovalniSeznam.setNaziv(nakupovalniSeznamDto.getNaziv());
        nakupovalniSeznam.setOpis(nakupovalniSeznamDto.getOpis());

        nakupovalniSeznamZrno.updateNakupovalniSeznam(nakupovalniSeznam.getId(), nakupovalniSeznam);

        return nakupovalniSeznam;

    }

    public NakupovalniSeznam odstraniNakupovalniSeznam(Long uporabnikId, Integer seznamId) {

        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(uporabnikId);

        if (uporabnik == null) {

            log.info("Uporabnik ne obstaja. Ne morem ustvariti novega nakupovalnega seznama.");
            return null;

        }

        if (uporabnik.getNakupovalniSeznami().size() < seznamId || seznamId < 1) {

            log.info("Nakupovalni seznam " + seznamId +  " uporabnika " + uporabnik.getIme() + " ne obstaja. Ne morem odstraniti nakupovalnega seznama.");
            return null;

        }

        NakupovalniSeznam nakupovalniSeznam = uporabnik.getNakupovalniSeznami().get(seznamId-1);

        nakupovalniSeznamZrno.deleteNakupovalniSeznam(nakupovalniSeznam.getId());

        return nakupovalniSeznam;

    }

    public Uporabnik ustvariUporabnika(UporabnikDto uporabnikDto) {

        if(uporabnikZrno.pridobiUporabnikaByUsername(uporabnikDto.getUporabniskoIme()) == null) {
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
