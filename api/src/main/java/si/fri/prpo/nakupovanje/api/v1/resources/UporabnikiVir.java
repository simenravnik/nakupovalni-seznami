package si.fri.prpo.nakupovanje.api.v1.resources;

import si.fri.prpo.nakupovanje.entitete.Uporabnik;
import si.fri.prpo.nakupovanje.zrna.UporabnikZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
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

    @GET
    @Path("{id}")
    public Response pridobiUporabnika(@PathParam("id") Long id) {

        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(id);

        if(uporabnik != null) {
            return Response.ok(uporabnik).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    public Response dodajUporabnika(Uporabnik uporabnik) {

        return Response.status(Response.Status.CREATED)
                .entity(uporabnikZrno.insertUporabnik(uporabnik))
                .build();

    }

    @PUT
    @Path("{id}")
    public Response posodobiUporabnika(@PathParam("id") Long id, Uporabnik uporabnik) {

        if (uporabnikZrno.pridobiUporabnika(id) != null) {
            uporabnikZrno.updateUporabnik(id, uporabnik);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @DELETE
    @Path("{id}")
    public Response odstraniUporabnika(@PathParam("id") Long id) {

        if (uporabnikZrno.pridobiUporabnika(id) != null) {
            uporabnikZrno.deleteUporabnik(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

}
