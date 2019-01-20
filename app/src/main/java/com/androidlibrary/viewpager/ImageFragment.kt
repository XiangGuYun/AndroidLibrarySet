package com.androidlibrary.viewpager

import com.kotlinlib.other.LayoutId
import com.kotlinlib.view.KotlinFragment
import android.os.Bundle
import com.androidlibrary.R
import kotlinx.android.synthetic.main.fragment_image.*


@LayoutId(R.layout.fragment_image)
class ImageFragment:KotlinFragment(){

    override fun init() {
        ivPic.sIR(arguments?.getInt("IMG")!!)
    }

    companion object {
        fun newInstance(param1: Int): ImageFragment {
            val fragment = ImageFragment()
            val args = Bundle()
            args.putInt("IMG", param1)
            fragment.arguments = args
            return fragment
        }
    }


}
