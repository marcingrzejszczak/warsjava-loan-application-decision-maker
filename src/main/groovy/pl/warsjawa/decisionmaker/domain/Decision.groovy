package pl.warsjawa.decisionmaker.domain

import groovy.transform.CompileStatic
import groovy.transform.Immutable
import org.springframework.data.cassandra.mapping.PrimaryKey
import org.springframework.data.cassandra.mapping.Table

@CompileStatic
@Table
class Decision {
    @PrimaryKey
    final String loanApplicationId
    final String firstName, lastName, job, fraudStatus, decision
    final int amount

    Decision(String loanApplicationId, String firstName, String lastName, String job, int amount, String fraudStatus, String decision) {
        this.loanApplicationId = loanApplicationId
        this.firstName = firstName
        this.lastName = lastName
        this.job = job
        this.fraudStatus = fraudStatus
        this.decision = decision
        this.amount = amount
    }
}
