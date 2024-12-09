BEGIN TRANSACTION;

INSERT INTO users (username,email, password_hash,role)
VALUES ('user', 'user@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K','ROLE_USER')
ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username,email, password_hash,role)
VALUES ('admin', 'admin@example.com','$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K','ROLE_ADMIN')
ON CONFLICT (username) DO NOTHING;

INSERT INTO collections(collection_name, user_id, is_public, thumbnail_url)
VALUES ('user''s Collection', 1, false, '');
--ON CONFLICT (user_id) DO NOTHING;

INSERT INTO collections(collection_name, user_id, is_public, thumbnail_url)
VALUES ('admin''s Collection', 2, true, '');
--ON CONFLICT (user_id) DO NOTHING;

COMMIT TRANSACTION;
