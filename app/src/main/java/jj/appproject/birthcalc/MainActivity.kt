package jj.appproject.birthcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInHours : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInHours = findViewById(R.id.tvAgeInHours)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker(){


        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            { _, selectedyear, selectedmonth, selecteddayOfMonth ->                    // 중괄호 앞에 DatePickerDialog.OnDateSetListener 생략
                Toast.makeText(this,
                    "${selectedyear}년 ${selectedmonth+1}월 ${selecteddayOfMonth}일 ",
                    Toast.LENGTH_LONG).show()  // Toast는 짧은 팝업 메시지

                val selectedDate = "$selectedyear/${selectedmonth+1}/$selecteddayOfMonth"

                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("yyyy/mm/dd", Locale.KOREAN)



                val theDate = sdf.parse(selectedDate)
                theDate?.let{
                    val selectedDateInHours = theDate.time / (60000*60)

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))  // theDate나 currentDate 하이라이트 표시 -> 유형 안정성 부재 -> ?.let{} 사용 ->  null값 발생 방지
                    currentDate?.let{
                        val currentDateInHours = currentDate.time / (60000*60)

                        val differenceInHours = (currentDateInHours - selectedDateInHours)

                        tvAgeInHours?.text = differenceInHours.toString()
                    }

                }

            },
            year,
            month,
            day
            )

        dpd.datePicker.maxDate = System.currentTimeMillis() - (3600000*24)
        dpd.show()

    }
}