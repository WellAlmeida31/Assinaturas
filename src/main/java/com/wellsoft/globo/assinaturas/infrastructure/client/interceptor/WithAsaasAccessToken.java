package com.wellsoft.globo.assinaturas.infrastructure.client.interceptor;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithAsaasAccessToken {
}
