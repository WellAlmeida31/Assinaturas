package com.wellsoft.globo.assinaturas.infrastructure.rest.controller.port;

import com.wellsoft.globo.assinaturas.infrastructure.rest.controller.request.UserRequestDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = Paths.APP, produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserController {

    @PostMapping(Paths.USER.CREATE)
    @Operation(
            summary = "Create - Cria um Usuário",
            description = "criação de usuário para o sistema de assinaturas")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = UserResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
    ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto dto, UriComponentsBuilder uriBuilder);
}
