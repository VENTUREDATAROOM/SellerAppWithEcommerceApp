package com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Service.PaymentHandlerService;

@RestController
@RequestMapping("/payment")
public class PaymentHandlerController {

	@Autowired
	private PaymentHandlerService paymentService;

	@PostMapping("/call/created/{userCode}")
	public ResponseEntity<?> createOrderPayment(@RequestParam("rupee") String repee,
			@PathVariable("userCode") String userCode) {

		try {
			String data = this.paymentService.createOderForPayment(repee, userCode);
			if (data == null) {
				throw new NullPointerException("error_NoData");
			} else {
				return new ResponseEntity<String>(data, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping("/call/success/api")
	public ResponseEntity<?> successPaymentApi(@RequestParam("paymentId") String paymentId,
			@RequestParam("orderId") String orderId) {

		try {
			String data = this.paymentService.paymentSuccess(paymentId, orderId);
			if (data == null) {
				throw new NullPointerException("error_NoData");
			} else {
				return new ResponseEntity<String>(data, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_GATEWAY);
		}

	}

}
