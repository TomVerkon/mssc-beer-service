package guru.springframework.msscbeerservice.services.inventory.model;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerInventoryDto {
    private Long id;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
    private Long upc;
    private Integer quantityOnHand;
}