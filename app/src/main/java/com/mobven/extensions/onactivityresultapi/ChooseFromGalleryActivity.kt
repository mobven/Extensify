package com.mobven.extensions.onactivityresultapi

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mobven.extension.Constants
import com.mobven.extension.chooseImagesFromGallery
import com.mobven.extension.click
import com.mobven.extensions.databinding.ActivityChooseFromGalleryBinding

class ChooseFromGalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseFromGalleryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseFromGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val chooser = this@ChooseFromGalleryActivity.chooseImagesFromGallery { uriList:List<Uri?> ->
                Glide.with(this@ChooseFromGalleryActivity).load(uriList.last()).into(ivPreview)
            }
            btnChoosePhotoFromGallery.click {
                chooser.launch(Constants.MIME_TYPE_IMAGE)
            }
            btnChooseVideoFromGallery.click {
                chooser.launch(Constants.MIME_TYPE_VIDEO)
            }
        }
    }
}