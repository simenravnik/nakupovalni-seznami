package si.fri.prpo.nakupovanje.interceptorji;

import si.fri.prpo.nakupovanje.zrna.BelezenjeKlicevZrno;
import si.fri.prpo.nakupovanje.anotacije.BeleziKlice;

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