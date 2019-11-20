package si.fri.prpo.nakupovanje.api.v1.resources;

import si.fri.prpo.nakupovanje.zrna.UporabnikZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("uporabniki")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UporabnikiVir {

    @Inject
    private UporabnikZrno uporabnikZrno;

    @GET
    public Response pridobiUporabnike() {
        return Response.ok(uporabnikZrno.pridobiUporabnike()).build();
    }
}
