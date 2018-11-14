package com.balan.core.entity;

import java.io.Serializable;

public interface Persistable<PK extends Serializable> extends Serializable {

    PK getId();
}
