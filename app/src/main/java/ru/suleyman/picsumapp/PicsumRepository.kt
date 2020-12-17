package ru.suleyman.picsumapp

import ru.suleyman.picsumapp.factory.RetrofitFactory

object PicsumRepository {

    fun picsum(): PicsumService {
        return RetrofitFactory.provideRetrofit().create(PicsumService::class.java)
    }

}