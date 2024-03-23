package com.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CustomerCart {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String customerUserCode;
	private String productMasterSubCode;
	private int price;
	private String productSubName;
	private String productName;
	private String orderCode;
	private String sellerCode;
	private int quntity;
	private String cartUnicCode;

}
