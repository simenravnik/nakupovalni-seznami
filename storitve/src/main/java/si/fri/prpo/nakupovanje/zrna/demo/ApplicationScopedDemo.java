package si.fri.prpo.nakupovanje.zrna.demo;

import si.fri.prpo.nakupovanje.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class ApplicationScopedDemo {


    Logger log = Logger.getLogger(ApplicationScopedDemo.class.getName());

    private UUID id;

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    @PostConstruct
    private void init() {

        id = UUID.randomUUID();

        log.info("Zaganjam zrno z id: " + id.toString());

    }

    @PreDestroy
    private void destroy() {

        log.info("Unicujem zrno z id: " + id.toString());

    }

    public List<Uporabnik> pridobiUporabnike() {

        List<Uporabnik> uporabniki = em.createNamedQuery("Uporabnik.getAll").getResultList();

        return uporabniki;

    }
}
