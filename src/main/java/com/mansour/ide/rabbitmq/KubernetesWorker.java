package com.mansour.ide.rabbitmq;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class KubernetesWorker {

    public void executeCode(String language, String content) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Pod pod = new PodBuilder()
                    .withNewMetadata().withName(language + "-pod").endMetadata()
                    .withNewSpec()
                    .addNewContainer()
                    .withName(language + "-container")
                    .withImage("your-image-for-" + language) // 언어별 이미지 사용
                    .withCommand("/bin/sh", "-c", "echo '" + content + "' | run-script") // 실행할 스크립트 또는 명령어
                    .endContainer()
                    .endSpec()
                    .build();

            client.pods().create(pod);
            System.out.println("Pod created for language " + language);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
