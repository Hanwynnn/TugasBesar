package com.willychia.TugasBesar

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.view.isEmpty
import com.google.android.material.snackbar.Snackbar
import com.willychia.TugasBesar.Room.BigDB
import com.willychia.TugasBesar.Room.RoomPengunjung.NotePengunjung
import com.willychia.TugasBesar.databinding.ActivityRegisBinding
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class RegisActivity : AppCompatActivity() {
    val db by lazy { BigDB(this) }
    private var userId: Int = 0
    private lateinit var binding: ActivityRegisBinding
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notificationId1 = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val cal = Calendar.getInstance()
        val tahun = cal.get(Calendar.YEAR)
        val bulan = cal.get(Calendar.MONTH)
        val hari = cal.get(Calendar.DAY_OF_MONTH)


        binding.btnTgl.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { datePicker, tahun, bulan, hari ->
                binding.btnTgl.text="" + hari + "/ " + (bulan.toInt() + 1).toString() + "/ " + tahun },tahun,bulan,hari)
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

                db.pengunjungDAO().addNotePengunjung(
                    NotePengunjung(0, binding.textInputLayoutNama.getEditText()?.text.toString(), binding.btnTgl.text.toString(), binding.textInputLayoutEmail.getEditText()?.text.toString()
                        , binding.textInputLayoutPassword.getEditText()?.text.toString(), binding.textInputLayoutnoTelp.getEditText()?.text.toString())
                )
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

}