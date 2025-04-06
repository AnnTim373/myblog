package ru.practicum.myblog;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.practicum.myblog.config.DataSourceConfiguration;
import ru.practicum.myblog.config.WebConfiguration;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class TestContext {
}
