package fr.dawan.nogashi.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;


@Entity
@Component
public class ProductDetail extends DbObject {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false, length = 500)
	private String name;
	@Column(nullable = false, length = 1000)
	private String description;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	private EnumManager enumManager;
	
	
	
	
	
	//------------------------------
	
	public ProductDetail(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "ProductDetail [name=" + name + ", description=" + description + "]";
	}

	public ProductDetail() {
		super();
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

	public EnumManager getEnumManager() {
		return enumManager;
	}

	public void setEnumManager(EnumManager enumManager) {
		this.enumManager = enumManager;
	}
	
	
}
