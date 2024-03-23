package com.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Entity.CustomerOrder;
import com.Service.CustomerOrderService;
import com.StatusModel.OrderResponse;

@RestController
@RequestMapping("/order")
public class CustomerOrderController {

	@Autowired
	private CustomerOrderService orderService;

	@PostMapping("/CreatedOrder")
	public ResponseEntity<?> createdOrderOfCustomer(@RequestParam("UserCode") String userCode) {
		try {
			OrderResponse responseData = this.orderService.createOrder(userCode);
			if (responseData == null) {
				throw new NullPointerException("No such Data Inserted  !!");
			}
			return new ResponseEntity<OrderResponse>(responseData, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getAllOrderOfCustomer")
	public ResponseEntity<?> getAllOrderOfCustomer(@RequestParam("UserCode") String userCode) {
		try {
			List<CustomerOrder> responseData = this.orderService.getAllOrderOfCustomer(userCode);
			if (responseData == null) {
				throw new NullPointerException("No such Data Inserted  !!");
			}
			return new ResponseEntity<List<CustomerOrder>>(responseData, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
		}
	}
//
//	@PostMapping("/nn")
//	public String dd(@RequestParam("LAL") MultipartFile file) {
//
//		return "kk";
//	}

}
