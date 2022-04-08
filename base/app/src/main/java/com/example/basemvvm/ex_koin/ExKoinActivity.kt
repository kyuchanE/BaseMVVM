package com.example.basemvvm.ex_koin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm.R
import com.example.basemvvm.base.BaseActivity
import com.example.basemvvm.base.BaseViewModel
import com.example.basemvvm.databinding.ActivityExKoinBinding
import com.example.basemvvm.model.BasicApi
import com.example.basemvvm.utils.L

class ExKoinActivity : BaseActivity<ActivityExKoinBinding>() {

    // dataBinding
    override val layoutId: Int = R.layout.activity_ex_koin
    lateinit var viewModel: ExKoinVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ExKoinVM::class.java)

        setDataObserve()

    }

    override fun onBtnEvents(v: View) {
        super.onBtnEvents(v)
        when (v.id) {
            // Retrofit2 테스트
            R.id.btn_retrofit -> {
                L.d("ExKoinActivity btn_retrofit OnClick >> ")
                val drwno = binding.etDrwno.text.toString()
                if (drwno.isNotEmpty()) {
                    viewModel.testApi(drwno)
                }
            }
            // MWT
            R.id.btn_mwt -> {

            }
        }
    }

    /**
     *  ViewModel LiveData
     */
    private fun setDataObserve() {

        /**
         *  ViewModel LiveData Observe
         *  사용 시 - kotlin 1.3.x 버전까지는 Observer을 명시해야 한다.
         */
        viewModel.lottoLiveData.observe(this, Observer {
            binding.drwtno1 = it.get("drwtNo1").toString()
        })

        viewModel.testLiveData.observe(this, Observer {
            binding.num = it.toString()
        })

        /**
         *  ViewModel LiveData Observe
         *  kotlin 1.4.x 버전부터는 SAM 지원으로 아래와 같다.
         */
//        viewModel.lottoLiveData.observe(this) {
//
//        }
    }

}