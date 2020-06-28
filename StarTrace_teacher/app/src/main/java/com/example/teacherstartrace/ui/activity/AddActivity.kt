package com.example.teacherstartrace.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import android.widget.TimePicker.OnTimeChangedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.teacherstartrace.base.BaseActivity
import java.util.*
import com.example.teacherstartrace.R
import com.example.teacherstartrace.util.ToolBarManager
import org.jetbrains.anko.find

class AddActivity() : BaseActivity(), View.OnClickListener,
    DatePicker.OnDateChangedListener, OnTimeChangedListener, ToolBarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    override fun initData() {
        initSettingToolBar("增加课程")
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp.getBoolean("push",false)

    }
    private var context: Context? = null
    private var llDate: LinearLayout? = null
    private var llTime: LinearLayout? = null
    private var tvDate: TextView? = null
    private var tvTime: TextView? = null
    private var year = 0
    private var month = 0
    private var day = 0
    private var hour = 0
    private var minute = 0

    //在TextView上显示的字符
    private var date: StringBuffer? = null
    private var time: StringBuffer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_addcourse)
        context = this
        date = StringBuffer()
        time = StringBuffer()
        initView()
        initDateTime()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_addcourse
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        llDate = findViewById<View>(R.id.ll_date) as LinearLayout
        tvDate = findViewById<View>(R.id.tv_date) as TextView
        llTime = findViewById<View>(R.id.ll_time) as LinearLayout
        tvTime = findViewById<View>(R.id.tv_time) as TextView
        llDate!!.setOnClickListener(this)
        llTime!!.setOnClickListener(this)
    }

    /**
     * 获取当前的日期和时间
     */
    private fun initDateTime() {
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
        day = calendar[Calendar.DAY_OF_MONTH]
        hour = calendar[Calendar.HOUR]
        minute = calendar[Calendar.MINUTE]
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_date -> {
                val builder =
                    AlertDialog.Builder(context)
                builder.setPositiveButton(
                    "设置"
                ) { dialog, which ->
                    if (date!!.length > 0) { //清除上次记录的日期
                        date!!.delete(0, date!!.length)
                    }
                    tvDate!!.text =
                        date!!.append(year.toString()).append("年").append(month.toString())
                            .append("月").append(day).append("日")
                    dialog.dismiss()
                }
                builder.setNegativeButton(
                    "取消"
                ) { dialog, which -> dialog.dismiss() }
                val dialog = builder.create()
                val dialogView =
                    View.inflate(context, R.layout.dialog_date, null)
                val datePicker =
                    dialogView.findViewById<View>(R.id.datePicker) as DatePicker
                dialog.setTitle("设置日期")
                dialog.setView(dialogView)
                dialog.show()
                //初始化日期监听事件
                datePicker.init(year, month - 1, day, this)
            }
            R.id.ll_time -> {
                val builder2 =
                    AlertDialog.Builder(context)
                builder2.setPositiveButton(
                    "设置"
                ) { dialog, which ->
                    if (time!!.length > 0) { //清除上次记录的日期
                        time!!.delete(0, time!!.length)
                    }
                    tvTime!!.text =
                        time!!.append(hour.toString()).append("时").append(minute.toString())
                            .append("分")
                    dialog.dismiss()
                }
                builder2.setNegativeButton(
                    "取消"
                ) { dialog, which -> dialog.dismiss() }
                val dialog2 = builder2.create()
                val dialogView2 =
                    View.inflate(context, R.layout.dialog_time, null)
                val timePicker =
                    dialogView2.findViewById<View>(R.id.timePicker) as TimePicker
                timePicker.currentHour = hour
                timePicker.currentMinute = minute
                timePicker.setIs24HourView(true) //设置24小时制
                timePicker.setOnTimeChangedListener(this)
                dialog2.setTitle("设置时间")
                dialog2.setView(dialogView2)
                dialog2.show()
            }
        }
    }

    /**
     * 日期改变的监听事件
     *
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    override fun onDateChanged(
        view: DatePicker,
        year: Int,
        monthOfYear: Int,
        dayOfMonth: Int
    ) {
        this.year = year
        month = monthOfYear
        day = dayOfMonth
    }

    /**
     * 时间改变的监听事件
     *
     * @param view
     * @param hourOfDay
     * @param minute
     */
    override fun onTimeChanged(view: TimePicker, hourOfDay: Int, minute: Int) {
        hour = hourOfDay
        this.minute = minute
    }
}