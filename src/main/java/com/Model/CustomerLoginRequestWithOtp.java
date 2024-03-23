package com.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerLoginRequestWithOtp {

	private String customerMobileNumber;
	private String customerPassword;
	private String customerOtp;

}
