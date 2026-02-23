package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.port;

import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.UserRequestDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserCreateResponseDto;
import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.response.UserResponseDto;
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
public interface UserController {

    @PostMapping(Paths.USER.CREATE)
    @Operation(
            summary = "Create - Cria um Usuário",
            description = "criação de usuário para o sistema de assinaturas")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = UserCreateResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
    ResponseEntity<UserCreateResponseDto> createUser(@RequestBody @Valid UserRequestDto dto, UriComponentsBuilder uriBuilder);


    @GetMapping(Paths.USER.FIND)
    @Operation(
            summary = "Find by identifier - Consulta um usuário",
            description = "Consulta um Usuário pelo identifier, retornando sua Assinatura e cartão cadastrados, se houver"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    })
    ResponseEntity<UserResponseDto> findUserByIdentifier(@PathVariable(Paths.Placeholder.IDENTIFIER) String identifier);

}
