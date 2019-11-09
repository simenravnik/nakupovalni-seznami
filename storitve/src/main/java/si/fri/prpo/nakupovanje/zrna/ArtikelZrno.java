package si.fri.prpo.nakupovanje.zrna;

import si.fri.prpo.nakupovanje.entitete.Artikel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}