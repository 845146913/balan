package com.balan.core.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public abstract class GenericEntity<PK extends Serializable> implements Persistable<PK> {

    @Id  // 必须配置主键,不然selectByPrimaryKey方法会报错
    private PK id;
}
