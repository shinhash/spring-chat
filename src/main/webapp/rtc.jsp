<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>WebRtc tutorial</title>
</head>

<body>
	화상회의 테스트
	

    <div>
        <video id="localVideo" autoplay width="480px"></video>
        <script src="https://webrtc.github.io/adapter/adapter-latest.js"></script>
        <video id="remoteVideo" width="480px" autoplay></video>
    </div>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/1.4.5/socket.io.min.js"></script>
    <script src="/viewChat/socket.io/socket.io.js"></script>
    <script src="/viewChat/rtc/rtc.js"></script>
</body>

</html>