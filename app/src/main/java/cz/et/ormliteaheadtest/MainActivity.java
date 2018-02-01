package cz.et.ormliteaheadtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.j256.ormlite.misc.TransactionManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

public final class MainActivity extends AppCompatActivity {
	
	private DbHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new DbHelper(this);
		setContentView(R.layout.activity_main);
		findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					dbHelper.setWriteAheadLoggingEnabled(
							((CheckBox) findViewById(R.id.chbAheadLogging)).isChecked()
					);
					if (((CheckBox) findViewById(R.id.chbTransaction)).isChecked()) {
						TransactionManager.callInTransaction(dbHelper.getConnectionSource(), new Callable<Void>() {
							@Override
							public Void call() throws Exception {
								doInsert();
								return null;
							}
						});
					} else {
						doInsert();
					}
				} catch (SQLException e) {
					log(e.getMessage());
				}
			}
		});
		findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					dbHelper.setWriteAheadLoggingEnabled(
							((CheckBox) findViewById(R.id.chbAheadLogging)).isChecked()
					);
					if (((CheckBox) findViewById(R.id.chbTransaction)).isChecked()) {
						TransactionManager.callInTransaction(dbHelper.getConnectionSource(), new Callable<Void>() {
							@Override
							public Void call() throws Exception {
								doUpdate();
								return null;
							}
						});
					} else {
						doUpdate();
					}
				} catch (SQLException e) {
					log(e.getMessage());
				}
			}
		});
	}
	
	private void doInsert() throws SQLException {
		logResult(
				"Insert",
				dbHelper.getFooDao().create(new FooEntity("Test"))
		);
	}
	
	private void doUpdate() throws SQLException {
		logResult(
				"Update",
				dbHelper.getFooDao().update(
						dbHelper.getFooDao().queryForId(1)
				)
		);
	}
	
	private void logResult(String cmd, int affectedRows) {
		log(getString(
				R.string.result,
				cmd,
				((CheckBox) findViewById(R.id.chbAheadLogging)).isChecked() ? "on" : "off",
				((CheckBox) findViewById(R.id.chbTransaction)).isChecked() ? "on" : "off",
				affectedRows
		));
	}
	
	private void log(String message) {
		TextView log = findViewById(R.id.lblLog);
		log.setText(String.format(
				"%s - %s\n%s",
				SimpleDateFormat.getTimeInstance().format(new Date()), message, log.getText()
		));
	}
}
