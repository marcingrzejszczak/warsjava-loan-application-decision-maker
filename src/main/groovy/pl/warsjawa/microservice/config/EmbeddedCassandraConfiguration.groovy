package pl.warsjawa.microservice.config

import com.google.common.collect.ImmutableList
import com.ofg.config.BasicProfiles
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration

@TypeChecked
@Configuration
@Slf4j
@Profile([BasicProfiles.PRODUCTION, BasicProfiles.DEVELOPMENT])
class EmbeddedCassandraConfiguration extends AbstractCassandraConfiguration {

    private static final String KEYSPACE = "decisions"

    @Autowired
    private Environment env

    @Override
    protected String getKeyspaceName() {
        return KEYSPACE
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return ImmutableList.of(CreateKeyspaceSpecification.createKeyspace(KEYSPACE))
    }

    @Override
    protected int getPort() {
        return env.getProperty("embedded.cassandra.port", Integer.class)
    }

    @Bean
    public CassandraDb cassandraDb() {
        return new CassandraDb()
    }

}
