package com.sgp.qa.repository;

import com.sgp.qa.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository  extends JpaRepository<Question, Integer> {

}
