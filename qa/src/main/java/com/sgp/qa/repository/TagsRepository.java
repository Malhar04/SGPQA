package com.sgp.qa.repository;

import com.sgp.qa.model.Tags;
import com.sgp.qa.model.UnverifiedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags, Long> {
}
