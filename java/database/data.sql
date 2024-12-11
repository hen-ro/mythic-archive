BEGIN TRANSACTION;

--INSERT INTO users (username,email, password_hash,role)
--VALUES ('user', 'user@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K','ROLE_USER')
--ON CONFLICT (username) DO NOTHING;

--INSERT INTO users (username,email, password_hash,role)
--VALUES ('admin', 'admin@example.com','$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K','ROLE_ADMIN')
--ON CONFLICT (username) DO NOTHING;

-- Users table
INSERT INTO users (username, email, password_hash, role) VALUES
('user', 'user@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K','ROLE_USER'),
('admin', 'admin@example.com','$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K','ROLE_ADMIN'),
('RobertDowneyJr', 'rdowneyjr@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K', 'ROLE_ADMIN'),
('ScarlettJohansson', 'sjohansson@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K', 'ROLE_USER'),
('ChrisHemsworth', 'chemsworth@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K', 'ROLE_USER'),
('TomHolland', 'tholland@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K', 'ROLE_USER'),
('NataliePortman', 'nportman@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K', 'ROLE_USER'),
('ChrisEvans', 'cevans@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K', 'ROLE_USER'),
('AustinPost', 'apost@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K', 'ROLE_USER'),
('EmmaWatson', 'ewatson@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K', 'ROLE_USER'),
('WillSmith', 'wsmith@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K', 'ROLE_USER'),
('JohnnyDepp', 'jdepp@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K', 'ROLE_USER'),
('SethGreen', 'ajolie@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K', 'ROLE_USER'),
('JasonAlexander', 'jalexander@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K', 'ROLE_USER');

--INSERT INTO collections(collection_name, user_id, is_public) VALUES
--('user''s Collection', 1, false);
--('admin''s Collection', 2, true);

-- Collections table
INSERT INTO collections (collection_name, user_id, is_public, thumbnail_url) VALUES
('user''s Collection', 1, false, 'images/CardBack.jpg'),
('admin''s Collection', 2, true, 'images/CardBack.jpg'),
('Robert Downey Jr''s Collection', 3, FALSE, 'images/CardBack.jpg'),
('Scarlett Johansson''s Collection', 4, FALSE, 'images/CardBack.jpg'),
('Chris Hemsworth''s Collection', 5, TRUE, 'images/CardBack.jpg'),
('Tom Holland''s Collection', 6, FALSE, 'images/CardBack.jpg'),
('Natalie Portman''s Collection', 7, TRUE, 'images/CardBack.jpg'),
('Chris Evans Collection', 8, FALSE, 'images/CardBack.jpg'),
('Austin Post''s Collection', 9, TRUE, 'images/CardBack.jpg'),
('Emma Watson''s Collection', 10, FALSE, 'images/CardBack.jpg'),
('Will Smith''s Collection', 11, TRUE, 'images/CardBack.jpg'),
('Johnny Depp''s Collection', 12, FALSE, 'images/CardBack.jpg'),
('Seth Green''s Collection', 13, TRUE, 'images/CardBack.jpg'),
('Jason Alexander''s Collection', 14, FALSE, 'images/CardBack.jpg');

COMMIT;
