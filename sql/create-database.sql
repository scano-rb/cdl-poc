--Setup database
DROP DATABASE IF EXISTS demo;
CREATE DATABASE demo;
\c demo;

DROP TABLE IF EXISTS transactions;

CREATE TABLE IF NOT EXISTS transactions (
  transaction_id BIGSERIAL,
  terminal_number VARCHAR(255) NOT NULL,
  payment_media_id smallint,
  amount NUMERIC(22,2),
  card_number VARCHAR(255) NOT NULL,
  PRIMARY KEY(transaction_id)
);