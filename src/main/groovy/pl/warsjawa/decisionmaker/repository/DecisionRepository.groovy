package pl.warsjawa.decisionmaker.repository

import com.google.common.collect.ImmutableMap
import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cassandra.core.cql.CqlIdentifier
import org.springframework.data.cassandra.core.CassandraAdminOperations
import org.springframework.stereotype.Repository
import pl.warsjawa.decisionmaker.domain.Decision

import javax.annotation.PostConstruct

@Repository
@TypeChecked
class DecisionRepository {

    private final CassandraAdminOperations cassandraTemplate

    @Autowired
    DecisionRepository(CassandraAdminOperations cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate
    }

    @PostConstruct
    void init() {
        CqlIdentifier decisionId = CqlIdentifier.cqlId(Decision.class.getSimpleName().toLowerCase())
        cassandraTemplate.createTable(true, decisionId, Decision.class, ImmutableMap.of())
    }

    void saveDecision(Decision decision) {
        cassandraTemplate.insert(decision)
    }

    Decision findById(String loanApplicationId) {
        return cassandraTemplate.selectOneById(Decision.class, loanApplicationId)
    }
}
