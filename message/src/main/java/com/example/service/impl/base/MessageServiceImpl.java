package com.example.service.impl.base;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.service.MessageService;

//@Service
public class MessageServiceImpl implements MessageService {

	// 這邊是手動模擬消息隊列的處理，套用上RabbitMQ等工具後就可以退場
	
	private ArrayList<String> msgList = new ArrayList<String>();

	@Override
	public void sendMessage(String id) {
		System.out.println("待發送消息的訂單已納入處理隊列，id：" + id);
		msgList.add(id);
	}

	@Override
	public String doMessage() {
		String id = msgList.remove(0);
		System.out.println("已完成消息發送業務，id：" + id);
		return id;
	}

}
