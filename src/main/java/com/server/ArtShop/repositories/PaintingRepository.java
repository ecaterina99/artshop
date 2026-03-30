package com.server.ArtShop.repositories;

import com.server.ArtShop.models.Painting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaintingRepository  extends JpaRepository<Painting, Integer> {
}
