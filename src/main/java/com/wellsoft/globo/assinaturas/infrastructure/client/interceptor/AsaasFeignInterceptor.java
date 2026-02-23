package com.wellsoft.globo.assinaturas.infrastructure.client.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
public class AsaasFeignInterceptor implements RequestInterceptor {

    private final AccessTokenProvider accessTokenProvider;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            Method method = requestTemplate.methodMetadata().method();

            if (method.isAnnotationPresent(WithAsaasAccessToken.class) || method.getDeclaringClass().isAnnotationPresent(WithAsaasAccessToken.class)) {

                String accessToken = accessTokenProvider.getToken();
                if (accessToken != null) {
                    requestTemplate.header("access_token", accessToken);
                }
            }
        } catch (Exception ignored) {}
    }
}
