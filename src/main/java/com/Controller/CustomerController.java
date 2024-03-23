package com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Entity.Customer;
import com.Helper.JwtHelper;
import com.Model.CustomerLoginRequest;
import com.Model.CustomerLoginRequestWithOtp;
import com.Model.CustomerLoginResponse;
import com.Model.CustomerSignUpRequest;
import com.Model.CustomerSignUpResponse;
import com.Model.JwtResponse;
import com.Repository.CustomerRepository;
import com.Service.CustomerService;

@RestController
@RequestMapping("/auth")
public class CustomerController {

	@Autowired
	private CustomerRepository CustomerRepo;

	@Autowired
	private JwtHelper JwtHelper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomerService CustomerService;

	@GetMapping("/yes")
	public String nn() {
		return "yes !!";
	}

	@PostMapping("/signUp/Customer")
	public ResponseEntity<?> addCustomerData(@RequestBody CustomerSignUpRequest CustomerData) {
		try {
			CustomerSignUpResponse ResponseData = this.CustomerService.customerRegistation(CustomerData);
			if (ResponseData == null) {
				throw new NullPointerException("no data inserted !!");
			}
			return new ResponseEntity<CustomerSignUpResponse>(ResponseData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Opps! Bad getway..!", HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping("/login/customer")
	public ResponseEntity<?> loginWithCredencial(@RequestBody CustomerLoginRequest LoginData) {

		System.out.print(LoginData);

		// return "bhai";

		try {
			CustomerLoginResponse ResponseData = this.CustomerService.customerLogin(LoginData);
			if (ResponseData == null) {
				throw new NullPointerException("Credenctials Faild !!");
			}
			return new ResponseEntity<CustomerLoginResponse>(ResponseData, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping("/login/customerWithOtp")
	public ResponseEntity<?> loginWithOtp(@RequestBody CustomerLoginRequestWithOtp LoginWithOtp) {

		boolean check = this.CustomerService.CheckOtp(LoginWithOtp.getCustomerOtp());
		if (check == false) {
			return new ResponseEntity<String>("wrong Otp", HttpStatus.BAD_REQUEST);
		}

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				LoginWithOtp.getCustomerMobileNumber(), LoginWithOtp.getCustomerPassword()));
		if (authentication.isAuthenticated()) {
			UserDetails userDetailseData = this.CustomerService
					.loadUserByUsername(LoginWithOtp.getCustomerMobileNumber());

			Customer CustomerData = this.CustomerRepo
					.findByCustomerMobileNumber(LoginWithOtp.getCustomerMobileNumber());

			String token = this.JwtHelper.generateToken(userDetailseData);

			JwtResponse jwtResponseData = new JwtResponse();
			jwtResponseData.setCustomerName(CustomerData.getCustomerName());
			jwtResponseData.setCustomerUserCode(CustomerData.getCustomerUserCode());
			jwtResponseData.setToken(token);
			return new ResponseEntity<JwtResponse>(jwtResponseData, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Bad request", HttpStatus.BAD_REQUEST);

	}

}
