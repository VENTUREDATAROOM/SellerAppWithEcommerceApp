package com.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DummyModel.CartProduct;
import com.Entity.CustomerCart;
import com.Service.CustomerCartService;

@RestController
@RequestMapping("/cart")
public class CustomerCartController {

	@Autowired
	private CustomerCartService customerCartService;

	@PostMapping("/addToCart")
	public ResponseEntity<?> addToCartproduct(@RequestParam("UserCode") String userCode,
			@RequestParam("OrderCode") String orderCode, @RequestParam("Quantity") int quantity) {

		try {
			CustomerCart responseData = this.customerCartService.addItemToCart(userCode, orderCode, quantity);
			if (responseData == null) {
				throw new NullPointerException("Opps ! item  not Inserted..!");
			}
			return new ResponseEntity<CustomerCart>(responseData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("bad Getway", HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping("/moveToCart")
	public ResponseEntity<?> showAllCartData(@RequestParam("CustomerUserCode") String customerUserCode) {

		try {
			List<CartProduct> ResponseData = this.customerCartService.getListOfCart(customerUserCode);
			return new ResponseEntity<List<CartProduct>>(ResponseData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_GATEWAY);
		}
	}

}
