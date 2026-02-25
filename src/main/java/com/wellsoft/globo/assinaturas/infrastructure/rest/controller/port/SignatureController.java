package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.port;

import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.SignatureRequestDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.UserRequestDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.SignatureResponseDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserCreateResponseDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.path.Paths;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = Paths.APP, produces = MediaType.APPLICATION_JSON_VALUE)
public interface SignatureController {

    @PostMapping(Paths.SIGNATURE.CREATE)
    @Operation(
            summary = "Create - Cria uma Assinatura",
            description = "Criação de uma assinatura para um usuário criado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = UserCreateResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
    ResponseEntity<SignatureResponseDto> createSignatureToUser(@RequestBody @Valid SignatureRequestDto dto, UriComponentsBuilder uriBuilder);

    @DeleteMapping(Paths.SIGNATURE.DELETE)
    @Operation(
            summary = "Delete - Cancela uma Assinatura",
            description = "Cancelamento de uma assinatura para um usuário criado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = UserCreateResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
    ResponseEntity<Void> cancelSignatureToUser(@PathVariable(Paths.Placeholder.IDENTIFIER) String identifier);

}
