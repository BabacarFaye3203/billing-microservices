CREATE TABLE invoices (
                          id BIGSERIAL PRIMARY KEY,
                          uuid VARCHAR(255),
                          number VARCHAR(50),
                          price DOUBLE PRECISION
);