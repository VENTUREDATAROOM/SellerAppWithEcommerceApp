package com.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Entity.Customer;
import com.Entity.LoginOtp;
import com.Model.CustomerLoginRequest;
import com.Model.CustomerLoginResponse;
import com.Model.CustomerSignUpRequest;
import com.Model.CustomerSignUpResponse;
import com.Repository.CustomerRepository;
import com.Repository.LoginOtpRepository;

@Service
public class CustomerService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private LoginOtpRepository LoginOtpRepo;

	@Autowired
	private CustomerRepository CustomerRepo;

	public CustomerSignUpResponse customerRegistation(CustomerSignUpRequest CustomerData) {

		CustomerSignUpResponse CustomerResponseData = new CustomerSignUpResponse();
		LocalDateTime dateData = LocalDateTime.now();
		try {

			Customer CustomerEntity = new Customer();

			CustomerEntity.setCustomerName(CustomerData.getCustomerName());
			CustomerEntity.setCustomerEmail(CustomerData.getCustomerEmail());
			CustomerEntity.setCustomerMobileNumber(CustomerData.getCustomerMobileNumber());
			CustomerEntity.setCustomerPassword(passwordEncoder.encode(CustomerData.getCustomerPassword()));

			String UserCode = CustomerData.getCustomerName().substring(0, 3) + generateUserCode();
			CustomerEntity.setCustomerUserCode(UserCode);

			CustomerEntity.setCustomerCreate(dateData);
			CustomerEntity.setLastLogin(dateData);

			Customer ResponseData = this.CustomerRepo.save(CustomerEntity);
			if (ResponseData == null) {
				throw new NullPointerException("Customer Not Inserted !!");
			} else {
				CustomerResponseData.setCustomerName(ResponseData.getCustomerName());
				CustomerResponseData.setCustomerUserCode(ResponseData.getCustomerUserCode());
				CustomerResponseData.setStatus("welcome ! " + ResponseData.getCustomerName() + " you are Registered !");
				return CustomerResponseData;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public CustomerLoginResponse customerLogin(CustomerLoginRequest LoginData) {

		CustomerLoginResponse ResponseData = new CustomerLoginResponse();

		try {

			Customer EntityData = this.CustomerRepo.findByCustomerMobileNumber(LoginData.getCustomerMobileNumber());

			if (EntityData == null) {
				throw new NullPointerException("Wrong Credencial ..!!");
			} else {

				int otp = (int) (Math.random() * 10000);

				LoginOtp OtpResponse = new LoginOtp();

				OtpResponse.setCustomerOtp(Integer.toString(otp));
				OtpResponse.setCustomerMobileNumber(EntityData.getCustomerMobileNumber());

				LoginOtp OtpEntity = this.LoginOtpRepo.save(OtpResponse);
				if (OtpEntity == null) {
					throw new NullPointerException("Wrong Credencial ..!!");
				}

				ResponseData.setCustomerMobileNumber(LoginData.getCustomerMobileNumber());
				ResponseData.setCustomerPassword(LoginData.getCustomerPassword());
				ResponseData.setCustomerUserCode(EntityData.getCustomerUserCode());
				ResponseData.setCustomerOpt(Integer.toString(otp));
				ResponseData.setCustomerStatus("Right Credencial !!");
				return ResponseData;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// it is for generate Random serviceCode filed
	static String generateUserCode() {
		String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		// create a super set of all characters
		String allCharacters = alphabetsInLowerCase + alphabetsInUpperCase + numbers;
		// initialize a string to hold result
		StringBuffer randomString = new StringBuffer();
		// loop for 10 times
		for (int i = 0; i < 10; i++) {
			// generate a random number between 0 and length of all characters
			int randomIndex = (int) (Math.random() * allCharacters.length());
			// retrieve character at index and add it to result
			randomString.append(allCharacters.charAt(randomIndex));
		}
		return randomString.toString();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserDetails EntityData = this.CustomerRepo.findByCustomerMobileNumber(username);
		return EntityData;
	}

	public boolean CheckOtp(String otp) {

		LoginOtp LoginOtpResponse = this.LoginOtpRepo.findByCustomerOtp(otp);

		if (LoginOtpResponse == null) {
			return false;
		} else {
			return true;
		}
	}

}
