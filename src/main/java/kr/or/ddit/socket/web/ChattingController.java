package kr.or.ddit.socket.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ChattingController {

	
	private static final Logger logger = LoggerFactory.getLogger(ChattingController.class);

	
	// 채팅방 입장
	@RequestMapping(value = "/socketChat", method = RequestMethod.GET)
	public String view_chat(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		logger.debug("일단 옴");
		
		return "chat/view_chat";
	}
}
