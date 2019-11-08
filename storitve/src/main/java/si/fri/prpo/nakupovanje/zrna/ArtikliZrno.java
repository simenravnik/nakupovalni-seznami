package si.fri.prpo.nakupovanje.zrna;

import si.fri.prpo.nakupovanje.entitete.Artikel;

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
public class ArtikliZrno {
    private Logger log = Logger.getLogger(ArtikliZrno.class.getName());

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    public List<Artikel> pridobiArtikle() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Artikel> cq =  cb.createQuery(Artikel.class);
        Root<Artikel> rootEntry = cq.from(Artikel.class);

        CriteriaQuery<Artikel> all = cq.select(rootEntry);
        TypedQuery<Artikel> q = em.createQuery(all);
        List<Artikel> artikli = q.getResultList();

        return artikli;
    }
}