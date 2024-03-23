package com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Entity.Customer;
import com.Entity.CustomerAddress;
import com.Repository.CustomerRepository;
import com.Service.CustomerAddressService;
import com.StatusModel.AddressResponse;

@RestController
@RequestMapping("/address")
public class CustomerAddressController {

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private CustomerAddressService addressService;

	@PostMapping("/add/CustomerAddress")
	public ResponseEntity<?> addAddressOfCustomer(@RequestBody CustomerAddress addressData) {

		Customer customerData = this.customerRepo.findByCustomerUserCode(addressData.getCustomerUserCode());
		System.out.print(customerData);
		if (customerData == null) {
			return new ResponseEntity<String>("Opps! UserCode Not Valid !!", HttpStatus.BAD_GATEWAY);
		}

		try {
			AddressResponse responseData = this.addressService.insertCustomerAddress(addressData);
			if (responseData == null) {
				throw new NullPointerException("No such Data. !");
			}
			return new ResponseEntity<AddressResponse>(responseData, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
		}
	}

}
