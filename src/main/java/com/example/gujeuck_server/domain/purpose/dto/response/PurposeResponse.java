<<<<<<< Updated upstream:src/main/java/com/example/gujeuck_server/domain/purpose/dto/response/PurposeResponse.java
package com.example.gujeuck_server.domain.purpose.dto.response;
=======
package com.example.gujeuck_server.domain.purpose.dto;
>>>>>>> Stashed changes:src/main/java/com/example/gujeuck_server/domain/purpose/dto/PurposeResponse.java

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurposeResponse {
    private Long id;
    private String purpose;
    private String purposeImage;
}
