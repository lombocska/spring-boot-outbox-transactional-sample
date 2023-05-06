package io.lombocska.springbootoutboxpatternexample.config;


import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.integration.jdbc.store.channel.PostgresChannelMessageStoreQueryProvider;
import org.springframework.messaging.MessageChannel;

@Configuration
public class PsqlPubSubConfig {

    @Bean
    public JdbcChannelMessageStore messageStore(DataSource dataSource) {
        JdbcChannelMessageStore messageStore = new JdbcChannelMessageStore(dataSource);
        messageStore.setChannelMessageStoreQueryProvider(new PostgresChannelMessageStoreQueryProvider());
        return messageStore;
    }

    @Bean
    public MessageChannel catChannel(JdbcChannelMessageStore messageStore) {
        return MessageChannels.queue(messageStore, "cat-group").get();
    }

    @Bean
    public MessageChannel dogChannel(JdbcChannelMessageStore messageStore) {
        return MessageChannels.queue(messageStore, "dog-group").get();
    }

}
