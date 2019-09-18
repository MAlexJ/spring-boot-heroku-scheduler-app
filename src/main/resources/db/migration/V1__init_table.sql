-- -----------------------------------------------------
-- Table words
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS words (
  id            BIGSERIAL NOT NULL PRIMARY KEY,
  name          VARCHAR(50),
  idPos         INTEGER,
  language_id   INTEGER
);

-- -----------------------------------------------------
-- Table languages
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS languages (
  id            BIGSERIAL NOT NULL PRIMARY KEY,
  name          VARCHAR(20)
);

ALTER TABLE words
    ADD CONSTRAINT fk_words_languages FOREIGN KEY (language_id) REFERENCES languages (id);

-- -----------------------------------------------------
-- Table part_of_speech
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS part_of_speech (
  id            BIGSERIAL NOT NULL PRIMARY KEY,
  name          VARCHAR(40),
  abbreviation  VARCHAR(20),
  description   VARCHAR(255)
);

-- -----------------------------------------------------
-- Table words_part_of_speech
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS words_part_of_speech (
  id            BIGSERIAL NOT NULL PRIMARY KEY,
  pos_id        INTEGER,
  word_id       INTEGER
);

ALTER TABLE words_part_of_speech
    ADD CONSTRAINT fk_w_pos_pos FOREIGN KEY (pos_id) REFERENCES part_of_speech (id),
    ADD CONSTRAINT fk_w_pos_words FOREIGN KEY (word_id) REFERENCES words (id);