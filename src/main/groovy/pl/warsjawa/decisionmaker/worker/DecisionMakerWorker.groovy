package pl.warsjawa.decisionmaker.worker

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.util.logging.Slf4j

@CompileStatic
@Slf4j
@PackageScope
class DecisionMakerWorker implements PropagationWorker {

    private final ServiceRestClient serviceRestClient

    DecisionMakerWorker(ServiceRestClient serviceRestClient) {
        this.serviceRestClient = serviceRestClient
    }

    @Override
    void checkAndPropagate(String loanApplicationId, String loanApplicationDetails) {
        log.info('Some action has been taken')
    }

}
