package com.server.ArtShop.controllers;

import com.server.ArtShop.dto.LoginRequest;
import com.server.ArtShop.dto.TokenResponse;
import com.server.ArtShop.dto.UserDTO;
import com.server.ArtShop.exceptions.ApiError;
import com.server.ArtShop.models.User;
import com.server.ArtShop.services.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User registration and login")
public class AuthController {

    private final RegistrationService registrationService;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user")
    @ApiResponse(responseCode = "201", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "409", description = "User with this email already exists",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public UserDTO register(@Valid @RequestBody UserDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        User savedUser = registrationService.register(user);
        return new UserDTO(savedUser.getId(), savedUser.getEmail(), null, savedUser.getRole());
    }

    @PostMapping("/login")
    @Operation(summary = "Login and get JWT token")
    @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned")
    @ApiResponse(responseCode = "401", description = "Invalid credentials",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public TokenResponse login(@Valid @RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(req.email())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .claim("roles", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new TokenResponse(token);
    }
}
