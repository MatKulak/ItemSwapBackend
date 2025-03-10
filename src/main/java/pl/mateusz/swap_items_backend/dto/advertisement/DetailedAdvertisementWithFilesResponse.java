package pl.mateusz.swap_items_backend.dto.advertisement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailedAdvertisementWithFilesResponse {

    private DetailedAdvertisementResponse detailedAdvertisement;
    private List<UUID> fileIds;
}