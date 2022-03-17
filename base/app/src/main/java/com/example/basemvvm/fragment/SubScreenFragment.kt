package com.example.basemvvm.fragment

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.example.basemvvm.base.BaseFragment
import com.example.basemvvm.utils.L
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

//class SubScreenFragment (val mContext: Context) : BaseFragment<FragmentSubScreenBinding>() {
//
//    override val layoutId: Int
//        get() = R.layout.fragment_sub_screen
//
//    private lateinit var activity: MainActivity
//
//    private var imgAnimationTimerDisposable: Disposable? = null  // 이미지 전환 타이머
//
//    companion object {
//        fun newInstance(context: Context): SubScreenFragment {
//            val args = Bundle()
//            val fragment = SubScreenFragment(context)
//            fragment.arguments = args
//            return fragment
//        }
//
//        const val MY_HOME_REPORT = "my_home_report" // 마이 홈 리포트
//        const val MY_HOME_SUB_1 = "my_home_sub_1"   // 가전인사이트 리포트
//        const val MY_HOME_SUB_2 = "my_home_sub_2"   // 가전 에너지 모니터링
//        const val MY_HOME_SUB_3 = "my_home_sub_3"   // 스마트 진단
//        const val LAB = "lab"     // 생활 연구소
//        const val RENTAL = "rental"     // 렌탈
//    }
//
//    var homeImgList = mutableListOf<Drawable>()   // 마이 홈 이미지
//    var home_img_sub_1 = mutableListOf<Drawable>()  // 가전인사이트 리포트
//    var home_img_sub_2 = mutableListOf<Drawable>()  // 가전 에너지 모니터링
//    var home_img_sub_3 = mutableListOf<Drawable>()  // 스마트 진단
//    var lab_img = mutableListOf<Drawable>()         // 생활 연구소
//    var rental_img = mutableListOf<Drawable>()      // 렌탈
//
//    var cnt = 0
//    var listSize = 0
//    var currentList = mutableListOf<Drawable>()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        activity = mContext as MainActivity
//
////        System.gc()
//
//        binding.btnPrev.setOnClickListener {
//            activity.timerReset()
//            disposableClear()
//            imgAnimationTimerDisposable = null
//
//            cnt--
//            if (cnt < 0) {
//                cnt = (listSize - 1)
//            }
//
//            setImgAnim(currentList)
//
//        }
//
//        binding.btnNext.setOnClickListener {
//            activity.timerReset()
//            disposableClear()
//            imgAnimationTimerDisposable = null
//
//            cnt++
//            if (cnt > (listSize - 1)) {
//                cnt = 0
//            }
//
//            setImgAnim(currentList)
//
//        }
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        C.subScreenModeLiveData.observe(activity, {
//            initMode(it)
//        })
//
//    }
//
//    override fun onStop() {
//        super.onStop()
//
//        disposableClear()
//        imgAnimationTimerDisposable = null
//
//    }
//
//    fun initMode(mode: String = MY_HOME_REPORT) {
//        disposableClear()
//
//        homeImgList.clear()
//        home_img_sub_1.clear()
//        home_img_sub_2.clear()
//        home_img_sub_3.clear()
//        lab_img.clear()
//        rental_img.clear()
//
//        cnt = 0
//        when (mode) {
//            MY_HOME_REPORT -> {
//                mContext.getDrawable(R.drawable.img_my_home_0)?.let { homeImgList.add(it) }
//                mContext.getDrawable(R.drawable.img_my_home_1)?.let { homeImgList.add(it) }
//                mContext.getDrawable(R.drawable.img_my_home_2)?.let { homeImgList.add(it) }
//                mContext.getDrawable(R.drawable.img_my_home_3)?.let { homeImgList.add(it) }
//                mContext.getDrawable(R.drawable.img_my_home_4)?.let { homeImgList.add(it) }
//                mContext.getDrawable(R.drawable.img_my_home_5)?.let { homeImgList.add(it) }
//                currentList = homeImgList
//            }
//            MY_HOME_SUB_1 -> {
//                mContext.getDrawable(R.drawable.img_report_0)?.let { home_img_sub_1.add(it) }
//                mContext.getDrawable(R.drawable.img_report_1)?.let { home_img_sub_1.add(it) }
//                mContext.getDrawable(R.drawable.img_report_2)?.let { home_img_sub_1.add(it) }
//                currentList = home_img_sub_1
//            }
//            MY_HOME_SUB_2 -> {
//                mContext.getDrawable(R.drawable.img_monitoring_0)?.let { home_img_sub_2.add(it) }
//                mContext.getDrawable(R.drawable.img_monitoring_1)?.let { home_img_sub_2.add(it) }
//                mContext.getDrawable(R.drawable.img_monitoring_2)?.let { home_img_sub_2.add(it) }
//                currentList = home_img_sub_2
//            }
//            MY_HOME_SUB_3 -> {
//                mContext.getDrawable(R.drawable.img_smart_0)?.let { home_img_sub_3.add(it) }
//                mContext.getDrawable(R.drawable.img_smart_1)?.let { home_img_sub_3.add(it) }
//                mContext.getDrawable(R.drawable.img_smart_2)?.let { home_img_sub_3.add(it) }
//                mContext.getDrawable(R.drawable.img_smart_3)?.let { home_img_sub_3.add(it) }
//                mContext.getDrawable(R.drawable.img_smart_4)?.let { home_img_sub_3.add(it) }
//                currentList = home_img_sub_3
//            }
//            LAB -> {
//                mContext.getDrawable(R.drawable.img_lab_0)?.let { lab_img.add(it) }
//                mContext.getDrawable(R.drawable.img_lab_1)?.let { lab_img.add(it) }
//                mContext.getDrawable(R.drawable.img_lab_2)?.let { lab_img.add(it) }
//                mContext.getDrawable(R.drawable.img_lab_3)?.let { lab_img.add(it) }
//                mContext.getDrawable(R.drawable.img_lab_4)?.let { lab_img.add(it) }
//                mContext.getDrawable(R.drawable.img_lab_5)?.let { lab_img.add(it) }
//                currentList = lab_img
//            }
//            RENTAL -> {
//                mContext.getDrawable(R.drawable.img_rental_0)?.let { rental_img.add(it) }
//                mContext.getDrawable(R.drawable.img_rental_1)?.let { rental_img.add(it) }
//                mContext.getDrawable(R.drawable.img_rental_2)?.let { rental_img.add(it) }
//                mContext.getDrawable(R.drawable.img_rental_3)?.let { rental_img.add(it) }
//                mContext.getDrawable(R.drawable.img_rental_4)?.let { rental_img.add(it) }
//                currentList = rental_img
//            }
//            else -> {
//                mContext.getDrawable(R.drawable.img_my_home_0)?.let { homeImgList.add(it) }
//                mContext.getDrawable(R.drawable.img_my_home_1)?.let { homeImgList.add(it) }
//                mContext.getDrawable(R.drawable.img_my_home_2)?.let { homeImgList.add(it) }
//                mContext.getDrawable(R.drawable.img_my_home_3)?.let { homeImgList.add(it) }
//                mContext.getDrawable(R.drawable.img_my_home_4)?.let { homeImgList.add(it) }
//                mContext.getDrawable(R.drawable.img_my_home_5)?.let { homeImgList.add(it) }
//                currentList = homeImgList
//            }
//        }
//        binding.ivSubScreen.setImageDrawable(currentList[cnt])
//        setImgAnim(currentList)
//    }
//
//    fun setImgAnim(list: MutableList<Drawable>) {
//        listSize = list.size
//        binding.ivSubScreen.setImageDrawable(currentList[cnt])
//        imgAnimationTimerDisposable = Observable
//            .timer(2, TimeUnit.SECONDS)
//            .repeat()
//            .subscribeOn(Schedulers.computation())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnError {
//                L.d("$it")
//            }
//            .subscribe(
//                { item ->
//                    L.d("setImgAnim >> $item")
//                    cnt++
//                    if (cnt >= listSize) {
//                        cnt = 0
//                    }
//                    binding.ivSubScreen.setImageDrawable(currentList[cnt])
//                },
//                {error -> L.d( "setImgAnim >> ${error.message}")}
//            )
//
//        imgAnimationTimerDisposable?.let { addDisposable(it) }
//
//    }
//
//    override fun onBackBtnEvent() {
//        super.onBackBtnEvent()
//
//    }
//}