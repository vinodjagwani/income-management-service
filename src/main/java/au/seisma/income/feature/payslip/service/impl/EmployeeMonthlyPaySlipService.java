package au.seisma.income.feature.payslip.service.impl;

import au.seisma.income.feature.payslip.service.IncomeCalculatorService;
import au.seisma.income.feature.payslip.service.PaySlipService;
import au.seisma.income.feature.payslip.web.dto.EmployeeSlipCreateDetailRequest;
import au.seisma.income.feature.payslip.web.dto.EmployeeSlipCreateDetailResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.temporal.TemporalAdjusters;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmployeeMonthlyPaySlipService implements PaySlipService<Flux<EmployeeSlipCreateDetailRequest>, EmployeeSlipCreateDetailResponse> {

    IncomeCalculatorService incomeCalculatorService;

    @Override
    public Flux<EmployeeSlipCreateDetailResponse> generatePaySlip(final Flux<EmployeeSlipCreateDetailRequest> requests) {
        log.debug("Start generating payslip {}", requests);
        return requests.map(request -> new EmployeeSlipCreateDetailResponse(
                request.firstName(),
                request.lastName(),
                request.paymentStartDate(),
                request.paymentStartDate().with(TemporalAdjusters.lastDayOfMonth()),
                incomeCalculatorService.calculateGrossIncome(request.annualSalary()),
                incomeCalculatorService.calculateIncomeTax(request.annualSalary()),
                incomeCalculatorService.calculateNetIncome(request.annualSalary()),
                incomeCalculatorService.calculateSuper(request.annualSalary(), request.supperRate()),
                request.annualSalary()));
    }
}
