# Spring Boot Starter for GDPR Compliance

A custom Spring Boot Starter to provide an easy way for gdpr compliance through masking and encryption operations. For background information see the [TBD Confluence Documentation](https://portal.ionity.eu)

## How to Use this Starter?

First apply this library to your service by adding the following dependencies to your `build.gradle.kts` file:

```kotlin
implementation("eu.ionity.commons:spring-boot-starter-gdpr-compliance:<version>")
```

## How to Use this Starter?

### Data Masking for Storing
Data masking for storing can be achieved using annotations. Use the `@Convert` annotation with the `DataMaskingConverter` class to mask sensitive data before storing it in your database.

### Data Masking for Logging
Data masking for logging can be achieved using mask patterns in the `logback-spring.xml` file. This ensures that sensitive data is masked in log messages, helping you comply with GDPR requirements.
## Implementation Guidelines

## Example Implementation

### Data Masking for Storing example:
```kotlin
@Entity
data class User(
    @Convert(converter = DataMaskingConverter::class)
    var firstName: String? = "John",
    // Other fields...
)

### Configuration in logback-spring.xml:

To configure data masking for logging, follow these steps:

1. **Define Mask Patterns**: Identify the sensitive data in your application that needs to be masked, such as social security numbers, credit card numbers, or personal names.

2. **Configure Masking Pattern Layout**: Define a custom layout using `MaskingPatternLayout` provided by this starter in the `logback-spring.xml` configuration file.

3. **Add Masking Patterns**: Specify the masking patterns for the sensitive data you want to mask within the `MaskingPatternLayout` configuration. You can use regular expressions to define the patterns.

Example `logback-spring.xml` configuration:

```xml
<configuration>
    <appender name="mask" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
            <layout class="eu.ionity.commons.gdprcompliance.masking.logging.MaskingPatternLayout">
                <maskPattern>(\w+@\w+\.\w+)</maskPattern> <!-- Email pattern -->
                <pattern>%-5p [%d{ISO8601,UTC}] [%thread] %c: %m%n%rootException</pattern>
            </layout>
        </encoder>
    </appender>

    <root level="debug">
        <appender-ref ref="mask" />
    </root>
</configuration>
