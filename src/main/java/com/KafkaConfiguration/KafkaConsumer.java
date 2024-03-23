package com.KafkaConfiguration;

import org.springframework.kafka.annotation.KafkaListener;

import com.Constant.AppConstant;

public class KafkaConsumer {

	@KafkaListener(topics = AppConstant.Topic_Name, groupId = AppConstant.group_Id)
	public String consume(Location locationData) {

		System.out.print(locationData);

		return "good";

	}

}
