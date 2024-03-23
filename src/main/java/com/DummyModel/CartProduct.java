package com.DummyModel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartProduct {

	private Long id;
	private String customerUserCode;
	private String productMasterSubCode;
	private int price;
	private String productName;
	private String productSubName;
	private String orderCode;
	private String sellerCode;
	private int quntity;
	private String cartUnicCode;
	private List<String> imageData;

}
