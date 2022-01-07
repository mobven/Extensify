package com.mobven.extensions.recyclerview.concatadapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import com.mobven.extensions.MenuAdapter
import com.mobven.extensions.databinding.ActivityConcatExampleBinding

class ConcatExampleActivity: AppCompatActivity() {

    private lateinit var binding: ActivityConcatExampleBinding

    private val horizontalFeedAdapter = HorizontalFeedAdapter()
    private val horizontalFeedAdapter2 = HorizontalFeedAdapter()
    private val menuAdapter = MenuAdapter(listOf("dssad","dsasda","dssad","dsasda","dssad","dsasda","dssad","dsasda"))

    private val concatAdapter = ConcatAdapter(
        horizontalFeedAdapter,
        menuAdapter,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcatExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        horizontalFeedAdapter2.setData(getDummyData())
        horizontalFeedAdapter.setData(getDummyData2())
        concatAdapter.addAdapter(0,horizontalFeedAdapter2)
        binding.rvActivity.adapter = concatAdapter
    }

    private fun getDummyData() = mutableListOf(
        "sil",
        "DATA2",
        "DATA23",
        "DATA34",
        "DATA35",
        "DATA31",
        "DATA10",
        "DSADSA",
        "WWW",
        "dsadsFFadsa",
        "UUUU",
        "TTTT",
        "TTRTRRTRTRTTR"
    )

    private fun getDummyData2() = mutableListOf(
        "sil",
        "dsa",
        "asd",
        "dsa",
        "DATA35",
    )

}
