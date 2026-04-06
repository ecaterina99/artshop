INSERT INTO painting (name, description, length, high, style, price, medium, img)
SELECT 'Starry Night', 'A swirling night sky over a quiet village', 92, 73, 'IMPRESSIONIST', 5000, 'Oil on canvas', 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/Van_Gogh_-_Starry_Night_-_Google_Art_Project.jpg/1280px-Van_Gogh_-_Starry_Night_-_Google_Art_Project.jpg'
WHERE NOT EXISTS (SELECT 1 FROM painting WHERE name = 'Starry Night');

INSERT INTO painting (name, description, length, high, style, price, medium, img)
SELECT 'Ocean Waves', 'Abstract interpretation of crashing ocean waves', 120, 80, 'ABSTRACTION', 3500, 'Acrylic on canvas', 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e'
WHERE NOT EXISTS (SELECT 1 FROM painting WHERE name = 'Ocean Waves');

INSERT INTO painting (name, description, length, high, style, price, medium, img)
SELECT 'Silent Garden', 'A minimalist depiction of a zen garden at dawn', 100, 70, 'MINIMALIST', 2800, 'Watercolor on paper', 'https://images.unsplash.com/photo-1585320806297-9794b3e4eeae'
WHERE NOT EXISTS (SELECT 1 FROM painting WHERE name = 'Silent Garden');

INSERT INTO painting (name, description, length, high, style, price, medium, img)
SELECT 'Melting Clocks', 'A surrealist landscape with distorted timepieces', 110, 85, 'SURREALIST', 7200, 'Oil on canvas', 'https://images.unsplash.com/photo-1501472312651-726afe119ff1'
WHERE NOT EXISTS (SELECT 1 FROM painting WHERE name = 'Melting Clocks');

INSERT INTO painting (name, description, length, high, style, price, medium, img)
SELECT 'Urban Pulse', 'Modern city skyline captured in bold geometric shapes', 150, 100, 'MODERN', 4100, 'Acrylic on canvas', 'https://images.unsplash.com/photo-1514565131-fce0801e5785'
WHERE NOT EXISTS (SELECT 1 FROM painting WHERE name = 'Urban Pulse');

INSERT INTO painting (name, description, length, high, style, price, medium, img)
SELECT 'Autumn Reflections', 'Impressionist scene of autumn trees reflected in a lake', 130, 90, 'IMPRESSIONIST', 4500, 'Oil on canvas', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d'
WHERE NOT EXISTS (SELECT 1 FROM painting WHERE name = 'Autumn Reflections');

INSERT INTO painting (name, description, length, high, style, price, medium, img)
SELECT 'Fractured Light', 'Abstract play of light through shattered glass', 80, 60, 'ABSTRACTION', 3200, 'Mixed media', 'https://images.unsplash.com/photo-1543857778-c4a1a3e0b2eb'
WHERE NOT EXISTS (SELECT 1 FROM painting WHERE name = 'Fractured Light');

INSERT INTO painting (name, description, length, high, style, price, medium, img)
SELECT 'Floating Dreams', 'Surrealist scene of objects suspended in mid-air', 140, 95, 'SURREALIST', 6800, 'Oil on canvas', 'https://images.unsplash.com/photo-1536431311719-398b6704d4cc'
WHERE NOT EXISTS (SELECT 1 FROM painting WHERE name = 'Floating Dreams');

INSERT INTO painting (name, description, length, high, style, price, medium, img)
SELECT 'One Line', 'A single continuous line forming a human silhouette', 60, 90, 'MINIMALIST', 1900, 'Ink on paper', 'https://images.unsplash.com/photo-1579783902614-a3fb3927b6a5'
WHERE NOT EXISTS (SELECT 1 FROM painting WHERE name = 'One Line');

INSERT INTO painting (name, description, length, high, style, price, medium, img)
SELECT 'Neon District', 'A modern nightscape lit by neon signs and rain', 160, 110, 'MODERN', 5500, 'Digital print on canvas', 'https://images.unsplash.com/photo-1519608487953-e999c86e7455'
WHERE NOT EXISTS (SELECT 1 FROM painting WHERE name = 'Neon District');
