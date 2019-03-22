package amazing.co.controllers.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class UpdateNodeDTO {

    @Setter
    @Getter
    private Long newParentId;
}
