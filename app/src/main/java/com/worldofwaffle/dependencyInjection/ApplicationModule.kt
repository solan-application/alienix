package com.worldofwaffle.dependencyInjection

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.worldofwaffle.ForApplication
import com.worldofwaffle.commondialog.WaffleDialogFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import androidx.sqlite.db.SupportSQLiteDatabase
import com.worldofwaffle.database.*


@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @ForApplication
    fun provideContext(): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideFordDialogFactory(): WaffleDialogFactory {
        return WaffleDialogFactory()
    }

    @Singleton
    @Provides
    fun provideWaffleMenuDatabase(@ForApplication context: Context): WaffleMenuDatabase {
        return Room.databaseBuilder(context, WaffleMenuDatabase::class.java, "WAFFLE_MENU_DB")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .addCallback(object: RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(101, 1001 ,\"WHITE CHOCO\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(102, 1002 ,\"DEATH BY CHOCOLATE\", 120);");
                }
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideOrderDetailDatabase(@ForApplication context: Context): OrderDetailRoomDatabase {
        return Room.databaseBuilder(context, OrderDetailRoomDatabase::class.java, "ORDER_DETAIL_DB")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideOrderHistoryDatabase(@ForApplication context: Context): OrderHistoryDatabase {
        return Room.databaseBuilder(context, OrderHistoryDatabase::class.java, "ORDER_HISTORY_DB")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideAddOnDatabase(@ForApplication context: Context): AddOnsDatabase {
        return Room.databaseBuilder(context, AddOnsDatabase::class.java, "ADD_ON_DB")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .addCallback(object: RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(101, 2001 ,\"ICE CREAM\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(102, 2002 ,\"WHITE CHOCO CHIP\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(103, 2003 ,\"DARK CHOCO CHIP\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(104, 2004 ,\"GEMS\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(105, 2005 ,\"KITKAT\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(106, 2005 ,\"OREO\", 20);");
                }
            })
            .build()
    }
}