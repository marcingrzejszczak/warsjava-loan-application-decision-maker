package pl.warsjawa.decisionmaker.worker

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.util.logging.Slf4j
import pl.warsjawa.decisionmaker.Dependencies
import pl.warsjawa.decisionmaker.domain.Decision
import pl.warsjawa.decisionmaker.repository.DecisionRepository

import static pl.warsjawa.decisionmaker.DecisionMakerApi.*

@CompileStatic
@Slf4j
@PackageScope
class DecisionMakerWorker implements PropagationWorker {

    private final ServiceRestClient serviceRestClient
    private final DecisionMaker decisionMaker
    private final RequestBodyBuilder requestBodyBuilder
    private final DecisionRepository decisionRepository

    DecisionMakerWorker(ServiceRestClient serviceRestClient,
                        DecisionMaker decisionMaker,
                        DecisionRepository decisionRepository,
                        RequestBodyBuilder requestBodyBuilder) {
        this.decisionRepository = decisionRepository
        this.serviceRestClient = serviceRestClient
        this.decisionMaker = decisionMaker
        this.requestBodyBuilder = requestBodyBuilder
    }

    @Override
    void checkAndPropagate(String loanApplicationId, String loanApplicationDetails) {
        Decision decision = decisionMaker.makeLoanDecision(loanApplicationId, loanApplicationDetails)
        decisionRepository.saveDecision(decision)

        log.info("Sending a request to [$Dependencies.MARKETING] to create additional offer")
        serviceRestClient.forService(Dependencies.MARKETING.toString())
            .put()
            .onUrl("/api/marketing/$loanApplicationId")
            .body(requestBodyBuilder.buildMarketingRequestBody(decision))
            .withHeaders()
                .contentType(MARKETING_MAKER_V1)
            .andExecuteFor()
            .ignoringResponse()

        log.info("Sending a request to [$Dependencies.REPORTING] to save loan application for reporting purposes")
        serviceRestClient.forService(Dependencies.REPORTING.toString())
            .put()
            .onUrl("/api/reporting/$loanApplicationId")
            .body(requestBodyBuilder.buildReportingRequestBody(decision))
            .withHeaders()
                .contentType(REPORTING_MAKER_V1)
            .andExecuteFor()
            .ignoringResponse()
    }

}
