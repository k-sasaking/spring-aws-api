package com.sample.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.api.entity.Article;
import com.sample.api.repository.ArticleRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	ArticleRepository articleRepository;
	
	@Override
	public List<Article> getAllArticles() {
		return articleRepository.findAll();
	}

	@Override
	public Optional<Article> getArticleById(int id) {
		return articleRepository.findById(id);
	}

	@Override
	public void createArticle(Article article) {
		articleRepository.saveAndFlush(article);	
	}

	@Override
	public void updateArticle(Article article) {
		articleRepository.saveAndFlush(article);			
	}

	@Override
	public void deleteArticle(Integer id) {
		articleRepository.deleteById(id);	
	}

}