package au.seisma.income.feature.payslip.service.impl;

import au.seisma.income.feature.payslip.service.IncomeCalculatorService;
import au.seisma.income.feature.payslip.web.dto.EmployeeSlipCreateDetailRequest;
import au.seisma.income.feature.payslip.web.dto.EmployeeSlipCreateDetailResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class EmployeeMonthlyPaySlipServiceTest {

    @Mock
    IncomeCalculatorService incomeCalculatorService;

    @InjectMocks
    EmployeeMonthlyPaySlipService employeeMonthlyPaySlipService;

    @Test
    @DisplayName("Test Generate Payslip")
    void testGeneratePaySlip() {
        when(incomeCalculatorService.calculateGrossIncome(any(BigDecimal.class))).thenReturn(BigDecimal.ONE);
        when(incomeCalculatorService.calculateIncomeTax(any(BigDecimal.class))).thenReturn(BigDecimal.ONE);
        when(incomeCalculatorService.calculateNetIncome(any(BigDecimal.class))).thenReturn(BigDecimal.ONE);
        when(incomeCalculatorService.calculateSupperRate(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(BigDecimal.ONE);
        final Flux<EmployeeSlipCreateDetailResponse> response = employeeMonthlyPaySlipService.generatePaySlip(Flux.just(buildEmployeeSlipCreateDetailRequest()));
        StepVerifier.create(response).expectNextCount(1).verifyComplete();
        response.blockFirst();
        verify(incomeCalculatorService, atLeastOnce()).calculateGrossIncome(any(BigDecimal.class));
        verify(incomeCalculatorService, atLeastOnce()).calculateIncomeTax(any(BigDecimal.class));
        verify(incomeCalculatorService, atLeastOnce()).calculateNetIncome(any(BigDecimal.class));
        verify(incomeCalculatorService, atLeastOnce()).calculateSupperRate(any(BigDecimal.class), any(BigDecimal.class));
    }

    private EmployeeSlipCreateDetailRequest buildEmployeeSlipCreateDetailRequest() {
        return new EmployeeSlipCreateDetailRequest("test", "test", BigDecimal.ONE, BigDecimal.ONE, LocalDate.now());
    }
}
