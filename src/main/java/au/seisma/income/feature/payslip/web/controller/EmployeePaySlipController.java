package au.seisma.income.feature.payslip.web.controller;

import au.seisma.income.annotation.DefaultApiResponse;
import au.seisma.income.feature.payslip.web.dto.EmployeeSlipCreateDetailRequest;
import au.seisma.income.feature.payslip.web.dto.EmployeeSlipCreateDetailResponse;
import au.seisma.income.feature.payslip.web.facade.EmployeeMonthlyPaySlipFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@Api(tags = "PaySlips")
@RequiredArgsConstructor
@RequestMapping("/v1/payslips")
@Tag(name = "PaySlips", description = "PaySlips")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmployeePaySlipController {

    EmployeeMonthlyPaySlipFacade employeeMonthlyPaySlipFacade;

    @DefaultApiResponse
    @PostMapping(value = "/employees/monthly-payslips", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Generate Monthly Employees Payslip Api", nickname = "generateEmployeesMonthlyPaySlip", notes = "This API is used for generating monthly payslips for employees")
    public ResponseEntity<Flux<EmployeeSlipCreateDetailResponse>> generateEmployeesMonthlyPaySlip(@Valid @RequestBody @NotEmpty(message = "employees can't be null or empty") final List<@Valid @NotNull(message = "employee can't be null or empty") EmployeeSlipCreateDetailRequest> request) {
        return new ResponseEntity<>(employeeMonthlyPaySlipFacade.generatePaySlip(Flux.fromIterable(request)), HttpStatus.CREATED);
    }
}
