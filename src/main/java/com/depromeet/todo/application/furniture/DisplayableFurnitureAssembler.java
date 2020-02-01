package com.depromeet.todo.application.furniture;

import com.depromeet.todo.application.Displayable;
import com.depromeet.todo.domain.furniture.Furniture;

public interface DisplayableFurnitureAssembler {
    Displayable toDisplayableFurniture(Furniture furniture);
}
