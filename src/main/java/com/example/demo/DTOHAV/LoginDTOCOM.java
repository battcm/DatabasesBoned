package com.example.demo.DTOHAV;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@ToString(doNotUseGetters = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Builder
@Table(name="UserTable")
public class LoginDTOCOM {
    @JsonProperty("username")
    @NonNull
    @Id
    @Column(name ="UserName")
    private String username;

    @JsonProperty("passwordHash")
    @NonNull
    @Column(name ="passwordHash")
    private String passwordHash;

    @JsonProperty("passwordSalt")
    @NonNull
    @Column(name ="passwordSalt")
    private String passwordSalt;
}
