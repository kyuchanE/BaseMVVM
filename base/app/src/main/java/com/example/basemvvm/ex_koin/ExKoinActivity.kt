package com.example.basemvvm.ex_koin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import com.example.basemvvm.R
import com.example.basemvvm.base.BaseActivity
import com.example.basemvvm.base.BaseViewModel
import com.example.basemvvm.databinding.ActivityExKoinBinding
import com.example.basemvvm.ex_koin.MWT.MwtMain
import com.example.basemvvm.model.BasicApi
import com.example.basemvvm.utils.L
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.experimental.property.inject
import org.koin.ext.getOrCreateScope
import org.koin.ext.scope

class ExKoinActivity : BaseActivity<ActivityExKoinBinding>() {

    // dataBinding
    override val layoutId: Int = R.layout.activity_ex_koin

    // inject() 의존성 주입 - Lazy 방식
    val bb_inject1 : BB by inject()	// inject Type 유형 1 - Type by inject()
    val bb_inject2 by inject<BB>()	// inject Type 유형 2 - by inject<Type>()

    // get() 의존성 주입 - 바로 주입 방식
    var bb_get1 : BB = get()		// get Type 유형 1 - Type = get()
    var bb_get2 = get<BB>()		// get Type 유형 2 - get<Type>()

    val scopeA : A = get()
    val scopeForA = scopeA.getOrCreateScope()
    val scopeB : B = scopeForA.get()
    val scopeC : C = scopeForA.get()
    //    val scopeB : B = scopeA.scope.get()
    //    val scopeC : C = scopeA.scope.get()

    val retrofitClient : BasicApi by inject()
//    val viewModel : ExKoinVM = get()

    // Fragment -> 2개 이상의 View가 ViewModel을 공유할 경우, shareViewModel을 사용하면 됩니다.각각 by viewModel() 대신, by sharedViewModel()로 바꾸어 주입해줍니다.
    // by viewModel이 아닌 by sharedViewModel을 사용함으로써 해당 뷰모델의 생성은 두번 되지 않고 Activity에서 생성한 뷰모델을 공유해서 사용한다.
    //  val viewModel by sharedViewModel<MainViewModel>()

    val viewModel : ExKoinVM by viewModel()     // by viewModel()  키워드를 이용하면 non-lazy 하게 주입 받을 수 있습니다.

    // single, factory 의 의존성 주입 메서드로 by inject() / get() 사용방법 입니다.
    //
    // inject 와 get 방식 둘 다 Type 선언 방식은 2가지로 구분
    //
    //    1) var 변수명: Type = get() 형식 - Koin 이 해당 변수의 Type 을 보고 알맞은 의존성을 주입
    //
    //    2) var 변수명 = get<Type>() 형식 - Koin 이 get()함수에 선언된 Type 에 맞는 의존성을 주입
    //
    // Inject 와 get 방식의 차이
    //
    //    inject - Lazy 방식의 주입, 해당 객체가 사용되는 시점에 의존성 주입
    //
    //    get - 바로 주입, 해당 코드 실행시간에 바로 객체를 주입

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setRetrofit(retrofitClient)

        L.d("bb_inject1 name is ${bb_inject1.name()}")
        L.d("bb_inject2 name is ${bb_inject2.name()}")

        L.d("bb_get1 name is ${bb_get1.name()}")
        L.d("bb_get2 name is ${bb_get2.name()}")

        L.d("scopeB name is ${scopeB.name()}, scope close ? : ${scopeForA.closed}")
        L.d("scopeC name is ${scopeC.name()}")

        scopeForA.close()

        L.d("after close() scopeB name is ${scopeB.name()}, scope close ? : ${scopeForA.closed}")
        L.d("after close() scopeC name is ${scopeC.name()}")

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
                startActivity(Intent(this, MwtMain::class.java))
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