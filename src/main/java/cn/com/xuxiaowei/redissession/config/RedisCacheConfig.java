package cn.com.xuxiaowei.redissession.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Redis 开启声明缓存支持
 *
 * @author xuxiaowei
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {

    /**
     * Redis 缓存管理器
     */
    @Bean
    protected RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {

        // 检查指定的对象引用是否不是 null，如果是，抛出空指针异常
        RedisConnectionFactory redisConnectionFactory = Objects.requireNonNull(redisTemplate.getConnectionFactory());

        // 从Redis写入/读取二进制数据
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        // 获取序列化的Redis
        RedisSerializer<?> valueSerializer = redisTemplate.getValueSerializer();

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer));

        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    /**
     * Redis 缓存模板
     */
    @Bean
    protected RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        // Helper类简化了Redis数据访问代码。
        RedisTemplate<Object, Object> template = new RedisTemplate<>();

        // 设置连接工厂。
        template.setConnectionFactory(redisConnectionFactory);

        // 可以使用读写JSON
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // ObjectMapper提供了从基本POJO（普通旧Java对象）或从通用JSON树模型（{@link JsonNode}）读取和写入JSON的功能，以及执行转换的相关功能。
        ObjectMapper objectMapper = new ObjectMapper();

        // 枚举，定义影响Java对象序列化方式的简单开/关功能。
        // 默认情况下启用功能，因此默认情况下日期/时间序列化为时间戳。
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 如果启用，上下文<code> TimeZone </ code>将基本上覆盖任何其他TimeZone信息;如果禁用，则仅在值本身不包含任何TimeZone信息时使用。
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        // 注册使用Jackson核心序列化{@code java.time}对象的功能的类。
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // 添加序列化
        // 序列化与反序列化要相同
        // 使用什么类型的时间，就用什么类型去序列化和反序列化
        //  对 java 8 的 日期时间 进行序列化
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("MMM dd yyyy h:mm:ss:SSSa")));
//        <code>javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern()));</code>
//        <code>javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern()));</code>
//        <code>javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern()));</code>

        // 添加反序列化
        // 序列化与反序列化要相同
        // 使用什么类型的时间，就用什么类型去序列化和反序列化
        // 对 java 8 的 日期时间 进行反序列化
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("MMM dd yyyy h:mm:ss:SSSa")));
//        <code>javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern()));</code>
//        <code>javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern()));</code>
//        <code>javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern()));</code>

        // 用于注册可以扩展该映射器提供的功能的模块的方法; 例如，通过添加自定义序列化程序和反序列化程序的提供程序。
        objectMapper
                .registerModule(javaTimeModule)
                .registerModule(new ParameterNamesModule());

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 1
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // 2
        template.setKeySerializer(new StringRedisSerializer());

        template.afterPropertiesSet();

        return template;
    }

}
