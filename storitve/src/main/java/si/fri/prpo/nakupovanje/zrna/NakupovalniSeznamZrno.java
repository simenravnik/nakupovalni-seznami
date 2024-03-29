package si.fri.prpo.nakupovanje.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.nakupovanje.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovanje.entitete.Uporabnik;

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
public class NakupovalniSeznamZrno {

    private Logger log = Logger.getLogger(NakupovalniSeznamZrno.class.getName());

    @PostConstruct
    private void init() {

        log.info("Inicializacija zrna " + NakupovalniSeznamZrno.class.getSimpleName());

    }

    @PreDestroy
    private void destroy() {

        log.info("Deinicializacija zrna " + NakupovalniSeznamZrno.class.getSimpleName());

    }

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    @Inject
    private UporabnikZrno uporabnikZrno;

    public List<NakupovalniSeznam> pridobiNakupovalneSezname() {

        List<NakupovalniSeznam> seznami = em.createNamedQuery("NakupovalniSeznam.getAll").getResultList();

        return seznami;

    }

    public List<NakupovalniSeznam> pridobiNakupovalneSezname(QueryParameters query) {

        return JPAUtils.queryEntities(em, NakupovalniSeznam.class, query);

    }

    public Long pridobiNakupovalneSeznameCount(QueryParameters query) {

        return JPAUtils.queryEntitiesCount(em, NakupovalniSeznam.class, query);

    }

    public NakupovalniSeznam pridobiNakupovalniSeznam(Long nakupovalniSeznamId) {

        NakupovalniSeznam nakupovalniSeznam = em.find(NakupovalniSeznam.class, nakupovalniSeznamId);

        return nakupovalniSeznam;

    }

    // return all shopping lists from a specific user
    public List<NakupovalniSeznam> pridobiNakupovalneSeznameUporabnika(Uporabnik uporabnik) {

        List<NakupovalniSeznam> nakupovalniSeznami = em.createNamedQuery("NakupovalniSeznam.getSeznamForUporabnik").setParameter("uporabnik", uporabnik).getResultList();

        return nakupovalniSeznami;

    }

    @Transactional
    public NakupovalniSeznam insertNakupovalniSeznam(NakupovalniSeznam nakupovalniSeznam) {

        if(nakupovalniSeznam != null) {

            Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(nakupovalniSeznam.getUporabnik().getId());
            uporabnik.addNakupovalniSeznam(nakupovalniSeznam);  // updating list of uporabnik

            em.persist(nakupovalniSeznam);
        }

        return nakupovalniSeznam;

    }

    @Transactional
    public void updateNakupovalniSeznam(Long nakupovalniSeznamId, NakupovalniSeznam nakupovalniSeznam) {

        NakupovalniSeznam ns = em.find(NakupovalniSeznam.class, nakupovalniSeznamId);

        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(ns.getUporabnik().getId());
        uporabnik.updateNakupovalniSeznam(nakupovalniSeznamId, ns);

        nakupovalniSeznam.setId(ns.getId());
        em.merge(nakupovalniSeznam);

    }

    @Transactional
    public void deleteNakupovalniSeznam(Long nakupovalniSeznamId) {

        NakupovalniSeznam nakupovalniSeznam = em.find(NakupovalniSeznam.class, nakupovalniSeznamId);

        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(nakupovalniSeznam.getUporabnik().getId());
        uporabnik.removeNakupovalniSeznam(nakupovalniSeznam);

        if(nakupovalniSeznam != null) {
            em.remove(nakupovalniSeznam);
        }

    }
}
