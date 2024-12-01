package ru.soloboev.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.soloboev.MySecondTestAppSpringBoot.model.Positions;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService{
    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        int daysInYear = isYear() ? 366 : 365;
        return salary * bonus * daysInYear * positions.getPositionCoefficient() / workDays;
    }

    private boolean isYear() {
        int year = java.time.Year.now().getValue();
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    @Override
    public double calculateQuarterlyBonus(Positions position, double salary) {
        if (!position.isManager()) {
            throw new IllegalArgumentException("Quarterly bonus for managers.");
        }
        return salary * 0.25 * position.getPositionCoefficient();
    }
}