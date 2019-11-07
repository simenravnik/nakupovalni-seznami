package si.fri.prpo.nakupovanje.zrna;

import si.fri.prpo.nakupovanje.entitete.Uporabnik;

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
public class UporabnikiZrno {

    private Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    public List<Uporabnik> pridobiUporabnike() {
        List<Uporabnik> uporabniki = em.createNamedQuery("Uporabnik.getAll").getResultList();

        return uporabniki;
    }

    public List<Uporabnik> pridobiUporabnikeCriteria() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Uporabnik> cq =  cb.createQuery(Uporabnik.class);
        Root<Uporabnik> uporabnik = cq.from(Uporabnik.class);
        cq.select(uporabnik);
        TypedQuery<Uporabnik> q = em.createQuery(cq);
        List<Uporabnik> uporabniki = q.getResultList();
        return uporabniki;
    }
}
