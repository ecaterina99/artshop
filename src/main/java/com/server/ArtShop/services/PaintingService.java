package com.server.ArtShop.services;

import com.server.ArtShop.dto.CreatePaintingDTO;
import com.server.ArtShop.dto.PaintingDTO;
import com.server.ArtShop.dto.UpdatePaintingDTO;
import com.server.ArtShop.models.Painting;
import com.server.ArtShop.repositories.PaintingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaintingService {

    private final PaintingRepository paintingRepository;
    private final ModelMapper modelMapper;

    public PaintingService(PaintingRepository paintingRepository, ModelMapper modelMapper) {
        this.paintingRepository = paintingRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<PaintingDTO> getAllPaintings() {
        return paintingRepository.findAll().stream()
                .map(painting -> modelMapper.map(painting, PaintingDTO.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public PaintingDTO getPaintingById(int id) {
        Painting painting = paintingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Painting with id " + id + " does not exist"));
        return modelMapper.map(painting, PaintingDTO.class);
    }

    @Transactional
    public PaintingDTO createPainting(CreatePaintingDTO createPaintingDTO) {
        Painting painting = modelMapper.map(createPaintingDTO, Painting.class);
        Painting saved = paintingRepository.save(painting);
        return modelMapper.map(saved, PaintingDTO.class);
    }

    @Transactional
    public PaintingDTO updatePainting(int id, UpdatePaintingDTO updatePaintingDTO) {
        Painting painting = paintingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Painting with id " + id + " does not exist"));

        if (updatePaintingDTO.getName() != null) {
            painting.setName(updatePaintingDTO.getName());
        }
        if (updatePaintingDTO.getDescription() != null) {
            painting.setDescription(updatePaintingDTO.getDescription());
        }
        if (updatePaintingDTO.getLength() != null) {
            painting.setLength(updatePaintingDTO.getLength());
        }
        if (updatePaintingDTO.getHigh() != null) {
            painting.setHigh(updatePaintingDTO.getHigh());
        }
        if (updatePaintingDTO.getStyle() != null) {
            painting.setStyle(Painting.fromString(updatePaintingDTO.getStyle()));
        }
        if (updatePaintingDTO.getPrice() != null) {
            painting.setPrice(updatePaintingDTO.getPrice());
        }
        if (updatePaintingDTO.getMedium() != null) {
            painting.setMedium(updatePaintingDTO.getMedium());
        }
        if (updatePaintingDTO.getImg() != null) {
            painting.setImg(updatePaintingDTO.getImg());
        }

        Painting saved = paintingRepository.save(painting);
        return modelMapper.map(saved, PaintingDTO.class);
    }

    @Transactional
    public void deletePainting(int id) {
        Painting painting = paintingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Painting with id " + id + " does not exist"));
        paintingRepository.delete(painting);
    }
}
