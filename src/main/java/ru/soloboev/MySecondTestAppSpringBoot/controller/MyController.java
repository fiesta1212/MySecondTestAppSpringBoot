package ru.soloboev.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.soloboev.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.soloboev.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.soloboev.MySecondTestAppSpringBoot.model.*;
import ru.soloboev.MySecondTestAppSpringBoot.service.*;
import ru.soloboev.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.time.Instant;
import java.util.Date;
@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;
    private final ModifyRequestServiceSource modifyRequestServiceSource;
    private final AnnualBonusService annualBonusService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        @Qualifier("ModifyRequestServiceSource") ModifyRequestService modifyRequestService,
                        @Qualifier("ModifyRequestServiceSource") ModifyRequestServiceSource modifyRequestServiceSource,
                        AnnualBonusService annualBonusService){
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
        this.modifyRequestServiceSource = modifyRequestServiceSource;
        this.annualBonusService = annualBonusService;
    }
    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult){
        log.info("Received request: {}", request);
        request.setReceivedTime(Instant.now());
        Response response = buildInitialResponse(request);
        try {
            validateRequest(bindingResult, request);
        } catch (ValidationFailedException e) {
            log.error("ValidationFailedException occurred: {}", e.getMessage());
            return buildErrorResponse(response, Codes.FAILED, ErrorCodes.VALIDATION, ErrorMessages.VALIDATION, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Exception occurred: {}", e.getMessage());
            return buildErrorResponse(response, Codes.FAILED, ErrorCodes.UNKNOWN, ErrorMessages.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        applyModifications(request, response);
        log.info("Final response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void applyModifications(Request request, Response response) {
        modifyRequestServiceSource.modify(request);
        modifyRequestService.modify(request);
        modifyResponseService.modify(response);
    }

    private ResponseEntity<Response> buildErrorResponse(Response response, Codes code, ErrorCodes errorCode, ErrorMessages errorMessage, HttpStatus status) {
        response.setCode(code);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        log.info("Updated response: {}", response);
        return new ResponseEntity<>(response, status);
    }

    private Response buildInitialResponse(Request request) {
        return Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .annualBonus(annualBonusService.calculate(request.getPosition(),request.getSalary(),request.getBonus(),request.getWorkDays()))
                .quarterlyBonus(annualBonusService.calculateQuarterlyBonus(request.getPosition(), request.getSalary()))
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
    }

    private void validateRequest(BindingResult bindingResult, Request request) throws ValidationFailedException, UnsupportedCodeException {
        if (bindingResult.hasErrors()) {
            log.error("BindingResult has errors: {}", bindingResult.getAllErrors());
            throw new ValidationFailedException("Validation failed!");
        }
        validationService.isValid(bindingResult);
        if ("123".equals(request.getUid())) {
            log.error("Unsupported UID: {}", request.getUid());
            throw new UnsupportedCodeException("Uid = 123 не поддерживается");
        }
    }
}
