package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Model of <b>blog user</b> form table <i>blog_users</i>.
 * 
 * @author dbrcina
 *
 */
@Entity
@Table(name = "blog_users")
@NamedQueries({ 
	@NamedQuery(name = "GetUsers.query", query = "select b from BlogUser as b"),
	@NamedQuery(name = "CheckNick.query", query = "select b from BlogUser as b where b.nick=:nick")
})
public class BlogUser {

	/**
	 * User's id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * User's first name. It cannot be <code>null</code> and can consist of max
	 * <i>100</i> characters.
	 */
	@Column(length = 100, nullable = false)
	private String firstName;

	/**
	 * User's last name. It cannot be <code>null</code> and can consist of max
	 * <i>100</i> characters.
	 */
	@Column(length = 100, nullable = false)
	private String lastName;

	/**
	 * User's nick. It cannot be <code>null</code> and can consist of max <i>100</i>
	 * characters.
	 */
	@Column(length = 100, nullable = false, unique = true)
	private String nick;

	/**
	 * User's email. It cannot be <code>null</code> and can consist of max
	 * <i>100</i> characters.
	 */
	@Column(length = 100, nullable = false)
	private String email;

	/**
	 * Property used for storing a <i>hex-encoded</i> hash value(calculated as SHA-1
	 * hash) obtained from users password.
	 */
	@Column(length = 100, nullable = false)
	private String passwordHash;

	/**
	 * A reference to blogs.
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<BlogEntry> blogEntries = new ArrayList<>();

	/**
	 * Getter for user's id.
	 * 
	 * @return user's id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter for user's id.
	 * 
	 * @param id user's id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for user's first name.
	 * 
	 * @return user's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for user's first name.
	 * 
	 * @param firstName first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for user's last name.
	 * 
	 * @return user's last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for user's last name.
	 * 
	 * @param lastName last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for user's nick.
	 * 
	 * @return user's nick.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for user's nick.
	 * 
	 * @param nick nick.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for user's email.
	 * 
	 * @return user's email.
	 */
	public String getEMail() {
		return email;
	}

	/**
	 * Setter for user's email.
	 * 
	 * @param email email.
	 */
	public void setEMail(String email) {
		this.email = email;
	}

	/**
	 * Getter for {@link #passwordHash} property.
	 * 
	 * @return {@link #passwordHash} property.
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter for {@link #passwordHash} property.
	 * 
	 * @param passwordHash password hash.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Getter for {@link #blogEntries} property.
	 * 
	 * @return {@link #blogEntries} property.
	 */
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * Setter for {@link #blogEntries} property.
	 * 
	 * @param blogEntrys blog entrys.
	 */
	public void setBlogEnties(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
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
		if (!(obj instanceof BlogUser))
			return false;
		BlogUser other = (BlogUser) obj;
		return Objects.equals(id, other.id);
	}

}
