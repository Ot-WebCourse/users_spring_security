package users.models;

import java.util.Date;

public class Post {
	private String author;
    private String text;
    private Date createdAt;
    
    public Post() { //TODO for DB
		super();
	}

	public Post(String author, String text, Date createdAt) {
		super();
		this.author = author;
		this.text = text;
		this.createdAt = createdAt;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
