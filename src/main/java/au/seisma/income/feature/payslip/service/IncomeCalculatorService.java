package au.seisma.income.feature.payslip.service;

import java.math.BigDecimal;

public interface IncomeCalculatorService {

    BigDecimal calculateNetIncome(final BigDecimal annualSalary);

    BigDecimal calculateIncomeTax(final BigDecimal annualSalary);

    BigDecimal calculateGrossIncome(final BigDecimal annualSalary);

    BigDecimal calculateSupperRate(final BigDecimal annualSalary, final BigDecimal superRate);

}
