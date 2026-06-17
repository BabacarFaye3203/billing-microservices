CREATE TABLE invoice_payments (
                                  id BIGSERIAL PRIMARY KEY,
                                  uuid VARCHAR(255),
                                  invoice_uuid VARCHAR(255),
                                  amount DOUBLE PRECISION,
                                  payment_method VARCHAR(255),
                                  date TIMESTAMP,
                                  invoice_id BIGINT,

                                  CONSTRAINT fk_invoice_payments_invoice
                                      FOREIGN KEY (invoice_id)
                                          REFERENCES invoices(id)
                                          ON DELETE CASCADE
);