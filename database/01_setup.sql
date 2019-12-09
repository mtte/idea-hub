-- SCRIPT TO CREATE THE DATABASE

-- SCHEMA ideahub
CREATE SCHEMA IF NOT EXISTS ideahub;

ALTER SCHEMA ideahub OWNER TO ideahub;

-- TABLE user
CREATE TABLE IF NOT EXISTS ideahub."user"
(
    id       SERIAL       NOT NULL,
    name     VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- TABLE note
CREATE TABLE IF NOT EXISTS ideahub.note
(
    id      SERIAL       NOT NULL,
    title   VARCHAR(100) NOT NULL,
    content TEXT         NOT NULL,
    created TIMESTAMP    NOT NULL,
    author  INT          NOT NULL,
    shared  BOOLEAN      NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (author) REFERENCES ideahub."user" (id)
);

-- TABLE category
CREATE TABLE IF NOT EXISTS ideahub.category
(
    id   SERIAL       NOT NULL,
    name VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

-- TABLE note_has_category
CREATE TABLE IF NOT EXISTS ideahub.note_has_category
(
    note     INT NOT NULL,
    category INT NOT NULL,
    PRIMARY KEY (note, category),
    FOREIGN KEY (note) REFERENCES ideahub.note (id),
    FOREIGN KEY (category) REFERENCES ideahub.category (id)
);
