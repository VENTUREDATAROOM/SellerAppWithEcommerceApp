package com.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerSignUpRequest {

	private String customerName;
	private String customerEmail;
	private String customerMobileNumber;
	private String customerPassword;

}
