
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private String userName;

	@Column
	private Date joinDate;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private Set<Album> userAlbums = new HashSet<Album>();


	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private Set<Photo> userPhotos = new HashSet<Photo>();

	@ManyToMany(mappedBy = "likes", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Photo> likedPhotos = new HashSet<Photo>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "users_users", joinColumns = @JoinColumn(name = "user1_id"), inverseJoinColumns = @JoinColumn(name = "user2_id"))
	private Set<User> friends = new HashSet<User>();

	public User() {
	}

	public User(String name) {
		this.userName = name;
		this.joinDate = new Date(System.currentTimeMillis());
	}

	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public Set<Album> getUserAlbums() {
		return userAlbums;
	}

	public void addAlbum(Album album) {
		userAlbums.add(album);
	}

	public void removeAlbum(Album album) {
		userAlbums.remove(album);
	}

	public Set<Photo> getUserPhotos() {
		return userPhotos;
	}

	public void addPhoto(Photo photo) {
		userPhotos.add(photo);
	}

	public void removePhoto(Photo photo) {
		userPhotos.remove(photo);
	}

	public Set<Photo> getLikedPhotos() {
		return likedPhotos;
	}

	public void addLike(Photo photo) {
		if (!likedPhotos.contains(photo)) {
			if (friends.contains(photo.getOwner())) {
				likedPhotos.add(photo);
				photo.addLike(this);
			} else {
				System.out.println("Musicie byæ znajomymi aby móc polubieæ zdjêcie.");
			}
		}
	}

	public void removeLike(Photo photo) {
		if (likedPhotos.contains(photo)) {
			likedPhotos.remove(photo);
			photo.removeLike(this);
		}
	}

	public Set<User> getFriends() {
		return friends;
	}

	public void addFriend(User user) {
		if (!friends.contains(user)) {
			friends.add(user);
			user.addFriend(this);
		}
	}

	public void removeFriend(User user) {
		if (friends.contains(user)) {
			friends.remove(user);
			user.removeFriend(this);
		}
	}

}
