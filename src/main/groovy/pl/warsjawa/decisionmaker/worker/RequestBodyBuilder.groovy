package pl.warsjawa.decisionmaker.worker

import groovy.transform.PackageScope
import pl.warsjawa.decisionmaker.domain.Decision

@PackageScope
class RequestBodyBuilder {

    String buildMarketingRequestBody(Decision data) {
        def builder = new groovy.json.JsonBuilder()
        builder {
            decision data.decision
            person(
                    firstName: data.firstName,
                    lastName: data.lastName
            )
        }

        return builder.toString()
    }

    String buildReportingRequestBody(Decision data) {
        def builder = new groovy.json.JsonBuilder()
        builder {
            decision data.decision
            fraudStatus: data.fraudStatus
            job: data.job
            amount: data.amount
            person(
                    firstName: data.firstName,
                    lastName: data.lastName
            )
        }

        return builder.toString()
    }

}
