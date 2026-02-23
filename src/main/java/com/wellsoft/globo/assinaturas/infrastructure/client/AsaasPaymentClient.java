package com.wellsoft.globo.assinaturas.infrastructure.client;

import com.wellsoft.globo.assinaturas.infrastructure.client.interceptor.WithAsaasAccessToken;
import com.wellsoft.globo.assinaturas.infrastructure.client.request.AsaasClientRequest;
import com.wellsoft.globo.assinaturas.infrastructure.client.request.PayCreditCardRequest;
import com.wellsoft.globo.assinaturas.infrastructure.client.request.PaymentRequest;
import com.wellsoft.globo.assinaturas.infrastructure.client.request.TokenizeCreditCardRequest;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.AsaasClientResponse;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.CreatePaymentResponse;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.PaymentResponse;
import com.wellsoft.globo.assinaturas.infrastructure.client.response.TokenizeCreditCardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@WithAsaasAccessToken
@FeignClient(name = "AsaasPaymentClient", url = "${asaas.server-url}")
public interface AsaasPaymentClient {

    @PostMapping(value = "v3/customers", consumes = MediaType.APPLICATION_JSON_VALUE)
    AsaasClientResponse createClient(@RequestBody AsaasClientRequest clientRequest);

    @PostMapping(value = "v3/creditCard/tokenizeCreditCard", consumes = MediaType.APPLICATION_JSON_VALUE)
    TokenizeCreditCardResponse tokenizeCreditCard(@RequestBody TokenizeCreditCardRequest tokenizeCreditCardRequest);

    @PostMapping(value = "v3/payments", consumes = MediaType.APPLICATION_JSON_VALUE)
    CreatePaymentResponse createPayment(@RequestBody PaymentRequest paymentRequest);

    @PostMapping(value = "v3/payments/{paymentId}/payWithCreditCard", consumes = MediaType.APPLICATION_JSON_VALUE)
    PaymentResponse payWithCreditCard(@PathVariable String paymentId,
                                      @RequestBody PayCreditCardRequest creditCardRequest);
}
