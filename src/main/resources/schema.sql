-- Schema para Github-pullrequest-ms
-- Base de datos: PostgreSQL

-- Tabla de repositorios (referencia simplificada)
CREATE TABLE IF NOT EXISTS repositories (
    id BIGSERIAL PRIMARY KEY,
    owner VARCHAR(50) NOT NULL,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    visibility VARCHAR(10) NOT NULL DEFAULT 'PRIVATE',
    default_branch VARCHAR(100) DEFAULT 'main',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_repositories_owner_name UNIQUE (owner, name)
);

-- Tabla de pull requests
CREATE TABLE IF NOT EXISTS pull_requests (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    repository_id BIGINT NOT NULL,
    number INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    source_branch VARCHAR(100) NOT NULL,
    target_branch VARCHAR(100) NOT NULL,
    author_id UUID NOT NULL,
    author_username VARCHAR(50) NOT NULL,
    status VARCHAR(10) NOT NULL DEFAULT 'OPEN',
    has_conflicts BOOLEAN NOT NULL DEFAULT FALSE,
    commits_count INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    merged_at TIMESTAMP WITH TIME ZONE,
    merged_by_id UUID,
    merged_by_username VARCHAR(50),
    CONSTRAINT fk_pull_requests_repository FOREIGN KEY (repository_id) REFERENCES repositories(id) ON DELETE CASCADE,
    CONSTRAINT uk_pull_requests_repo_number UNIQUE (repository_id, number),
    CONSTRAINT chk_pull_requests_status CHECK (status IN ('OPEN', 'CLOSED', 'MERGED'))
);

-- Tabla de reviews de pull requests
CREATE TABLE IF NOT EXISTS pull_request_reviews (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pull_request_id UUID NOT NULL,
    reviewer_id UUID NOT NULL,
    reviewer_username VARCHAR(50) NOT NULL,
    decision VARCHAR(20) NOT NULL,
    comment TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reviews_pull_request FOREIGN KEY (pull_request_id) REFERENCES pull_requests(id) ON DELETE CASCADE,
    CONSTRAINT chk_reviews_decision CHECK (decision IN ('APPROVED', 'CHANGES_REQUESTED', 'COMMENTED'))
);

-- Tabla de comentarios de pull requests
CREATE TABLE IF NOT EXISTS pull_request_comments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pull_request_id UUID NOT NULL,
    body TEXT NOT NULL,
    file_path VARCHAR(500),
    line_number INTEGER,
    author_id UUID NOT NULL,
    author_username VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comments_pull_request FOREIGN KEY (pull_request_id) REFERENCES pull_requests(id) ON DELETE CASCADE,
    CONSTRAINT chk_comments_line_number CHECK (line_number IS NULL OR line_number >= 1)
);

-- Indices para mejorar performance
CREATE INDEX IF NOT EXISTS idx_pull_requests_repository ON pull_requests(repository_id);
CREATE INDEX IF NOT EXISTS idx_pull_requests_status ON pull_requests(status);
CREATE INDEX IF NOT EXISTS idx_pull_requests_author ON pull_requests(author_id);
CREATE INDEX IF NOT EXISTS idx_pull_requests_created ON pull_requests(created_at DESC);

CREATE INDEX IF NOT EXISTS idx_reviews_pull_request ON pull_request_reviews(pull_request_id);
CREATE INDEX IF NOT EXISTS idx_reviews_reviewer ON pull_request_reviews(reviewer_id);

CREATE INDEX IF NOT EXISTS idx_comments_pull_request ON pull_request_comments(pull_request_id);
CREATE INDEX IF NOT EXISTS idx_comments_author ON pull_request_comments(author_id);
CREATE INDEX IF NOT EXISTS idx_comments_file ON pull_request_comments(pull_request_id, file_path);
