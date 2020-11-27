<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>웹소켓 알람	</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.js"></script>
	<script type="text/javascript">
// 		var webSocket = {
// 			init: function(param) {

// 				console.log(param)
// 				this._url = param.url;
// 				this._initSocket();
// 			},
// 			sendChat: function() {
// 				this._sendMessage($('#message').val());
// 				$('#message').val('');
// 			},
// 			clickSubscript : function(){
// 				this._sendMessage("follow");
// 			},
// 			receiveMessage: function(str) {
// 				$('#divChatData').append('<div>' + str + '</div>');				
// 			},
// 			closeMessage: function(str) {
// 				$('#divChatData').append('<div>' + '연결 끊김 : ' + str + '</div>');
// 			},
// 			disconnect: function() {
// 				this._socket.close();
// 			},
// 			_initSocket: function() {
// 				this._socket = new SockJS(this._url);
// 				this._socket.onmessage = function(evt) {
// 					webSocket.receiveMessage(evt.data);
// 				};
// 				this._socket.onclose = function(evt) {
// 					webSocket.closeMessage(evt.data);
// 				}
// 			},
// 			_sendMessage: function(str) {
// 				this._socket.send(str);
// 			}
// 		};
	</script>	
	<script type="text/javascript">

		//전역변수 선언-모든 홈페이지에서 사용 할 수 있게 index에 저장
		var socket = null;

		$(document).ready(function() {

			connectWs();
			$("#alamnBtn").on("click", function() {
				alert("follow!!")
				var txt = "follow";
				var receiverNm = "보스";
				var receiverId = "boss";
				var cnt = "2";
				sendMessage(txt + "," + "${userid}" + "," + receiverNm + "," + receiverId + "," + cnt);
			});
		});

		function connectWs() {
			sock = new SockJS("<c:url value='/alamnSocket'/>");
			//sock = new SockJS('/replyEcho');
			socket = sock;

			// alamnBtn을 클릭하면 socket 시작
			sock.onopen = function() {
				console.log('info: connection opened.');
			};

			// 소켓통신이 시작되면 java단의 handler에서 socket list에 저장된 모든 사람에게
			// 메시지를 전달 ==> afterConnectionEstablished() 메서드
			// afterConnectionEstablished() 메서드 실행시 보내진 데이터를 evt라는 변수로 활용
			sock.onmessage = function(evt) {
				console.log(evt);
				var data = evt.data;
				console.log("ReceivMessage : " + data + "\n");
				$('#divChatData').append('<div>' + data + '</div>');

				$.ajax({
					url : '/alamnReceive.do',
					type : 'post',
					dataType : 'text',
					success : function(data) {
						if (data == '0') {
							console.log("data : " + data);
						} else {
							console.log("data : " + data);
							$('#alarmCountSpan').addClass('bell-badge-danger bell-badge')
							$('#alarmCountSpan').text(data);
							alert(data)
						}
					},
					error : function(err) {
						alert('err');
					}
				});

// 				// 모달 알림
// 			   	var TOASTTOP = APP.TOAST.CREATE({
// 		            TEXT: "알림 : " + DATA + "\N",
// 		            POSITION: 'TOP',
// 		            CLOSEBUTTON: TRUE,
// 		          });
// 		          toastTop.open();
			};

			sock.onclose = function() {
				console.log('connect close');
				/* setTimeout(function(){conntectWs();} , 1000); */
			};

			sock.onerror = function(err) {
				console.log('Errors : ', err);
			};

// 		   var AlarmData = {
// 					"myAlarm_receiverEmail" : receiverEmail,
// 					"myAlarm_callerNickname" : memNickname,
// 					"myAlarm_title" : "스크랩 알림",
// 					"myAlarm_content" :  memNickname + "님이 <a type='external' href='/mentor/essayboard/essayboardView?pg=1&seq="+essayboard_seq+"&mentors="+ memberSeq +"'>" + essayboard_seq + "</a>번 에세이를 스크랩 했습니다."
// 			};
// 			//스크랩 알림 DB저장
// 			$.ajax({
// 				type : 'post',
// 				url : '/mentor/member/saveAlarm',
// 				data : JSON.stringify(AlarmData),
// 				contentType: "application/json; charset=utf-8",
// 				dataType : 'text',
// 				success : function(data){
// 					if(socket){
// 						let socketMsg = "scrap," + memNickname +","+ memberSeq +","+ receiverEmail +","+ essayboard_seq;
// 						console.log("msgmsg : " + socketMsg);
// 						socket.send(socketMsg);
// 					}

// 				},
// 				error : function(err){
// 					console.log(err);
// 				}
// 			});

			sendMessage = function(str) {
				console.log(str);
				this.socket.send(str);
			}
		}
	</script>
</head>
<body>
	<div style="width: 300px; height: 200px; padding: 10px; border: solid 1px #e1e3e9;">
		<div id="divChatData"></div>
	</div>
	<div style="width: 100%; height: 10%; padding: 10px;">
		<input type="text" id="message" size="25" onkeypress="if(event.keyCode==13){webSocket.sendChat();}" />
		<input type="button" id="btnSend" value="채팅 전송" onclick="webSocket.sendChat()" />
		<input type="button" id="alamnBtn" value="좋아요!!" />
	</div>
	<div id="alarmCountSpan">
	</div>
</body>
</html>