package com.willychia.TugasBesar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.willychia.TugasBesar.Room.BigDB
import com.willychia.TugasBesar.Room.Constant
import com.willychia.TugasBesar.Room.RoomFilm.NoteFilm
import kotlinx.android.synthetic.main.activity_edit_film.*


class EditFilmActivity : AppCompatActivity() {
    val db by lazy { BigDB(this) }
    private var noteId: Int = 0
    private var button_update: Button?=null
    private var button_save: Button?=null
    private val CHANNEL_ID_2 ="channel_notification_02"
    private var notificationId2 = 1
    val GROUP_KEY_WORK_EMAIL = "film_notif"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_film)

        button_save = findViewById(R.id.button_save)
        button_update = findViewById(R.id.button_update)

        val film = db.filmDAO().getNotesFilm()
        Log.d("MainActivity", "dbResponse: $film")

        for(i in film){
            notificationId2++
        }

        notificationId2*=2

        createNotificationChannel()
        setupView()

        button_save?.setOnClickListener {
            sendNotification()
            db.filmDAO().addNoteFilm(
                NoteFilm(0,edit_judul.text.toString(),
                    edit_genre.text.toString(),
                    edit_rating.text.toString().toFloat())
            )
            finish()
        }
        button_update?.setOnClickListener {
            db.filmDAO().updateNoteFilm(
                NoteFilm(noteId,edit_judul.text.toString(),
                    edit_genre.text.toString(),
                    edit_rating.text.toString().toFloat())
            )
            finish()
        }
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                button_update?.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save?.visibility = View.GONE
                button_update?.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                button_save?.visibility = View.GONE
                getNote()
            }
        }
    }

    fun getNote() {
        noteId = intent.getIntExtra("intent_id", 0)
        val notes = db.filmDAO().getNote(noteId)[0]
        edit_judul.setText(notes.judul)
        edit_genre.setText(notes.genre)
        edit_rating.setText(notes.rating.toString())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun sendNotification(){
        val SUMMARY_ID = 0

        val builder = NotificationCompat.Builder(this@EditFilmActivity, CHANNEL_ID_2)
            .setSmallIcon(R.drawable.ic_user_24)
            .setContentTitle("Berhasil Menambahkan Film")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Film dengan judul "+edit_judul.text.toString()+" dengan Genre: "+edit_genre.text.toString()+" memiliki Rating film sebanyak "+edit_rating.text.toString()+". Terima Kasih")
                .setSummaryText("Tambah Film"))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setGroup(GROUP_KEY_WORK_EMAIL)

        val summaryNotification = NotificationCompat.Builder(this@EditFilmActivity, CHANNEL_ID_2)
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setGroup(GROUP_KEY_WORK_EMAIL)
            .setGroupSummary(true)

        with(NotificationManagerCompat.from(this)){
            notify(SUMMARY_ID, summaryNotification.build())
            notify(notificationId2, builder.build())
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel2 = NotificationChannel(CHANNEL_ID_2, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel2)
            println("berhasil buat")
        }
    }
}