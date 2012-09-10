package net.pikzen.formulas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/***
 * Helper singleton class to manage SQLiteDatabase Create and Restore
 * 
 * @author 
 * 
 */
// Modify by me 24/6/2010
public class DatabaseHelper extends SQLiteOpenHelper {
        private static SQLiteDatabase sqliteDb;
        private static DatabaseHelper instance;
        private static final int DATABASE_VERSION = 1;
        private static String DB_PATH_PREFIX = "/data/data/";
        private static String DB_PATH_SUFFIX = "/databases/";
        private static final String TAG = "DatabaseHelper";
        
        public static final int KEY_ROWID_INDEX = 0;
        public static final int KEY_NAME_INDEX = 1;
        public static final int KEY_ID_INDEX = 2;
        public static final int KEY_CATEGORY_INDEX = 3;
        public static final int KEY_DESCRIPTION_INDEX = 4;
        public static final int KEY_IMAGE_INDEX = 5;
        public static final int KEY_CONSTANT_INDEX = 6;
        
        
        public static final String KEY_NAME = "name";
        private Context context;

        /***
         * Constructor
         * 
         * @param context
         *            : application context
         * @param name
         *            : database name
         * @param factory
         *            : cursor Factory
         * @param version
         *            : DB version
         */
        private DatabaseHelper(Context context, String name,
                        CursorFactory factory, int version) {
                super(context, name, factory, version);
                this.context = context;
                Log.i(TAG, "Starting DBHelper : " + name);
        }
        
        public Cursor query(String query)
        {
        	return sqliteDb.rawQuery(query, null);
        }

        /***
         * Initialize method
         * 
         * @param context
         *            : application context
         * @param databaseName
         *            : database name
         */
        private static void initialize(Context context, String databaseName) {
                if (instance == null) {
                        /**
                         * Try to check if there is an Original copy of DB in asset
                         * Directory
                         */
                        if (!checkDatabase(context, databaseName)) {
                                // if not exists, I try to copy from asset directory
                                try {
                                        copyDataBase(context, databaseName);
                                } catch (IOException e) {
                                        Log.e(TAG, "Database " + databaseName + " does not exists and there is no Original Version in Asset dir");
                                }
                        }
                        
                        try
                        {
                        	if (!checkDBVersion(context))
                        	{
                        		Log.i(TAG, "A newer version of the database has been found on the sdcard. Updating");
                        		try {
                                    copyDataBase(context, databaseName);
                        		} catch (IOException e) {
                                    Log.e(TAG,
                                          "Database " + databaseName  + " does not exists and there is no Original Version in Asset dir");
                        		}
                        	}
                        }
                        catch(Exception e)
                        {
                        	throw new Error("Woopsie, something went wrong.");
                        }
                        

                        Log.i(TAG, "Trying to create instance of database (" + databaseName
                                        + ")");
                        instance = new DatabaseHelper(context, databaseName,
                                        null, DATABASE_VERSION);
                        sqliteDb = instance.getWritableDatabase();
                        Log.i(TAG, "instance of database (" + databaseName + ") created !");
                }
        }

        
        private static Boolean checkDBVersion(Context context) throws Exception
        {
        	String data = getMD5Checksum(new FileInputStream(DB_PATH_PREFIX + context.getPackageName() + DB_PATH_SUFFIX + "sciencedata.db"));
        	String assets = getMD5Checksum(context.getResources().getAssets().open("sciencedata.db"));
        	
        	if (data.contentEquals(assets))
        	{
        		Log.e(TAG, "Database is up to date.");
        		return true;
        	}
        	
        	else
        	{
        		Log.e(TAG, "Database is outdated");
        		return false;
        	}
        	
        	
        }
        public static byte[] createChecksum(InputStream filename) throws Exception {
            InputStream fis =  filename;

            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("MD5");
            int numRead;

            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);

