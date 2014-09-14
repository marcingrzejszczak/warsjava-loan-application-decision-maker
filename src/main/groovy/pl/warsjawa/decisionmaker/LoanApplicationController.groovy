package pl.warsjawa.decisionmaker

import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pl.warsjawa.decisionmaker.worker.PropagationWorker

import javax.validation.constraints.NotNull
import java.util.concurrent.Callable

import static DecisionMakerApi.*

@CompileStatic
@Slf4j
@RestController
@RequestMapping(API_URL)
@Api(value = "loanApplication", description = "Decides whether to grant loan or not")
class LoanApplicationController {

    private final PropagationWorker propagationWorker

    @Autowired
    LoanApplicationController(PropagationWorker propagationWorker) {
        this.propagationWorker = propagationWorker
    }

    @RequestMapping(
            value = LOAN_APPLICATION_URL,
            method = RequestMethod.PUT,
            consumes = API_VERSION_1,
            produces = API_VERSION_1)
    @ApiOperation(value = "Async granting of loan",
            notes = "This will asynchronously verify whether the user can get a loan")
    Callable<Void> decideOnLoanGranting(@PathVariable @NotNull String loanApplicationId, @RequestBody @NotNull String loanApplicationDetails) {
        return {
            propagationWorker.checkAndPropagate(loanApplicationId, loanApplicationDetails)
        }
    }
}
