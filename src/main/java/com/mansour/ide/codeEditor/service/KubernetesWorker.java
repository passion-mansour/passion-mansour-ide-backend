package com.mansour.ide.codeEditor.service;

import com.mansour.ide.codeEditor.model.CodeResult;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;


/**
 * RabbitMQ로부터 메시지를 수신하여 Kubernetes 클라이언트를 사용해 Pod를 생성
 * 1. Pod 생성
 * 2. 컨테이너 설정
 *      - 이미지와 명령어
 * 3. Pod 실행
 * 4. Pod 사용
 * 5. 에러 헨들링
 * 6. Pod의 생명주기 설정
 */
@Slf4j
@AllArgsConstructor
@Service
public class KubernetesWorker {
    private final CodeResult codeResult;


    // 언어별 실행 커맨드 생성 함수
    private String buildCommand(String language, String content) {
        switch (language.toLowerCase()) {
            case "java":
                return "echo '" + content + "' > Main.java && javac Main.java && java Main";
            case "python":
                return "echo '" + content + "' > script.py && python script.py";
            case "javascript":
                return "echo '" + content + "' > script.js && node script.js";
            default:
                return "echo 'Unsupported language'";
        }
    }

    // pod 생성 및 실행
    public void executeCode(String image, String language, String content) {
        Config config = new ConfigBuilder()
                .withMasterUrl("https://passion-mansour.shop/").build();

        try (KubernetesClient client = new DefaultKubernetesClient()) {
            String podName = language.toLowerCase() + "-pod";
            String command = buildCommand(language, content);
            log.info("Pod 생성 시작");
            log.info("==============");
            log.info("language = {}", language);
            log.info("content = {}", content);

            Pod pod = new PodBuilder()
                    .withNewMetadata().withName(podName).endMetadata()
                    .withNewSpec()
                    .addNewContainer()
                    .withName(language + "-container")
                    .withImage(image)
                    .withCommand("/bin/sh", "-c", command)
                    .endContainer()
                    .endSpec()
                    .build();

            log.info("Pod 설정 완료 생성 시작");

            // Pod 생성
            pod = client.pods().inNamespace("default").create(pod);
            log.info("Pod created for language " + language + " using " + image);

            // Pod의 실행이 완료될 때까지 기다림
            waitForPodCompletion(client, podName);


            // 로그 추출
            String logs = client.pods().inNamespace("default").withName(podName).getLog();
            if (codeResult.isException()) {
                codeResult.setStderr(Collections.singletonList(logs));
            } else {
                codeResult.setStdout(logs);
            }
            log.info("Logs from the pod: " + logs);

            // Pod 삭제
            client.pods().inNamespace("default").withName(podName).delete();
            log.info("Pod deleted: " + podName);

        } catch (Exception e) {
            log.error("Error during Kubernetes operation: ", e);
            codeResult.setException(true);
            codeResult.setStderr(Collections.singletonList(e.getMessage()));
        }
    }



    // 이미 pod가 실행 중이라면 기다리고 종료상태(성공 or 실패)가 되면
    private void waitForPodCompletion(KubernetesClient client, String podName) throws InterruptedException {
        Pod pod;
        boolean isRunning = true;

        while (isRunning) {
            pod = client.pods().inNamespace("default").withName(podName).get();
            if (pod.getStatus().getPhase().equals("Succeeded") || pod.getStatus().getPhase().equals("Failed")) {
                codeResult.setException(!pod.getStatus().getPhase().equals("Succeeded"));
                isRunning = false;
            } else {
                Thread.sleep(1000);
            }
        }
    }
}
