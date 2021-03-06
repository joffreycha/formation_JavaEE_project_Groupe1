package fr.dawan.nogashi.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Component
public class Commerce extends DbObject {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String codeSiret;
	private String uniqueIdName;					//to make difference between 2 subway in the same city (or the same street), you add a unique subName to make them different.
	@Column(length = 1000)
	private String description;
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	@OneToOne(fetch = FetchType.EAGER)
	private SchedulerWeek schedulerWeek;						// horaires. Todo in futur : a week calendar also for not school period + a holiday calendar + by saison.
	
	private String pictureLogo = "NoLogo.jpg";					// logo for the merchant's (or commerce's) mark
	private String pictureDescription = "NoDescription.jpg";	// real picture of commerce, or patchwork to describe the commerce.
	private boolean isOpened = false;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Merchant merchant;
	
	@OneToMany @XmlTransient @JsonIgnore
	private List<CommerceCategory> commerceCategories = new ArrayList<CommerceCategory>();
	
	@ManyToMany(mappedBy = "commerces") @XmlTransient @JsonIgnore
	private List<ProductTemplate> productTemplates = new ArrayList<ProductTemplate>();
	
	@OneToMany(mappedBy = "commerce") @XmlTransient @JsonIgnore
	private List<Product> products = new ArrayList<Product>();
	
	@OneToMany(mappedBy = "commerce") @XmlTransient @JsonIgnore
	private List<ShoppingCartByCommerce> shoppingCartByCommerces = new ArrayList<ShoppingCartByCommerce>();			//equivalent des commandes payées.
	
	
	@OneToOne
	private Faq faq = null;


	
	/* ---------- TRUCS A RAJOUTER DANS LE CONSTRUCTEUR AU FUR ET A MESURE ---------- */
	// Merchant merchant
	public Commerce(String name, String codeSiret, Address address, String pictureLogo, String pictureDescription, String description, SchedulerWeek listsw, String uniqueIdName) {
		super();
		this.name = name;
		this.codeSiret = codeSiret;
		this.address = address;
		this.pictureLogo = pictureLogo;
		this.pictureDescription = pictureDescription;
		this.description = description;
		this.schedulerWeek = listsw;
		this.uniqueIdName = uniqueIdName;
		// this.merchant = merchant;
	}
	
	
	public Commerce(String name, String codeSiret, String uniqueIdName) {
		super();
		this.name = name;
		this.codeSiret = codeSiret;
		this.uniqueIdName = uniqueIdName;
	}


	public void addCommerceCategory(CommerceCategory cc) 
	{	
		if(!this.commerceCategories.contains(cc))
			this.commerceCategories.add(cc);	
	}
	public void removeCommerceCategory(CommerceCategory cc) 
	{	
		if(this.commerceCategories.contains(cc))
			this.commerceCategories.remove(cc);	
	}
	
	public void addProductTemplate(ProductTemplate pt) 
	{	
		if(!this.productTemplates.contains(pt))
		{
			this.productTemplates.add(pt);
			pt.addCommerces(this);
		}
	}
	public void removeProductTemplate(ProductTemplate pt) 
	{	
		if(this.productTemplates.contains(pt))
		{
			this.productTemplates.remove(pt);
			pt.removeCommerces(this);
		}
	}
	
	public void addShoppingCartByCommerces(ShoppingCartByCommerce sc) {
		if(!shoppingCartByCommerces.contains(sc)) {
			shoppingCartByCommerces.add(sc);
			sc.setCommerce(this);
		}
	}
	public void removeShoppingCartByCommerces(ShoppingCartByCommerce sc) {
		if(shoppingCartByCommerces.contains(sc)) {
			shoppingCartByCommerces.remove(sc);
			sc.setCommerce(null);
		}
	}
	
	
	public void addProduct(Product p) 
	{	
		if(!this.products.contains(p))
		{
			this.products.add(p);
			p.setCommerce(this);
		}
	}
	public void removeProduct(Product p) 
	{	
		if(this.products.contains(p))
		{
			this.products.remove(p);
			p.setCommerce(null);
		}
	}
	public void clearProduct() 
	{	
		for(Product p : this.products)
			p.setCommerce(null);
		
		this.products.clear();		
	}
	
	
	
	
	
	
	
	
	//----------------------------
	
	public Commerce() {
		super();
	}	

	@Override
	public String toString() {
		return "Commerce [name=" + name + ", description=" + description + ", uniqueIdName=" + uniqueIdName
				+ ", isOpened=" + isOpened + ", merchant=" + merchant + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeSiret() {
		return codeSiret;
	}

	public void setCodeSiret(String codeSiret) {
		this.codeSiret = codeSiret;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUniqueIdName() {
		return uniqueIdName;
	}

	public void setUniqueIdName(String uniqueIdName) {
		this.uniqueIdName = uniqueIdName;
	}

	public boolean isOpened() {
		return isOpened;
	}

	public void setOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	

	public SchedulerWeek getSchedulerWeek() {
		return schedulerWeek;
	}


	public void setSchedulerWeek(SchedulerWeek schedulerWeek) {
		this.schedulerWeek = schedulerWeek;
	}


	public List<ShoppingCartByCommerce> getShoppingCartByCommerces() {
		return shoppingCartByCommerces;
	}


	public void setShoppingCartByCommerces(List<ShoppingCartByCommerce> shoppingCartByCommerces) {
		this.shoppingCartByCommerces = shoppingCartByCommerces;
	}


	public String getPictureLogo() {
		return pictureLogo;
	}

	public void setPictureLogo(String pictureLogo) {
		this.pictureLogo = pictureLogo;
	}

	public String getPictureDescription() {
		return pictureDescription;
	}

	public void setPictureDescription(String pictureDescription) {
		this.pictureDescription = pictureDescription;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		
		if(this.merchant==merchant)					//avoid infinity loops
			return;
		
		if(this.merchant!=null)
			merchant.removeCommerces(this);
		
		this.merchant = merchant;

		if(this.merchant!=null)
			merchant.addCommerces(this);
	}

	public List<CommerceCategory> getCommerceCategories() {
		return commerceCategories;
	}

	public void setCommerceCategories(List<CommerceCategory> commerceCategories) {
		this.commerceCategories = commerceCategories;
	}

	public List<ProductTemplate> getProductTemplates() {
		return productTemplates;
	}

	public void setProductTemplates(List<ProductTemplate> productTemplates) {
		this.productTemplates = productTemplates;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Faq getFaq() {
		return faq;
	}

	public void setFaq(Faq faq) {
		this.faq = faq;
	}
	
	
	
	
	
	
}
