package si.fri.prpo.nakupovanje.api.v1.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import si.fri.prpo.nakupovanje.api.v1.mappers.UporabniskoImeObstajaExceptionMapper;
import si.fri.prpo.nakupovanje.dto.UporabnikDto;
import si.fri.prpo.nakupovanje.entitete.Uporabnik;
import si.fri.prpo.nakupovanje.izjeme.UporabniskoImeObstajaException;
import si.fri.prpo.nakupovanje.zrna.UporabnikZrno;
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
@Path("uporabniki")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UporabnikiVir {

    private Logger log = Logger.getLogger(UporabnikiVir.class.getName());

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Vrne seznam uporabnikov.", summary = "Seznam uporabnikov.",
        tags = "Uporabniki", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Seznam uporabnikov",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Uporabnik.class)
                            )),
                    headers = {@Header(name = "X-Total-Count", description = "Stevilo vrnjenih uporabnikov.")}
            )})
    @SecurityRequirement(name = "openid-connect")
    @GET
    public Response pridobiUporabnike() {

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long uporabnikiCount = uporabnikZrno.pridobiUporabnikeCount(query);

        return Response.ok(uporabnikZrno.pridobiUporabnike(query)).header("X-Total-Count", uporabnikiCount).build();

    }

    @Operation(description = "Vrne podrobnosti uporabnika.", summary = "Podrobnosti uporabnika.",
            tags = "Uporabniki", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Podrobnosti uporabnika",
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class))
            )})
    @GET
    @Path("{id}")
    public Response pridobiUporabnika(
            @Parameter(
                description = "Identifikator uporabnika.",
                required = true)
            @PathParam("id") Long id) {

        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(id);

        if(uporabnik != null) {
            return Response.ok(uporabnik).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @Operation(description = "Dodaj uporabnika.", summary = "Dodajanje uporabnika.",
            tags = "Uporabniki", responses = {
            @ApiResponse(responseCode = "201",
                    description = "Uporabnik uspesno dodan."
            ),
                    @ApiResponse(responseCode = "405", description = "Napaka.")
            })
    @POST
    public Response dodajUporabnika(
            @RequestBody(
                description = "DTO objekt za dodajanje uporabnikov.",
                required = true,
                content = @Content(schema = @Schema(implementation = UporabnikDto.class)))
            UporabnikDto uporabnikDto) {

        try {

            Uporabnik uporabnik = upravljanjeNakupovalnihSeznamovZrno.ustvariUporabnika(uporabnikDto);
            return Response.status(Response.Status.CREATED).entity(uporabnik).build();

        } catch (UporabniskoImeObstajaException e) {
            return new UporabniskoImeObstajaExceptionMapper().toResponse(e);
        }

    }

    @Operation(description = "Posodobi uporabnika.", summary = "Posodabljanje uporabnika.",
            tags = "Uporabniki", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Uporabnik uspesno posodobljen."
            )
    })
    @PUT
    @Path("{id}")
    public Response posodobiUporabnika(
            @Parameter(
                description = "Identifikator uporabnika za posodabljanje.",
                required = true)
            @PathParam("id") Long id,
            @RequestBody(
                description = "DTO objekt za posodabljanje uporabnikov.",
                required = true,
                content = @Content(schema = @Schema(implementation = UporabnikDto.class)))
            Uporabnik uporabnik) {

        if (uporabnikZrno.pridobiUporabnika(id) != null) {
            uporabnikZrno.updateUporabnik(id, uporabnik);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @Operation(description = "Odstrani uporabnika.", summary = "Brisanje uporabnika.",
            tags = "Uporabniki",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Uporabnik uspesno odstranjen."
                ), @ApiResponse(
                        responseCode = "404",
                        description = "Uporabnik ne obstaja."
                )
            })
    @DELETE
    @Path("{id}")
    public Response odstraniUporabnika(
            @Parameter(
                description = "Identifikator uporabnika za brisanje.",
                required = true)
            @PathParam("id") Long id) {

        if (uporabnikZrno.pridobiUporabnika(id) != null) {

            uporabnikZrno.deleteUporabnik(id);
            return Response.status(Response.Status.OK).build();

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
