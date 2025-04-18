package ru.practicum.myblog;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.myblog.config.DataSourceConfiguration;
import ru.practicum.myblog.config.WebConfiguration;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, WebConfiguration.class})
@TestConfiguration
@TestPropertySource(locations = "classpath:application-test.yml")
public abstract class TestContext {
}
