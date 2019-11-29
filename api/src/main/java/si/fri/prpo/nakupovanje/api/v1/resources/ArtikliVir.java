package si.fri.prpo.nakupovanje.api.v1.resources;

import si.fri.prpo.nakupovanje.dto.ArtikelDto;
import si.fri.prpo.nakupovanje.entitete.Artikel;
import si.fri.prpo.nakupovanje.zrna.UpravljanjeNakupovalnihSeznamovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("artikli")
public class ArtikliVir {

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @GET
    @Path("/uporabniki/{uporabnikId}/seznami/{seznamId}")
    public Response pridobiArtikleNakupovalnegaSeznama(@PathParam("uporabnikId") Long uporabnikId, @PathParam("seznamId") Integer seznamId) {

        return Response.ok(upravljanjeNakupovalnihSeznamovZrno.pridobiArtikleNakupovalnegaSeznama(uporabnikId, seznamId)).build();

    }

    @GET
    @Path("/{artikelId}/uporabniki/{uporabnikId}/seznami/{seznamId}/")
    public Response pridobiArtikleNakupovalnegaSeznama(@PathParam("artikelId") Integer artikelId, @PathParam("uporabnikId") Long uporabnikId, @PathParam("seznamId") Integer seznamId) {

        Artikel artikel = upravljanjeNakupovalnihSeznamovZrno.pridobiArtikelNakupovalnegaSeznama(uporabnikId, seznamId, artikelId);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    @Path("/uporabniki/{uporabnikId}")
    public Response dodajArtikelNakupovalnemuSeznamuUporabnika(@PathParam("uporabnikId") Long uporabnikId, ArtikelDto artikelDto) {

        Artikel artikel = upravljanjeNakupovalnihSeznamovZrno.ustvariArtikel(uporabnikId, artikelDto);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }

    }

    @PUT
    @Path("/{artikelId}/uporabniki/{uporabnikId}/")
    public Response posodobiArtikelUporabniku(@PathParam("artikelId") Integer artikelId, @PathParam("uporabnikId") Long uporabnikId,  ArtikelDto artikelDto) {

        Artikel artikel = upravljanjeNakupovalnihSeznamovZrno.posodobiArtikel(artikelId, uporabnikId, artikelDto);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @DELETE
    @Path("/{artikelId}/uporabniki/{uporabnikId}/seznami/{seznamId}")
    public Response odstraniArtikelUporabniku(@PathParam("artikelId") Integer artikelId, @PathParam("uporabnikId") Long uporabnikId, @PathParam("seznamId") Integer seznamId) {

        Artikel artikel = upravljanjeNakupovalnihSeznamovZrno.odstraniArtikel(artikelId, uporabnikId, seznamId);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

}
