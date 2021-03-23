package by.gsu.epamlab.model.beans;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;

import by.gsu.epamlab.helpers.DateUtils;

public class Task {

	private int id;
	
	private String description;
	
	private Date date;
	
	private File file;

	public Task() { }

	public Task(String description, Date date, int id, File file) {
		this.description = description;
		this.date = date;
		this.id = id;
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setStringDate(String date) throws ParseException {
		setDate(DateUtils.convertFromString(date));
	}	
	
	public long getKBFileSize() {
		return Math.round(file.length() / 1024);
	}

}
