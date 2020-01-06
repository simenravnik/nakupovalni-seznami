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
@CrossOrigin(supportedMethods = "GET,POST,HEAD,OPTIONS,PUT,DELETE")
public class NakupovalniSeznamiVir {

    Logger log = Logger.getLogger(NakupovalniSeznamiVir.class.getName());

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @Inject
    private NakupovalniSeznamZrno nakupovalniSeznamZrno;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Vrne seznam nakupovalnih seznamov uporabnika.", summary = "Seznam seznamov uporabnika.",
            tags = "Nakupovalni seznami", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Seznam nakupovalnih seznamov",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = NakupovalniSeznam.class)
                            ))
                    )
            }
    )
    @GET
    @Path("uporabniki/{id}")
    public Response pridobiNakupovalneSeznameUporabnika(
            @Parameter(
                description = "Identifikator uporabnika.",
                required = true)
            @PathParam("id") Long uporabnikId) {

        return Response.ok(upravljanjeNakupovalnihSeznamovZrno.pridobiNakupovalniSeznameUporabnika(uporabnikId)).build();

    }


    @Operation(description = "Vrne seznam vseh nakupovalnih seznamov.", summary = "Seznam nakupovalnih seznamov.",
            tags = "Nakupovalni seznami", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Seznam nakupovalnih seznamov",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = NakupovalniSeznam.class)
                            )),
                    headers = {@Header(name = "X-Total-Count", description = "Stevilo vrnjenih seznamov.")}
                    )

            }
    )
    @GET
    public Response pridobiNakupovalneSezname() {

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long nakupovalniSeznamiCount = nakupovalniSeznamZrno.pridobiNakupovalneSeznameCount(query);

        return Response.ok(nakupovalniSeznamZrno.pridobiNakupovalneSezname(query)).header("X-Total-Count", nakupovalniSeznamiCount).build();

    }

    @Operation(description = "Vrne podrobnosti nakupovalnega seznama.", summary = "Podrobnosti nakupovalnega seznama.",
            tags = "Nakupovalni seznami", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Nakupovalni seznam",
                    content = @Content(
                            schema = @Schema(implementation = NakupovalniSeznam.class)
                    ))
            }
    )
    @GET
    @Path("{seznamId}")
    public Response pridobiNakupovalniSeznam(
            @Parameter(
                description = "Identifikator nakupovalnega seznama.",
                required = true)
            @PathParam("seznamId") Long seznamId) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.pridobiNakupovalniSeznam(seznamId);

        if(nakupovalniSeznam != null) {
            return Response.ok(nakupovalniSeznam).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @Operation(description = "Doda seznam uporabniku.", summary = "Dodajanje seznama.",
            tags = "Nakupovalni seznami", responses = {
            @ApiResponse(responseCode = "201",
                    description = "Seznam uspesno dodan."
            ),
            @ApiResponse(responseCode = "405", description = "Napaka.")
    })
    @POST
    public Response dodajNakupovalniSeznamUporabniku(
            @RequestBody(
                description = "DTO objekt za dodajanje seznamov.",
                required = true,
                content = @Content(schema = @Schema(implementation = NakupovalniSeznamDto.class)))
            NakupovalniSeznamDto nakupovalniSeznamDto) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.ustvariNakupovalniSeznam(nakupovalniSeznamDto);

        if (nakupovalniSeznam != null) {
            return Response.ok(nakupovalniSeznam).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }


    @Operation(description = "Posodobi nakupovalni seznam.", summary = "Posodabljanje nakupovalnega seznama.",
            tags = "Nakupovalni seznami",
            responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Seznam uspesno posodobljen."
                    )
            }
    )
    @PUT
    @Path("{seznamId}")
    public Response posodobiNakupovalniSeznamUporabniku(
            @Parameter(
                description = "Identifikator nakupovalnega seznama.",
                required = true)
            @PathParam("seznamId") Long seznamId,
            @RequestBody(
                description = "DTO objekt za dodajanje seznamov.",
                required = true,
                content = @Content(schema = @Schema(implementation = NakupovalniSeznamDto.class)))
            NakupovalniSeznamDto nakupovalniSeznamDto) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.posodobiNakupovalniSeznam(seznamId, nakupovalniSeznamDto);

        if (nakupovalniSeznam != null) {
            return Response.ok(nakupovalniSeznam).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @Operation(description = "Odstrani nakupovalni seznam.", summary = "Brisanje nakupovalnega seznama.",
            tags = "Nakupovalni seznami",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Seznam uspesno odstranjen."
                    ), @ApiResponse(
                            responseCode = "404",
                            description = "Seznam ne obstaja."
                    )
            }
    )
    @DELETE
    @Path("{seznamId}")
    public Response odstraniNakupovalniSeznam(
            @Parameter(
                description = "Identifikator nakupovalnega seznama.",
                required = true)
            @PathParam("seznamId") Long seznamId) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.odstraniNakupovalniSeznam(seznamId);

        if (nakupovalniSeznam != null) {
            return Response.ok(nakupovalniSeznam).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
