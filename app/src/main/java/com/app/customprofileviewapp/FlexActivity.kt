package com.app.customprofileviewapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnNextLayout
import com.app.customprofileviewapp.databinding.ActivityFlexBinding
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxItemDecoration
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class FlexActivity : AppCompatActivity() {

    val binding: ActivityFlexBinding by lazy { ActivityFlexBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val stringArray = resources.getStringArray(R.array.profile_info)
        // convert array to arraylist
        val profileInfoList: ArrayList<String> = stringArray.toCollection(ArrayList())


        //FlexboxLayoutManager
        val layoutManager = FlexboxLayoutManager(this).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.SPACE_BETWEEN
            alignItems = AlignItems.BASELINE
        }
        val decor = FlexboxItemDecoration(this)
        decor.setDrawable(getDrawable(R.drawable.divider))
        decor.setOrientation(FlexboxItemDecoration.VERTICAL)
        binding.rvInfo.addItemDecoration(decor)
        binding.rvInfo.layoutManager = layoutManager

        binding.rvInfo.adapter = ProfileInfoAdapter(this, profileInfoList)

        binding.rvInfo.doOnNextLayout {
            try {
                // Accessing the internal mFlexLines field using reflection
                val flexLinesField = FlexboxLayoutManager::class.java.getDeclaredField("mFlexLines")
                flexLinesField.isAccessible = true

                val layoutManager = binding.rvInfo.layoutManager as? FlexboxLayoutManager
                layoutManager?.let {
                    val flexLines = flexLinesField.get(it)
                    for (flexLine in flexLines as List<*>) {
                        val itemCountField = flexLine?.javaClass?.getDeclaredField("mItemCount")
                        itemCountField?.isAccessible = true
                        Log.d(
                            "Flexbox",
                            "Item count in this flex line: ${itemCountField?.get(flexLine)}"
                        )
                    }
                }
            } catch (e: NoSuchFieldException) {
                Log.e("Flexbox", "Error accessing field", e)
            } catch (e: IllegalAccessException) {
                Log.e("Flexbox", "Error accessing field", e)
            }
        }

    }
}