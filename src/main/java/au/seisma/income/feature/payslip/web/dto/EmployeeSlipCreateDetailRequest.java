package au.seisma.income.feature.payslip.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeSlipCreateDetailRequest(@NotEmpty(message = "firstName can't be null or empty")
                                              @ApiModelProperty(position = 1, name = "firstName", example = "Vinod") @JsonProperty("firstName") String firstName,
                                              @NotEmpty(message = "lastName can't be null or empty")
                                              @ApiModelProperty(position = 2, name = "lastName", example = "Jagwani") @JsonProperty("lastName") String lastName,
                                              @NotNull(message = "annualSalary can't be null or empty")
                                              @Range(min = 1, message = "annualSalary can't be negative or zero")
                                              @ApiModelProperty(position = 3, name = "annualSalary", example = "1000") @JsonProperty("annualSalary") BigDecimal annualSalary,
                                              @NotNull(message = "supperRate can't be null or empty")
                                              @Range(min = 0, message = "supperRate can't be negative")
                                              @ApiModelProperty(position = 4, name = "supperRate", example = "6", notes = "supperRate represents in percentage") @JsonProperty("supperRate") BigDecimal supperRate,
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                              @NotNull(message = "paymentStartDate can't be null or empty")
                                              @ApiModelProperty(position = 5, name = "paymentStartDate", example = "2021-09-13") @JsonProperty("paymentStartDate") LocalDate paymentStartDate) {
}
