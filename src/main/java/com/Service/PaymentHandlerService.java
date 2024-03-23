package com.Service;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entity.PaymentHandler;
import com.Repository.PaymentHandlerRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class PaymentHandlerService {

	@Autowired
	private PaymentHandlerRepository paymentHandlerRepo;

	public String createOderForPayment(String rupee, String userCode) {

		// int Rupee = Integer.parseInt(userCode);

		try {
			RazorpayClient clint = new RazorpayClient("rzp_test_83tddDN1EWSnIq", "z9FZ3rijqwHfrqGvi8uxQDU3");
			JSONObject orderRequest = new JSONObject();
			int Rupee = Integer.parseInt(rupee);
			orderRequest.put("amount", Rupee * 100);
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "receipt#1");
			Order order = clint.orders.create(orderRequest);
			System.out.print(order);

			PaymentHandler PaymentData = new PaymentHandler();
			PaymentData.setAmount(order.get("amount").toString());
			PaymentData.setOrderId(order.get("id"));
			PaymentData.setStatus(order.get("status"));
			PaymentData.setAttempts(order.get("attempts").toString());
			PaymentData.setCustomerUserCode(userCode);
			LocalDateTime localDateTime = LocalDateTime.now();
			PaymentData.setCreatedAt(localDateTime);

			PaymentHandler saveData = this.paymentHandlerRepo.save(PaymentData);

			if (saveData == null) {
				return null;
			}
			return order.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public String paymentSuccess(String paymentId, String orderId) {

		PaymentHandler paymentData = this.paymentHandlerRepo.findByOrderId(orderId);
		paymentData.setPaymentId(paymentId);
		paymentData.setAttempts("1");
		paymentData.setStatus("success");
		PaymentHandler data = this.paymentHandlerRepo.save(paymentData);
		if (data == null) {
			return null;
		} else {
			return "success";
		}
	}

}
