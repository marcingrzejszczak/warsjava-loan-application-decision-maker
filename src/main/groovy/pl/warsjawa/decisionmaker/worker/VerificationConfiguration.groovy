package pl.warsjawa.decisionmaker.worker

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import groovy.transform.CompileStatic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@CompileStatic
class VerificationConfiguration {

    @Bean
    FlowPropagator flowPropagator(ServiceRestClient serviceRestClient) {
        new FlowPropagator(serviceRestClient, new RequestBodyBuilder())
    }

}
