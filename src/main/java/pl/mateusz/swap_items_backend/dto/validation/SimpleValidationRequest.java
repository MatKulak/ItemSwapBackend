package pl.mateusz.swap_items_backend.dto.validation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleValidationRequest {

    private String property;
    private String value;
}
