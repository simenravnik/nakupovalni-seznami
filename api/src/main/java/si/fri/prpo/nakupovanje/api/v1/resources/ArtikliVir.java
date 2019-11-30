package si.fri.prpo.nakupovanje.api.v1.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.nakupovanje.dto.ArtikelDto;
import si.fri.prpo.nakupovanje.entitete.Artikel;
import si.fri.prpo.nakupovanje.zrna.ArtikelZrno;
import si.fri.prpo.nakupovanje.zrna.UpravljanjeArtiklovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("artikli")
public class ArtikliVir {

    @Inject
    private UpravljanjeArtiklovZrno upravljanjeArtiklovZrno;

    @Inject
    private ArtikelZrno artikelZrno;

    @Context
    protected UriInfo uriInfo;

    @GET
    @Path("/uporabniki/{uporabnikId}/seznami/{seznamId}")
    public Response pridobiArtikleNakupovalnegaSeznama(@PathParam("uporabnikId") Long uporabnikId, @PathParam("seznamId") Integer seznamId) {

        return Response.ok(upravljanjeArtiklovZrno.pridobiArtikleNakupovalnegaSeznama(uporabnikId, seznamId)).build();

    }

    @GET
    public Response pridobiArtikleNakupovalnegaSeznama() {

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long nakupovalniSeznamiCount = artikelZrno.pridobiArtikleCount(query);

        return Response
                .ok(artikelZrno.pridobiArtikle(query))
                .header("X-Total-Count", nakupovalniSeznamiCount)
                .build();

    }

    @GET
    @Path("/{artikelId}/uporabniki/{uporabnikId}/seznami/{seznamId}/")
    public Response pridobiArtikleNakupovalnegaSeznama(@PathParam("artikelId") Integer artikelId, @PathParam("uporabnikId") Long uporabnikId, @PathParam("seznamId") Integer seznamId) {

        Artikel artikel = upravljanjeArtiklovZrno.pridobiArtikelNakupovalnegaSeznama(uporabnikId, seznamId, artikelId);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    @Path("/uporabniki/{uporabnikId}")
    public Response dodajArtikelNakupovalnemuSeznamuUporabnika(@PathParam("uporabnikId") Long uporabnikId, ArtikelDto artikelDto) {

        Artikel artikel = upravljanjeArtiklovZrno.ustvariArtikel(uporabnikId, artikelDto);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }

    }

    @PUT
    @Path("/{artikelId}/uporabniki/{uporabnikId}/")
    public Response posodobiArtikelUporabniku(@PathParam("artikelId") Integer artikelId, @PathParam("uporabnikId") Long uporabnikId,  ArtikelDto artikelDto) {

        Artikel artikel = upravljanjeArtiklovZrno.posodobiArtikel(artikelId, uporabnikId, artikelDto);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @DELETE
    @Path("/{artikelId}/uporabniki/{uporabnikId}/seznami/{seznamId}")
    public Response odstraniArtikelUporabniku(@PathParam("artikelId") Integer artikelId, @PathParam("uporabnikId") Long uporabnikId, @PathParam("seznamId") Integer seznamId) {

        Artikel artikel = upravljanjeArtiklovZrno.odstraniArtikel(artikelId, uporabnikId, seznamId);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

}
