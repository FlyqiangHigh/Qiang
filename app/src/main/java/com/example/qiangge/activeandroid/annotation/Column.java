package com.example.qiangge.activeandroid.annotation;

/*
 * Copyright (C) 2010 Michael Pardo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	 enum ConflictAction {
		ROLLBACK, ABORT, FAIL, IGNORE, REPLACE
	}

	 enum ForeignKeyAction {
		SET_NULL, SET_DEFAULT, CASCADE, RESTRICT, NO_ACTION
	}

	 String name();

	 int length() default -1;

	 boolean notNull() default false;

	 ConflictAction onNullConflict() default ConflictAction.FAIL;

	 ForeignKeyAction onDelete() default ForeignKeyAction.NO_ACTION;

	 ForeignKeyAction onUpdate() default ForeignKeyAction.NO_ACTION;

	 boolean unique() default false;

	 ConflictAction onUniqueConflict() default ConflictAction.FAIL;
}