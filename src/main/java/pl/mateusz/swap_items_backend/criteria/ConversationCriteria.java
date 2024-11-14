package pl.mateusz.swap_items_backend.criteria;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.util.MultiValueMap;
import pl.mateusz.swap_items_backend.entities.QAdvertisement;
import pl.mateusz.swap_items_backend.entities.QConversation;
import pl.mateusz.swap_items_backend.utils.Utils;

import java.util.UUID;

public class ConversationCriteria {

    public static Predicate updateCriteria(final Predicate predicate, final MultiValueMap<String, String> params) {

        final QConversation conversation = QConversation.conversation;
        final QAdvertisement advertisement = QAdvertisement.advertisement;
        final BooleanBuilder builder = new BooleanBuilder();
        final UUID loggedUserId = Utils.getLoggedUserId();

        builder.and(conversation.participant.id.eq(loggedUserId).or(advertisement.user.id.eq(loggedUserId)));
        builder.and(predicate);
        return builder;
    }
}
