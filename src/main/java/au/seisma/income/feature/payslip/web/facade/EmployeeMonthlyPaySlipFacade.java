package au.seisma.income.feature.payslip.web.facade;

import au.seisma.income.feature.payslip.service.PaySlipService;
import au.seisma.income.feature.payslip.web.dto.EmployeeSlipCreateDetailRequest;
import au.seisma.income.feature.payslip.web.dto.EmployeeSlipCreateDetailResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmployeeMonthlyPaySlipFacade {

    PaySlipService<Flux<EmployeeSlipCreateDetailRequest>, EmployeeSlipCreateDetailResponse> paySlipService;

    public Flux<EmployeeSlipCreateDetailResponse> generatePaySlip(final Flux<EmployeeSlipCreateDetailRequest> request) {
        log.debug("Start calling generate payslip service with request {}", request);
        return paySlipService.generatePaySlip(request);
    }
}
