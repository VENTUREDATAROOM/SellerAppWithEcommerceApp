package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Entity.CurrentLocation;

public interface CurrentLocationRepository extends JpaRepository<CurrentLocation, Long> {

	CurrentLocation findByCustomerUserCode(String customerUserCode);

}
