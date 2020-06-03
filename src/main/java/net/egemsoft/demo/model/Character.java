package net.egemsoft.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Karakter bilgilerini karsilayan/kaydeden tablo modeli, entity.
 * @author Mehmet Atif Imal
 *
 */
@Entity
@Table(name = "CHARACTER")
public class Character {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "CHR_NAME")
	private String name;

	@Column(name = "CHR_STAT")
	private String status;

	@Column(name = "CHR_SPEC")
	private String species;

	@Column(name = "CHR_TYPE")
	private String type;

	@Column(name = "CHR_GND")
	private String gender;

	// Gomulu alt tablo. Tek kayit olsa da ayri bir Embeddable sinif tercih edildi.
	@Embedded
	private ChrOriginLocation origin;

	// Gomulu alt tablo. Tek kayit olsa da ayri bir Embeddable sinif tercih edildi.
	@Embedded
	private ChrCurrentLocation location;

	@Column(name = "CHR_IMG")
	private String image;

	@ElementCollection(targetClass = String.class)
	private List<String> episode = new ArrayList<String>();

	// (Hatirlatma) AppMain'de datalar okunurken URL'den ID bilgisi alinarak eklenmisti,
	// Ilk kayit icin bu bilgi "1",
	// "https://rickandmortyapi.com/api/character/1" degil.
	@Column(name = "CHR_URL")
	private String url;

	@Column(name = "CHR_CRTD")
	private String created;

	public Character() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public ChrOriginLocation getOrigin() {
		return origin;
	}

	public void setOrigin(ChrOriginLocation origin) {
		this.origin = origin;
	}

	public ChrCurrentLocation getLocation() {
		return location;
	}

	public void setLocation(ChrCurrentLocation location) {
		this.location = location;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<String> getEpisode() {
		return episode;
	}

	public void setEpisode(List<String> episode) {
		this.episode = episode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

}
