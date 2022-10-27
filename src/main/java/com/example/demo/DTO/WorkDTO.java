package com.example.demo.DTO;

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
@Table(name="WorksFor")
public class WorkDTO {
    @JsonProperty("user")
    @NonNull
    @Id
    @Column(name ="UserName")
    private String user;

    @JsonProperty("rest")
    @NonNull
    @Column(name ="Password")
    private int restId;
    @JsonProperty("perm")
    @NonNull
    @Column(name ="Password")
    private String perm;}



