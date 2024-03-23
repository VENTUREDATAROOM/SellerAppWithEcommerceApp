package com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Entity.CurrentLocation;
import com.Entity.Customer;
import com.Repository.CustomerRepository;
import com.Service.CurrentLocationService;

@RestController
@RequestMapping("/location")
public class CurrentLocationController {

	@Autowired
	private CurrentLocationService currentLocationService;

	@Autowired
	private CustomerRepository customerRepo;

	@PostMapping("/add/CurrentLocation")
	public ResponseEntity<?> addLocationOfUser(@RequestBody CurrentLocation LocationData) {

		System.out.print(LocationData.getLocation());
		Customer customerData = this.customerRepo.findByCustomerUserCode(LocationData.getCustomerUserCode());
		System.out.print(customerData);
		if (customerData == null) {
			return new ResponseEntity<String>("Opps! UserCode Not Valid !!", HttpStatus.BAD_GATEWAY);
		}
		try {

			CurrentLocation LocationResponse = this.currentLocationService.addCurrentLocation(LocationData);
			if (LocationResponse == null) {
				throw new NullPointerException("Opps ! data not Inserted");
			}
			return new ResponseEntity<CurrentLocation>(LocationResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Opps! SomeThing Wrong !!", HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping("/get/CurrentLocation")
	public ResponseEntity<?> getCurrentLocation(@RequestParam("UserCode") String userCode) {

		try {
			CurrentLocation responseData = this.currentLocationService.getDataOfUser(userCode);
			if (responseData == null) {
				throw new NullPointerException("Opps! some thing wromg");
			}
			return new ResponseEntity<CurrentLocation>(responseData, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_GATEWAY);
		}
	}

}
