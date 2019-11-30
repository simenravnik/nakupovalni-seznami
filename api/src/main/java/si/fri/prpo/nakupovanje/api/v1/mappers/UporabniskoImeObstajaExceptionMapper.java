package si.fri.prpo.nakupovanje.api.v1.mappers;

import si.fri.prpo.nakupovanje.izjeme.UporabniskoImeObstajaException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UporabniskoImeObstajaExceptionMapper implements ExceptionMapper<UporabniskoImeObstajaException> {

    @Override
    public Response toResponse(UporabniskoImeObstajaException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napaka\":\"" + exception.getMessage() + "\"}")
                .build();
    }
}
