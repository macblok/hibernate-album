
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "photos")
public class Photo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private String name;

	@Column
	private Date date;

	@ManyToOne
	private User owner;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "users_photos", joinColumns = @JoinColumn(name = "photo_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> likes = new HashSet<User>();

	public Photo() {
	}

	public Photo(String name, User owner) {
		this.name = name;
		this.date = new Date(System.currentTimeMillis());
		this.owner = owner;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public Set<User> getLikes() {
		return likes;
	}

	public void addLike(User user) {
		if (!likes.contains(user)) {
			if (user.getFriends().contains(owner)) {
				likes.add(user);
				user.addLike(this);
			} else {
				System.out.println("Musicie byæ znajomymi aby móc polubieæ zdjêcie.");
			}
		}
	}

	public void removeLike(User user) {
		if (likes.contains(user)) {
			likes.remove(user);
			user.removeLike(this);
		}
	}

	public User getOwner() {
		return owner;
	}

}
