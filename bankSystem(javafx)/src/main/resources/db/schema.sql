-- src/main/resources/db/schema.sql

DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  username      VARCHAR(50)  NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  full_name     VARCHAR(100) NOT NULL
);

CREATE TABLE accounts (
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  acc_no      VARCHAR(20)  NOT NULL UNIQUE,
  customer_id BIGINT       NOT NULL,
  type        VARCHAR(20)  NOT NULL CHECK (type IN ('SAVINGS','INVESTMENT','CHEQUE')),
  balance     DECIMAL(18,2) NOT NULL DEFAULT 0,
  opened_at   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_accounts_customer
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

CREATE TABLE transactions (
  id             BIGINT AUTO_INCREMENT PRIMARY KEY,
  acc_id         BIGINT       NOT NULL,
  ts             TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  kind           VARCHAR(20)  NOT NULL CHECK (kind IN ('DEPOSIT','WITHDRAW','TRANSFER_IN','TRANSFER_OUT','INTEREST')),
  amount         DECIMAL(18,2) NOT NULL,
  balance_after  DECIMAL(18,2) NOT NULL,
  note           VARCHAR(200),
  CONSTRAINT fk_tx_account
    FOREIGN KEY (acc_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE INDEX ix_tx_acc_ts ON transactions(acc_id, ts);

-- Optional seed so login/demo works quickly
INSERT INTO customers (username, password_hash, full_name)
VALUES ('alice', 'plaintext-for-now', 'Alice');

INSERT INTO accounts (acc_no, customer_id, type, balance) VALUES
('SA-1', 1, 'SAVINGS',    1000.00),
('IV-1', 1, 'INVESTMENT',  450.00),
('CQ-1', 1, 'CHEQUE',      325.00);






