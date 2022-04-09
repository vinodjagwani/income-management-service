package au.seisma.income.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.Map;


@Getter
@Validated
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "income-tax")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class IncomeTaxRatePropConfig {

    private Map<String, String> rates;

}
