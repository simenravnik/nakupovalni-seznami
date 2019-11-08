package si.fri.prpo.nakupovanje.zrna;

import si.fri.prpo.nakupovanje.entitete.NakupovalniSeznam;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class SeznamiZrno {

    private Logger log = Logger.getLogger(SeznamiZrno.class.getName());

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    public List<NakupovalniSeznam> pridobiSezname() {
        log.info("Pridobivam sezname.");
        List<NakupovalniSeznam> seznami = em.createNamedQuery("NakupovalniSeznam.getAll").getResultList();
        return seznami;
    }
}