            fis.close();
            return complete.digest();
        }

        public static String getMD5Checksum(InputStream filename) throws Exception {
            byte[] b = createChecksum(filename);
            String result = "";

            for (int i=0; i < b.length; i++) {
                result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            return result;
        }
        /***
         * Static method for getting singleton instance
         * 
         * @param context
         *            : application context
         * @param databaseName
         *            : database name
         * @return : singleton instance
         */
        public static final DatabaseHelper getInstance(
                        Context context, String databaseName) {
                initialize(context, databaseName);
                return instance;
        }

        /***
         * Method to get database instance
         * 
         * @return database instance
         */
        public SQLiteDatabase getDatabase() {
                return sqliteDb;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                Log.d(TAG, "onCreate : nothing to do");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                Log.d(TAG, "onCreate : nothing to do");

        }

        /***
         * Method for Copy the database from asset directory to application's data
         * directory
         * 
         * @param databaseName
         *            : database name
         * @throws IOException
         *             : exception if file does not exists
         */
        private void copyDataBase(String databaseName) throws IOException {
                copyDataBase(context, databaseName);
        }

        /***
         * Static method for copy the database from asset directory to application's
         * data directory
         * 
         * @param aContext
         *            : application context
         * @param databaseName
         *            : database name
         * @throws IOException
         *             : exception if file does not exists
         */
        private static void copyDataBase(Context aContext, String databaseName)
                        throws IOException {

                // Open your local db as the input stream
                InputStream myInput = aContext.getAssets().open(databaseName);

                // Path to the just created empty db
                String outFileName = getDatabasePath(aContext, databaseName);

                Log.i(TAG, "Checking if the phone directory exists : " + DB_PATH_PREFIX
                                + aContext.getPackageName() + DB_PATH_SUFFIX);

                // if the path doesn't exist first, create it
                File f = new File(DB_PATH_PREFIX + aContext.getPackageName()
                                + DB_PATH_SUFFIX);
                if (!f.exists())
                        f.mkdir();

                Log.i(TAG, "Trying to copy local DB to : " + outFileName);

                // Open the empty db as the output stream
                OutputStream myOutput = new FileOutputStream(outFileName);

                // transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                        myOutput.write(buffer, 0, length);
                }

                // Close the streams
                myOutput.flush();
                myOutput.close();
                myInput.close();

                Log.i(TAG, "DB (" + databaseName + ") copied!");
        }

        /***
         * Method to check if database exists in application's data directory
         * 
         * @param databaseName
         *            : database name
         * @return : boolean (true if exists)
         */
        public boolean checkDatabase(String databaseName) {
                return checkDatabase(context, databaseName);
        }

        /***
         * Static Method to check if database exists in application's data directory
         * 
         * @param aContext
         *            : application context
         * @param databaseName
         *            : database name
         * @return : boolean (true if exists)
         */
        public static boolean checkDatabase(Context aContext, String databaseName) {
                SQLiteDatabase checkDB = null;

                try {
                        String myPath = getDatabasePath(aContext, databaseName);

                        Log.i(TAG, "Trying to conntect to : " + myPath);
                        checkDB = SQLiteDatabase.openDatabase(myPath, null,
                                        SQLiteDatabase.OPEN_READONLY);
                        Log.i(TAG, "Database " + databaseName + " found!");
                        checkDB.close();
                } catch (SQLiteException e) {
                        Log.i(TAG, "Database " + databaseName + " does not exists!");

                }

                return checkDB != null ? true : false;
        }

        /***
         * Method that returns database path in the application's data directory
         * 
         * @param databaseName
         *            : database name
         * @return : complete path
         */
        private String getDatabasePath(String databaseName) {
                return getDatabasePath(context, databaseName);
        }

        /***
         * Static Method that returns database path in the application's data
         * directory
         * 
         * @param aContext
         *            : application context
         * @param databaseName
         *            : database name
         * @return : complete path
         */
        private static String getDatabasePath(Context aContext, String databaseName) {
                return DB_PATH_PREFIX + aContext.getPackageName() + DB_PATH_SUFFIX
                                + databaseName;
        }
}