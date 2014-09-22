package pl.warsjawa.decisionmaker.worker

import groovy.json.JsonSlurper
import groovy.transform.PackageScope
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import pl.warsjawa.decisionmaker.domain.Decision

@Slf4j
@PackageScope
@Service
class DecisionMaker {

    Decision makeLoanDecision(String loanApplicationId, String loanApplicationDetails) {
        def root = new JsonSlurper().parseText(loanApplicationDetails)

        String fraudStatus = root.fraudStatus.toLowerCase()

        String decision = doMakeDecision(fraudStatus)
        return new Decision(loanApplicationId, root.firstName, root.lastName, root.job, root.amount, fraudStatus, decision)
    }

    private String doMakeDecision(fraudStatus) {
        log.info("making decision for: $fraudStatus")
        switch (fraudStatus) {
            case 'ok':
                return 'granted'
            case 'verification_required':
                return 'on_hold'
            default:
                return 'rejected'
        }
    }

}
