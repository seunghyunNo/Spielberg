package com.project.MovieMania.domain.listener;

import java.time.LocalDateTime;

public interface Auditable {
	LocalDateTime getCreatedAt();
	void setCreatedAt(LocalDateTime createdAt);
}
