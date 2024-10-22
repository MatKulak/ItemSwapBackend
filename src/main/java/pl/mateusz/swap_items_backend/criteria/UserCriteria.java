package pl.mateusz.swap_items_backend.criteria;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.util.MultiValueMap;
import pl.mateusz.swap_items_backend.entities.QAdvertisement;

import static pl.mateusz.swap_items_backend.utils.Utils.getLoggedUser;

public class UserCriteria {

    public static Predicate updateCriteria(final Predicate predicate, final MultiValueMap<String, String> params) {
        final QAdvertisement qAdvertisement = QAdvertisement.advertisement;
        final String filter = params.getFirst("filter");
        final BooleanBuilder builder = new BooleanBuilder();

        if (filter != null && filter.equals("user")) builder.and(qAdvertisement.user.id.eq(getLoggedUser().getId()));

        if (filter != null && filter.equals("followed"))
            builder.and(qAdvertisement.followers.any().id.eq(getLoggedUser().getId()));

        if (filter != null && filter.equals("all"))
            builder.and(qAdvertisement.user.id.eq(getLoggedUser().getId()).not());

        builder.and(predicate);
        return builder;
    }
}
