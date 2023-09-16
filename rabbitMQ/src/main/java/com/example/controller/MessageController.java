package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.MessageService;

@RestController
@RequestMapping("/msgs")
public class MessageController {
	
	// 這邊是手動模擬消息隊列的處理，套用上RabbitMQ等工具後就可以退場

	@Autowired
	private MessageService messageService;
	
	@GetMapping
	public String doMessage() {
		String id = messageService.doMessage();
		return id;
	}
}
