CREATE TABLE invoice_clients (
                                 id BIGSERIAL PRIMARY KEY,
                                 uuid VARCHAR(255) NOT NULL UNIQUE,
                                 client_uuid VARCHAR(255),
                                 first_name VARCHAR(255) NOT NULL,
                                 last_name VARCHAR(255) NOT NULL,
                                 email VARCHAR(255) NOT NULL UNIQUE,
                                 phone VARCHAR(50),
                                 address VARCHAR(255),
                                 city VARCHAR(100),
                                 country VARCHAR(100),
                                 created_at DATE NOT NULL
);