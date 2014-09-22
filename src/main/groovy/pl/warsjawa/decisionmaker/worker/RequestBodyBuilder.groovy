package pl.warsjawa.decisionmaker.worker

import groovy.json.JsonOutput
import groovy.transform.PackageScope
import pl.warsjawa.decisionmaker.domain.DecisionData

@PackageScope
class RequestBodyBuilder {

    String buildMarketingRequestBody(DecisionData data) {
        def builder = new groovy.json.JsonBuilder()
        builder {
            decision data.decision
            person(
                    firstName: data.person.firstName,
                    lastName: data.person.lastName
            )
        }

        return builder.toString()
    }

    String buildReportingRequestBody(DecisionData data) {
        return JsonOutput.toJson(data)
    }

}
