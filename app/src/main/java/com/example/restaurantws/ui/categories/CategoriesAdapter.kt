package com.example.restaurantws.ui.categories

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantws.data.main.models.categorias.Category
import com.example.restaurantws.databinding.ItemCategoriesBinding
import com.example.restaurantws.utils.load

class CategoriesAdapter(
    private var items: List<Category>,
    private val onClickItem: (Category) -> Unit
) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) = with(binding)
        {
            categoryName.text = category.nombre
            categoryDesc.text = category.descripcion

            imgCategory.load(
                category.url_imagen
            )

            root.setOnClickListener {
                onClickItem(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<Category>) {
        items = newItems
        notifyDataSetChanged()
    }
}