package com.greentoad.turtlebody.mediapicker.ui.common

import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.greentoad.turtlebody.mediapicker.core.PickerConfig
import com.greentoad.turtlebody.mediapicker.ui.ActivityLibMain
import com.greentoad.turtlebody.mediapicker.ui.base.FragmentBase
import kotlinx.android.synthetic.main.tb_media_picker_file_fragment.*


/**
 * Created by niraj on 12-04-2019.
 */
abstract class MediaListFragment : FragmentBase() {
    companion object {

        const val B_ARG_PICKER_CONFIG = "media_list_fragment.args.pickerConfig"
    }

    var mPickerConfig: PickerConfig = PickerConfig()
    var mFolderId: String = ""
    var mUriList: ArrayList<Uri> = arrayListOf()

    abstract fun onRestoreState(savedInstanceState: Bundle?, args: Bundle?)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        arguments?.let {
            mPickerConfig = it.getSerializable(B_ARG_PICKER_CONFIG) as PickerConfig
        }
        onRestoreState(savedInstanceState, arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.greentoad.turtlebody.mediapicker.R.layout.tb_media_picker_file_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()


        /*******************************************************
         * Dynamically change marginBottom for recyclerView
         *******************************************************/
        if(mPickerConfig.mAllowMultiImages){
            val params = CoordinatorLayout.LayoutParams(
                    CoordinatorLayout.LayoutParams.MATCH_PARENT,
                    CoordinatorLayout.LayoutParams.MATCH_PARENT)

            val r = context!!.resources
            val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56f, r.displayMetrics).toInt()

            params.setMargins(0, 0, 0, px)
            tb_media_picker_file_fragment_recycler_view.layoutParams = params
        }
    }

    private fun initButton() {
        iv_cancel.setOnClickListener {
            (activity as ActivityLibMain).onBackPressed()
        }
        btn_add_file.setOnClickListener {
            getAllUris()
        }

        if (!mPickerConfig.mAllowMultiImages) {
            ll_bottom_layout.visibility = View.GONE
        }
    }

    abstract fun getAllUris()
}