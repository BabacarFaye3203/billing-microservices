CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          uuid VARCHAR(255),
                          name VARCHAR(255),
                          description VARCHAR(255),
                          price DOUBLE PRECISION,
                          quantity DOUBLE PRECISION
);