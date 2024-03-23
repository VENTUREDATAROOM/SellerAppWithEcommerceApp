package com.Entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection = "CustomerOrder")
public class CustomerOrder {

	@Id
	private String id;
	private String orderId;
	private Long addressID;
	private String customerUserCode;
	private List<CustomerCart> productItem;

}
