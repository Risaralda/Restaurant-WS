package com.example.restaurantws.ui.products

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavArgs
import com.example.restaurantws.`data`.main.models.categorias.Category
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.String
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class ProductsFragmentArgs(
  public val category: Category,
  public val title: String
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
    val result = Bundle()
    if (Parcelable::class.java.isAssignableFrom(Category::class.java)) {
      result.putParcelable("category", this.category as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(Category::class.java)) {
      result.putSerializable("category", this.category as Serializable)
    } else {
      throw UnsupportedOperationException(Category::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    result.putString("title", this.title)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): ProductsFragmentArgs {
      bundle.setClassLoader(ProductsFragmentArgs::class.java.classLoader)
      val __category : Category?
      if (bundle.containsKey("category")) {
        if (Parcelable::class.java.isAssignableFrom(Category::class.java) ||
            Serializable::class.java.isAssignableFrom(Category::class.java)) {
          __category = bundle.get("category") as Category?
        } else {
          throw UnsupportedOperationException(Category::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__category == null) {
          throw IllegalArgumentException("Argument \"category\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"category\" is missing and does not have an android:defaultValue")
      }
      val __title : String?
      if (bundle.containsKey("title")) {
        __title = bundle.getString("title")
        if (__title == null) {
          throw IllegalArgumentException("Argument \"title\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"title\" is missing and does not have an android:defaultValue")
      }
      return ProductsFragmentArgs(__category, __title)
    }
  }
}
