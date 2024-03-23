package com.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.DummyModel.SingleProduct;

@RestController
@RequestMapping("sellerApp")
public class SellerAppController {

	@Autowired
	private RestTemplate restTamplate;

	@GetMapping("/All/PlacedOrder")
	public List getAllPlacedOrder() {
		List data = this.restTamplate.getForObject("http://localhost:8082/api/get/allproductForSell", List.class);
		return data;
	}

	@GetMapping("All/product")
	public List getAllProduct() {
		List data = this.restTamplate.getForObject("http://localhost:8082/api/get/allProductData", List.class);
		return data;
	}

	@PostMapping("/get/AllSubProduct")
	public ResponseEntity<List> getAllSubProductofEspacificProduct(
			@RequestParam("ProductMasterCode") String ProductMasterCode) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("ProductMasterCode", ProductMasterCode);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<List> response = this.restTamplate
				.postForEntity("http://localhost:8082/api/get/allSubProductWithImage", request, List.class);
		return response;
	}

	@PostMapping("/get/AllSubProductWithSeller")
	public ResponseEntity<List> getAllSubProductWithSeller(
			@RequestParam("ProductMasterSubCode") String ProductMasterSubCode) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("ProductMasterSubCode", ProductMasterSubCode);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<List> response = this.restTamplate
				.postForEntity("http://localhost:8082/api/get/AllsubProductWithSeller", request, List.class);
		return response;
	}

	@PostMapping("/get/SingleSubProduct")
	public ResponseEntity<SingleProduct> getSingleSubProductofEspacificProduct(
			@RequestParam("OrderCode") String OrderCode) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("OrderCode", OrderCode);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<SingleProduct> response = this.restTamplate.postForEntity(
				"http://localhost:8082/api/get/SingleEspecificProductForSell", request, SingleProduct.class);

		return response;
	}

}
