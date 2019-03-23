package amazing.co.controllers.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
public class UpdateNodeDTO {

    @Setter
    @Getter
    @NotNull
    private String newParent;
}
