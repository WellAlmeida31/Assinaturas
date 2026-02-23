package com.wellsoft.globo.assinaturas.infrastructure.config;

import com.wellsoft.globo.assinaturas.infrastructure.client.interceptor.AccessTokenProvider;
import com.wellsoft.globo.assinaturas.infrastructure.client.interceptor.AsaasFeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignAutoConfiguration {

    @Bean
    public RequestInterceptor authFeignInterceptor(AccessTokenProvider accessTokenProvider) {
        return new AsaasFeignInterceptor(accessTokenProvider);
    }

}
