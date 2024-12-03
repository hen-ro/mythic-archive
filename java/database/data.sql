BEGIN TRANSACTION;

INSERT INTO users (username,email, password_hash,role) VALUES ('user', 'user@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K','ROLE_USER');
INSERT INTO users (username,email, password_hash,role) VALUES ('admin', 'user2@example.com','$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K','ROLE_ADMIN');

COMMIT TRANSACTION;
