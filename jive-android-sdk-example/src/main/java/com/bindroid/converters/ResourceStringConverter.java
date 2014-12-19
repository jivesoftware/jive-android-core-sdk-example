package com.bindroid.converters;

import android.content.res.Resources;
import android.view.View;
import com.bindroid.ValueConverter;

import java.util.List;

/**
 * A {@link com.bindroid.ValueConverter} for converting to boolean values or visibilities in
 * {@link com.bindroid.BindingMode#ONE_WAY} bindings.
 */
public class ResourceStringConverter extends ValueConverter {

  private final Resources resources;

  public ResourceStringConverter(Resources resources) {
    this.resources = resources;
  }

  public Object convertToTarget(Object sourceValue, Class<?> targetType) {
    // sourceValue will be an integer
    if (sourceValue != null && sourceValue instanceof Integer) {
      Integer stringId = (Integer) sourceValue;
      return resources.getString(stringId);
    } else {
      return "awww hell";
    }
  }
}
