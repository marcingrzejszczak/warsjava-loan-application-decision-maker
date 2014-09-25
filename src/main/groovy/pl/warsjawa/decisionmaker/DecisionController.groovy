package pl.warsjawa.decisionmaker

import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.warsjawa.decisionmaker.domain.Decision
import pl.warsjawa.decisionmaker.repository.DecisionRepository
import pl.warsjawa.decisionmaker.worker.PropagationWorker

import javax.validation.constraints.NotNull
import java.util.concurrent.Callable

import static DecisionMakerApi.*

@Slf4j
@RestController
@RequestMapping(API_URL)
@Api(value = "loanApplication", description = "Decides whether to grant loan or not")
class DecisionController {

    private final PropagationWorker propagationWorker
    private final DecisionRepository decisionRepository

    @Autowired
    DecisionController(PropagationWorker propagationWorker, DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository
        this.propagationWorker = propagationWorker
    }

    @RequestMapping(
            value = DECISION_MAKER_URL,
            method = RequestMethod.PUT,
            consumes = API_VERSION_1,
            produces = API_VERSION_1)
    @ApiOperation(value = "Async granting of loan",
            notes = "This will asynchronously verify whether the user can get a loan")
    Callable<Void> decideOnLoanGranting(@PathVariable @NotNull String loanApplicationId, @RequestBody @NotNull String loanApplicationDetails) {
        return {
            propagationWorker.makeDecisionAndPropagate(loanApplicationId, loanApplicationDetails)
        }
    }

    @RequestMapping(
            value = DECISION_MAKER_URL,
            method = RequestMethod.GET,
            consumes = API_VERSION_1,
            produces = API_VERSION_1)
    @ApiOperation(value = "Finds asynchronously decision of granting a loan",
            notes = "This will asynchronously finds decision taken for the loan with given identifier")
    Callable<String> getLoanDecision(@PathVariable @NotNull String loanApplicationid) {
        return {
            Decision foundDecision = decisionRepository.findById(loanApplicationid)
            return prepareJsonResponse(foundDecision)
        }
    }

    private String prepareJsonResponse(Decision foundDecision) {
        def builder = new groovy.json.JsonBuilder()
        builder {
            loanApplicationId foundDecision.loanApplicationId
            decision foundDecision.decision
        }

        return builder.toString()
    }
}
