package au.seisma.income.feature.payslip.web.controller;


import au.seisma.income.feature.payslip.web.dto.EmployeeSlipCreateDetailResponse;
import au.seisma.income.feature.payslip.web.facade.EmployeeMonthlyPaySlipFacade;
import au.seisma.income.utils.MockUtils;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;

import static java.util.Optional.ofNullable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
@WebFluxTest(value = EmployeePaySlipController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
class EmployeePaySlipControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    EmployeeMonthlyPaySlipFacade employeeMonthlyPaySlipFacade;

    @Test
    @SneakyThrows
    @DisplayName("Test Generate Employee Monthly Payslip")
    void testGenerateEmployeesMonthlyPaySlip() {
        final String request = ofNullable(MockUtils.getResource("mock/monthly-payslip-create-request.json", String.class)).orElse("");
        final EmployeeSlipCreateDetailResponse[] response = ofNullable(MockUtils.getResource("mock/monthly-payslip-create-response.json", EmployeeSlipCreateDetailResponse[].class)).orElse(new EmployeeSlipCreateDetailResponse[]{});
        when(employeeMonthlyPaySlipFacade.generatePaySlip(Mockito.any())).thenReturn(Flux.just(response));
        webTestClient.post().uri("/v1/payslips/employees/monthly-payslips")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$[0].firstName").isEqualTo("Vinod")
                .jsonPath("$[0].lastName").isEqualTo("Jagwani")
                .jsonPath("$[0].fromDate").isEqualTo("2021-09-13")
                .jsonPath("$[0].toDate").isEqualTo("2021-09-30")
                .jsonPath("$[0].grossIncome").isEqualTo("50042")
                .jsonPath("$[0].incomeTax").isEqualTo("39561")
                .jsonPath("$[0].netIncome").isEqualTo("10481")
                .jsonPath("$[0].superRate").isEqualTo("300502210")
                .jsonPath("$[0].annualSalary").isEqualTo("600500");
    }

    @Test
    @SneakyThrows
    void testGenerateEmployeesMonthlyPaySlipWithInvalidRequest() {
        final String request = ofNullable(MockUtils.getResource("mock/monthly-payslip-create-invalid-request.json", String.class)).orElse("");
        final EmployeeSlipCreateDetailResponse[] response = ofNullable(MockUtils.getResource("mock/monthly-payslip-create-response.json", EmployeeSlipCreateDetailResponse[].class)).orElse(new EmployeeSlipCreateDetailResponse[]{});
        when(employeeMonthlyPaySlipFacade.generatePaySlip(any())).thenReturn(Flux.just(response));
        webTestClient.post().uri("/v1/payslips/employees/monthly-payslips")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.code").isEqualTo("1000")
                .jsonPath("$.message").isEqualTo("generateEmployeesMonthlyPaySlip.request[0].firstName: firstName can't be null or empty");
    }
}
