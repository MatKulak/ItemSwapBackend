package pl.mateusz.swap_items_backend.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.mateusz.swap_items_backend.security.JwtService;
import pl.mateusz.swap_items_backend.services.UserService;

import java.util.Map;

//@Component
//public class JwtChannelInterceptor implements ChannelInterceptor {
//
//    private final JwtService jwtService;
//    private final UserService userService;
//
//    public JwtChannelInterceptor(JwtService jwtService, UserService userService) {
//        this.jwtService = jwtService;
//        this.userService = userService;
//    }
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        Map<String, Object> attributes = SimpMessageHeaderAccessor.wrap(message).getSessionAttributes();
//        String token = (String) attributes.get("jwt");
//
//        if (token != null && jwtService.isTokenValid(token)) {
//            String username = jwtService.extractUsername(token);
//            UserDetails userDetails = userService.loadUserByUsername(username);
//
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                    userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//        }
//        return message;
//    }
//
//    @Override
//    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
//        SecurityContextHolder.clearContext();
//    }
//}