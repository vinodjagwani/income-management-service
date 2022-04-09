package au.seisma.income.feature.payslip.service;

import reactor.core.publisher.Flux;

public interface PaySlipService<D, R> {

    Flux<R> generatePaySlip(final D request);

}
