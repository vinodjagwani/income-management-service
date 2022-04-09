package au.seisma.income.feature.payslip.web.facade;


import au.seisma.income.feature.payslip.service.PaySlipService;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class EmployeeMonthlyPaySlipFacadeTest {

    @Mock
    PaySlipService<Flux<EmployeeSlipCreateDetailRequest>, EmployeeSlipCreateDetailResponse> paySlipService;

    @InjectMocks
    EmployeeMonthlyPaySlipFacade employeeMonthlyPaySlipFacade;

    @Test
    @DisplayName("Test Generate Payslip")
    void testGeneratePaySlip() {
        when(paySlipService.generatePaySlip(any())).thenReturn(Flux.empty());
        employeeMonthlyPaySlipFacade.generatePaySlip(Flux.just(buildEmployeeSlipCreateDetailRequest()));
        verify(paySlipService, atLeastOnce()).generatePaySlip(any());
    }

    private EmployeeSlipCreateDetailRequest buildEmployeeSlipCreateDetailRequest() {
        return new EmployeeSlipCreateDetailRequest("test", "test", BigDecimal.ONE, BigDecimal.ONE, LocalDate.now());
    }
}
