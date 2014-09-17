package pl.warsjawa.decisionmaker.worker

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.util.logging.Slf4j
import pl.warsjawa.decisionmaker.Dependencies

import static pl.warsjawa.decisionmaker.DecisionMakerApi.MARKETING_MAKER_V1

@CompileStatic
@Slf4j
@PackageScope
class DecisionMakerWorker implements PropagationWorker {

    private final ServiceRestClient serviceRestClient
    private final DecisionMaker decisionMaker
    private final RequestBodyBuilder requestBodyBuilder

    DecisionMakerWorker(ServiceRestClient serviceRestClient, DecisionMaker decisionMaker, RequestBodyBuilder requestBodyBuilder) {
        this.serviceRestClient = serviceRestClient
        this.decisionMaker = decisionMaker
        this.requestBodyBuilder = requestBodyBuilder
    }

    @Override
    void checkAndPropagate(String loanApplicationId, String loanApplicationDetails) {
        DecisionData decision = decisionMaker.makeLoanDecision(loanApplicationDetails)

        log.info("Sending a request to [$Dependencies.MARKETING] to create additional offer")
        serviceRestClient.forService(Dependencies.MARKETING.toString())
            .put()
            .onUrl("/api/marketing/$loanApplicationId")
            .body(requestBodyBuilder.buildMarketingRequestBody(decision))
            .withHeaders()
                .contentType(MARKETING_MAKER_V1)
            .andExecuteFor()
            .ignoringResponse()
    }

}
