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
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(101, 1001 ,\"DBC\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(102, 1002 ,\"MC\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(103, 1003 ,\"WC\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(104, 1004 ,\"PF\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(105, 1005 ,\"SS\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(106, 1006 ,\"CB\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(107, 1007 ,\"DC\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(108, 1008 ,\"NN\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(109, 1009 ,\"CS\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(110, 1010 ,\"DRC\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(111, 1011 ,\"CAC\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(112, 1012 ,\"MM\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(113, 1013 ,\"BSC\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(114, 1014 ,\"OR\", 150);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(115, 1015 ,\"KK\", 150);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(116, 1016 ,\"GM\", 150);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(117, 1017 ,\"DD\", 150);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(118, 1018 ,\"TC\", 170);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(119, 1019 ,\"VI\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(120, 1020 ,\"BI\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(121, 1021 ,\"SI\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(122, 1022 ,\"MI\", 120);");
                    db.execSQL("INSERT INTO WAFFLE_MENU_TABLE VALUES(123, 1023 ,\"CI\", 120);");
                }
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideWaffleFillingsDatabase(@ForApplication context: Context): WaffleFillingsDatabase {
        return Room.databaseBuilder(context, WaffleFillingsDatabase::class.java, "WAFFLE_FILLINGS_DB")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .addCallback(object: RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("INSERT INTO WAFFLE_FILLINGS_TABLE VALUES(101, 3001 ,\"WC\");");
                    db.execSQL("INSERT INTO WAFFLE_FILLINGS_TABLE VALUES(102, 3002 ,\"DUKE\");");
                    db.execSQL("INSERT INTO WAFFLE_FILLINGS_TABLE VALUES(103, 3003 ,\"CAC\");");
                    db.execSQL("INSERT INTO WAFFLE_FILLINGS_TABLE VALUES(104, 3004 ,\"NUTELLA\");");
                    db.execSQL("INSERT INTO WAFFLE_FILLINGS_TABLE VALUES(105, 3005 ,\"DC\");");
                    db.execSQL("INSERT INTO WAFFLE_FILLINGS_TABLE VALUES(106, 3006 ,\"CB\");");
                    db.execSQL("INSERT INTO WAFFLE_FILLINGS_TABLE VALUES(107, 3007 ,\"BSC\");");
                    db.execSQL("INSERT INTO WAFFLE_FILLINGS_TABLE VALUES(108, 3008 ,\"PN\");");
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
    fun provideHomeDatabase(@ForApplication context: Context): HomeDatabase {
        return Room.databaseBuilder(context, HomeDatabase::class.java, "HOME_DB")
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
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(101, 2001 ,\"GEMS\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(102, 2002 ,\"OREO\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(103, 2003 ,\"KITKAT\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(104, 2004 ,\"WCC\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(105, 2005 ,\"DCC\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(106, 2006 ,\"AN\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(107, 2007 ,\"RS\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(108, 2008 ,\"CS\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(109, 2009 ,\"VI\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(110, 2010 ,\"BI\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(111, 2011 ,\"SI\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(112, 2012 ,\"MI\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(113, 2013 ,\"CI\", 20);");
                    db.execSQL("INSERT INTO ADD_ON_TABLE VALUES(114, 2014 ,\"EXTRA CHOCO\", 30);");
                }
            })
            .build()
    }
}