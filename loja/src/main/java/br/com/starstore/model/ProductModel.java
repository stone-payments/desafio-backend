package br.com.starstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductModel {

	@JsonIgnore()
	private Long id;

	private String title;
	private Integer price;
	private String zipcode;
	private String seller;
	private String thumbnailHd;
	private String date;

	public ProductModel() {
	}

	public ProductModel(String title, Integer price, String zipcode, String seller, String thumbnailHd, String date) {
		super();
		this.title = title;
		this.price = price;
		this.zipcode = zipcode;
		this.seller = seller;
		this.thumbnailHd = thumbnailHd;
		this.date = date;
	}

	@Override
	public String toString() {
		return "ProductModel [title=" + title + ", price=" + price + ", zipcode=" + zipcode + ", seller=" + seller
				+ ", thumbnailHd=" + thumbnailHd + ", date=" + date + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
