package com.app.customprofileviewapp

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.app.customprofileviewapp.databinding.ActivityDynamicLinearBinding


class DynamicLinearActivity : AppCompatActivity() {

    val binding: ActivityDynamicLinearBinding by lazy {
        ActivityDynamicLinearBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupDynamicViews()

    }

    private fun setupDynamicViews() {
        val parentList = resources.getStringArray(R.array.profile_info).toList()

        val firstListSize = minOf(2, parentList.size) // Ensure first list doesn't exceed parent size
        val firstList = parentList.subList(0, firstListSize)

        val secondListSize = minOf(3, parentList.size - firstListSize) // Consider remaining items
        val secondList = parentList.subList(firstListSize, firstListSize + secondListSize)

        val remainingList = parentList.subList(firstListSize + secondListSize, parentList.size)

        if (firstList.isNotEmpty()) {
            val layout1 = createDynamicLinearLayout(this, firstList)
            binding.llInfo.addView(layout1)
        }

        if (secondList.isNotEmpty()) {
            val layout2 = createDynamicLinearLayout(this, secondList)
            binding.llInfo.addView(layout2)
        }

        if (remainingList.isNotEmpty()) {
            val otherChunkedList = remainingList.chunked(3)//Change size according to your needs
            otherChunkedList.forEach {
                binding.llInfo.addView(createDynamicLinearLayout(this, it))
            }
        }
    }

    private fun createDynamicLinearLayout(context: Context, itemNames: List<String>): LinearLayout {
        val linearLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(15, 10, 15, 10)
            }
        }

        itemNames.forEachIndexed { index, itemName ->
            linearLayout.addView(
                createDynamicRowItem(
                    context,
                    itemName,
                    isFirstItem = index == 0,
                    isLastItem = index == itemNames.size - 1
                )
            )
            if (index != itemNames.size - 1) {
                linearLayout.addView(createDynamicDivider(context))
            }
        }

        return linearLayout
    }

    private fun createDynamicRowItem(
        context: Context,
        text: String,
        isFirstItem: Boolean,
        isLastItem: Boolean
    ): LinearLayout {
        val rowItemLayout = LayoutInflater.from(context).inflate(R.layout.row_dynamic, null) as LinearLayout
        rowItemLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            weight = 1.0f
        }

        rowItemLayout.setHorizontalGravity(
            when {
                isFirstItem -> Gravity.START
                isLastItem -> Gravity.END
                else -> Gravity.CENTER
            }
        )

        rowItemLayout.findViewById<TextView>(R.id.tvType).text = text

        return rowItemLayout
    }

    private fun createDynamicDivider(context: Context): View {
        val inflater = LayoutInflater.from(context)
        val rowItemLayout = inflater.inflate(R.layout.custom_divider, null) as LinearLayout
        return rowItemLayout
    }

}