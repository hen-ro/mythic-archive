BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, collections, cards, cards_collections CASCADE;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	email varchar(100) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE collections (
    collection_id SERIAL NOT NULL,
    collection_name varchar (50) NOT NULL,
    user_id INT NOT NULL UNIQUE,
    is_public boolean NOT NULL DEFAULT FALSE,
    thumbnail_url varchar(2083) DEFAULT '',
    CONSTRAINT PK_collection PRIMARY KEY (collection_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE cards (
    card_id UUID NOT NULL UNIQUE,
    card_name varchar (250) NOT NULL,
    image_url varchar (2083) NOT NULL,
    thumbnail_url varchar(2083) NOT NULL,
    CONSTRAINT PK_card PRIMARY KEY (card_id)
);

CREATE TABLE cards_collections (
    card_id UUID NOT NULL,
    collection_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    CONSTRAINT PK_cards_collections PRIMARY KEY(card_id, collection_id),
    CONSTRAINT FK_cards_collections_cards FOREIGN KEY(card_id) REFERENCES cards(card_id),
    CONSTRAINT FK_cards_collections_collections FOREIGN KEY(collection_id) REFERENCES collections(collection_id),
    CONSTRAINT CK_quantity CHECK (quantity >= 0)
);

CREATE TABLE decks (
    deck_id SERIAL NOT NULL,
    deck_name varchar (50) NOT NULL,
    user_id INT NOT NULL UNIQUE,
    is_public boolean NOT NULL DEFAULT FALSE,
    thumbnail_url varchar(2083) DEFAULT '',
    deckFormat varchar(150) DEFAULT '',
    CONSTRAINT PK_deck PRIMARY KEY (deck_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);
CREATE TABLE cards_decks (
    card_id UUID NOT NULL,
    deck_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    CONSTRAINT PK_cards_decks PRIMARY KEY(card_id, deck_id),
    CONSTRAINT FK_cards_decks_cards FOREIGN KEY(card_id) REFERENCES cards(card_id),
    CONSTRAINT FK_cards_decks_decks FOREIGN KEY(deck_id) REFERENCES decks(deck_id),
    CONSTRAINT CK_quantity CHECK (quantity >= 0)
);


COMMIT TRANSACTION;

