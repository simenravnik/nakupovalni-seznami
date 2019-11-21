package si.fri.prpo.nakupovanje.api.v1.resources;

import si.fri.prpo.nakupovanje.dto.NakupovalniSeznamDto;
import si.fri.prpo.nakupovanje.zrna.UpravljanjeNakupovalnihSeznamovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@ApplicationScoped
@Path("nakupovalniseznami")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NakupovalniSeznamiVir {

    Logger log = Logger.getLogger(NakupovalniSeznamiVir.class.getName());

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @GET
    @Path("uporabnik/{id}")
    public Response pridobiNakupovalneSeznameUporabnika(@PathParam("id") Long uporabnikId) {

        return Response.ok(upravljanjeNakupovalnihSeznamovZrno.pridobiNakupovalniSeznameUporabnika(uporabnikId)).build();

    }

    @POST
    public Response dodajNakupovalniSeznamUporabniku(NakupovalniSeznamDto nakupovalniSeznamDto) {

        return Response.ok(upravljanjeNakupovalnihSeznamovZrno.ustvariNakupovalniSeznam(nakupovalniSeznamDto)).build();

    }
}
