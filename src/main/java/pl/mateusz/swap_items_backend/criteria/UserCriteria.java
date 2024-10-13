package pl.mateusz.swap_items_backend.criteria;

import com.querydsl.core.types.Predicate;
import org.springframework.util.MultiValueMap;

public class UserCriteria {

    public static Predicate updateCriteria(final Predicate predicate, final MultiValueMap<String, String> params) {
        return predicate;
    }
}
