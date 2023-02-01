package com.android.busapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object
    {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "AppDatabase"

        private const val TABLE_BUS = "BusTable"
        private const val KEY_ID = "id"
        private const val KEY_LICENSE = "license"
        private const val KEY_MODEL = "model"
        private const val KEY_SEATS = "seats"

        private const val TABLE_TRIPS = "TripTable"
        private const val KEY_TID = "tid"
        private const val KEY_BID = "bid"
        private const val KEY_START = "start"
        private const val KEY_DEST = "dest"
        private const val KEY_DATE = "date"
        private const val KEY_TIME = "time"

        private const val TABLE_TRANS = "TransTable"
        private const val KEY_TRANSID = "_id"
        private const val KEY_TRANSUID = "uid"

        private const val TABLE_USER = "UserTable"
        private const val KEY_UID = "_id"
        private const val KEY_AADHAAR = "aadhaar"
        private const val KEY_PHONE = "phone"
        private const val KEY_UNAME = "uname"
    }

    override fun onCreate(db: SQLiteDatabase?)
    {
        //bus table
        val createTable1 = ("CREATE TABLE "+ TABLE_BUS+"("+KEY_ID+" INTEGER PRIMARY KEY,"+ KEY_LICENSE+" TEXT,"
                + KEY_MODEL+" TEXT,"+KEY_SEATS+" INTEGER"+")")
        db?.execSQL(createTable1)

        //trips table
        val createTable2 = ("CREATE TABLE "+ TABLE_TRIPS +"("+ KEY_TID +" INTEGER PRIMARY KEY,"+ KEY_BID +" INTEGER,"
                + KEY_START +" TEXT,"+ KEY_DEST +" TEXT,"+ KEY_DATE +" TEXT,"+ KEY_TIME +" TEXT,"
                +"FOREIGN KEY ($KEY_BID) REFERENCES $TABLE_BUS($KEY_ID) ON DELETE CASCADE"+")")
        db?.execSQL(createTable2)

        //user table
        val createTable4 = ("CREATE TABLE $TABLE_USER($KEY_UID INTEGER PRIMARY KEY,$KEY_AADHAAR TEXT UNIQUE,$KEY_PHONE TEXT UNIQUE," +
                "$KEY_UNAME TEXT)")
        db?.execSQL(createTable4)

        //transaction table
        val createTable3 = ("CREATE TABLE $TABLE_TRANS($KEY_TRANSID INTEGER PRIMARY KEY,$KEY_TRANSUID INTEGER,"
                + KEY_TID+" INTEGER,"+KEY_SEATS+" INTEGER,"+"FOREIGN KEY ($KEY_TRANSUID) REFERENCES $TABLE_USER($KEY_UID) ON DELETE CASCADE," +
                "FOREIGN KEY ($KEY_TID) REFERENCES $TABLE_TRIPS($KEY_TID) ON DELETE CASCADE)")
        db?.execSQL(createTable3)

        db?.execSQL("PRAGMA foreign_keys = ON;")//foreign keys by default turned off
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        //this method called when db exists but requested version no is higher
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_BUS")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_TRIPS")
        onCreate(db)
        //db?.execSQL("PRAGMA foreign_keys = ON;")//foreign keys by default turned off
    }
    fun addBus(bus: BusModelClass): Long
    {
        val db = this.writableDatabase
        db?.execSQL("PRAGMA foreign_keys = ON;")

        val contentValues = ContentValues()
        contentValues.put(KEY_ID, bus.id)
        contentValues.put(KEY_LICENSE, bus.license)
        contentValues.put(KEY_MODEL, bus.model)
        contentValues.put(KEY_SEATS, bus.seats)

        // Inserting student details using insert query.
        val success = db.insert(TABLE_BUS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }
    //Method to read the records from database in form of ArrayList
    fun viewBus(): ArrayList<BusModelClass>
    {
        val busList: ArrayList<BusModelClass> = ArrayList<BusModelClass>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM $TABLE_BUS"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(selectQuery, null)//feed select query into the database

        }
        catch (e: SQLiteException)
        {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var license: String
        var model: String
        var seats: Int

        if (cursor.moveToFirst())
        {
            do
            {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                license = cursor.getString(cursor.getColumnIndexOrThrow(KEY_LICENSE))
                model = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MODEL))
                seats = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SEATS))
                val bus = BusModelClass(id = id, license = license, model = model,seats=seats)
                busList.add(bus)

            } while (cursor.moveToNext())
        }
        return busList
    }
    fun updateBus(bus: BusModelClass): Int
    {
        val db = this.writableDatabase
        db?.execSQL("PRAGMA foreign_keys = ON;")

        val contentValues = ContentValues()
        contentValues.put(KEY_ID, bus.id)
        contentValues.put(KEY_LICENSE, bus.license)
        contentValues.put(KEY_MODEL, bus.model)
        contentValues.put(KEY_SEATS, bus.seats)

        // Updating record based on id
        val success = db.update(TABLE_BUS, contentValues, KEY_ID + "=" + bus.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }
    fun deleteBus(bus: BusModelClass): Int
    {
        val db = this.writableDatabase
        db?.execSQL("PRAGMA foreign_keys = ON;")
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, bus.id) // EmpModelClass id
        // Deleting Row
        val success = db.delete(TABLE_BUS, KEY_ID + "=" + bus.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }
    fun addTrip(trip: TripModelClass): Long
    {
        val db = this.writableDatabase
        db?.execSQL("PRAGMA foreign_keys = ON;")

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.KEY_TID, trip.tid)
        contentValues.put(DatabaseHandler.KEY_BID, trip.bid)
        contentValues.put(DatabaseHandler.KEY_START, trip.start)
        contentValues.put(DatabaseHandler.KEY_DEST, trip.dest)
        contentValues.put(DatabaseHandler.KEY_DATE, trip.date)
        contentValues.put(DatabaseHandler.KEY_TIME, trip.time)

        // Inserting student details using insert query.
        val success = db.insert(DatabaseHandler.TABLE_TRIPS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }
    //Method to read the records from database in form of ArrayList
    fun viewTrips(): ArrayList<TripModelClass>
    {
        val TripList: ArrayList<TripModelClass> = ArrayList<TripModelClass>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM ${DatabaseHandler.TABLE_TRIPS}"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(selectQuery, null)//feed select query into the database

        }
        catch (e: SQLiteException)
        {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var tid: Int
        var bid: Int
        var start: String
        var dest: String
        var date: String
        var time: String

        if (cursor.moveToFirst())
        {
            do
            {
                tid = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_TID))
                bid = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_BID))
                start = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_START))
                dest = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_DEST))
                date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_DATE))
                time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_TIME))

                val trip = TripModelClass(tid = tid, bid = bid, start = start,dest=dest,date=date,time=time)
                TripList.add(trip)

            } while (cursor.moveToNext())
        }
        return TripList
    }
    fun updateTrip(trip: TripModelClass): Int
    {
        val db = this.writableDatabase
        db?.execSQL("PRAGMA foreign_keys = ON;")

        val contentValues = ContentValues()
        contentValues.put(KEY_TID, trip.tid)
        contentValues.put(KEY_BID, trip.bid)
        contentValues.put(KEY_START, trip.start)
        contentValues.put(KEY_DEST, trip.dest)
        contentValues.put(KEY_DATE, trip.date)
        contentValues.put(KEY_TIME, trip.time)

        val success = db.update(TABLE_TRIPS, contentValues, KEY_TID + "=" + trip.tid, null)

        db.close()
        return success
    }
    fun deleteTrip(trip: TripModelClass): Int
    {
        val db = this.writableDatabase
        db?.execSQL("PRAGMA foreign_keys = ON;")
        val contentValues = ContentValues()
        contentValues.put(KEY_TID, trip.tid)
        // Deleting Row
        val success = db.delete(TABLE_TRIPS, KEY_TID + "=" + trip.tid, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }
    fun search(start1:String?,dest1:String?,date1:String?):ArrayList<SearchResultsModelClass>
    {
        val resultsList: ArrayList<SearchResultsModelClass> = ArrayList()

        // Query search from the table
        val selectQuery = "SELECT $KEY_TID,$KEY_MODEL,$KEY_START,$KEY_DEST,$KEY_DATE,$KEY_TIME " +
                "FROM $TABLE_BUS,$TABLE_TRIPS WHERE $TABLE_BUS.$KEY_ID=$TABLE_TRIPS.$KEY_BID " +
                "AND $TABLE_TRIPS.$KEY_START=\"$start1\" AND $TABLE_TRIPS.$KEY_DEST=\"$dest1\" " +
                "AND $TABLE_TRIPS.$KEY_DATE=\"$date1\""

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(selectQuery, null)//feed select query into the database

        }
        catch (e: SQLiteException)
        {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var tid:Int
        var model: String
        var start: String
        var dest: String
        var date: String
        var time: String

        if (cursor.moveToFirst())
        {
            do
            {
                tid = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_TID))
                model = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_MODEL))
                start = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_START))
                dest = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_DEST))
                date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_DATE))
                time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_TIME))

                val result = SearchResultsModelClass(tid=tid,model=model,start = start,dest=dest,date=date,time=time)
                resultsList.add(result)

            } while (cursor.moveToNext())
        }
        return resultsList
    }
    fun checkSeats(tripid:Int,userSeats:Int): Int
    {
        var retvalue:Int=0;
        val db = this.readableDatabase
        val sumQuery = "SELECT SUM($KEY_SEATS) FROM $TABLE_TRANS WHERE $KEY_TID=$tripid"//find total seats occupied for that trip
        val busIdQuery="SELECT $KEY_BID FROM $TABLE_TRIPS WHERE $KEY_TID=$tripid"//find bus id of that trip


        var cursor: Cursor? = null

        //retrieve sum of seats for that trip
        try
        {
            cursor = db.rawQuery(sumQuery, null)//feed select query into the database

        }
        catch (e: SQLiteException)
        {
            db.execSQL(sumQuery)
            retvalue=0
        }
        cursor?.moveToFirst()
        val sum= cursor?.getInt(cursor.getColumnIndexOrThrow("SUM($KEY_SEATS)"))
        cursor?.close()

        //retrieve bus id for that trip
        try
        {
            cursor = db.rawQuery(busIdQuery, null)//feed select query into the database

        }
        catch (e: SQLiteException)
        {
            db.execSQL(sumQuery)
            retvalue=0
        }
        cursor?.moveToFirst()
        val id= cursor?.getInt(cursor.getColumnIndexOrThrow("$KEY_BID"))
        cursor?.close()

        //retrieve total seats that bus
        val seatsQuery="SELECT $KEY_SEATS FROM $TABLE_BUS WHERE $KEY_ID=$id"//find total seats in that bus
        try
        {
            cursor = db.rawQuery(seatsQuery, null)//feed select query into the database

        }
        catch (e: SQLiteException)
        {
            db.execSQL(sumQuery)
            retvalue=0
        }
        cursor?.moveToFirst()
        val seats= cursor?.getInt(cursor.getColumnIndexOrThrow("$KEY_SEATS"))
        cursor?.close()

        if (sum != null)
        {
            if(sum+userSeats > seats!!)
            {
                retvalue=0
            }
            else
            {
                retvalue=1
            }
        }
        return retvalue
        db.close()
    }
    fun addTrans(trans: TransactionModelClass): Long
    {
        val db = this.writableDatabase
        db?.execSQL("PRAGMA foreign_keys = ON;")

        val contentValues = ContentValues()
        //contentValues.put(DatabaseHandler.KEY_TRANSID, trans.transid)
        contentValues.put(DatabaseHandler.KEY_TRANSUID, trans.uid)
        contentValues.put(DatabaseHandler.KEY_TID, trans.tid)
        contentValues.put(DatabaseHandler.KEY_SEATS, trans.seats)

        val success = db.insert(DatabaseHandler.TABLE_TRANS, null, contentValues)

        db.close()
        return success
    }
    fun addUser(user: UserModelClass): Int?
    {
        val db = this.writableDatabase
        db?.execSQL("PRAGMA foreign_keys = ON;")

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.KEY_AADHAAR, user.aadhaar)
        contentValues.put(DatabaseHandler.KEY_PHONE, user.phone)
        contentValues.put(DatabaseHandler.KEY_UNAME, user.uname)

        db.insert(DatabaseHandler.TABLE_USER, null, contentValues)
        val findUserQuery = "SELECT $KEY_UID FROM $TABLE_USER WHERE $KEY_AADHAAR = "+"\"${user.aadhaar}\" AND $KEY_PHONE = "+
                "\"${user.phone}\" AND $KEY_UNAME=\"${user.uname}\""
        var cursor: Cursor?=null

        try
        {
            cursor = db.rawQuery(findUserQuery, null)//feed select query into the database

        }
        catch (e: SQLiteException)
        {
            db.execSQL(findUserQuery)
            return -1
        }
        if(cursor?.moveToFirst()==false)
            return -1
        val uid:Int?= cursor?.getInt(cursor.getColumnIndexOrThrow("$KEY_UID"))
        cursor?.close()
        db.close()
        return uid
    }

    fun viewTrans(): ArrayList<TransactionModelClass>
    {
        val transList: ArrayList<TransactionModelClass> = ArrayList()

        // Query to select all the records from the table.
        val selectQuery = "SELECT $TABLE_TRANS.$KEY_TRANSID,$KEY_TRANSUID,$KEY_UNAME,$KEY_TID,$KEY_SEATS FROM $TABLE_TRANS,$TABLE_USER " +
                "WHERE $TABLE_TRANS.$KEY_TRANSUID = $TABLE_USER.$KEY_UID"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(selectQuery, null)//feed select query into the database

        }
        catch (e: SQLiteException)
        {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var transid: Int
        var uid: Int
        var name:String
        var tid: Int
        var seats: Int

        if (cursor.moveToFirst())
        {
            do
            {
                transid = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TRANSID))
                uid = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TRANSUID))
                name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_UNAME))
                tid = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TID))
                seats = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SEATS))
                val trans = TransactionModelClass(transid = transid, uid = uid,name=name, tid = tid,seats=seats)
                transList.add(trans)

            } while (cursor.moveToNext())
        }
        return transList
    }
    fun viewUser(): ArrayList<UserModelClass>
    {
        val userList: ArrayList<UserModelClass> = ArrayList()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM $TABLE_USER"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(selectQuery, null)//feed select query into the database

        }
        catch (e: SQLiteException)
        {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var uid: Int
        var aadhaar: String
        var phone: String
        var uname: String

        if (cursor.moveToFirst())
        {
            do
            {
                uid = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_UID))
                aadhaar = cursor.getString(cursor.getColumnIndexOrThrow(KEY_AADHAAR))
                phone = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE))
                uname = cursor.getString(cursor.getColumnIndexOrThrow(KEY_UNAME))
                val user = UserModelClass(uid = uid, aadhaar = aadhaar, phone = phone,uname=uname)
                userList.add(user)

            } while (cursor.moveToNext())
        }
        return userList
    }
}