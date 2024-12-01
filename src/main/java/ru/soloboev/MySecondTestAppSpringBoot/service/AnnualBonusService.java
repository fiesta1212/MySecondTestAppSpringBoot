package ru.soloboev.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.soloboev.MySecondTestAppSpringBoot.model.Positions;

@Service
public interface AnnualBonusService {
    double calculate(Positions positions, double salary, double bonus, int workDays);
    double calculateQuarterlyBonus (Positions position, double salary);
}