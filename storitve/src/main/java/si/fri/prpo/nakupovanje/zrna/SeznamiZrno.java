package si.fri.prpo.nakupovanje.zrna;

import si.fri.prpo.nakupovanje.entitete.NakupovalniSeznam;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class SeznamiZrno {

    private Logger log = Logger.getLogger(SeznamiZrno.class.getName());

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    public List<NakupovalniSeznam> pridobiSezname() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<NakupovalniSeznam> cq =  cb.createQuery(NakupovalniSeznam.class);
        Root<NakupovalniSeznam> rootEntry = cq.from(NakupovalniSeznam.class);
        CriteriaQuery<NakupovalniSeznam> all =  cq.select(rootEntry);
        TypedQuery<NakupovalniSeznam> q = em.createQuery(all);
        List<NakupovalniSeznam> seznami = q.getResultList();
        return seznami;
    }
}
