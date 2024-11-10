package pl.mateusz.swap_items_backend.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import java.util.Map;

//public class AuthHandshakeInterceptor implements HandshakeInterceptor {
//
//    @Override
//    public boolean beforeHandshake(
//            ServerHttpRequest request,
//            ServerHttpResponse response,
//            WebSocketHandler wsHandler,
//            Map<String, Object> attributes) {
//
//        // Extract the token from the Authorization header
//        String token = request.getHeaders().getFirst("Authorization");
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//            attributes.put("jwt", token);
//        }
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(
//            ServerHttpRequest request,
//            ServerHttpResponse response,
//            WebSocketHandler wsHandler,
//            Exception exception) {
//        // Nothing to do here
//    }
//}
