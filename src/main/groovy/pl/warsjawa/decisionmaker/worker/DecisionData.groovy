package pl.warsjawa.decisionmaker.worker

import groovy.transform.CompileStatic
import groovy.transform.Immutable

@CompileStatic
@Immutable
class DecisionData {
    Person person
    String job
    int amount
    String fraudStatus
    String decision
}
