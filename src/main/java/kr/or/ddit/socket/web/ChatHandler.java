package kr.or.ddit.socket.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(ChatHandler.class);
	public static String webUserid;
	
	
	// 접속한 사원의 소켓정보와 아이디를 담을 map객체
	private List<Map<String, Object>> userMapList = SocketChatServer.userMapList;
	private Map<String, Object> userMap;
	
	
	
	// 클라이언트가 서버로 연결 처리
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		userMap = new HashMap<String, Object>();
		
		logger.debug("");
		logger.debug("");
		logger.debug("=============================================");
		logger.debug("connecting success");
		logger.debug("=============================================");
		logger.debug("");
		logger.debug("");
		
		
		
		userMap.put("userid", webUserid);
		userMap.put("userSocketID", session.getId());
		userMap.put("userSocketSession", session);
		
		userMapList.add(userMap);
		
		logger.debug("userid : {}", userMap.get("userid"));
		logger.debug("userSocketID : {}", userMap.get("userSocketID"));
	
		
		// 모든 세션에 채팅 전달
		for (int i = 0; i < userMapList.size(); i++) {
			WebSocketSession s = (WebSocketSession) (userMapList.get(i).get("userSocketSession"));
			s.sendMessage(new TextMessage(userMap.get("userid") + " 님이 입장했습니다."));
		}
	}

	// 클라이언트가 서버로 메세지 전송 처리
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		String userid = "";
		
		// 현재 접속한 세션의 정보와 list에 추가한 세션정보가 같은 유저(본 브라우저 접속자)의 아이디를 추출
		for(int i = 0; i < userMapList.size(); i++) {
			WebSocketSession s = (WebSocketSession) (userMapList.get(i).get("userSocketSession"));
			if((session.getId()).equals(userMapList.get(i).get("userSocketID"))) {
				userid = (String) userMapList.get(i).get("userid");
				break;
			}
		}
		
		// 추출된 아이디를 사용하여 접속한 전체 인원에게 메시지 전달
		for(int i = 0; i < userMapList.size(); i++) {
			WebSocketSession s = (WebSocketSession) (userMapList.get(i).get("userSocketSession"));
			s.sendMessage(new TextMessage(userid + " : " + message.getPayload()));
		}
	}

	// 클라이언트가 연결을 끊음 처리
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		
		
		
		String userid = "";
		
		// 현재 접속한 세션의 정보와 list에 추가한 세션정보가 같은 유저(본 브라우저 접속자)의 아이디를 추출
		for(int i = 0; i < userMapList.size(); i++) {
			WebSocketSession s = (WebSocketSession) (userMapList.get(i).get("userSocketSession"));
			if((session.getId()).equals(userMapList.get(i).get("userSocketID"))) {
				userid = (String) userMapList.get(i).get("userid");
				
				// 채팅방에서 나가기
				userMapList.remove(i);
				break;
			}
		}
		
	
		// 모든 세션에 채팅 전달
		for (int i = 0; i < userMapList.size(); i++) {
			WebSocketSession s = (WebSocketSession) (userMapList.get(i).get("userSocketSession"));
			s.sendMessage(new TextMessage(userid + "님이 퇴장 했습니다."));
		}
		
		

	}
}
