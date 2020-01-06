package si.fri.prpo.nakupovanje.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.rest.beans.QueryParameters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import si.fri.prpo.nakupovanje.dto.ArtikelDto;
import si.fri.prpo.nakupovanje.entitete.Artikel;
import si.fri.prpo.nakupovanje.zrna.ArtikelZrno;
import si.fri.prpo.nakupovanje.zrna.UpravljanjeArtiklovZrno;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.List;

@ApplicationScoped
@Path("artikli")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(allowOrigin = "http://localhost:4200/")
public class ArtikliVir {

    @Inject
    private UpravljanjeArtiklovZrno upravljanjeArtiklovZrno;

    @Inject
    private ArtikelZrno artikelZrno;

    @Context
    protected UriInfo uriInfo;

    private Client httpClient;

    private String baseUrl;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrl = "http://localhost:8081/v1";
    }


    @Operation(description = "Vrne seznam artiklov nakupovalnega seznama.", summary = "Artikle seznama.",
            tags = "Artikli", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Seznam artiklov",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Artikel.class)
                            ))
                    )
            }
    )
    @GET
    @Path("/seznam/{seznamId}")
    public Response pridobiArtikleNakupovalnegaSeznama(
            @Parameter(
                description = "Identifikator nakupovalnega seznama.",
                required = true)
            @PathParam("seznamId") Long seznamId) {

        return Response.ok(upravljanjeArtiklovZrno.pridobiArtikleNakupovalnegaSeznama(seznamId)).build();

    }

    @Operation(description = "Vrne seznam vseh artiklov.", summary = "Vse artikle.",
            tags = "Artikli", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Seznam artiklov",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Artikel.class)
                            )),
                    headers = {@Header(name = "X-Total-Count", description = "Stevilo vrnjenih artiklov.")}
            )

    }
    )
    @GET
    public Response pridobiArtikle() {

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long nakupovalniSeznamiCount = artikelZrno.pridobiArtikleCount(query);

        return Response.ok(artikelZrno.pridobiArtikle(query)).header("X-Total-Count", nakupovalniSeznamiCount).build();

    }

    @Operation(description = "Vrne podrobnosti artikla.", summary = "Podrobnosti artikla.",
            tags = "Artikli", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Artikel",
                    content = @Content(
                            schema = @Schema(implementation = Artikel.class)
                    ))
            }
    )
    @GET
    @Path("{artikelId}")
    public Response pridobiArtikel(
            @Parameter(
                description = "Identifikator artikla.",
                required = true)
            @PathParam("artikelId") Long artikelId) {

        Artikel artikel = upravljanjeArtiklovZrno.pridobiArtikel(artikelId);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @Operation(description = "Doda artikel seznamu.", summary = "Dodajanje artikla.",
            tags = "Artikli", responses = {
            @ApiResponse(responseCode = "201",
                    description = "Artikel uspesno dodan."
            ),
            @ApiResponse(responseCode = "405", description = "Napaka.")
    })
    @POST
    public Response dodajArtikelNakupovalnemuSeznamu(
            @RequestBody(
                description = "DTO objekt za dodajanje artiklov.",
                required = true,
                content = @Content(schema = @Schema(implementation = ArtikelDto.class)))
            ArtikelDto artikelDto) {

        Artikel artikel = upravljanjeArtiklovZrno.ustvariArtikel(artikelDto);

        /* ko dodamo artikel, ga dodamo se v priporocilne sisteme */
        httpClient.target(baseUrl)
                .path("pogostiArtikli")
                .request()
                .post(Entity.entity(artikelDto, MediaType.APPLICATION_JSON));


        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }

    }

    @Operation(description = "Posodobi artikel.", summary = "Posodabljanje artikla.",
            tags = "Artikli",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Artikel uspesno posodobljen."
                    )
            })
    @PUT
    @Path("{artikelId}")
    public Response posodobiArtikelUporabniku(
            @Parameter(
                description = "Identifikator nakupovalnega seznama.",
                required = true)
            @PathParam("artikelId") Long artikelId,
            @RequestBody(
                description = "DTO objekt za dodajanje seznamov.",
                required = true,
                content = @Content(schema = @Schema(implementation = ArtikelDto.class)))
            ArtikelDto artikelDto) {

        Artikel artikel = upravljanjeArtiklovZrno.posodobiArtikel(artikelId, artikelDto);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @Operation(description = "Odstrani artikel.", summary = "Brisanje artikla.",
            tags = "Artikli",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Artikel uspesno odstranjen."
                    ), @ApiResponse(
                    responseCode = "404",
                    description = "Artikel ne obstaja."
                    )
            }
    )
    @DELETE
    @Path("{artikelId}")
    public Response odstraniArtikel(
            @Parameter(
                description = "Identifikator artikla.",
                required = true)
            @PathParam("artikelId") Long artikelId) {

        Artikel artikel = upravljanjeArtiklovZrno.odstraniArtikel(artikelId);

        if(artikel != null) {
            return Response.ok(artikel).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @GET
    @Path("/similar/{imeArtikla}")
    public Response pridobiPodobne(@PathParam("imeArtikla") String imeArtikla) throws IOException {

        String url = "https://twinword-word-associations-v1.p.rapidapi.com/associations/?entry=" + imeArtikla;

        String response = httpClient.target(url)
                .request("text/plain")
                .header("x-rapidapi-host", "twinword-word-associations-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "c4a4cc3548mshf9e1e9e6078d257p167885jsn92ad0379098d")
                .buildGet()
                .invoke(String.class);

        return Response.ok(response).build();
    }

    /**
     * Priporocilni Sistemi
     */
    @GET
    @Path("/pogostiArtikli")
    public Response pridobiPogosteArtikle() {
        List<String> pogostiArtikli = httpClient.target(baseUrl)
                .path("pogostiArtikli")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<String>>() {});

        return Response.ok(pogostiArtikli).build();
    }
}
