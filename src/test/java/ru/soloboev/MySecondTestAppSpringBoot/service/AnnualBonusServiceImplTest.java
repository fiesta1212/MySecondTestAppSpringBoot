package ru.soloboev.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.soloboev.MySecondTestAppSpringBoot.model.Positions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnualBonusServiceImplTest {

    @Test
    void calculate() {
        //given
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        //when
        double result = new AnnualBonusServiceImpl().calculate(position, salary, bonus, workDays);

        //then
        double expected = 360493.8271604938;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterlyBonus_forManager() {
        Positions position = Positions.PM;
        double salary = 100000.00;
        double result = new AnnualBonusServiceImpl().calculateQuarterlyBonus(position, salary);
        double expected = salary * 0.25 * position.getPositionCoefficient();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterlyBonus_forNonManager() {
        Positions position = Positions.DEV;
        double salary = 100000.00;
        AnnualBonusServiceImpl service = new AnnualBonusServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> service.calculateQuarterlyBonus(position, salary));
    }
}