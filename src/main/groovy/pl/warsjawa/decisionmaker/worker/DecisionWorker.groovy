package pl.warsjawa.decisionmaker.worker

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.warsjawa.decisionmaker.domain.Decision
import pl.warsjawa.decisionmaker.repository.DecisionRepository

@Slf4j
@Service
@PackageScope
@CompileStatic
class DecisionWorker implements PropagationWorker {

    private final DecisionMaker decisionMaker
    private final DecisionRepository decisionRepository
    private final FlowPropagator flowPropagator

    @Autowired
    DecisionWorker(DecisionMaker decisionMaker,
                   DecisionRepository decisionRepository,
                   FlowPropagator flowPropagator) {
        this.flowPropagator = flowPropagator
        this.decisionRepository = decisionRepository
        this.decisionMaker = decisionMaker
    }

    @Override
    void makeDecisionAndPropagate(String loanApplicationId, String loanApplicationDetails) {
        Decision decision = decisionMaker.makeLoanDecision(loanApplicationId, loanApplicationDetails)
        decisionRepository.saveDecision(decision)
        flowPropagator.checkAndPropagate(decision)
    }

}
