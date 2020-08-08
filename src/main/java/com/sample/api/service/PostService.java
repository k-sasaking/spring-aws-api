package com.sample.api.service;

import java.util.List;
import java.util.Optional;

import com.sample.api.entity.Article;

public interface PostService {
	
	public List<Article> getAllArticles();
	public Optional<Article> getArticleById(int id);
	public void createArticle(Article article);
	public void updateArticle(Article article);
	public void deleteArticle(Integer id);

}