ALTER TABLE invoices
    ADD COLUMN client_id BIGINT;

ALTER TABLE invoices
    ADD COLUMN status VARCHAR(50);

ALTER TABLE invoices
    ADD CONSTRAINT fk_invoice_client
        FOREIGN KEY (client_id)
            REFERENCES invoice_clients(id)
            ON DELETE RESTRICT;