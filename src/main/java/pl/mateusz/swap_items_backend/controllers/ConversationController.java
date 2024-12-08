package pl.mateusz.swap_items_backend.controllers;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz.swap_items_backend.dto.conversation.ConversationResponse;
import pl.mateusz.swap_items_backend.dto.conversation.SimpleConversationResponse;
import pl.mateusz.swap_items_backend.entities.Advertisement;
import pl.mateusz.swap_items_backend.mappers.ConversationMapper;
import pl.mateusz.swap_items_backend.services.ConversationService;

import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ConversationController {

    private static final String API_CONVERSATIONS = "/api/conversations";
    private static final String API_CONVERSATION = "/api/conversations/{id}";
    private static final String API_CONVERSATION_ADV = "/api/conversation";
    private static final String API_CONVERSATIONS_PAGE = API_CONVERSATIONS + "/page";

    private final ConversationService conversationService;

    @GetMapping(API_CONVERSATION_ADV)
    public ConversationResponse getOneByAdvertisement(@RequestParam final UUID advertisementId) {
        return ConversationMapper.toConversationResponse(conversationService.getConversationByAdvertisementId(advertisementId));
    }

    @GetMapping(API_CONVERSATION)
    public ConversationResponse getOneById(@PathVariable("id") final UUID id) {
        return ConversationMapper.toConversationResponse(conversationService.getConversationById(id));
    }

    @GetMapping(API_CONVERSATIONS_PAGE)
    public Page<SimpleConversationResponse> getAll(final @QuerydslPredicate(root = Advertisement.class) Predicate predicate,
                                                   final @PageableDefault Pageable pageable,
                                                   final @RequestParam MultiValueMap<String, String> params) {
        return conversationService.getAll(predicate, pageable, params);
    }
}
