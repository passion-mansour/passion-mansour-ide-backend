package com.mansour.ide.codeEditor.service;

import com.mansour.ide.codeEditor.model.CodeResult;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

@Component
public class JavaCodeExecutor {
    @Setter
    static String content;

    public static CodeResult run() throws Exception {
        CodeResult codeResult = new CodeResult(content, null, null, false);

        // 코드를 컴파일하고 클래스 파일을 메모리에 로드
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaFileObject file = new DynamicJavaObject(content);
        Iterable<? extends JavaFileObject> compilationUnits = List.of(file);
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);
        boolean success = task.call();

        if (!success) {
            // 컴파일 에러가 있을 경우 에러 메시지 출력
            List<String> errList = new ArrayList<>();

            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                String stderr = "Error on line "
                        + diagnostic.getLineNumber()
                        + "in "
                        + diagnostic.getSource().toUri()
                        + "\n\n"
                        + diagnostic.getMessage(null);

                errList.add(stderr);
            }
            codeResult.setStderr(errList);
            codeResult.setException(true);
        } else {
            // 컴파일이 성공하면 해당 클래스를 로드하고 실행
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { new File("").toURI().toURL()});
            Class<?> cls = Class.forName("UserCode", true, classLoader);
            Method method = cls.getDeclaredMethod("main", String[].class);

            // 실행결과를 캡처하기 위해 PrintStream을 임시로 생성하여 stdout을 변경
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos); // 텍스트를 출력하기 위한 스트림
            PrintStream temp = System.out;           // 표준 출력 스트림
            System.setOut(ps);

            // 메서드 실행
            method.invoke(null, (Object) new String[]{});

            // stdout을 원래대로 복원
            System.out.flush();
            System.setOut(temp);

            // 실행 결과를 문자열로 변환하여 codeResult에 저장
            codeResult.setStdout(baos.toString());
        }

        return codeResult;
    }

    static class DynamicJavaObject extends SimpleJavaFileObject {
        DynamicJavaObject(String code) {
            super(URI.create("string:///" + "UserCode.java"), Kind.SOURCE);
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return content;
        }
    }
}


