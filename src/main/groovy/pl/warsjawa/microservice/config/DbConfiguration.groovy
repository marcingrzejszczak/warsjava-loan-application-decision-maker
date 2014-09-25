package pl.warsjawa.microservice.config

import com.github.fakemongo.Fongo
import com.mongodb.Mongo
import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.WriteResultChecking
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@TypeChecked
@Configuration
@EnableMongoRepositories('pl.warsjawa.decisionmaker')
class DbConfiguration {

    @Bean
    Mongo mongo() {
        return new Fongo("decisionmaker-service-fongo").getMongo()
    }

    @Bean
    MongoTemplate mongoTemplate(Mongo mongo, @Value('${mongodb.dbname:client}') String dbName) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo, dbName)
        mongoTemplate.writeResultChecking = WriteResultChecking.EXCEPTION
        return mongoTemplate
    }

}