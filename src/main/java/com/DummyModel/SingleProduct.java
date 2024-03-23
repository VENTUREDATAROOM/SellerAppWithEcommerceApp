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
public class SingleProduct {
	
	private String productName;
	private String productSubName;
	private String userCode;
	private String productMasterCode;
	private String ProductMasterSubCode;
	private String mandiName;
	private String quantity;
	private String expectedPrice;
	private String totalPrice;
	private String location;
	private String quality;
	private String distanceFromMandi;
	private String orderOtp;
	private String address;
	private String orderCode;
	private String harvestDate;
	private String transactionId;
	private List<String> imageData;

}
