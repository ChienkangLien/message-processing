package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service.MessageService;
import com.example.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	MessageService messageService;

	@Override
	public void order(String id) {
		//業務操作...
		System.out.println("訂單處理開始");
		//消息處理
		messageService.sendMessage(id);
		System.out.println("訂單處理結束");
	}

}
