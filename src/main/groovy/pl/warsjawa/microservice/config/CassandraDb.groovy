package pl.warsjawa.microservice.config

import groovy.util.logging.Slf4j
import org.cassandraunit.utils.EmbeddedCassandraServerHelper
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
@Slf4j
class CassandraDb {

    @PostConstruct
    public void start() {
        log.info("Starting embedded Cassandra...")
        EmbeddedCassandraServerHelper.startEmbeddedCassandra("cassandra.yaml")
        log.info("Embedded Cassandra started.")
    }

    @PreDestroy
    public void stop() {
        log.debug("Stopping embedded Cassandra...")
        EmbeddedCassandraServerHelper.stopEmbeddedCassandra()
        log.debug("Embedded Cassandra stopped.")
    }

}
