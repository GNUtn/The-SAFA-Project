package org.safaproject.safa.model.content;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.safaproject.safa.model.indicator.Indicator;
import org.safaproject.safa.model.tag.Tag;
import org.safaproject.safa.model.user.User;

/**
 * @author reyiyo
 * 
 */
@Entity
@Table(name = "CONTENT")
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "contentId")
	private Long id;

	@Column(name = "title", nullable = false)
	@Size(min = 1, message = "{Content.title.Size}")
	private String title;

	@Column(name = "description", nullable = false)
	@Size(min = 1, message = "{Content.description.Size}")
	private String description;

	@Column(name = "uploadDate", nullable = false)
	private Date uploadDate;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "TAG_CONTENT")
	private Set<Tag> tags;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "INDICATOR_CONTENT")
	private Set<Indicator> indicators;

	@Column(name = "available", nullable = false)
	private Boolean available = true;

	@OneToMany
	@JoinColumn(name = "contentId")
	@Size(min = 1, message = "{Content.resources.Size}")
	private Set<Resource> resources;

	@ManyToOne
	@JoinColumn(name = "idThumbnailResource")
	private Resource thumbnail;

	@Column(name = "reviewed", nullable = false)
	private Boolean reviewed = false;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "contentId")
	private Set<Comment> comments;

	public Content() {
		// Hibernate constructor
	}

	public Content(String title, String description, Date uploadDate,
			User user, Set<Tag> tags, Set<Indicator> indicators,
			boolean available, Set<Resource> resources, Resource thumbnail,
			boolean reviewed, Set<Comment> comments) {
		super();
		this.title = title;
		this.description = description;
		this.uploadDate = uploadDate;
		this.user = user;
		this.tags = tags;
		this.indicators = indicators;
		this.available = available;
		this.resources = resources;
		this.thumbnail = thumbnail;
		this.reviewed = reviewed;
		this.comments = comments;
	}

	/**
	 * @return the contentId
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the contentId to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the uploadDate
	 */
	public Date getUploadDate() {
		return uploadDate;
	}

	/**
	 * @param uploadDate
	 *            the uploadDate to set
	 */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the tags
	 */
	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	/**
	 * @return the indicadores
	 */
	public Set<Indicator> getIndicators() {
		return indicators;
	}

	/**
	 * @param indicadores
	 *            the indicadores to set
	 */
	public void setIndicators(Set<Indicator> indicators) {
		this.indicators = indicators;
	}

	/**
	 * @return the available
	 */
	public Boolean isAvailable() {
		return available;
	}

	/**
	 * @param available
	 *            the available to set
	 */
	public void setAvailable(Boolean available) {
		this.available = available;
	}

	/**
	 * @return the resources
	 */
	public Set<Resource> getResources() {
		return resources;
	}

	/**
	 * @param resources
	 *            the resources to set
	 */
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	/**
	 * @return the thumbnail
	 */
	public Resource getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail
	 *            the thumbnail to set
	 */
	public void setThumbnail(Resource thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @return the reviewed
	 */
	public Boolean isReviewed() {
		return reviewed;
	}

	/**
	 * @param reviewed
	 *            the reviewed to set
	 */
	public void setReviewed(Boolean reviewed) {
		this.reviewed = reviewed;
	}

	/**
	 * @return the comments
	 */
	public Set<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

}
