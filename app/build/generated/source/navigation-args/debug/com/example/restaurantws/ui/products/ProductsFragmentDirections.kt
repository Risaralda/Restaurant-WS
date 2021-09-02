package com.example.restaurantws.ui.products

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.example.restaurantws.NavGraphDirections
import com.example.restaurantws.R
import com.example.restaurantws.`data`.main.models.products.Product
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Suppress

public class ProductsFragmentDirections private constructor() {
  private data class ActionProductsFragmentToProductDetailFragment(
    public val producto: Product,
    public val title: String,
    public val isLocalPedido: Boolean
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_productsFragment_to_productDetailFragment

    @Suppress("CAST_NEVER_SUCCEEDS")
    public override fun getArguments(): Bundle {
      val result = Bundle()
      if (Parcelable::class.java.isAssignableFrom(Product::class.java)) {
        result.putParcelable("producto", this.producto as Parcelable)
      } else if (Serializable::class.java.isAssignableFrom(Product::class.java)) {
        result.putSerializable("producto", this.producto as Serializable)
      } else {
        throw UnsupportedOperationException(Product::class.java.name +
            " must implement Parcelable or Serializable or must be an Enum.")
      }
      result.putString("title", this.title)
      result.putBoolean("isLocalPedido", this.isLocalPedido)
      return result
    }
  }

  public companion object {
    public fun actionProductsFragmentToProductDetailFragment(
      producto: Product,
      title: String,
      isLocalPedido: Boolean
    ): NavDirections = ActionProductsFragmentToProductDetailFragment(producto, title, isLocalPedido)

    public fun actionGlobalPedidosFragment(): NavDirections =
        NavGraphDirections.actionGlobalPedidosFragment()
  }
}
