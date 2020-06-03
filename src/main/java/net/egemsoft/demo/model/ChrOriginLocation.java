package net.egemsoft.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Karakterlerin dogdugu konumunun bilgilerini karsiladigimiz/kaydettigimiz tablo modeli, embeddable.
 * @author Mehmet Atif Imal
 *
 */
@Embeddable
public class ChrOriginLocation {

	@Column(name = "ORI_LOC_NAME")
	private String name;

	@Column(name = "ORI_LOC_URL")
	private String url;

	public ChrOriginLocation() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
