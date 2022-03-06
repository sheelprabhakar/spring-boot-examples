package examples.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisConfiguration {
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisClusterConfiguration config = new RedisClusterConfiguration();
        for(int port =7000; port < 7006; ++port){
            config.addClusterNode(new RedisClusterNode("192.168.1.29", port));
        }
        // RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("server", 6379);

        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}
