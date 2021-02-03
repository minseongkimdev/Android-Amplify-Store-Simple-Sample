package com.example.amplifytest2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import com.obsez.android.lib.filechooser.ChooserDialog
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val exampleFile = File(applicationContext.filesDir, "ExampleKey")

        exampleFile.writeText("Example file contents")


        Amplify.Storage.uploadFile(
                System.currentTimeMillis().toString(),
            exampleFile,
            { result -> Log.d("MyAmplifyApp", "Successfully uploaded: " + result) },
            { error -> Log.d("MyAmplifyApp", "Upload failed", error) }
        )

        findViewById<View>(R.id.button).setOnClickListener{
            ChooserDialog(this)
                .withFilterRegex(false, true, ".*\\.(jpe?g|png|mp4)")
                .withStartFile("")
                .withChosenListener { path, pathFile ->
                    Toast.makeText(this, "FILE: $path; PATHFILE: $pathFile", Toast.LENGTH_SHORT).show()
                    for (i in 0..5){
                        Amplify.Storage.uploadFile(
                            "minseong/" + System.currentTimeMillis().toString() +"_$i",
                            pathFile,
                            { result -> Log.d("MyAmplifyApp", "Successfully uploaded: " + result.key)
                                Toast.makeText(this@MainActivity,"upload successful : " +result.key,Toast.LENGTH_LONG).show()
                            },
                            { error -> Log.d("MyAmplifyApp", "Upload failed", error) }
                        )
                    }
                }
                .withNavigateUpTo { true }
                .withNavigateTo { true }
                .build()
                .show()

        }
    }

}