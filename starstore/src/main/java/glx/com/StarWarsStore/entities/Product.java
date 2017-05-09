package glx.com.StarWarsStore.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Entity
public class Product {

	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	private String title;
	@NotNull
	private Integer price;
	@NotNull
	private String zipcode;
	@NotNull
	private String seller;
	@NotNull
	private String thumbnailHd;
	@NotNull
	private String date;

	
	Product() {
	}
	
	public Product(String title, Integer price, String zipcode, String seller, String thumbnailHd, String date) {
		this.title = title;
		this.price = price;
		this.zipcode = zipcode;
		this.seller = seller;
		this.thumbnailHd = thumbnailHd;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getThumbnailHd() {
		return thumbnailHd;
	}

	public void setThumbnailHd(String thumbnailHd) {
		this.thumbnailHd = thumbnailHd;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", price=" + price + ", zipcode=" + zipcode + ", seller="
				+ seller + ", thumbnailHd=" + thumbnailHd + ", date=" + date + "]";
	}



}
