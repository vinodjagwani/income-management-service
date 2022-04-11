package au.seisma.income.feature.payslip.service.impl;

import au.seisma.income.config.IncomeTaxRatePropConfig;
import au.seisma.income.feature.payslip.service.IncomeCalculatorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmployeeIncomeService implements IncomeCalculatorService {

    IncomeTaxRatePropConfig incomeTaxRatePropConfig;

    @Override
    public BigDecimal calculateNetIncome(final BigDecimal annualSalary) {
        log.debug("Start calculating net income for  annualSalary {}", annualSalary);
        return calculateGrossIncome(annualSalary).subtract(calculateIncomeTax(annualSalary));
    }

    @Override
    public BigDecimal calculateIncomeTax(final BigDecimal annualSalary) {
        log.debug("Start calculating income tax for annualSalary {}", annualSalary);
        return incomeTaxRatePropConfig.getRates().keySet().stream()
                .filter(fRange -> annualSalary.compareTo(new BigDecimal(fRange.split("-")[0])) >= 0 && annualSalary.compareTo(new BigDecimal(fRange.split("-")[1])) <= 0)
                .findFirst()
                .map(range -> {
                    final Map<String, String> rangeValues = incomeTaxRatePropConfig.getRates();
                    final String[] splitRange = rangeValues.get(range).split(",");
                    final BigDecimal taxValue = new BigDecimal(splitRange[0]);
                    final BigDecimal taxAddition = new BigDecimal(splitRange[1]).divide(BigDecimal.valueOf(100L), RoundingMode.HALF_EVEN);
                    final BigDecimal onTotal = new BigDecimal(splitRange[2]);
                    return taxValue.add((annualSalary.subtract(onTotal).multiply(taxAddition))).divide(BigDecimal.valueOf(12L), RoundingMode.HALF_EVEN);
                }).orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal calculateGrossIncome(final BigDecimal annualSalary) {
        log.debug("Start calculating gross income for annualSalary {}", annualSalary);
        return annualSalary.divide(BigDecimal.valueOf(12), RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal calculateSuper(final BigDecimal annualSalary, final BigDecimal superRate) {
        log.debug("Start calculating super for annualSalary {}", annualSalary);
        return calculateGrossIncome(annualSalary).multiply(superRate).divide(BigDecimal.valueOf(100L), RoundingMode.HALF_EVEN);
    }
}
