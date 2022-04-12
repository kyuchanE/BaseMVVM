package com.example.basemvvm.ex_koin.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.basemvvm.R
import com.example.basemvvm.base.BaseActivity
import com.example.basemvvm.databinding.ActivityExLottoBinding
import com.example.basemvvm.ex_koin.MWT.MwtMain
import com.example.basemvvm.ex_koin.koin.ExKoinVM
import com.example.basemvvm.utils.L
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExLottoActivity : BaseActivity<ActivityExLottoBinding>() {

    val viewModel : ExKoinVM by viewModel()     // by viewModel()  키워드를 이용하면 non-lazy 하게 주입 받을 수 있습니다.

    override val layoutId: Int
        get() = R.layout.activity_ex_lotto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    override fun initViews() {
        super.initViews()

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
                finish()
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
            L.d("2@@@@@@@@@ ExLottoActivity lottoLiveData observe")
            binding.drwtno1 = it.get("drwtNo1").toString()
        })

        viewModel.testLiveData.observe(this, Observer {
            L.d("2@@@@@@@@@ ExLottoActivity testLiveData observe")
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