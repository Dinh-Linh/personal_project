package com.example.expense_management.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.expense_management.R
import com.example.expense_management.data.Expense
import com.example.expense_management.data.Revenue
import com.example.expense_management.databinding.ActivityMonthlyReportBinding
import com.example.expense_management.func.ConvertToTimestamp
import com.example.expense_management.func.DateUtils
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MonthlyReportActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMonthlyReportBinding.inflate(layoutInflater) }
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Set date
        var startDate = binding.startDate.text.toString()
        var endDate = binding.endDate.text.toString()
        val dateUtils = DateUtils(this@MonthlyReportActivity)
        binding.startDate.text = dateUtils.getCurrentDate()
        binding.startDate.setOnClickListener {
            dateUtils.showDatePickerDialog { selectedDate ->
                startDate = selectedDate
                binding.startDate.text = startDate
            }
        }
        binding.endDate.text = dateUtils.getCurrentDate()
        binding.endDate.setOnClickListener {
            dateUtils.showDatePickerDialog { selectedDate ->
                endDate = selectedDate
                binding.endDate.text = endDate
            }
        }

        db.collection("total_revenue").whereEqualTo("title", "Tiền lương").get()
            .addOnSuccessListener { documents ->
                val list = mutableListOf<Revenue>()
                var totalPrice = 0.0
                for (document in documents) {
                    val revenue = document.toObject<Revenue>()
                    list.add(revenue)
                    totalPrice += revenue.price!!
                }
            }
        binding.btnConfirm.setOnClickListener {
            val startDateTimestamp = ConvertToTimestamp().convertToTimestamp(startDate)
            val endDateTimestamp = ConvertToTimestamp().convertToTimestamp(endDate)

            if (startDateTimestamp != null && endDateTimestamp != null) {
                totalPriceRevenue(
                    "Tiền lương",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienLuong.text = "$totalPrice VND"
                }

                totalPriceRevenue(
                    "Tiền thưởng",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienThuong.text = "$totalPrice VND"
                }

                totalPriceRevenue(
                    "Tiền phụ cấp",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienPhuCap.text = "$totalPrice VND"
                }
                totalPriceRevenue(
                    "Thu nhập phụ",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienPhu.text = "$totalPrice VND"
                }
                totalPriceRevenue(
                    "Đầu tư",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienDauTu.text = "$totalPrice VND"
                }
                totalPriceRevenue(
                    "Thu nhập tạm",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienTam.text = "$totalPrice VND"
                }

                totalPriceExpense(
                    "Ăn uống",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienAn.text = "$totalPrice VND"
                }

                totalPriceExpense(
                    "Chi tiêu hàng",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienTieuVat.text = "$totalPrice VND"
                }
                totalPriceExpense(
                    "Quần áo",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienQuanAo.text = "$totalPrice VND"
                }
                totalPriceExpense(
                    "Phí giao lưu",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienGiaoLuu.text = "$totalPrice VND"
                }
                totalPriceExpense(
                    "Sức khoẻ",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienSucKhoe.text = "$totalPrice VND"
                }
                totalPriceExpense(
                    "Học tập",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienHocTap.text = "$totalPrice VND"
                }
                totalPriceExpense(
                    "Đi lại",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienDiLai.text = "$totalPrice VND"
                }
                totalPriceExpense(
                    "Liên lạc",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienLienLac.text = "$totalPrice VND"
                }
                totalPriceExpense(
                    "Nhà cửa",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienNhaCua.text = "$totalPrice VND"
                }
                var totalRevenue = 0.0
                var totalExpense = 0.0

                val revenueTask = db.collection("total_revenue")
                    .whereGreaterThan("date", startDateTimestamp)
                    .whereLessThan("date", endDateTimestamp).get()

                val expenseTask = db.collection("total_expense")
                    .whereGreaterThan("date", startDateTimestamp)
                    .whereLessThan("date", endDateTimestamp).get()
                //Chờ kết quả 2 truy vấn hoàn thành rồi mới tiếp tục thực hiện code
                Tasks.whenAllComplete(revenueTask, expenseTask).addOnSuccessListener {
                    val revenueDocuments = revenueTask.result
                    for (document in revenueDocuments) {
                        val revenue = document.toObject<Revenue>()
                        totalRevenue += revenue.price!!
                    }
                    binding.totalRevenue.text = totalRevenue.toString()
                    val expenseDocuments = expenseTask.result
                    for (document in expenseDocuments) {
                        val expense = document.toObject<Expense>()
                        totalExpense += expense.price!!
                    }
                    binding.totalExpense.text = totalExpense.toString()

                    val total = totalRevenue - totalExpense
                    binding.total.text = total.toString()
                }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
                    }
            }
        }

        binding.tienLuong.setOnClickListener {
            db.collection("total_revenue").whereEqualTo("title", "Tiền lương").get()
                .addOnSuccessListener { documents ->
                    val list = mutableListOf<Revenue>()
                    for (document in documents) {
                        val revenue = document.toObject<Revenue>()
                        list.add(revenue)
                    }
                }
        }
        binding.tienAn.setOnClickListener {
            onCategoryClick("Ăn uống", startDate, endDate)
            startActivity(Intent(this, CatalogDetailsActivity::class.java))
        }

        binding.tienQuanAo.setOnClickListener {
            onCategoryClick("Quần áo", startDate, endDate)
        }
    }

    //Total money func
    fun totalPriceRevenue(
        vlue: String,
        startDate: Timestamp,
        endDate: Timestamp,
        callback: (Double) -> Unit
    ) {
        var totalPrice = 0.0
        db.collection("total_revenue").whereEqualTo("title", vlue)
            .whereGreaterThan("date", startDate).whereLessThan("date", endDate).get()
            .addOnSuccessListener { documents ->
                val list = mutableListOf<Revenue>()
                for (document in documents) {
                    val revenue = document.toObject<Revenue>()
                    list.add(revenue)
                    totalPrice += revenue.price!!
                }
                callback(totalPrice)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            }
    }

    fun totalPriceExpense(
        vlue: String,
        startDate: Timestamp,
        endDate: Timestamp,
        callback: (Double) -> Unit
    ) {
        var totalPrice = 0.0
        db.collection("total_expense").whereEqualTo("title", vlue)
            .whereGreaterThan("date", startDate).whereLessThan("date", endDate).get()
            .addOnSuccessListener { documents ->
                val list = mutableListOf<Expense>()
                for (document in documents) {
                    val expense = document.toObject<Expense>()
                    list.add(expense)
                    totalPrice += expense.price!!
                }
                callback(totalPrice)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            }
    }

    // Thay thế "Tiền ăn" bằng tên của mục được chọn
    fun onCategoryClick(categoryName: String, startDate: String, endDate: String) {
        val startDateTimestamp = ConvertToTimestamp().convertToTimestamp(startDate)
        val endDateTimestamp = ConvertToTimestamp().convertToTimestamp(endDate)

        if (startDateTimestamp != null && endDateTimestamp != null) {
            val intent = Intent(this, CatalogDetailsActivity::class.java)
            intent.putExtra("category_name", categoryName)
            intent.putExtra("start_date", startDateTimestamp)
            intent.putExtra("end_date", endDateTimestamp)
            startActivity(intent)
        }

    }

    fun fetchData(
        collection: String,
        category: String,
        startDate: Timestamp,
        endDate: Timestamp,
        callback: (List<Expense>) -> Unit
    ) {
        db.collection(collection)
            .whereEqualTo("title", category)
            .whereGreaterThan("date", startDate)
            .whereLessThan("date", endDate)
            .get()
            .addOnSuccessListener { documents ->
                val list = mutableListOf<Expense>()
                for (document in documents) {
                    val expense = document.toObject<Expense>()
                    list.add(expense)
                }
                callback(list)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            }
    }

}


