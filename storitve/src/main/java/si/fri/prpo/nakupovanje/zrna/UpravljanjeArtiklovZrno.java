package si.fri.prpo.nakupovanje.zrna;

import si.fri.prpo.nakupovanje.anotacije.BeleziKlice;
import si.fri.prpo.nakupovanje.dto.ArtikelDto;
import si.fri.prpo.nakupovanje.entitete.Artikel;
import si.fri.prpo.nakupovanje.entitete.NakupovalniSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeArtiklovZrno {

    private Logger log = Logger.getLogger(UpravljanjeArtiklovZrno.class.getName());

    @Inject
    private ArtikelZrno artikelZrno;

    @Inject UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @PostConstruct
    private void init() {

        log.info("Inicializacija zrna " + UpravljanjeArtiklovZrno.class.getSimpleName());

    }

    @PreDestroy
    private void destroy() {

        log.info("Deinicializacija zrna " + UpravljanjeArtiklovZrno.class.getSimpleName());

    }

    public List<Artikel> pridobiArtikleNakupovalnegaSeznama(Long uporabnikId, Integer seznamId) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.pridobiNakupovalniSeznamUporabnika(uporabnikId, seznamId);

        if (nakupovalniSeznam == null) {
            log.info("Nakupovalni seznam ne obstaja");
            return null;
        }

        return nakupovalniSeznam.getArtikli();

    }

    public Artikel pridobiArtikelNakupovalnegaSeznama(Long uporabnikId, Integer seznamId, Integer artikelId) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.pridobiNakupovalniSeznamUporabnika(uporabnikId, seznamId);

        if (nakupovalniSeznam == null) {
            log.info("Nakupovalni seznam ne obstaja");
            return null;
        }

        List<Artikel> artikli =  nakupovalniSeznam.getArtikli();

        if (artikli.size() < artikelId || artikelId < 1) {
            log.info("Artikel " + artikelId +  " uporabnika " + nakupovalniSeznam.getUporabnik().getIme() + " ne obstaja. Ne morem vrniti artikla.");
            return null;
        }

        return artikli.get(artikelId-1);

    }

    @BeleziKlice
    public Artikel ustvariArtikel(Long uporabnikId, ArtikelDto artikelDto) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.pridobiNakupovalniSeznamUporabnika(uporabnikId, artikelDto.getSeznamId());

        if (nakupovalniSeznam == null) {
            log.info("Nakupovalni seznam ne obstaja");
            return null;
        }

        Artikel artikel = new Artikel();

        artikel.setNakupovalniSeznam(nakupovalniSeznam);
        artikel.setImeArtikla(artikelDto.getImeArtikla());

        return artikelZrno.insertArtikel(artikel);

    }

    public Artikel posodobiArtikel(Integer artikelId, Long uporabnikId, ArtikelDto artikelDto) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.pridobiNakupovalniSeznamUporabnika(uporabnikId, artikelDto.getSeznamId());

        if (nakupovalniSeznam == null) {
            log.info("Nakupovalni seznam ne obstaja");
            return null;
        }

        Artikel artikel = pridobiArtikelNakupovalnegaSeznama(uporabnikId, artikelDto.getSeznamId(), artikelId);

        if (artikel == null) {
            log.info("Artikel " + artikelId + ", nakupovalnega seznama " + artikelDto.getSeznamId() +  " ne obstaja.");
            return null;
        }

        artikel.setNakupovalniSeznam(nakupovalniSeznam);
        artikel.setImeArtikla(artikelDto.getImeArtikla());

        artikelZrno.updateArtikel(artikel.getId(), artikel);

        return artikel;

    }

    public Artikel odstraniArtikel(Integer artikelId, Long uporabnikId, Integer seznamId) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.pridobiNakupovalniSeznamUporabnika(uporabnikId, seznamId);

        if (nakupovalniSeznam == null) {
            log.info("Nakupovalni seznam ne obstaja");
            return null;
        }

        Artikel artikel = pridobiArtikelNakupovalnegaSeznama(uporabnikId, seznamId, artikelId);

        if (artikel == null) {
            log.info("Artikel " + artikelId + ", nakupovalnega seznama " + seznamId +  " ne obstaja.");
            return null;
        }

        artikelZrno.deleteArtikel(artikel.getId());

        return artikel;

    }

}
