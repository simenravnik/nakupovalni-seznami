package si.fri.prpo.nakupovanje.api.v1.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.nakupovanje.dto.NakupovalniSeznamDto;
import si.fri.prpo.nakupovanje.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovanje.zrna.NakupovalniSeznamZrno;
import si.fri.prpo.nakupovanje.zrna.UpravljanjeNakupovalnihSeznamovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

@ApplicationScoped
@Path("nakupovalniseznami")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NakupovalniSeznamiVir {

    Logger log = Logger.getLogger(NakupovalniSeznamiVir.class.getName());

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @Inject
    private NakupovalniSeznamZrno nakupovalniSeznamZrno;

    @Context
    protected UriInfo uriInfo;

    @GET
    @Path("uporabniki/{id}")
    public Response pridobiNakupovalneSeznameUporabnika(@PathParam("id") Long uporabnikId) {

        return Response.ok(upravljanjeNakupovalnihSeznamovZrno.pridobiNakupovalniSeznameUporabnika(uporabnikId)).build();

    }

    @GET
    public Response pridobiNakupovalneSezname() {

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long nakupovalniSeznamiCount = nakupovalniSeznamZrno.pridobiNakupovalneSeznameCount(query);

        return Response
                .ok(nakupovalniSeznamZrno.pridobiNakupovalneSezname(query))
                .header("X-Total-Count", nakupovalniSeznamiCount)
                .build();

    }

    @GET
    @Path("{seznamId}/uporabniki/{uporabnikId}")
    public Response pridobiNakupovalniSeznamUporabnika(@PathParam("uporabnikId") Long uporabnikId, @PathParam("seznamId") Integer seznamId) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.pridobiNakupovalniSeznamUporabnika(uporabnikId, seznamId);

        if(nakupovalniSeznam != null) {
            return Response.ok(nakupovalniSeznam).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    public Response dodajNakupovalniSeznamUporabniku(NakupovalniSeznamDto nakupovalniSeznamDto) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.ustvariNakupovalniSeznam(nakupovalniSeznamDto);

        if (nakupovalniSeznam != null) {
            return Response.ok(nakupovalniSeznam).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Path("{seznamId}/uporabniki/{uporabnikId}")
    public Response posodobiNakupovalniSeznamUporabniku(@PathParam("uporabnikId") Long uporabnikId, @PathParam("seznamId") Integer seznamId, NakupovalniSeznamDto nakupovalniSeznamDto) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.posodobiNakupovalniSeznam(uporabnikId, seznamId, nakupovalniSeznamDto);

        if (nakupovalniSeznam != null) {
            return Response.ok(nakupovalniSeznam).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @DELETE
    @Path("{seznamId}/uporabniki/{uporabnikId}")
    public Response odstraniNakupovalniSeznamUporabniku(@PathParam("uporabnikId") Long uporabnikId, @PathParam("seznamId") Integer seznamId) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.odstraniNakupovalniSeznam(uporabnikId, seznamId);

        if (nakupovalniSeznam != null) {
            return Response.ok(nakupovalniSeznam).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
