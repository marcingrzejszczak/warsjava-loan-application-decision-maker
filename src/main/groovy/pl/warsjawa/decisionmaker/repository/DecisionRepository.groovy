package pl.warsjawa.decisionmaker.repository

import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import pl.warsjawa.decisionmaker.domain.Decision

@Repository
@TypeChecked
class DecisionRepository {

    private final MongoTemplate mongoTemplate

    @Autowired
    DecisionRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate
    }

    void saveDecision(Decision decision) {
        mongoTemplate.save(decision)
    }

    Decision findById(String loanApplicationId) {
        return mongoTemplate.findById(loanApplicationId, Decision.class)
    }
}
