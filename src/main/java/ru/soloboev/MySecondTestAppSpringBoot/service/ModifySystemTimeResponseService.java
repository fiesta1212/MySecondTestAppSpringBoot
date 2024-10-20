package ru.soloboev.MySecondTestAppSpringBoot.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.soloboev.MySecondTestAppSpringBoot.model.Response;
import ru.soloboev.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Service
@Qualifier("ModifySystemTimeResponseService")
public class ModifySystemTimeResponseService
        implements ModifyResponseService {
    @Override
    public Response modify(Response response){
        response.setSystemTime(DateTimeUtil.getCustomFormat()
                .format(new Date()));
        return response;
    }
}