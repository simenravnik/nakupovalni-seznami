package si.fri.prpo.nakupovanje.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.nakupovanje.entitete.Artikel;
import si.fri.prpo.nakupovanje.entitete.NakupovalniSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

    @Inject
    private NakupovalniSeznamZrno nakupovalniSeznamZrno;

    public List<Artikel> pridobiArtikle() {

        List<Artikel> artikli = em.createNamedQuery("Artikel.getAll").getResultList();

        return artikli;

    }

    public List<Artikel> pridobiArtikle(QueryParameters query) {

        return JPAUtils.queryEntities(em, Artikel.class, query);

    }

    public Long pridobiArtikleCount(QueryParameters query) {

        return JPAUtils.queryEntitiesCount(em, Artikel.class, query);

    }

    public List<Artikel> pridobiArtikleSeznama(NakupovalniSeznam seznam) {

        List<Artikel> artikli = em.createNamedQuery("Artikel.getArtikelForNakupovalniSeznam").setParameter("nakupovalniSeznam", seznam).getResultList();

        return artikli;

    }

    public Artikel pridobiArtikel(Long artikelId) {

        Artikel artikel = em.find(Artikel.class, artikelId);

        return artikel;

    }

    @Transactional
    public Artikel insertArtikel(Artikel artikel) {

        if(artikel != null) {

            NakupovalniSeznam nakupovalniSeznam = nakupovalniSeznamZrno.pridobiNakupovalniSeznam(artikel.getNakupovalniSeznam().getId());
            nakupovalniSeznam.addArtikel(artikel); // updating list of nakupovalniSeznam

            em.persist(artikel);
        }

        return artikel;

    }

    @Transactional
    public void updateArtikel(Long artikelId, Artikel artikel) {

        Artikel a = em.find(Artikel.class, artikelId);

        NakupovalniSeznam nakupovalniSeznam = nakupovalniSeznamZrno.pridobiNakupovalniSeznam(a.getNakupovalniSeznam().getId());
        nakupovalniSeznam.updateArtikel(artikelId, a); // updating list of nakupovalniSeznam

        artikel.setId(a.getId());
        em.merge(artikel);

    }

    @Transactional
    public void deleteArtikel(Long artikelId) {

        Artikel artikel = em.find(Artikel.class, artikelId);

        NakupovalniSeznam nakupovalniSeznam = nakupovalniSeznamZrno.pridobiNakupovalniSeznam(artikel.getNakupovalniSeznam().getId());
        nakupovalniSeznam.removeArtikel(artikel); // updating list of nakupovalniSeznam

        if(artikel != null) {
            em.remove(artikel);
        }

    }
}