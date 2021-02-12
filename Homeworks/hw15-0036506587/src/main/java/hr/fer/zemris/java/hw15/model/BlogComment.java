package hr.fer.zemris.java.hw15.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Model of <b>blog comment</b> from table <i>blog_comments</i>.
 * 
 * @author dbrcina
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * <i>Primary key</i> for blog comment.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * <i>Foreign key</i> which references <i>blog_entries(comments)</i> property.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private BlogEntry blogEntry;

	/**
	 * Representation of user's email. It can consist of max <i>100</i> characters.
	 */
	@Column(length = 100, nullable = false)
	private String usersEMail;

	/**
	 * Comment message. It can consist of max <i>4KB</i> content.
	 */
	@Column(length = 4096, nullable = false)
	private String message;

	/**
	 * Date which represents when this comment was published.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date postedOn;

	/**
	 * Getter for comment's id.
	 * 
	 * @return comment's id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter for comment's id.
	 * 
	 * @param id comment's id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for reference on some {@link BlogEntry}.
	 * 
	 * @return reference on blog entry.
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Setter for {@link #blogEntry}.
	 * 
	 * @param blogEntry blog entry.
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter for users email.
	 * 
	 * @return users email.
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter for users email.
	 * 
	 * @param usersEMail users email.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter for comment message.
	 * 
	 * @return comment message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for comment message.
	 * 
	 * @param message comment message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for {@link #postedOn} property.
	 * 
	 * @return {@link #postedOn} property.
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter for {@link #postedOn} property.
	 * 
	 * @param postedOn posted on.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		if (!(obj instanceof BlogComment))
			return false;
		BlogComment other = (BlogComment) obj;
		return Objects.equals(id, other.id);
	}

}
