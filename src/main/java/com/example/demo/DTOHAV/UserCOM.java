package com.example.demo.DTOHAV;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;

@Data
@ToString(doNotUseGetters = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Builder
public class UserCOM {
    @JsonProperty("username")
    @NonNull
    @Id
    @Column(name ="UserName")
    private String username;
    @JsonProperty("password")
    @NonNull
    @Column(name ="Password")
    private String password;
}
