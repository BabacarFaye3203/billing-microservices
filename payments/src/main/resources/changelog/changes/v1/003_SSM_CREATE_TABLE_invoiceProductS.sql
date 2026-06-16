CREATE TABLE invoice_products (
                          id BIGSERIAL PRIMARY KEY,
                          uuid VARCHAR(255),
                          product_uuid VARCHAR(255),
                          name VARCHAR(255),
                          total_price DOUBLE PRECISION,
                          quantity DOUBLE PRECISION,
                          unit_price DOUBLE PRECISION,
                          invoice_id integer
);