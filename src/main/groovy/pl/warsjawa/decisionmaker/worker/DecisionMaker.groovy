package pl.warsjawa.decisionmaker.worker

import groovy.json.JsonSlurper
import groovy.transform.PackageScope
import groovy.util.logging.Slf4j
import pl.warsjawa.decisionmaker.Dependencies

@Slf4j
@PackageScope
class DecisionMaker {

    DecisionData makeLoanDecision(String loanApplicationDetails) {
        def root = new JsonSlurper().parseText(loanApplicationDetails)

        String fraudStatus = root.fraudStatus.toLowerCase()

        String decision = doMakeDecision(fraudStatus)
        Person person = new Person(root.firstName, root.lastName)
        return new DecisionData(person, root.job, root.amount, fraudStatus, decision)
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
