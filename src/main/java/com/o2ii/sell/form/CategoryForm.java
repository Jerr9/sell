package com.o2ii.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CategoryForm {
    private Integer categoryId;
    @NotEmpty(message = "品类名不能为空")
    private String categoryName;
    @NotNull(message = "品类编码不能为空")
    private Integer categoryType;
}
