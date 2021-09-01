package com.example.restaurantws.ui.categories

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.example.restaurantws.NavGraphDirections
import com.example.restaurantws.R
import com.example.restaurantws.`data`.main.models.categorias.Category
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

public class CategoriesFragmentDirections private constructor() {
  private data class ActionFirstFragmentToProductsFragment(
    public val category: Category
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_FirstFragment_to_productsFragment

    @Suppress("CAST_NEVER_SUCCEEDS")
    public override fun getArguments(): Bundle {
      val result = Bundle()
      if (Parcelable::class.java.isAssignableFrom(Category::class.java)) {
        result.putParcelable("category", this.category as Parcelable)
      } else if (Serializable::class.java.isAssignableFrom(Category::class.java)) {
        result.putSerializable("category", this.category as Serializable)
      } else {
        throw UnsupportedOperationException(Category::class.java.name +
            " must implement Parcelable or Serializable or must be an Enum.")
      }
      return result
    }
  }

  public companion object {
    public fun actionFirstFragmentToProductsFragment(category: Category): NavDirections =
        ActionFirstFragmentToProductsFragment(category)

    public fun actionGlobalPedidosFragment(): NavDirections =
        NavGraphDirections.actionGlobalPedidosFragment()
  }
}
