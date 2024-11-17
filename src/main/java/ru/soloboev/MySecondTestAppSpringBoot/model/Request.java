package ru.soloboev.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @NotBlank
    @Size(max = 32, message = "Длина не может превышать 32 символа")
    private String uid;

    @NotBlank
    @Size(max = 32, message = "Длина не может превышать 32 символа")
    private String operationUid;

    private Systems systemName;
    private String systemTime;
    private String source;
    private Instant receivedTime;

    @Min(value = 1, message = "Минимальное значение — 1")
    @Max(value = 100000, message = "Максимальное значение — 100000")
    private int communicationId;

    private int templateId;
    private int productCode;
    private int smsCode;
    @Override
    public String toString(){
        return "{"+
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName='" + systemName + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId='" + communicationId + '\'' +
                ", templateId='" + templateId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", receivedTime='" + receivedTime + '\'' +
                '}';
    }
}
