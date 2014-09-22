package pl.warsjawa.decisionmaker.worker

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.warsjawa.decisionmaker.repository.DecisionRepository

@Configuration
@CompileStatic
class VerificationConfiguration {

    @Bean
    PropagationWorker propagationWorker(ServiceRestClient serviceRestClient,
                                        DecisionMaker decisionMaker,
                                        DecisionRepository decisionRepository) {
        return new DecisionMakerWorker(serviceRestClient, decisionMaker, decisionRepository, new RequestBodyBuilder())
    }
}
