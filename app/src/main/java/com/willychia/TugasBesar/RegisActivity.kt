package com.willychia.TugasBesar

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.willychia.TugasBesar.api.PengunjungApi
import com.willychia.TugasBesar.databinding.ActivityRegisBinding
import com.willychia.TugasBesar.entity.Film
import com.willychia.TugasBesar.entity.Pengunjung
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*

class RegisActivity : AppCompatActivity() {
    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }
    private lateinit var binding: ActivityRegisBinding
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notificationId1 = 101
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        queue = Volley.newRequestQueue(this)
        val cal = Calendar.getInstance()
        val tahun = cal.get(Calendar.YEAR)
        val bulan = cal.get(Calendar.MONTH)
        val hari = cal.get(Calendar.DAY_OF_MONTH)


        binding.btnTgl.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { datePicker, tahun, bulan, hari ->
                binding.btnTgl.text="" + tahun +"/" + (bulan.toInt() + 1).toString() + "/" + hari },tahun,bulan,hari)
            datePickerDialog.show()
        }

        createNotificationChannel()

        binding.btnReg.setOnClickListener (View.OnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            val mBundle = Bundle()
            var checkReg: Boolean = false

            if(binding.textInputLayoutEmail.getEditText()?.text.toString().isEmpty() || binding.textInputLayoutPassword.getEditText()?.text.toString().isEmpty() || binding.textInputLayoutNama.getEditText()?.text.toString().isEmpty() || binding.textInputLayoutnoTelp.getEditText()?.text.toString().isEmpty()){
                checkReg = false
            }else{
                checkReg = true
            }

            if(!checkReg){
                Snackbar.make(binding.regisActivity, "Data masih ada yang kosong", Snackbar.LENGTH_LONG).show()
                return@OnClickListener
            }else{
                intent.putExtra("email", binding.textInputLayoutEmail.getEditText()?.text.toString())
                intent.putExtra("password", binding.textInputLayoutPassword.getEditText()?.text.toString())

//                db.pengunjungDAO().addNotePengunjung(
//                    NotePengunjung(0, binding.textInputLayoutNama.getEditText()?.text.toString(), binding.btnTgl.text.toString(), binding.textInputLayoutEmail.getEditText()?.text.toString()
//                        , binding.textInputLayoutPassword.getEditText()?.text.toString(), binding.textInputLayoutnoTelp.getEditText()?.text.toString())
//                )
                createPengunjung()
                sendNotification1()
                startActivity(intent)
            }


        })
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun sendNotification1(){
        val myBitmap = BitmapFactory.decodeResource(resources, R.drawable.gambar_bioskop)
        val intent : Intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent,0)

        val broadcastIntent : Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", "Berhasil Registrasi")
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_user_24)
            .setContentTitle("Register")
            .setContentText("Berhasil Registrasi")
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(myBitmap)
                .bigLargeIcon(myBitmap))

            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId1, builder.build())
        }
    }

    private fun createPengunjung(){
        val pengunjung = Pengunjung(
            binding.textInputLayoutNama.getEditText()?.text.toString(),
            binding.textInputLayoutEmail.getEditText()?.text.toString(),
            binding.textInputLayoutnoTelp.getEditText()?.text.toString(),
            binding.textInputLayoutPassword.getEditText()?.text.toString(),
            binding.btnTgl.text.toString()
        )
        val stringRequest: StringRequest =
            object: StringRequest(Method.POST, PengunjungApi.ADD_URL, Response.Listener { response ->
                val gson = Gson()
                var pengunjung = gson.fromJson(response, Pengunjung::class.java)

                if(pengunjung != null)
                    Toast.makeText(this@RegisActivity, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()


                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()
            }, Response.ErrorListener { error ->
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(this@RegisActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(pengunjung)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
        queue!!.add(stringRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RegisActivity.LAUNCH_ADD_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){

            }
        }
    }
}