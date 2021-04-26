package com.example.basemvvm.custom.view

import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.example.basemvvm.R
import com.example.basemvvm.base.BaseActivity
import com.example.basemvvm.base.BaseDialog
import com.example.basemvvm.databinding.DefaultDialogBinding
import com.example.basemvvm.utils.show
import com.example.basemvvm.utils.value

/*------------------------------------------------------------------------------
 * DESC    : 기본 다이얼로그
 *------------------------------------------------------------------------------*/

class DefaultDialog(activity: BaseActivity<*>): BaseDialog<DefaultDialogBinding>(activity) {

    override val layoutId: Int = R.layout.default_dialog

    var message: String
        get() = binding.tvMessage.value
        set(value) {
            binding.tvMessage.text = value
        }

    fun message(@StringRes res: Int): DefaultDialog {
        binding.tvMessage.setText(res)
        return this
    }

    fun message(contents: String): DefaultDialog {
        binding.tvMessage.text = contents
        return this
    }

    fun right(): DefaultDialog {
        showDialog()
        with(binding) {
            bRight.setOnClickListener { hideDialog() }
            bRight.show()
            if (bLeft.isVisible) vHline.show()
        }
        return this
    }


    fun right(@StringRes res: Int, event: () -> Unit = {}): DefaultDialog {
        showDialog()
        with(binding) {
            bRight.setText(res)
            bRight.setOnClickListener {
                event()
                hideDialog()
            }
            bRight.show()
            if (bLeft.isVisible) vHline.show()
        }
        return this
    }

    fun right(str: String = "확인", event: () -> Unit = {}): DefaultDialog {
        showDialog()
        with(binding) {
            bRight.text = str
            bRight.setOnClickListener {
                event()
                hideDialog()
            }
            bRight.show()
            if (bLeft.isVisible) vHline.show()
        }
        return this
    }

    fun left(): DefaultDialog {
        with(binding) {
            bLeft.setOnClickListener { hideDialog() }
            bLeft.show()
            if (bRight.isVisible) vHline.show()
        }
        return this
    }

    fun left(@StringRes res: Int, event: () -> Unit = {}): DefaultDialog {
        with(binding) {
            bLeft.setText(res)
            bLeft.setOnClickListener {
                event()
                hideDialog()
            }
            bLeft.show()
            if (bRight.isVisible) vHline.show()
        }
        return this
    }

    fun left(str: String = "취소", event: () -> Unit = {}): DefaultDialog {
        with(binding) {
            bLeft.text = str
            bLeft.setOnClickListener {
                event()
                hideDialog()
            }
            bLeft.show()
            if (bRight.isVisible) vHline.show()
        }
        return this
    }

}