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

    public List<Artikel> pridobiArtikleNakupovalnegaSeznama(Long seznamId) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.pridobiNakupovalniSeznam(seznamId);

        if (nakupovalniSeznam == null) {
            log.info("Nakupovalni seznam ne obstaja. Artikle nemorem pridobiti.");
            return null;
        }

        return nakupovalniSeznam.getArtikli();

    }

    public Artikel pridobiArtikel(Long artikelId) {

        Artikel artikel = artikelZrno.pridobiArtikel(artikelId);

        if(artikel == null) {
            log.info("Artikel z id = " + artikelId + " ne obstaja. Nemorem ga pridobiti.");
            return null;
        }

        return artikel;

    }

    @BeleziKlice
    public Artikel ustvariArtikel(ArtikelDto artikelDto) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.pridobiNakupovalniSeznam(artikelDto.getSeznamId());

        if (nakupovalniSeznam == null) {
            log.info("Nakupovalni seznam ne obstaja. Nemorem ustvariti artikla.");
            return null;
        }

        Artikel artikel = new Artikel();

        artikel.setNakupovalniSeznam(nakupovalniSeznam);
        artikel.setImeArtikla(artikelDto.getImeArtikla());

        return artikelZrno.insertArtikel(artikel);

    }

    public Artikel posodobiArtikel(Long artikelId, ArtikelDto artikelDto) {

        Artikel artikel = pridobiArtikel(artikelId);

        if (artikel == null) {
            log.info("Artikel z id = " + artikelId + " ne obstaja. Nemorem ga posodobiti.");
            return null;
        }

        artikel.setImeArtikla(artikelDto.getImeArtikla());
        artikelZrno.updateArtikel(artikelId, artikel);

        return artikel;

    }

    public Artikel odstraniArtikel(Long artikelId) {

        Artikel artikel = pridobiArtikel(artikelId);

        if (artikel == null) {
            log.info("Artikel z id = " + artikelId + " ne obstaja. Nemorem ga izbrisati.");
            return null;
        }

        artikelZrno.deleteArtikel(artikelId);

        return artikel;

    }
}
