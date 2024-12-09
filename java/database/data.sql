BEGIN TRANSACTION;

INSERT INTO users (username,email, password_hash,role) VALUES ('user', 'user@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K','ROLE_USER');
INSERT INTO users (username,email, password_hash,role) VALUES ('admin', 'admin@example.com','$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K','ROLE_ADMIN');

INSERT INTO collections(collection_name, user_id, is_public, thumbnail_url) VALUES ('user''s Collection', 1, false, '');
INSERT INTO collections(collection_name, user_id, is_public, thumbnail_url) VALUES ('admin''s Collection', 2, true, '');

COMMIT TRANSACTION;
