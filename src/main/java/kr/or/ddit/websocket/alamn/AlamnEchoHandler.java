package kr.or.ddit.websocket.alamn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class AlamnEchoHandler extends TextWebSocketHandler{
	
	
	private static final Logger logger = LoggerFactory.getLogger(AlamnEchoHandler.class);

	
	// 로그인 전체 인원
	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 1대1
	Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();
	

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		Map<String, Object> userIdMap = session.getAttributes();
		String userid = (String) userIdMap.get("userid");
		
		logger.debug("접속유저 : {}", userid);
		userSessionsMap.put(userid, session);
		
		for(int i = 0; i < sessions.size(); i++) {
			logger.debug("sessions : {}", sessions.get(i));
//			WebSocketSession s = (WebSocketSession) sessions.get(i);
//			s.sendMessage(new TextMessage(userid + " 님이 입장했습니다."));
		}
		
		
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload();
		
		logger.debug("좋아요 버튼 클릭함!");
		logger.debug("msg : {}", msg);
		
		
		
		if(StringUtils.isEmpty(msg)) {
		}else {
			String[] msgStr = msg.split(",");
			
			if(msgStr != null && msgStr.length == 5) {
				String cmd = msgStr[0];
				String caller = msgStr[1];
				String receiver = msgStr[2];
				String receiverUserid = msgStr[3];
				String seq = msgStr[4];
				
				logger.debug("cmd : {}", cmd);
				logger.debug("caller : {}", caller);
				logger.debug("receiver : {}", receiver);
				logger.debug("receiverUserid : {}", receiverUserid);
				logger.debug("seq", seq);
				
				WebSocketSession boardWriterSession = userSessionsMap.get(receiverUserid);
				if("follow".equals(cmd) && boardWriterSession != null) {
					TextMessage tmpMsg = new TextMessage(caller + " 님이 " + receiver + " 님을 팔로우함");
					boardWriterSession.sendMessage(tmpMsg);
				}
			}
		}
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		userSessionsMap.remove(session.getId());
		sessions.remove(session);
	}
}
