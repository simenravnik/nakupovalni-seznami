package si.fri.prpo.nakupovanje.anotacija;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {

    @Inject
    private BelezenjeKlicevZrno belezenjeKlicevZrno;

    @AroundInvoke
    public Object zabeleziKlic(InvocationContext invocationContext) throws Exception {

        belezenjeKlicevZrno.povecaj();
        return invocationContext.proceed();

    }
}