package com.android.daggerkotlinexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity() {

    @Inject @field:Named("network")
    lateinit var networkRepositry: Repositry
    @Inject @field:Named("database")
    lateinit var dbRepositry: Repositry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //first, you must create RepositryComponent, then buid project to inject Repositry
        DaggerRepositryComponent.create().injectRepositry(this)

        textviewval.text = networkRepositry.myText+"..."+dbRepositry.myText
    }
}


class Repositry @Inject constructor(val myText:String)


@Component (modules =  [FirstModule::class,SecondModule::class])
interface RepositryComponent{
    fun injectRepositry(app:MainActivity)
}


@Module
class FirstModule{

    @Provides @Named("network")
    fun networkCall():Repositry{
        return Repositry("This is a network Call")
    }
}

@Module
class SecondModule{

    @Provides @Named("database")
    fun databaseCall():Repositry{
        return Repositry("This is a database Call")
    }
}