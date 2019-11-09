package si.fri.prpo.nakupovanje.zrna;

import si.fri.prpo.nakupovanje.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UporabnikZrno {

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    private void init() {

        log.info("Inicializacija zrna " + UporabnikZrno.class.getSimpleName());

    }

    @PreDestroy
    private void destroy() {

        log.info("Deinicializacija zrna " + UporabnikZrno.class.getSimpleName());

    }

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    public List<Uporabnik> pridobiUporabnike() {

        List<Uporabnik> uporabniki = em.createNamedQuery("Uporabnik.getAll").getResultList();

        return uporabniki;

    }

    public List<Uporabnik> pridobiUporabnikeCriteria() {

        log.info("Pridobivam uporabnike s Criteria API.");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Uporabnik> cq =  cb.createQuery(Uporabnik.class);
        Root<Uporabnik> uporabnik = cq.from(Uporabnik.class);
        cq.select(uporabnik);
        TypedQuery<Uporabnik> q = em.createQuery(cq);
        List<Uporabnik> uporabniki = q.getResultList();

        return uporabniki;
    }

    public Uporabnik pridobiUporabnika(Long uporabnikId) {

        Uporabnik uporabnik = em.find(Uporabnik.class, uporabnikId);

        return uporabnik;

    }

    @Transactional
    public Uporabnik insertUporabnik(Uporabnik uporabnik) {

        if (uporabnik != null) {
            em.persist(uporabnik);
        }

        return uporabnik;

    }

    @Transactional
    public void updateUporabnik(Long uporabnikId, Uporabnik uporabnik) {

        Uporabnik u = em.find(Uporabnik.class, uporabnikId);

        uporabnik.setId(u.getId());
        em.merge(uporabnik);

    }

    @Transactional
    public void deleteUporabnik(Long uporabnikId) {

        Uporabnik uporabnik = em.find(Uporabnik.class, uporabnikId);

        if (uporabnik != null) {
            em.remove(uporabnik);
        }

    }
}
