package io.lombocska.listener.config;

import java.sql.DriverManager;
import javax.sql.DataSource;
import org.postgresql.jdbc.PgConnection;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jdbc.channel.PostgresChannelMessageTableSubscriber;
import org.springframework.integration.jdbc.channel.PostgresSubscribableChannel;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.integration.jdbc.store.channel.PostgresChannelMessageStoreQueryProvider;

@Configuration
public class PsqlPubSubConfig {

    @Bean
    public JdbcChannelMessageStore messageStore(DataSource dataSource) {
        JdbcChannelMessageStore messageStore = new JdbcChannelMessageStore(dataSource);
        messageStore.setChannelMessageStoreQueryProvider(new PostgresChannelMessageStoreQueryProvider());
        return messageStore;
    }

    @Bean
    public PostgresChannelMessageTableSubscriber subscriber(DataSourceProperties dsp) {
        return new PostgresChannelMessageTableSubscriber(() ->
                DriverManager.getConnection(dsp.determineUrl(), dsp.determineUsername(), dsp.determinePassword()).unwrap(PgConnection.class));
    }

    @Bean
    public PostgresSubscribableChannel catSubscriptionChannel(PostgresChannelMessageTableSubscriber subscriber,
                                                              JdbcChannelMessageStore messageStore) {
        return new PostgresSubscribableChannel(messageStore, "cat-group", subscriber);
    }

    @Bean
    public PostgresSubscribableChannel dogSubscriptionChannel(PostgresChannelMessageTableSubscriber subscriber,
                                                              JdbcChannelMessageStore messageStore) {
        return new PostgresSubscribableChannel(messageStore, "dog-group", subscriber);
    }

}
