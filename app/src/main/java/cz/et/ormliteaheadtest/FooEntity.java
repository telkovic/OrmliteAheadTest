package cz.et.ormliteaheadtest;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = FooEntity.TABLE_NAME)
public final class FooEntity {
	
	public static final String TABLE_NAME = "tblFoo";
	public static final String FIELD_NAME_ID = "id";
	public static final String FIELD_NAME_NAME = "name";
	
	@DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
	private int id;
	
	@DatabaseField(columnName = FIELD_NAME_NAME)
	private String name;
	
	public FooEntity() {
	}
	
	public FooEntity(String name) {
		this.name = name;
	}
}
