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
    @Path("/seznam/{seznamId}")
    public Response pridobiArtikleNakupovalnegaSeznama(@PathParam("seznamId") Long seznamId) {

        return Response.ok(upravljanjeArtiklovZrno.pridobiArtikleNakupovalnegaSeznama(seznamId)).build();

    }

    @GET
    public Response pridobiArtikle() {

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long nakupovalniSeznamiCount = artikelZrno.pridobiArtikleCount(query);

        return Response
                .ok(artikelZrno.pridobiArtikle(query))
                .header("X-Total-Count", nakupovalniSeznamiCount)
                .build();

    }

    @GET
    @Path("{artikelId}")
    public Response pridobiArtikel(@PathParam("artikelId") Long artikelId) {

        Artikel artikel = upravljanjeArtiklovZrno.pridobiArtikel(artikelId);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    public Response dodajArtikelNakupovalnemuSeznamu(ArtikelDto artikelDto) {

        Artikel artikel = upravljanjeArtiklovZrno.ustvariArtikel(artikelDto);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }

    }

    @PUT
    @Path("{artikelId}")
    public Response posodobiArtikelUporabniku(@PathParam("artikelId") Long artikelId, ArtikelDto artikelDto) {

        Artikel artikel = upravljanjeArtiklovZrno.posodobiArtikel(artikelId, artikelDto);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @DELETE
    @Path("{artikelId}")
    public Response odstraniArtikel(@PathParam("artikelId") Long artikelId) {

        Artikel artikel = upravljanjeArtiklovZrno.odstraniArtikel(artikelId);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

}
