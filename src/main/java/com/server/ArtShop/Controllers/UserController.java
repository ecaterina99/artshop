package com.server.ArtShop.Controllers;

import com.server.ArtShop.dto.UserDTO;
import com.server.ArtShop.exceptions.ApiError;
import com.server.ArtShop.models.User;
import com.server.ArtShop.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User profile management")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @Operation(summary = "Get current authenticated user")
    @ApiResponse(responseCode = "200", description = "User profile retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Not authenticated",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public UserDTO getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return new UserDTO(
                user.getId(),
                user.getEmail(),
                null,
                user.getRole()
        );
    }
}
