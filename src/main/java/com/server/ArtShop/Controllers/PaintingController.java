package com.server.ArtShop.Controllers;

import com.server.ArtShop.dto.CreatePaintingDTO;
import com.server.ArtShop.dto.PaintingDTO;
import com.server.ArtShop.dto.UpdatePaintingDTO;
import com.server.ArtShop.services.PaintingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paintings")
public class PaintingController {

    private final PaintingService paintingService;

    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @GetMapping
    public ResponseEntity<List<PaintingDTO>> getAllPaintings() {
        return ResponseEntity.ok(paintingService.getAllPaintings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaintingDTO> getPaintingById(@PathVariable int id) {
        return ResponseEntity.ok(paintingService.getPaintingById(id));
    }

    @PostMapping
    public ResponseEntity<PaintingDTO> createPainting(@Valid @RequestBody CreatePaintingDTO createPaintingDTO) {
        PaintingDTO created = paintingService.createPainting(createPaintingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaintingDTO> updatePainting(@PathVariable int id, @Valid @RequestBody UpdatePaintingDTO updatePaintingDTO) {
        PaintingDTO updated = paintingService.updatePainting(id, updatePaintingDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePainting(@PathVariable int id) {
        paintingService.deletePainting(id);
        return ResponseEntity.noContent().build();
    }
}
