-- SCRIPT TO CREATE THE DATABASE

-- TABLE user
CREATE TABLE IF NOT EXISTS public."user"
(
    id       SERIAL       NOT NULL,
    name     VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- TABLE note
CREATE TABLE IF NOT EXISTS public.note
(
    id      SERIAL       NOT NULL,
    title   VARCHAR(100) NOT NULL,
    content TEXT         NOT NULL,
    created TIMESTAMP    NOT NULL,
    author  INT          NOT NULL,
    shared  BOOLEAN      NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (author) REFERENCES public."user" (id)
);

-- TABLE category
CREATE TABLE IF NOT EXISTS public.category
(
    id   SERIAL       NOT NULL,
    name VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

-- TABLE note_has_category
CREATE TABLE IF NOT EXISTS public.note_has_category
(
    note     INT NOT NULL,
    category INT NOT NULL,
    PRIMARY KEY (note, category),
    FOREIGN KEY (note) REFERENCES public.note (id),
    FOREIGN KEY (category) REFERENCES public.category (id)
);
