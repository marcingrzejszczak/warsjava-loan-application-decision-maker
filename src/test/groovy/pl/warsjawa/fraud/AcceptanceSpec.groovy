package pl.warsjawa.fraud

import pl.warsjawa.base.MicroserviceMvcWiremockSpec
import spock.lang.Unroll

class AcceptanceSpec extends MicroserviceMvcWiremockSpec {

    @Unroll
    def "should mark job position as [#fraudResult] for a [#job] job"() {
        given: "a loan application with job position 'IT'"
        when: 'performing a loan application decision'
        then: 'ui should be updated with loan decision'
    }
    


}
