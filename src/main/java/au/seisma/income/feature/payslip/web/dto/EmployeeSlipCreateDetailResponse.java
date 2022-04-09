package au.seisma.income.feature.payslip.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeSlipCreateDetailResponse(
        @ApiModelProperty(position = 1, name = "firstName", example = "Vinod") @JsonProperty("firstName") String firstName,
        @ApiModelProperty(position = 2, name = "lastName", example = "Jagwani") @JsonProperty("lastName") String lastName,
        @ApiModelProperty(position = 3, name = "fromDate", example = "2020-12-12") @JsonProperty("fromDate") LocalDate fromDate,
        @ApiModelProperty(position = 4, name = "toDate", example = "2020-12-12") @JsonProperty("toDate") LocalDate toDate,
        @ApiModelProperty(position = 5, name = "grossIncome", example = "123") @JsonProperty("grossIncome") BigDecimal grossIncome,
        @ApiModelProperty(position = 6, name = "incomeTax", example = "12") @JsonProperty("incomeTax") BigDecimal incomeTax,
        @ApiModelProperty(position = 7, name = "netIncome", example = "12") @JsonProperty("netIncome") BigDecimal netIncome,
        @ApiModelProperty(position = 8, name = "superRate", example = "2") @JsonProperty("superRate") BigDecimal superRate,
        @ApiModelProperty(position = 9, name = "annualSalary", example = "1000") @JsonProperty("annualSalary") BigDecimal annualSalary) {
}
