package com.sample.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.api.entity.Article;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	List<Article> findAll();
	Optional<Article> findById(int id);
	
}