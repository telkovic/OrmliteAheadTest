package cz.et.ormliteaheadtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DbHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String DATABASE_NAME = "ormlite.db";
	private static final int DATABASE_VERSION = 1;
	
	private Dao<FooEntity, Integer> fooDao = null;
	
	public DbHelper(Context context) {
		super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, FooEntity.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
						  int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, FooEntity.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Dao<FooEntity, Integer> getFooDao() throws SQLException {
		if (fooDao == null) {
			fooDao = getDao(FooEntity.class);
		}
		return fooDao;
	}
	
	@Override
	public void close() {
		fooDao = null;
		super.close();
	}
}
