package pl.warsjawa.decisionmaker.worker

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import pl.warsjawa.decisionmaker.Dependencies
import pl.warsjawa.decisionmaker.domain.Decision

import static pl.warsjawa.decisionmaker.DecisionMakerApi.MARKETING_SRV_V1
import static pl.warsjawa.decisionmaker.DecisionMakerApi.REPORTING_SRV_V1

@Slf4j
@TypeChecked
class FlowPropagator {

    private final ServiceRestClient serviceRestClient
    private final RequestBodyBuilder requestBodyBuilder

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
                .contentType(MARKETING_SRV_V1)
                .andExecuteFor()
                .ignoringResponse()

        log.info("Sending a request to [$Dependencies.REPORTING] to save loan application for reporting purposes")
        serviceRestClient.forService(Dependencies.REPORTING.toString())
                .put()
                .onUrl("/loans")
                .body(requestBodyBuilder.buildReportingRequestBody(decision))
                .withHeaders()
                .contentType(REPORTING_SRV_V1)
                .andExecuteFor()
                .ignoringResponse()
    }
}
