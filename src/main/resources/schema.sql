CREATE TABLE IF NOT EXISTS todo (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    completed BOOLEAN
    );