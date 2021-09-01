// Generated by view binder compiler. Do not edit!
package com.example.restaurantws.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.restaurantws.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemProductsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imgAddToCart;

  @NonNull
  public final ImageView imgProduct;

  @NonNull
  public final ImageView imgRemoveFromCart;

  @NonNull
  public final TextView productDesc;

  @NonNull
  public final TextView productName;

  @NonNull
  public final TextView productPrice;

  @NonNull
  public final TextView productQuantity;

  private ItemProductsBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView imgAddToCart,
      @NonNull ImageView imgProduct, @NonNull ImageView imgRemoveFromCart,
      @NonNull TextView productDesc, @NonNull TextView productName, @NonNull TextView productPrice,
      @NonNull TextView productQuantity) {
    this.rootView = rootView;
    this.imgAddToCart = imgAddToCart;
    this.imgProduct = imgProduct;
    this.imgRemoveFromCart = imgRemoveFromCart;
    this.productDesc = productDesc;
    this.productName = productName;
    this.productPrice = productPrice;
    this.productQuantity = productQuantity;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemProductsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemProductsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_products, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemProductsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imgAddToCart;
      ImageView imgAddToCart = ViewBindings.findChildViewById(rootView, id);
      if (imgAddToCart == null) {
        break missingId;
      }

      id = R.id.imgProduct;
      ImageView imgProduct = ViewBindings.findChildViewById(rootView, id);
      if (imgProduct == null) {
        break missingId;
      }

      id = R.id.imgRemoveFromCart;
      ImageView imgRemoveFromCart = ViewBindings.findChildViewById(rootView, id);
      if (imgRemoveFromCart == null) {
        break missingId;
      }

      id = R.id.productDesc;
      TextView productDesc = ViewBindings.findChildViewById(rootView, id);
      if (productDesc == null) {
        break missingId;
      }

      id = R.id.productName;
      TextView productName = ViewBindings.findChildViewById(rootView, id);
      if (productName == null) {
        break missingId;
      }

      id = R.id.productPrice;
      TextView productPrice = ViewBindings.findChildViewById(rootView, id);
      if (productPrice == null) {
        break missingId;
      }

      id = R.id.productQuantity;
      TextView productQuantity = ViewBindings.findChildViewById(rootView, id);
      if (productQuantity == null) {
        break missingId;
      }

      return new ItemProductsBinding((ConstraintLayout) rootView, imgAddToCart, imgProduct,
          imgRemoveFromCart, productDesc, productName, productPrice, productQuantity);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
