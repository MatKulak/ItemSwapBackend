package pl.mateusz.swap_items_backend.dto.advertisement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mateusz.swap_items_backend.dto.category.MainCategoryResponse;
import pl.mateusz.swap_items_backend.dto.file.FileResponse;
import pl.mateusz.swap_items_backend.dto.localization.LocalizationResponse;
import pl.mateusz.swap_items_backend.dto.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementResponse {
    private UUID id;
    private String title;
    private String description;
    private String phoneNumber;
    private LocalDateTime addDate;
    private LocalizationResponse localizationResponse;
    private MainCategoryResponse mainCategoryResponse;
    private UserResponse userResponse;
//    private FileResponse

}
