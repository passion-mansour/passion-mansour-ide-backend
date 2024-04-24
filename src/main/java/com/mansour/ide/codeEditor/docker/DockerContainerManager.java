package com.mansour.ide.codeEditor.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.BuildImageResultCallback;

import java.io.File;

public class DockerContainerManager {

    // Docker 클라이언트 초기화
    DockerClient dockerClient = DockerClientBuilder.getInstance().build();

    public void JavaDocker() {
        // Dockerfile을 포함하는 디렉토리 지정
        File dockerfileDirectory = new File("java/com/mansour/ide/codeEditor/service/JavaCodeExecutor.java");

        // 이미지 빌드 및 콜백 설정
        dockerClient.buildImageCmd(dockerfileDirectory)
                .exec(new BuildImageResultCallback())
                .awaitImageId();

        DockerExec();
    }

    public void DockerExec(){
        // 컨테이너 실행
        String containerId = dockerClient.createContainerCmd("dynamic-code-executor-image")
                .withHostConfig(HostConfig.newHostConfig().withAutoRemove(true))
                .exec()
                .getId();

        dockerClient.startContainerCmd(containerId).exec();
    }
}
