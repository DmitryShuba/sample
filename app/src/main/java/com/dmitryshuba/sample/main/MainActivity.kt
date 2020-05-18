package com.dmitryshuba.sample.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.dmitryshuba.sample.R
import com.dmitryshuba.sample.main.sectionlist.util.ISectionListRouter
import com.dmitryshuba.sample.main.sectionlist.util.SectionListRouter
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

class MainActivity : AppCompatActivity(), KodeinAware, IMainActivity {

    private val parentKodein: Kodein by kodein()
    private val sectionListRouter: ISectionListRouter by lazy() {
        SectionListRouter(Navigation.findNavController(this@MainActivity, R.id.navHostFragment))
    }

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        bind<ISectionListRouter>() with provider { sectionListRouter }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(sampleToolbar)
    }

    override fun addBackButton() {
        sampleToolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        sampleToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun removeBackButton() {
        sampleToolbar.navigationIcon = null
    }
}
