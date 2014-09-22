package pl.warsjawa.decisionmaker.worker

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.warsjawa.decisionmaker.Dependencies
import pl.warsjawa.decisionmaker.domain.Decision

import static pl.warsjawa.decisionmaker.DecisionMakerApi.MARKETING_MAKER_V1
import static pl.warsjawa.decisionmaker.DecisionMakerApi.REPORTING_MAKER_V1

@Slf4j
@TypeChecked
@Service
class FlowPropagator {

    private final ServiceRestClient serviceRestClient
    private final RequestBodyBuilder requestBodyBuilder

    @Autowired
    FlowPropagator(ServiceRestClient serviceRestClient, RequestBodyBuilder requestBodyBuilder) {
        this.serviceRestClient = serviceRestClient
        this.requestBodyBuilder = requestBodyBuilder
    }

    void checkAndPropagate(Decision decision) {
        log.info("Sending a request to [$Dependencies.MARKETING] to create additional offer")
        serviceRestClient.forService(Dependencies.MARKETING.toString())
                .put()
                .onUrl("/api/marketing/$decision.loanApplicationId")
                .body(requestBodyBuilder.buildMarketingRequestBody(decision))
                .withHeaders()
                .contentType(MARKETING_MAKER_V1)
                .andExecuteFor()
                .ignoringResponse()

        log.info("Sending a request to [$Dependencies.REPORTING] to save loan application for reporting purposes")
        serviceRestClient.forService(Dependencies.REPORTING.toString())
                .put()
                .onUrl("/api/reporting/$decision.loanApplicationId")
                .body(requestBodyBuilder.buildReportingRequestBody(decision))
                .withHeaders()
                .contentType(REPORTING_MAKER_V1)
                .andExecuteFor()
                .ignoringResponse()
    }
}
