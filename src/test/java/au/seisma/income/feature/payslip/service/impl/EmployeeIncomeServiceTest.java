package au.seisma.income.feature.payslip.service.impl;

import au.seisma.income.config.IncomeTaxRatePropConfig;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class EmployeeIncomeServiceTest {

    @Mock
    IncomeTaxRatePropConfig incomeTaxRatePropConfig;

    @InjectMocks
    EmployeeIncomeService employeeIncomeService;

    @Test
    @DisplayName("Calculate Employee Net Income")
    void testCalculateNetIncome() {
        final BigDecimal result = employeeIncomeService.calculateNetIncome(BigDecimal.valueOf(100L));
        assertEquals(BigDecimal.valueOf(8L), result);
    }

    @Test
    @DisplayName("Calculate Employee Income Tax")
    void testCalculateIncomeTax() {
        when(incomeTaxRatePropConfig.getRates()).thenReturn(Map.of("18201-37000", "0,19,18200"));
        employeeIncomeService.calculateIncomeTax(BigDecimal.valueOf(36000L));
        verify(incomeTaxRatePropConfig, atLeastOnce()).getRates();
    }

    @Test
    @DisplayName("Calculate Employee Gross Income")
    void testCalculateGrossIncome() {
        final BigDecimal result = employeeIncomeService.calculateGrossIncome(BigDecimal.valueOf(300L));
        assertEquals(BigDecimal.valueOf(25L), result);
    }

    @Test
    @DisplayName("Calculate Employee Supper Rate")
    void testCalculateSupperRate() {
        final BigDecimal result = employeeIncomeService.calculateSupperRate(BigDecimal.valueOf(5000L), BigDecimal.valueOf(7));
        assertEquals(BigDecimal.valueOf(29L), result);
    }
}
