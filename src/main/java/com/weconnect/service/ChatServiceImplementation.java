package com.weconnect.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weconnect.models.Chat;
import com.weconnect.models.User;
import com.weconnect.repositories.ChatRepository;

@Service
public class ChatServiceImplementation implements  ChatService{

	@Autowired 
	private ChatRepository chatRepo;
	@Override
	public Chat createChat(User reqUser, User user2) {
	
		Chat isExist=chatRepo.findChatByUsersId(user2, reqUser);
		if(isExist!=null) {
			return isExist;
		}
		Chat chat=new Chat();
		chat.getUsers().add(user2);
		chat.getUsers().add(reqUser);
//		chat.setChat_image(null);
		chat.setTimestamp(LocalDateTime.now());
		return chatRepo.save(chat);
	}

	@Override
	public Chat findChatById(Integer chatId) throws Exception{
		Optional<Chat> opt=chatRepo.findById(chatId);
		if(opt.isEmpty()) {
			throw new Exception("chat not found with id"+chatId);
		}
		return opt.get();
		
	}

	@Override
	public List<Chat> findUsersChat(Integer userId) {
		return chatRepo.findByUsersId(userId);
	}

}