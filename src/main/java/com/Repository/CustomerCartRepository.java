package com.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Entity.CustomerCart;

public interface CustomerCartRepository extends JpaRepository<CustomerCart, Long> {

	List<CustomerCart> findByCustomerUserCode(String customerUserCode);

}
