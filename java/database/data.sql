BEGIN TRANSACTION;

INSERT INTO users (username,email, password_hash,role)
VALUES ('user', 'user@example.com', '$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K','ROLE_USER')
ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username,email, password_hash,role)
VALUES ('admin', 'admin@example.com','$2b$12$GQ/2aURgvKb8rv7FXDibk.n3exV2s14OL8exVMeQzT7sLOIAqFN4K','ROLE_ADMIN')
ON CONFLICT (username) DO NOTHING;

INSERT INTO collections(collection_name, user_id, is_public)
VALUES ('user''s Collection', 1, false);
--ON CONFLICT (user_id) DO NOTHING;

INSERT INTO collections(collection_name, user_id, is_public)
VALUES ('admin''s Collection', 2, true);
--ON CONFLICT (user_id) DO NOTHING;

INSERT INTO cards (card_id, card_name, card_type, card_color, mana_cost, rarity, price, set_name, image_url, thumbnail_url) VALUES
('087c13b7-1641-4902-b595-f730068e33cc', 'A-Young Blue Dragon // A-Sand Augury', 'Creature — Dragon // Sorcery — Adventure', 'U', '{4},{U} // {1},{U}', 'common', NULL, 'Alchemy Horizons: Baldur''s Gate', 'https://cards.scryfall.io/normal/front/0/8/087c13b7-1641-4902-b595-f730068e33cc.jpg?1681159579', 'https://cards.scryfall.io/art_crop/front/0/8/087c13b7-1641-4902-b595-f730068e33cc.jpg?1681159579'),
('98dea71b-2778-4374-8ea2-7fa82f0f6110', 'Blue Dragon', 'Creature — Dragon', 'U', '{5},{U},{U}', 'uncommon', 0.09, 'Adventures in the Forgotten Realms', 'https://cards.scryfall.io/normal/front/9/8/98dea71b-2778-4374-8ea2-7fa82f0f6110.jpg?1627702968', 'https://cards.scryfall.io/art_crop/front/9/8/98dea71b-2778-4374-8ea2-7fa82f0f6110.jpg?1627702968'),
('2f51f88f-f662-4572-a371-9a77718ed079', 'Blue Elemental Blast', 'Instant', 'U', '{U}', 'uncommon', 0.68, 'Masters 25', 'https://cards.scryfall.io/normal/front/2/f/2f51f88f-f662-4572-a371-9a77718ed079.jpg?1562434032', 'https://cards.scryfall.io/art_crop/front/2/f/2f51f88f-f662-4572-a371-9a77718ed079.jpg?1562434032'),
('0a764ec9-6a30-4df3-91b4-c64fd8e32fa0', 'Blue, Loyal Raptor', 'Legendary Creature — Dinosaur', 'G', '{2},{G},{U}', 'rare', 1.98, 'Jurassic World Collection', 'https://cards.scryfall.io/normal/front/0/a/0a764ec9-6a30-4df3-91b4-c64fd8e32fa0.jpg?1698988752', 'https://cards.scryfall.io/art_crop/front/0/a/0a764ec9-6a30-4df3-91b4-c64fd8e32fa0.jpg?1698988752'),
('cd2cb84e-c079-486e-87ad-d188fe38bc76', 'Blue Mana Battery', 'Artifact', 'null', '{4}', 'rare', 0.47, 'Fourth Edition', 'https://cards.scryfall.io/normal/front/c/d/cd2cb84e-c079-486e-87ad-d188fe38bc76.jpg?1559603819', 'https://cards.scryfall.io/art_crop/front/c/d/cd2cb84e-c079-486e-87ad-d188fe38bc76.jpg?1559603819'),
('da67aec2-18ff-426a-a074-25cd34b01f9c', 'Blue Ribbon', 'Artifact — Equipment', 'null', '{1}', 'rare', 0.07, 'Unfinity', 'https://cards.scryfall.io/normal/front/d/a/da67aec2-18ff-426a-a074-25cd34b01f9c.jpg?1673915030', 'https://cards.scryfall.io/art_crop/front/d/a/da67aec2-18ff-426a-a074-25cd34b01f9c.jpg?1673915030'),
('b423bb5a-eaac-4c1d-981a-1c635001fc5a', 'Blue Scarab', 'Enchantment — Aura', 'W', '{W}', 'uncommon', 0.18, 'Ice Age', 'https://cards.scryfall.io/normal/front/b/4/b423bb5a-eaac-4c1d-981a-1c635001fc5a.jpg?1562928584', 'https://cards.scryfall.io/art_crop/front/b/4/b423bb5a-eaac-4c1d-981a-1c635001fc5a.jpg?1562928584'),
('3a836e6b-6854-4f6f-bc78-2da1fd4dd224', 'Blue Sun''s Twilight', 'Sorcery', 'U', '{X},{U},{U}', 'rare', 0.38, 'Phyrexia: All Will Be One', 'https://cards.scryfall.io/normal/front/3/a/3a836e6b-6854-4f6f-bc78-2da1fd4dd224.jpg?1675956948', 'https://cards.scryfall.io/art_crop/front/3/a/3a836e6b-6854-4f6f-bc78-2da1fd4dd224.jpg?1675956948'),
('de62c833-c66b-442e-99ed-99ccd8eca024', 'Blue Ward', 'Enchantment', 'W', '{W}', 'uncommon', 0.19, 'Fourth Edition', 'https://cards.scryfall.io/normal/front/d/e/de62c833-c66b-442e-99ed-99ccd8eca024.jpg?1559601732', 'https://cards.scryfall.io/art_crop/front/d/e/de62c833-c66b-442e-99ed-99ccd8eca024.jpg?1559601732'),
('45dc82a0-f054-4614-a7b9-4c5a7c9d2a77', 'Bringer of the Blue Dawn', 'Creature — Bringer', 'U', '{7},{U},{U}', 'rare', 4.69, 'Fifth Dawn', 'https://cards.scryfall.io/normal/front/4/5/45dc82a0-f054-4614-a7b9-4c5a7c9d2a77.jpg?1562876707', 'https://cards.scryfall.io/art_crop/front/4/5/45dc82a0-f054-4614-a7b9-4c5a7c9d2a77.jpg?1562876707'),
('5ced2118-dfb3-4f29-ad6b-454c0a8a094b', 'Circle of Protection: Blue', 'Enchantment', 'W', '{1},{W}', 'uncommon', 0.14, 'Eighth Edition', 'https://cards.scryfall.io/normal/front/5/c/5ced2118-dfb3-4f29-ad6b-454c0a8a094b.jpg?1562913834', 'https://cards.scryfall.io/art_crop/front/5/c/5ced2118-dfb3-4f29-ad6b-454c0a8a094b.jpg?1562913834'),
('85f4ed3a-1851-49b1-baac-fdc8c00b6b71', 'Rune of Protection: Blue', 'Enchantment', 'W', '{1},{W}', 'common', 0.09, 'Urza''s Saga', 'https://cards.scryfall.io/normal/front/8/5/85f4ed3a-1851-49b1-baac-fdc8c00b6b71.jpg?1562923262', 'https://cards.scryfall.io/art_crop/front/8/5/85f4ed3a-1851-49b1-baac-fdc8c00b6b71.jpg?1562923262'),
('1fd2b6a7-b93a-42d3-b66a-a70b573fc58a', 'Urza''s Blueprints', 'Artifact', 'null', '{6}', 'rare', 0.07, 'Dominaria Remastered', 'https://cards.scryfall.io/normal/front/1/f/1fd2b6a7-b93a-42d3-b66a-a70b573fc58a.jpg?1675201126', 'https://cards.scryfall.io/art_crop/front/1/f/1fd2b6a7-b93a-42d3-b66a-a70b573fc58a.jpg?1675201126');

