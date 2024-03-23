package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entity.CurrentLocation;
import com.Repository.CurrentLocationRepository;

@Service
public class CurrentLocationService {

	@Autowired
	private CurrentLocationRepository currentLocationRepo;

	public CurrentLocation addCurrentLocation(CurrentLocation locationdata) {

		try {
			CurrentLocation locationResponse = this.currentLocationRepo.save(locationdata);
			if (locationResponse == null) {
				throw new NullPointerException("Opps! Location Not inserted ..! ");
			} else {
				return locationResponse;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public CurrentLocation getDataOfUser(String customerUserCode) {
		try {

			CurrentLocation ResponseData = this.currentLocationRepo.findByCustomerUserCode(customerUserCode);
			if (ResponseData == null) {
				throw new NullPointerException("Opps ! someThing wrong..!");
			}
			return ResponseData;

		} catch (Exception e) {
			return null;
		}
	}

}
