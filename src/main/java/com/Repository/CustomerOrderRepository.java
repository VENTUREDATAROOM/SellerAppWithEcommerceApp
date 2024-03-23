package com.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Entity.CustomerOrder;

public interface CustomerOrderRepository extends MongoRepository<CustomerOrder, String> {

	List<CustomerOrder> findByCustomerUserCode(String customerUserCode);

}
