package com.lhstack;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.apache.coyote.http11.Http11Processor;
import org.apache.tomcat.util.buf.MessageBytes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.apache.coyote.Processor;

@SpringBootApplication
public class TemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> webServerFactoryCustomizer(){
        return serverFactory -> {
            Connector connector = new Connector(new Http11Nio2Protocol() {
                @Override
                protected Processor createProcessor() {
                    return new Http11Processor(this, this.getAdapter()) {
                        @Override
                        protected void parseHost(MessageBytes valueMB) {
                            MessageBytes messageBytes = MessageBytes.newInstance();
                            String hostname = valueMB.toString();
                            messageBytes.setString(hostname.replace("_", "-"));
                            messageBytes.toBytes();
                            super.parseHost(messageBytes);
                        }
                    };
                }
            });
            connector.setPort(8080);
            serverFactory.addAdditionalTomcatConnectors(connector);
        };
    }


}
