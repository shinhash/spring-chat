package kr.or.ddit.socket.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;


@Controller
public class ChattingController {

	
	private static final Logger logger = LoggerFactory.getLogger(ChattingController.class);

	
	// 채팅방 입장
	@RequestMapping(value = "/socketChat.do", method = RequestMethod.GET)
	public String view_chat(HttpSession session, String userid) throws Exception {

		logger.debug("일단 옴");
		
		ChatHandler.webUserid = userid;
		session.setAttribute("userid", userid);
		return "chat/view_chat";
	}
}
