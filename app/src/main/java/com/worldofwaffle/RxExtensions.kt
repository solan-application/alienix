/*
 * CONFIDENTIAL FORD MOTOR COMPANY
 *  This is an unpublished work of authorship, which contains confidential information and/or trade secrets, created in 2021. Ford Motor Company owns all rights to this work and intends to maintain it in confidence to preserve its trade secret status. Ford Motor Company reserves all rights, under the copyright laws of the United States or those of any other country that may have jurisdiction, including the right to protect this work as an unpublished work, in the event of an inadvertent or deliberate unauthorized publication. Use of this work constitutes an agreement to maintain the confidentiality of the work, and to refrain from any reverse engineering, decompilation, or disassembly of this work.
 *  Ford Motor Company also reserves its rights under all copyright laws to protect this work as a published work, when appropriate. Those having access to this work may not copy it, use it, modify it, or disclose the information contained in it without the written authorization of Ford Motor Company.
 *  Copyright 2021, Ford Motor Company.
 */

package com.worldofwaffle

import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.*

fun <T> Observable<T>.onErrorResumeEmpty(processError: (Throwable) -> Unit = { it.printStackTrace() }) = run {
    this.onErrorResumeNext { error: Throwable -> processError(error); Observable.empty() }
}

fun <T> Observable<Optional<T>>.getIfPresent() = this.filter(Optional<T>::isPresent).map(Optional<T>::get)

fun <R> List<String>.forEachVin(onVin: (String) -> Observable<R>): Single<List<R>> =
        Observable.fromIterable(this).flatMap(onVin).toList()

fun <R> Observable<List<String>>.forEachVin(onVin: (String) -> Observable<R>) :Observable<List<R>> =
        this.flatMapSingle { vinList -> Observable.fromIterable(vinList).flatMap(onVin).toList() }

fun Completable.safeSubscribe(onComplete: () -> Unit): Disposable =
        subscribe(onComplete, Throwable::printStackTrace)

fun Completable.safeSubscribe(onComplete: () -> Unit, onError: (Throwable) -> Unit): Disposable =
        subscribe(onComplete, onError)

fun <T> Observable<T>.safeSubscribe(onNext: (T) -> Unit): Disposable =
        subscribe(onNext, Throwable::printStackTrace)

fun <T> Observable<T>.safeSubscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable =
        subscribe(onNext, onError)

fun <T> Flowable<T>.safeSubscribe(onNext: (T) -> Unit): Disposable =
        subscribe(onNext, Throwable::printStackTrace)

fun <T> Flowable<T>.safeSubscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable =
        subscribe(onNext, onError)

fun <T> Single<T>.safeSubscribe(onSuccess: (T) -> Unit): Disposable =
        subscribe(onSuccess, Throwable::printStackTrace)

fun <T> Single<T>.safeSubscribe(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit): Disposable =
        subscribe(onSuccess, onError)

fun <T> Maybe<T>.safeSubscribe(onSuccess: (T) -> Unit): Disposable =
        subscribe(onSuccess, Throwable::printStackTrace)

fun <T> Maybe<T>.safeSubscribe(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit): Disposable =
        subscribe(onSuccess, onError)
