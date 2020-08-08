package com.sample.api.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Article")
public class Article {

    /* Columnの定義 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "post_name", length = 30)
	private String postName;
    
    @Column(name = "post_date")
    private Date postDate;
	
    @Column(name = "post_text", length = 200)
	private String postText;
	
    @Column(name = "post_image", length = 256)
	private String postImage;

    @Column(name = "is_published")
	private boolean isPublished;
    
    public Article() {
    	
    }
    public Article(String name, String text, String image) {
    	this.postName = name;
    	this.postText = text;
    	this.postImage = image;
    	this.isPublished = true;
    }
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}
	public String getPostImage() {
		return postImage;
	}
	public void setPostImage(String postImage) {
		this.postImage = postImage;
	}
	public boolean isPublished() {
		return isPublished;
	}
	public void isPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}
	
}