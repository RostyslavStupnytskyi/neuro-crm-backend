CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    locale VARCHAR(10)
);

CREATE TABLE user_roles (
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    industry VARCHAR(255),
    size INT,
    billing_address VARCHAR(255)
);

CREATE TABLE contacts (
    id SERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(50),
    position VARCHAR(255)
);

CREATE TABLE leads (
    id SERIAL PRIMARY KEY,
    source VARCHAR(255),
    status VARCHAR(20),
    contact_data JSONB
);

CREATE TABLE opportunities (
    id SERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id),
    value NUMERIC(19,2),
    stage VARCHAR(50),
    close_date DATE,
    probability INT
);

CREATE TABLE activities (
    id SERIAL PRIMARY KEY,
    type VARCHAR(20),
    owner_id BIGINT,
    due_date TIMESTAMP,
    notes VARCHAR(1024)
);

CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id),
    subject VARCHAR(255),
    status VARCHAR(50),
    priority VARCHAR(50),
    sla_due TIMESTAMP,
    description VARCHAR(2048)
);

CREATE TABLE campaigns (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    channel VARCHAR(50),
    start_date DATE,
    end_date DATE,
    target_segment VARCHAR(255)
);

CREATE TABLE attachments (
    id SERIAL PRIMARY KEY,
    entity_type VARCHAR(50),
    entity_id BIGINT,
    filename VARCHAR(255),
    url VARCHAR(255),
    uploaded_at TIMESTAMP
);

CREATE TABLE audit_logs (
    id SERIAL PRIMARY KEY,
    actor_id BIGINT,
    action VARCHAR(255),
    entity_type VARCHAR(50),
    entity_id BIGINT,
    delta JSONB,
    ts TIMESTAMP
);

CREATE INDEX contacts_search_idx ON contacts USING GIN (to_tsvector('english', first_name || ' ' || last_name || ' ' || email));
