package si.fri.prpo.nakupovanje.zrna;

import si.fri.prpo.nakupovanje.entitete.NakupovalniSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class NakupovalniSeznamiZrno {

    private Logger log = Logger.getLogger(NakupovalniSeznamiZrno.class.getName());

    @PostConstruct
    private void init() {

        log.info("Inicializacija zrna " + NakupovalniSeznamiZrno.class.getSimpleName());

    }

    @PreDestroy
    private void destroy() {

        log.info("Deinicializacija zrna " + NakupovalniSeznamiZrno.class.getSimpleName());

    }

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    public List<NakupovalniSeznam> pridobiNakupovalneSezname() {

        List<NakupovalniSeznam> seznami = em.createNamedQuery("NakupovalniSeznam.getAll").getResultList();

        return seznami;

    }

    public NakupovalniSeznam pridobiNakupovalniSeznam(Long nakupovalniSeznamId) {

        NakupovalniSeznam nakupovalniSeznam = em.find(NakupovalniSeznam.class, nakupovalniSeznamId);

        return nakupovalniSeznam;

    }

    @Transactional
    public NakupovalniSeznam insertNakupovalniSeznam(NakupovalniSeznam nakupovalniSeznam) {

        if(nakupovalniSeznam != null) {
            em.persist(nakupovalniSeznam);
        }

        return nakupovalniSeznam;

    }

    @Transactional
    public void updateNakupovalniSeznam(Long nakupovalniSeznamId, NakupovalniSeznam nakupovalniSeznam) {

        NakupovalniSeznam ns = em.find(NakupovalniSeznam.class, nakupovalniSeznamId);

        nakupovalniSeznam.setId(ns.getId());
        em.merge(nakupovalniSeznam);

    }

    @Transactional
    public void deleteNakupovalniSeznam(Long nakupovalniSeznamId) {

        NakupovalniSeznam nakupovalniSeznam = em.find(NakupovalniSeznam.class, nakupovalniSeznamId);

        if(nakupovalniSeznam != null) {
            em.remove(nakupovalniSeznam);
        }

    }
}
