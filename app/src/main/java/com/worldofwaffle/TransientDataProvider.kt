package com.worldofwaffle

import io.reactivex.subjects.PublishSubject
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransientDataProvider @Inject constructor(){

    var transientDataNew: ConcurrentMap<Class<*>, UseCase> = ConcurrentHashMap()
    var dataSubjectNew = PublishSubject.create<Class<*>>()

    fun save(useCase: UseCase) {
        val useCaseClass: Class<out UseCase> = useCase.javaClass
        transientDataNew[useCaseClass] = useCase
        dataSubjectNew.onNext(useCaseClass)
    }

    fun <T : UseCase> remove(useCaseClass: Class<T>): T {
        val removedUseCase = transientDataNew.remove(useCaseClass)
        return useCaseClass.cast(removedUseCase)
    }

    fun <T : UseCase> containsUseCase(useCaseClass: Class<T>?): Boolean {
        return transientDataNew.containsKey(useCaseClass)
    }
}