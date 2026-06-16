ALTER TABLE invoice_products
    ADD CONSTRAINT fk_invoice_product_invoice
        FOREIGN KEY (invoice_id)
            REFERENCES invoices(id)
            ON DELETE CASCADE;