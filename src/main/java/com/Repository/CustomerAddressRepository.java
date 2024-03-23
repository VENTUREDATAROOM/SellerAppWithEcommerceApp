package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Entity.CustomerAddress;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

	CustomerAddress findByCustomerUserCode(String customerUserCode);

}
