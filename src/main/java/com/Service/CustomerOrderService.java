package com.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.Entity.CustomerAddress;
import com.Entity.CustomerCart;
import com.Entity.CustomerOrder;
import com.Repository.CustomerAddressRepository;
import com.Repository.CustomerCartRepository;
import com.Repository.CustomerOrderRepository;
import com.StatusModel.OrderResponse;

@Service
public class CustomerOrderService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CustomerAddressRepository cusromerAddressRepo;

	@Autowired
	private CustomerCartRepository cartRepo;

	@Autowired
	private CustomerOrderRepository orderRepo;

	public OrderResponse createOrder(String customerUserCode) {
		OrderResponse orderResponse = new OrderResponse();
		CustomerOrder orderEntity = new CustomerOrder();
		try {
			CustomerAddress customerAddressData = this.cusromerAddressRepo.findByCustomerUserCode(customerUserCode);
			List<CustomerCart> cartData = this.cartRepo.findByCustomerUserCode(customerUserCode);

			if (cartData.isEmpty()) {
				return null;
			}

			LocalDateTime Transactiondate = LocalDateTime.now();
			String newStr = Transactiondate.toString();
			String orderId = newStr.substring(0, 4) + newStr.substring(5, 7) + newStr.substring(8, 13)
					+ newStr.substring(14, 16) + newStr.substring(17, 19) + generateOrderCode();

			orderEntity.setAddressID(customerAddressData.getId());
			orderEntity.setCustomerUserCode(customerUserCode);
			orderEntity.setOrderId(orderId);
			orderEntity.setProductItem(cartData);

			CustomerOrder ResponseData = this.orderRepo.save(orderEntity);
			if (ResponseData == null) {
				return null;
			} else {
				orderResponse.setOrderId(orderId);
				orderResponse.setStatus("SuccessFully ! Order Your Product ..!");
				// ..

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

				MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
				map.add("CustomerUserCode", customerUserCode);

				HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

				this.restTemplate.postForEntity("http://localhost:8082/notification/get/notifiToSeller", request,
						List.class);
				// return response;

				// ....
			}
			return orderResponse;
		} catch (Exception e) {
			return null;
		}
	}

	public List<CustomerOrder> getAllOrderOfCustomer(String customerUserCode) {
		try {
			List<CustomerOrder> ResponseData = this.orderRepo.findByCustomerUserCode(customerUserCode);
			return ResponseData;
		} catch (Exception e) {
			return null;
		}
	}

	// it is for generate Random serviceCode filed
	static String generateOrderCode() {
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

}
