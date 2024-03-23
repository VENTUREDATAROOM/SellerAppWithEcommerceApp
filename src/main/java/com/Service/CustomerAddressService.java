package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entity.CustomerAddress;
import com.Repository.CustomerAddressRepository;
import com.StatusModel.AddressResponse;

@Service
public class CustomerAddressService {

	@Autowired
	private CustomerAddressRepository addressrepo;

	public AddressResponse insertCustomerAddress(CustomerAddress addressData) {
		AddressResponse addressResponse = new AddressResponse();
		try {
			CustomerAddress responseData = this.addressrepo.save(addressData);
			if (responseData != null) {
				addressResponse.setStatus("Address Inserted SuccessFully !!");
			}
			return addressResponse;
		} catch (Exception e) {
			return null;
		}
	}

}
