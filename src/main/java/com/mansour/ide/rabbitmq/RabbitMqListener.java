package com.mansour.ide.rabbitmq;

import com.mansour.ide.codeEditor.service.KubernetesWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMqListener {
    private String image;
    @Autowired
    private KubernetesWorker kubernetesWorker;

    @RabbitListener(queues = "javaQueue")
    public void receiveJavaMessage(String content) {
        if (content == null) {
            log.warn("Received null content in Java message");
            return; // 내용이 null이면 경고를 로그하고 메서드를 종료합니다.
        }

        log.info("Received Java message: {}", content);

        String language = "java";
        String image = "openjdk:17-oracle";

        // KubernetesWorker 객체와 executeCode 메서드가 null인지 확인합니다.
        if (kubernetesWorker == null) {
            log.error("KubernetesWorker is not initialized");
            return; // kubernetesWorker가 null이면 오류를 로그하고 메서드를 종료합니다.
        }

        try {
            kubernetesWorker.executeCode(image, language, content);
        } catch (Exception e) {
            log.error("Failed to execute code on KubernetesWorker", e);
            // executeCode 실행 중 발생한 예외를 로그합니다.
        }
    }

    @RabbitListener(queues = "pythonQueue")
    public void receivePythonMessage(String content) {
        if (content == null) {
            log.warn("Received null content in Python message");
            return; // 내용이 null이면 경고를 로그하고 메서드를 종료합니다.
        }

        log.info("Received Python message: {}", content);

        String language = "python";
        image = "python:3.9";

        // KubernetesWorker 객체와 executeCode 메서드가 null인지 확인합니다.
        if (kubernetesWorker == null) {
            log.error("KubernetesWorker is not initialized");
            return; // kubernetesWorker가 null이면 오류를 로그하고 메서드를 종료합니다.
        }

        try {
            kubernetesWorker.executeCode(image, language, content);
        } catch (Exception e) {
            log.error("Failed to execute code on KubernetesWorker", e);
            // executeCode 실행 중 발생한 예외를 로그합니다.
        }
    }

    @RabbitListener(queues = "javascriptQueue")
    public void receiveJsMessage(String content) {
        if (content == null) {
            log.warn("Received null content in Javascript message");
            return; // 내용이 null이면 경고를 로그하고 메서드를 종료합니다.
        }

        log.info("Received Javascript message: {}", content);

        String language = "javascript";
        image = "node:14";

        // KubernetesWorker 객체와 executeCode 메서드가 null인지 확인합니다.
        if (kubernetesWorker == null) {
            log.error("KubernetesWorker is not initialized");
            return; // kubernetesWorker가 null이면 오류를 로그하고 메서드를 종료합니다.
        }

        try {
            kubernetesWorker.executeCode(image, language, content);
        } catch (Exception e) {
            log.error("Failed to execute code on KubernetesWorker", e);
            // executeCode 실행 중 발생한 예외를 로그합니다.
        }
    }
}
