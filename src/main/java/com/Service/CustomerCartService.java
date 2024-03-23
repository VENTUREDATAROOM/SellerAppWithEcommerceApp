package com.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.DummyModel.CartProduct;
import com.DummyModel.SingleProduct;
import com.Entity.CustomerCart;
import com.Repository.CustomerCartRepository;

@Service
public class CustomerCartService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CustomerCartRepository customerCartRepo;

	public CustomerCart addItemToCart(String userCode, String orderCode, int Quantity) {
		CustomerCart cartEntity = new CustomerCart();

		try {

			SingleProduct productData = getSingleProductData(orderCode);

			cartEntity.setCustomerUserCode(userCode);
			cartEntity.setProductName(productData.getProductName());
			cartEntity.setProductSubName(productData.getProductSubName());
			cartEntity.setOrderCode(orderCode);
			cartEntity.setQuntity(Quantity);

			int price = Quantity * Integer.parseInt(productData.getExpectedPrice());
			cartEntity.setPrice(price);
			cartEntity.setSellerCode(productData.getUserCode());
			cartEntity.setProductMasterSubCode(productData.getProductMasterSubCode());
			cartEntity.setCartUnicCode(generateCartCode());
			CustomerCart responseData = this.customerCartRepo.save(cartEntity);
			if (responseData == null) {
				return null;
			} else {
				return responseData;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public SingleProduct getSingleProductData(String orderCode) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("OrderCode", orderCode);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<SingleProduct> response = this.restTemplate.postForEntity(
				"http://localhost:8082/api/get/SingleEspecificProductForSell", request, SingleProduct.class);

		return response.getBody();
	}

	public List<CartProduct> getListOfCart(String customerUserCode) {

		List<CustomerCart> customerCartData = this.customerCartRepo.findByCustomerUserCode(customerUserCode);
		List<CartProduct> listCartResponse = new ArrayList<>();
		for (CustomerCart item : customerCartData) {

			SingleProduct productData = getSingleProductData(item.getOrderCode());
			CartProduct cartResponse = new CartProduct();

			cartResponse.setProductName(item.getProductName());
			cartResponse.setProductSubName(item.getProductSubName());
			cartResponse.setProductMasterSubCode(item.getProductMasterSubCode());
			cartResponse.setPrice(item.getPrice());

			cartResponse.setCustomerUserCode(item.getCustomerUserCode());
			cartResponse.setSellerCode(item.getSellerCode());
			cartResponse.setOrderCode(item.getOrderCode());
			cartResponse.setCartUnicCode(item.getCartUnicCode());
			cartResponse.setImageData(productData.getImageData());
			listCartResponse.add(cartResponse);
		}
		return listCartResponse;
	}

	static String generateCartCode() {
		String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		// create a super set of all characters
		String allCharacters = alphabetsInLowerCase + alphabetsInUpperCase + numbers;
		// initialize a string to hold result
		StringBuffer randomString = new StringBuffer();
		// loop for 10 times
		for (int i = 0; i < 10; i++) {
			// generate a random number between 0 and length of all characters
			int randomIndex = (int) (Math.random() * allCharacters.length());
			// retrieve character at index and add it to result
			randomString.append(allCharacters.charAt(randomIndex));
		}
		return randomString.toString();
	}

}
