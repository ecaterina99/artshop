package com.server.ArtShop.Controllers;

import com.server.ArtShop.dto.CreatePaintingDTO;
import com.server.ArtShop.dto.PaintingDTO;
import com.server.ArtShop.dto.UpdatePaintingDTO;
import com.server.ArtShop.exceptions.ApiError;
import com.server.ArtShop.services.PaintingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paintings")
@Tag(name = "Paintings", description = "Painting gallery management")
public class PaintingController {

    private final PaintingService paintingService;

    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @GetMapping
    @Operation(summary = "Get all paintings", description = "Returns a list of all paintings in the gallery")
    @ApiResponse(responseCode = "200", description = "Paintings retrieved successfully")
    public ResponseEntity<List<PaintingDTO>> getAllPaintings() {
        return ResponseEntity.ok(paintingService.getAllPaintings());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get painting by ID")
    @ApiResponse(responseCode = "200", description = "Painting found")
    @ApiResponse(responseCode = "404", description = "Painting not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<PaintingDTO> getPaintingById(@PathVariable int id) {
        return ResponseEntity.ok(paintingService.getPaintingById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new painting")
    @ApiResponse(responseCode = "201", description = "Painting created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<PaintingDTO> createPainting(@Valid @RequestBody CreatePaintingDTO createPaintingDTO) {
        PaintingDTO created = paintingService.createPainting(createPaintingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing painting")
    @ApiResponse(responseCode = "200", description = "Painting updated successfully")
    @ApiResponse(responseCode = "404", description = "Painting not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<PaintingDTO> updatePainting(@PathVariable int id, @Valid @RequestBody UpdatePaintingDTO updatePaintingDTO) {
        PaintingDTO updated = paintingService.updatePainting(id, updatePaintingDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a painting")
    @ApiResponse(responseCode = "204", description = "Painting deleted successfully")
    @ApiResponse(responseCode = "404", description = "Painting not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<Void> deletePainting(@PathVariable int id) {
        paintingService.deletePainting(id);
        return ResponseEntity.noContent().build();
    }
}
