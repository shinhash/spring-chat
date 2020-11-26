package kr.or.ddit.websocket.alamn;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AlamnEchoController {

	
	private static final Logger logger = LoggerFactory.getLogger(AlamnEchoController.class);
	
	// 채팅방 입장
	@RequestMapping(value = "/alamn.do", method = RequestMethod.GET)
	public String view_chat(HttpSession session, String userid, String roomid) throws Exception {

		logger.debug("일단 옴");
		
		// session 셋팅
		session.setAttribute("userid", userid);
		session.setAttribute("roomid", roomid);
		
		
		return "alamn/alamn";
	}
}
