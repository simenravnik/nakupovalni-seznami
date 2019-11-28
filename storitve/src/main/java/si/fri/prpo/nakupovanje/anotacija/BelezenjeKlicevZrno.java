package si.fri.prpo.nakupovanje.anotacija;

import si.fri.prpo.nakupovanje.zrna.UporabnikZrno;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

@ApplicationScoped
public class BelezenjeKlicevZrno {
    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    private void init() {

        log.info("Inicializacija zrna " + UporabnikZrno.class.getSimpleName());

    }

    @PreDestroy
    private void destroy() {

        log.info("Deinicializacija zrna " + UporabnikZrno.class.getSimpleName());

    }

    private int numberOfCalls = 0;

    public void povecaj() {
        numberOfCalls++;
        log.info("Nova vrednost beleznika je " + numberOfCalls);
    }
}
