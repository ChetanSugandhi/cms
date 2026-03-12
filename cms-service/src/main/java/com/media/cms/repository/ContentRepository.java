package com.media.cms.repository;

import com.media.cms.model.entity.Content;
import com.media.cms.model.entity.ContentStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByStatus(ContentStatus status);
    List<Content> findByCreatorId(Long creatorId);
}
