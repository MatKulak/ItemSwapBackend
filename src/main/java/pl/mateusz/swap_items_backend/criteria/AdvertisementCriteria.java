package pl.mateusz.swap_items_backend.criteria;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.util.MultiValueMap;
import pl.mateusz.swap_items_backend.entities.QAdvertisement;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static pl.mateusz.swap_items_backend.utils.Utils.getLoggedUser;

public class AdvertisementCriteria {

    public static Predicate updateCriteria(final Predicate predicate, final MultiValueMap<String, String> params) {
        final QAdvertisement qAdvertisement = QAdvertisement.advertisement;
        final BooleanBuilder builder = new BooleanBuilder();
        final String filter = params.getFirst("filter");

        if (filter != null && filter.equals("user")) builder.and(qAdvertisement.user.id.eq(getLoggedUser().getId()));

        if (filter != null && filter.equals("followed"))
            builder.and(qAdvertisement.followers.any().id.eq(getLoggedUser().getId()));

        if (filter != null && filter.equals("all"))
            builder.and(qAdvertisement.user.id.eq(getLoggedUser().getId()).not());

        final String query = params.getFirst("query");

        if (query != null && !query.isEmpty())
            builder.and(qAdvertisement.title.lower().like("%" + query.toLowerCase() + "%"));

        builder.and(predicate);
        return builder;
    }

    public static Predicate addAdvertisementsIdsToCriteria(final Predicate predicate, final List<UUID> advertisementsIds, final Set<UUID> userAdvertisementsIds) {
        final QAdvertisement qAdvertisement = QAdvertisement.advertisement;
        final BooleanBuilder builder = new BooleanBuilder();

        builder.and(qAdvertisement.id.in(advertisementsIds));
        builder.and(qAdvertisement.id.in(userAdvertisementsIds).not());

        builder.and(predicate);
        return builder;
    }
}
