package com.packt.webstore.domain.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.exception.ProductNotFoundException;

@Repository
public class InMemoryProductRepository implements ProductRepository{
	private List<Product> listOfProducts=new ArrayList<Product>();
	public InMemoryProductRepository() {
		Product iphone=new Product("P1234","iPhone 5s",new BigDecimal(500));
		iphone.setDescription("Apple iPhone 5s, smartfon z 4-calowym wyœwietlaczem o rozdzielczoœci 640x1136 oraz"
				+ " 8-megapikselowym aparatem");
		iphone.setCategory("Cat3");
		iphone.setManufacturer("Apple");
		iphone.setUnitsInStock(1000);
		
		Product laptop_dell = new Product("P1235","Dell Inspiron", new BigDecimal(700));
		laptop_dell.setDescription("Dell Inspiron, 14-calowy laptop (czarny)z procesorem Intel Core 3. generacji");
		laptop_dell.setCategory("Cat1");
		laptop_dell.setManufacturer("Dell");
		laptop_dell.setUnitsInStock(1000);
	    
		Product tablet_Nexus = new Product("P1236","Nexus 7", new BigDecimal(300));
		tablet_Nexus.setDescription("Google Nexus 7 jest najl¿ejszym 7-calowym tabletem z "
				+ "4-rdzeniowym procesorem Qualcomm Snapdragon™ S4 Pro");
		tablet_Nexus.setCategory("Cat2");
		tablet_Nexus.setManufacturer("Google");
		tablet_Nexus.setUnitsInStock(1000);
		
		listOfProducts.add(iphone);
		listOfProducts.add(laptop_dell);
		listOfProducts.add(tablet_Nexus);
	}
	
	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return listOfProducts;
	}

	@Override
	public Product getProductById(String productId) {
		Product productById=null;
		for(Product product:listOfProducts) {
			if(product!=null && product.getProductId()!=null && product.getProductId().equals(productId)) {
				productById=product;
				break;
			}
		}
		if(productById==null) {
			throw new ProductNotFoundException(productId);
		}
		return productById;
	}

	@Override
	public List<Product> getProductByCategory(String category) {
		List<Product> productsByCategory=new ArrayList<Product>();
		for(Product product:listOfProducts) {
			if(category.equalsIgnoreCase(product.getCategory())) {
				productsByCategory.add(product);
			}
		}
		return productsByCategory;
	}

	@Override
	public Set<Product> getProductByFilter(Map<String, List<String>> filterParams) {
		Set<Product> productByBrand=new HashSet<Product>();
		Set<Product> productByCategory=new HashSet<Product>();
		//keySet zwraca zbiór kluczy Mapy (tutaj zbior Stringów)
		Set<String> criterias=filterParams.keySet();
		if(criterias.contains("brand")){
			for(String brandName: filterParams.get("brand")) {
				for(Product product:listOfProducts) {
					if(brandName.equalsIgnoreCase(product.getManufacturer())) {
						productByBrand.add(product);
					}
				}
			}
		}
		if(criterias.contains("category")) {
			for(String category:filterParams.get("category")) {
				productByCategory.addAll(this.getProductByCategory(category));
			}
		}
		//Zachowuje tylko te elementy ktore nale¿¹ do kolekcji 'productByBrand'
		productByCategory.retainAll(productByBrand);
		return productByCategory;
	}

	@Override
	public List<Product> getProductByManufacturer(String manufacturer) {
		List<Product> productsByManufacturer=new ArrayList<Product>();
		System.out.println("Maniufaturer: "+manufacturer);
		for(Product product:listOfProducts) {
			if(manufacturer.equalsIgnoreCase(product.getManufacturer())) {
				productsByManufacturer.add(product);
			}
		}
		return productsByManufacturer;
	}

	@Override
	public Set<Product> getProductsByPriceFilter(Map<String, List<String>> filterParams) {
		Set<Product> lowPrice=new HashSet<Product>();
		Set<Product> highPrice=new HashSet<Product>();
		Set<String> criterias=filterParams.keySet();
		if(criterias.contains("low")) {
			for(String low_price:filterParams.get("low")) {
				BigDecimal price=new BigDecimal(low_price);
				System.out.println("Price low="+price);
				for(Product product:listOfProducts) {
					int res=price.compareTo(product.getUnitPrice());
					if(res<=0) {
						lowPrice.add(product);
						System.out.println("Dodano low: "+lowPrice.size());
					}
				}
			}
		}else {
			lowPrice.addAll(listOfProducts);
		}
		if(criterias.contains("high")) {
			for(String high_price:filterParams.get("high")) {
				BigDecimal price=new BigDecimal(high_price);
				System.out.println("Price high="+price);
				for(Product product:listOfProducts) {
					int res=price.compareTo(product.getUnitPrice());
					if(res>=0) {
						highPrice.add(product);
						System.out.println("Dodano high: "+highPrice.size());
					}
				}
			}
		}else {
			lowPrice.addAll(listOfProducts);
		}
		lowPrice.retainAll(highPrice);
		System.out.println("AFter retain, lowPrice Size="+lowPrice.size());
		return lowPrice;
	}

	@Override
	public void addProduct(Product product) {
		listOfProducts.add(product);
	}
}
