package com.example.basemvvm.ex_koin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm.R
import com.example.basemvvm.base.BaseActivity
import com.example.basemvvm.databinding.ActivityExLottoBinding
import com.example.basemvvm.utils.L

class ExLottoActivity : BaseActivity<ActivityExLottoBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_ex_lotto

    lateinit var viewModel: ExKoinVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ExKoinVM::class.java)
        binding.vm = viewModel

        setDataObserve()

        initViews()
    }

    override fun initViews() {
        super.initViews()

        binding.etDrwno.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                binding.searchStr = s.toString()
            }

        })
    }

    /**
     *  ViewModel LiveData
     */
    private fun setDataObserve() {

        viewModel.lottoLiveData.observe(this, Observer {
            L.d("@@@@@@ ExLottoActivity lottoLiveData observe")
            binding.drwtno1 = it.get("drwtNo1").toString()
        })

        viewModel.testLiveData.observe(this, Observer {
            L.d("@@@@@@ ExLottoActivity lottoLiveData observe")
            binding.num = it.toString()
        })
    }
}