package com.mansour.ide.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.rabbitmq") // 환경 변수값과 연결
@Getter @Setter // Setter을 붙이는게 좋은 방법은 아니지만 바인딩을 할 수 있게 해주는 방법 중 하나이다.
public class RabbitMqProperties {
    private String host;
    private int port;
    private String username;
    private String password;
}