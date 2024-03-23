package com.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CustomerAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String customerUserCode;
	private String addressBillingOrHome;
	private String address1;
	private String address2;
	private String city;
	private String country;
	private int pinCode;
	private LocalDateTime effectiveDate;

}
