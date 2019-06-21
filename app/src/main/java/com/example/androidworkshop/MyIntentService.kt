package com.example.androidworkshop

import android.app.IntentService
import android.content.Intent
import com.example.androidworkshop.networking.BeerApiController
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions and extra parameters.
 */
class MyIntentService : IntentService("MyIntentService") {

    // run in the background
    override fun onHandleIntent(intent: Intent?) {

        // call a webservice; long run operation
        Timber.d("service is execute here")


        Timber.d("post event to eventBus")
        EventBus.getDefault().post(DownloadCompletedEvent(10))

        val beerController = BeerApiController()
        beerController.start()


        Timber.d("list size=${beerController.listOfBeers.size}")
    }
}
