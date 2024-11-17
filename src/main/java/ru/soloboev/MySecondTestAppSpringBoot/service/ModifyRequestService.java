package ru.soloboev.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.soloboev.MySecondTestAppSpringBoot.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}