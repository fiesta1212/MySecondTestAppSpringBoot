package ru.soloboev.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.soloboev.MySecondTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}