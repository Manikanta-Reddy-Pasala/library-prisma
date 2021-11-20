package com.prisma.library.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@ApiModel(value = "The User Details")
public class User implements Serializable {
    private static final long serialVersionUID = 3252591505029724236L;

    @Id
    @ApiModelProperty(value = "User Name", required = true)
    private String name;
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(value = "Member From")
    private Date memberSince;
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(value = "Member To")
    private Date memberTill;
    @ApiModelProperty(value = "Gender")
    private String gender;

}
