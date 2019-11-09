package si.fri.prpo.nakupovanje.zrna;

import si.fri.prpo.nakupovanje.entitete.Artikel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ArtikelZrno {

    private Logger log = Logger.getLogger(ArtikelZrno.class.getName());

    @PostConstruct
    private void init() {

        log.info("Inicializacija zrna " + ArtikelZrno.class.getSimpleName());

    }

    @PreDestroy
    private void destroy() {

        log.info("Deinicializacija zrna " + ArtikelZrno.class.getSimpleName());

    }

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    public List<Artikel> pridobiArtikle() {

        List<Artikel> artikli = em.createNamedQuery("Artikel.getAll").getResultList();

        return artikli;

    }

    public Artikel pridobiArtikel(Long artikelId) {

        Artikel artikel = em.find(Artikel.class, artikelId);

        return artikel;

    }

    @Transactional
    public Artikel insertArtikel(Artikel artikel) {

        if(artikel != null) {
            em.persist(artikel);
        }

        return artikel;

    }

    @Transactional
    public void updateArtikel(Long artikelId, Artikel artikel) {

        Artikel a = em.find(Artikel.class, artikelId);

        artikel.setId(a.getId());
        em.merge(artikel);

    }

    @Transactional
    public void deleteArtikel(Long artikelId) {

        Artikel artikel = em.find(Artikel.class, artikelId);

        if(artikel != null) {
            em.remove(artikel);
        }

    }
}