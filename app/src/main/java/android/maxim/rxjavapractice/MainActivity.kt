package android.maxim.rxjavapractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val TAG = "tag"
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        button.setOnClickListener {
            Log.d(TAG, "click click")
        }

        //val observable = Observable.just(1, 4, 7)

        val dispose = dataSource()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                button.text = "next int $it"
                Log.d(TAG, "next int $it")
            }, {
                Log.e(TAG, "it ${it.localizedMessage}")
            }, {})
    }

   private fun dataSource(): Observable<Int> {
        return Observable.create { subscriber ->
            for (i in 0..100) {
                Thread.sleep(10000)
                subscriber.onNext(i)
            }
        }
   }

}


