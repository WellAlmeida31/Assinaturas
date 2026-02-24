package com.wellsoft.globo.assinaturas.domain.provider;

import com.wellsoft.globo.assinaturas.infrastructure.persistence.dbo.SignatureDbo;

public interface SignatureProvider {

    SignatureDbo createSignature(SignatureDbo signature);
}
