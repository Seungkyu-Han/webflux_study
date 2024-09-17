package fastcampus.webflux.practice.handler.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private final String id;

    private final String name;

    private final int age;

    private final Long followCount;
}
