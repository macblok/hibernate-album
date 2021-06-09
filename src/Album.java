


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="albums")
public class Album {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column
	private String name;

	@Column
	private String description;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="album_id")
	private Set<Photo> photos = new HashSet<Photo>();

	public Album() {
	}
	
	public Album(String name, String description, User user) {
		this.name = name;
		this.description = description;
		user.addAlbum(this);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Photo> getPhotos() {
		return photos;
	}

	public void addPhoto(Photo photo) {
		photos.add(photo);
	}

	public void removePhoto(Photo photo) {
		photos.remove(photo);
	}

}
