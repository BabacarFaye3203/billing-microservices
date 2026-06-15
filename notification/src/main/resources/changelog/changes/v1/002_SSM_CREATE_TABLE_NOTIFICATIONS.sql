CREATE TABLE notifications (
                               id BIGSERIAL PRIMARY KEY,
                               uuid VARCHAR(255) NOT NULL UNIQUE,
                               name VARCHAR(255) NOT NULL,
                               email VARCHAR(255) NOT NULL,
                               objet VARCHAR(255) NOT NULL,
                               message TEXT NOT NULL,
                               date DATE NOT NULL
);