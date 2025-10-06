<<<<<<< Updated upstream:src/main/java/com/example/gujeuck_server/domain/purpose/dto/request/PurposeRequest.java
package com.example.gujeuck_server.domain.purpose.dto.request;
=======
package com.example.gujeuck_server.domain.purpose.dto;
>>>>>>> Stashed changes:src/main/java/com/example/gujeuck_server/domain/purpose/dto/PurposeRequest.java

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PurposeRequest {

    @Size(min = 1, max = 30)
    @NotBlank
    private String purpose;

    @NotBlank
    private String purposeImage;
}
