package com.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerLoginResponse {

	private String customerMobileNumber;
	private String customerPassword;
	private String customerUserCode;
	private String customerOpt;
	private String customerStatus;

}
