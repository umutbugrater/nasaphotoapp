package com.umutbugrater.nasaphotoapp

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.umutbugrater.nasaphotoapp.adapter.RoverPageAdapter
import com.umutbugrater.nasaphotoapp.model.PhotoDto
import com.umutbugrater.nasaphotoapp.service.RecyclerViewClick
import com.umutbugrater.nasaphotoapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerViewClick {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var roverPageAdapter: RoverPageAdapter

    private var roverType: String = "spirit"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabName = tab?.text.toString().lowercase()
                roverType = tabName
                mainViewModel.refreshData(roverType, "")
                recycler_view.smoothScrollToPosition(0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                recycler_view.smoothScrollToPosition(0)
            }

        })

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.refreshData(roverType, "")

        getLiveData()

        swipe_refresh_layout.setOnRefreshListener {
            recycler_view.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            mainViewModel.refreshData(roverType, "")

            swipe_refresh_layout.isRefreshing = false
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.camera_filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fhaz -> {
                Toast.makeText(this@MainActivity, "fhaz", Toast.LENGTH_SHORT).show()
                mainViewModel.refreshData(roverType, "fhaz")
                return true
            }
            R.id.rhaz -> {
                Toast.makeText(this@MainActivity, "RHAZ", Toast.LENGTH_SHORT).show()
                mainViewModel.refreshData(roverType, "rhaz")
                true
            }
            R.id.mast -> {
                Toast.makeText(this@MainActivity, "MAST", Toast.LENGTH_SHORT).show()
                mainViewModel.refreshData(roverType, "mast")
                true
            }
            R.id.chemcam -> {
                Toast.makeText(this@MainActivity, "CHENCAM", Toast.LENGTH_SHORT).show()
                mainViewModel.refreshData(roverType, "chencam")
                true
            }
            R.id.mahli -> {
                Toast.makeText(this@MainActivity, "MAHLI", Toast.LENGTH_SHORT).show()
                mainViewModel.refreshData(roverType, "mahli")
                true
            }
            R.id.mardi -> {
                Toast.makeText(this@MainActivity, "MARDI", Toast.LENGTH_SHORT).show()
                mainViewModel.refreshData(roverType, "mardi")
                true
            }
            R.id.navcam -> {
                Toast.makeText(this@MainActivity, "NAVCAM", Toast.LENGTH_SHORT).show()
                mainViewModel.refreshData(roverType, "navcam")
                true
            }
            R.id.pancam -> {
                Toast.makeText(this@MainActivity, "PANCAM", Toast.LENGTH_SHORT).show()
                mainViewModel.refreshData(roverType, "pancam")
                true
            }
            R.id.minites -> {
                Toast.makeText(this@MainActivity, "MINITES", Toast.LENGTH_SHORT).show()
                mainViewModel.refreshData(roverType, "minites")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun openWindow(photoDtoClick: PhotoDto) {

        val window = PopupWindow(this)
        val view = layoutInflater.inflate(R.layout.layout_popup, null)
        window.contentView = view
        window.showAtLocation(
            tabLayout,
            Gravity.CENTER,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val cardNasa = view.findViewById<CardView>(R.id.popup_card)
        cardNasa.setOnClickListener {
            window.dismiss()
        }

        val roverPhoto = view.findViewById<ImageView>(R.id.popup_rover_photo)
        val photoDate = view.findViewById<TextView>(R.id.popup_photo_date)
        val roverType = view.findViewById<TextView>(R.id.popup_rover_type)
        val camera = view.findViewById<TextView>(R.id.popup_camera)
        val missionStatus = view.findViewById<TextView>(R.id.popup_mission_status)
        val launchDate = view.findViewById<TextView>(R.id.popup_launch_date)
        val landingDate = view.findViewById<TextView>(R.id.popup_landing_date)

        val image = photoDtoClick.imgSrc.replace("http", "https")
        Glide.with(this).load(image).into(roverPhoto)
        photoDate.text = "Date: " + photoDtoClick.earthDate
        roverType.text = "Rover Name: " + photoDtoClick.rover.name
        camera.text = "Camera Name: " + photoDtoClick.camera.name
        missionStatus.text = "Status: " + photoDtoClick.rover.status
        launchDate.text = "Launch Date: " + photoDtoClick.rover.launchDate
        landingDate.text = "Landing Date: " + photoDtoClick.rover.landingDate
    }

    private fun getLiveData() {
        mainViewModel.nasaData.observe(this) { data ->
            recycler_view.visibility = View.VISIBLE

            roverPageAdapter = RoverPageAdapter(photos = data.photos, recyclerViewClick = this)
            recycler_view.layoutManager = GridLayoutManager(this@MainActivity, 2)
            recycler_view.adapter = roverPageAdapter
        }

        mainViewModel.error.observe(this) { error ->
            error?.let {
                if (error) {
                    tv_error.visibility = View.VISIBLE
                    pb_loading.visibility = View.GONE
                    recycler_view.visibility = View.GONE
                } else {
                    tv_error.visibility = View.GONE
                }
            }
        }

        mainViewModel.isLoading.observe(this) { load ->
            load?.let {
                if (load) {
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    recycler_view.visibility = View.GONE
                } else {
                    pb_loading.visibility = View.GONE
                }
            }
        }

    }
}