INSERT INTO cards (card_id, card_name, card_type, card_color, mana_cost, rarity, price, set_name, image_url, thumbnail_url)
VALUES
('9bb2795d-1225-47b8-9571-ddb7ed06f47d', 'Ancestral Recall', 'Instant', 'Blue', '{U}', 'Rare', 400.00, 'Revised Edition', 'https://c1.scryfall.com/file/scryfall-cards/normal/front/9/b/9bb2795d-1225-47b8-9571-ddb7ed06f47d.jpg?1595590767', 'https://c1.scryfall.com/file/scryfall-cards/small/front/9/b/9bb2795d-1225-47b8-9571-ddb7ed06f47d.jpg?1595590767');
INSERT INTO cards (card_id, card_name, card_type, card_color, mana_cost, rarity, price, set_name, image_url, thumbnail_url)
VALUES
('c83653f9-441d-490b-8dff-f8a39c9f88ad', 'Black Lotus', 'Artifact', 'Colorless', '{0}', 'Rare', 25000.00, 'Alpha', 'https://c1.scryfall.com/file/scryfall-cards/normal/front/c/8/c83653f9-441d-490b-8dff-f8a39c9f88ad.jpg?1595590767', 'https://c1.scryfall.com/file/scryfall-cards/small/front/c/8/c83653f9-441d-490b-8dff-f8a39c9f88ad.jpg?1595590767');
INSERT INTO cards (card_id, card_name, card_type, card_color, mana_cost, rarity, price, set_name, image_url, thumbnail_url)
VALUES
('e3135b4e-2273-4c98-ae3b-1f3a3d9e1f6d', 'Shivan Dragon', 'Creature', 'Red', '{4}{R}{R}', 'Rare', 10.00, 'Alpha', 'https://c1.scryfall.com/file/scryfall-cards/normal/front/e/3/e3135b4e-2273-4c98-ae3b-1f3a3d9e1f6d.jpg?1595590782', 'https://c1.scryfall.com/file/scryfall-cards/small/front/e/3/e3135b4e-2273-4c98-ae3b-1f3a3d9e1f6d.jpg?1595590782');
INSERT INTO cards (card_id, card_name, card_type, card_color, mana_cost, rarity, price, set_name, image_url, thumbnail_url)
VALUES
('f4fa1ff9-ea91-4c78-9d44-7cd0f4b3e149', 'Serra Angel', 'Creature', 'White', '{3}{W}{W}', 'Uncommon', 5.00, 'Revised Edition', 'https://c1.scryfall.com/file/scryfall-cards/normal/front/f/4/f4fa1ff9-ea91-4c78-9d44-7cd0f4b3e149.jpg?1595590789', 'https://c1.scryfall.com/file/scryfall-cards/small/front/f/4/f4fa1ff9-ea91-4c78-9d44-7cd0f4b3e149.jpg?1595590789');
INSERT INTO cards (card_id, card_name, card_type, card_color, mana_cost, rarity, price, set_name, image_url, thumbnail_url)
VALUES
('14fb8a6a-c82b-4d57-89be-9f697f24f71e', 'Lightning Bolt', 'Instant', 'Red', '{R}', 'Common', 0.10, 'Core Set 2010', 'https://c1.scryfall.com/file/scryfall-cards/normal/front/1/4/14fb8a6a-c82b-4d57-89be-9f697f24f71e.jpg?1595590844', 'https://c1.scryfall.com/file/scryfall-cards/small/front/1/4/14fb8a6a-c82b-4d57-89be-9f697f24f71e.jpg?1595590844');
INSERT INTO cards (card_id, card_name, card_type, card_color, mana_cost, rarity, price, set_name, image_url, thumbnail_url)
VALUES
('bbbe12bc-cb95-4b1e-a15a-5ff10c343555', 'Forest', 'Basic Land', 'Green', '', 'Common', 0.00, 'Alpha', 'https://c1.scryfall.com/file/scryfall-cards/normal/front/b/b/bbbe12bc-cb95-4b1e-a15a-5ff10c343555.jpg?1595590760', 'https://c1.scryfall.com/file/scryfall-cards/small/front/b/b/bbbe12bc-cb95-4b1e-a15a-5ff10c343555.jpg?1595590760');
INSERT INTO cards (card_id, card_name, card_type, card_color, mana_cost, rarity, price, set_name, image_url, thumbnail_url)
VALUES
('68a2c535-9df8-4b12-b4b0-66d66c6978f1', 'Thoughtseize', 'Sorcery', 'Black', '{B}{B}', 'Rare', 20.00, 'Lorwyn', 'https://c1.scryfall.com/file/scryfall-cards/normal/front/6/8/68a2c535-9df8-4b12-b4b0-66d66c6978f1.jpg?1595590810', 'https://c1.scryfall.com/file/scryfall-cards/small/front/6/8/68a2c535-9df8-4b12-b4b0-66d66c6978f1.jpg?1595590810');
INSERT INTO cards (card_id, card_name, card_type, card_color, mana_cost, rarity, price, set_name, image_url, thumbnail_url)
VALUES
('ff1fdc3b-1b8f-4f5d-9b7d-16f7e50421d4', 'Tarmogoyf', 'Creature', 'Green', '{1}{G}', 'Mythic', 100.00, 'Future Sight', 'https://c1.scryfall.com/file/scryfall-cards/normal/front/f/f/ff1fdc3b-1b8f-4f5d-9b7d-16f7e50421d4.jpg?1595590797', 'https://c1.scryfall.com/file/scryfall-cards/small/front/f/f/ff1fdc3b-1b8f-4f5d-9b7d-16f7e50421d4.jpg?1595590797');
INSERT INTO cards (card_id, card_name, card_type, card_color, mana_cost, rarity, price, set_name, image_url, thumbnail_url)
VALUES
('74df4ad5-4396-4723-b07c-f24b3e7f1535', 'Mox Jet', 'Artifact', 'Black', '{0}', 'Rare', 5000.00, 'Alpha', 'https://c1.scryfall.com/file/scryfall-cards/normal/front/7/4/74df4ad5-4396-4723-b07c-f24b3e7f1535.jpg?1595590791', 'https://c1.scryfall.com/file/scryfall-cards/small/front/7/4/74df4ad5-4396-4723-b07c-f24b3e7f1535.jpg?1595590791');

COMMIT TRANSACTION;
