package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Model of <b>blog entry</b> from table <i>blog_entries</i>.
 * 
 * @author dbrcina.
 *
 */
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
@NamedQueries({ @NamedQuery(name = "GetBlogs.query", query = "select b from BlogEntry as b where b.creator=:user") })
public class BlogEntry {

	/**
	 * Blog entry's <i>primary key</i>.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * List of comments posted on this specific blog ordered by posted on.<br>
	 * It is a foreign key which references <i>blog_comments(blogEntry)</i>
	 * property.
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	private List<BlogComment> comments = new ArrayList<>();

	/**
	 * Date which represents when this blog was created.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdAt;

	/**
	 * Date which represents when this blog was modified.<br>
	 * It can be <code>null</code>, which means that there were not any
	 * modifications.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date lastModifiedAt;

	/**
	 * Blog's title. It can consist of max <i>200</i> characters.
	 */
	@Column(length = 200, nullable = false)
	private String title;

	/**
	 * Blog's text. It can consist of max <i>4KB</i> content.
	 */
	@Column(length = 4096, nullable = false)
	private String text;

	/**
	 * A reference to creator of this blog.
	 */
	@ManyToOne
	@JoinColumn(nullable = true)
	private BlogUser creator;

	public BlogUser getCreator() {
		return creator;
	}

	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	/**
	 * Getter for blog's id.
	 * 
	 * @return blog's id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter for blog's id.
	 * 
	 * @param id blog's id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for list of blog comments.
	 * 
	 * @return list of blog comments.
	 */
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Setter for list of blog comments.
	 * 
	 * @param comments list of blog comments.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Getter for {@link #createdAt} property.
	 * 
	 * @return {@link #createdAt} property.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter for {@link #createdAt} property.
	 * 
	 * @param createdAt date created at.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Getter for {@link #lastModifiedAt} property.
	 * 
	 * @return {@link #lastModifiedAt} property.
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter for {@link #lastModifiedAt} property.
	 * 
	 * @param lastModifiedAt date last modified.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Getter for blog's title.
	 * 
	 * @return blog's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for blog's title.
	 * 
	 * @param title blog's title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for blog's text.
	 * 
	 * @return blog's text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter for blog's text.
	 * 
	 * @param text blog's text.
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BlogEntry))
			return false;
		BlogEntry other = (BlogEntry) obj;
		return Objects.equals(id, other.id);
	}

}
