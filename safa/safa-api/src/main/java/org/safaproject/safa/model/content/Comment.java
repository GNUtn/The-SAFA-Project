package org.safaproject.safa.model.content;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.safaproject.safa.model.user.User;

@Entity
@Table(name = "COMMENT")
public class Comment {

	public Comment() {
		// Constructor for hibernate
	}

	public Comment(Date commentDate, User user, String commentText) {
		super();
		this.commentDate = commentDate;
		this.user = user;
		this.commentText = commentText;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "commentId")
	private Long id;

	@Column(name = "commentDate", nullable = false)
	private Date commentDate;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@Column(name = "commentText", nullable = false)
	@Size(min = 1, message = "{Comment.commentText.Size}")
	private String commentText;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

}
