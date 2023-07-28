package com.mobven.extensions.tabrcsync

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.mobven.extensions.databinding.ActivityTabrcsyncBinding

class TabRcSyncActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabrcsyncBinding

    private var categoryTabs = ArrayList<TabModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabrcsyncBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTabLayoutInitialize()
    }

    private fun setTabLayoutInitialize() {

        getTemoData().forEachIndexed { _, items ->
            if (items.isCategory) {
                binding.tlCategory.addTab(binding.tlCategory.newTab().setText(items.name))
                categoryTabs.add(TabModel(items.categoryId, items.name))
                binding.tlCategory.tabGravity = TabLayout.GRAVITY_FILL
            }
        }

        val indexes = ArrayList<Int>()
        categoryTabs.forEach {
            var count = 0
            getTemoData().forEach { ct ->
                if (it.categoryId == ct.categoryId) {
                    count++
                }
            }
            indexes.add(count)
        }

        binding.rcList.apply {
            setTabLayout(binding.tlCategory)
            setSmoothScroll(true)
            setItemsByTabIndex(indexes)
            addOnScrollListener(RecyclerViewScrollListener())
            setLinearLayoutManager(isActive = true)
            layoutManager = LinearLayoutManager(
                binding.root.context,
                LinearLayoutManager.VERTICAL,false
            )
            adapter = SampleAdapter(getTemoData())
        }

    }

    private fun getTemoData(): ArrayList<ProductModel> {
        val catalogList = ArrayList<ProductModel>()
        catalogList.add(ProductModel(categoryId = "1", name = "Category 1", isCategory = true))
        catalogList.add(ProductModel(productId = "1", name = "Product 1", categoryId = "1"))
        catalogList.add(ProductModel(productId = "2", name = "Product 2", categoryId = "1"))
        catalogList.add(ProductModel(productId = "3", name = "Product 3", categoryId = "1"))
        catalogList.add(ProductModel(productId = "4", name = "Product 4", categoryId = "1"))
        catalogList.add(ProductModel(productId = "5", name = "Product 5", categoryId = "1"))
        catalogList.add(ProductModel(productId = "6", name = "Product 6", categoryId = "1"))
        catalogList.add(ProductModel(categoryId = "2", name = "Category 2", isCategory = true))
        catalogList.add(ProductModel(productId = "7", name = "Product 7", categoryId = "2"))
        catalogList.add(ProductModel(productId = "8", name = "Product 8", categoryId = "2"))
        catalogList.add(ProductModel(productId = "9", name = "Product 9", categoryId = "2"))
        catalogList.add(ProductModel(productId = "10", name = "Product 10", categoryId = "2"))
        catalogList.add(ProductModel(categoryId = "3", name = "Category 3", isCategory = true))
        catalogList.add(ProductModel(productId = "11", name = "Product 11", categoryId = "3"))
        catalogList.add(ProductModel(categoryId = "4", name = "Category 4", isCategory = true))
        catalogList.add(ProductModel(productId = "12", name = "Product 12", categoryId = "4"))
        catalogList.add(ProductModel(productId = "13", name = "Product 13", categoryId = "4"))
        catalogList.add(ProductModel(productId = "14", name = "Product 14", categoryId = "4"))
        catalogList.add(ProductModel(productId = "15", name = "Product 15", categoryId = "4"))
        catalogList.add(ProductModel(productId = "16", name = "Product 16", categoryId = "4"))
        catalogList.add(ProductModel(productId = "17", name = "Product 17", categoryId = "4"))
        catalogList.add(ProductModel(categoryId = "5", name = "Category 5", isCategory = true))
        catalogList.add(ProductModel(productId = "18", name = "Product 18", categoryId = "5"))
        catalogList.add(ProductModel(productId = "19", name = "Product 19", categoryId = "5"))
        catalogList.add(ProductModel(productId = "20", name = "Product 20", categoryId = "5"))
        return catalogList
    }
}