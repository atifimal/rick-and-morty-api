package net.egemsoft.demo.model;

/**
 * Entity'lerin cagirilma sayilarini yazdirmak icin olusturulan model
 * @author Mehmet Atif Imal
 *
 */
public class Report {

	private String name;
	private int num;

	public Report(String name, int num) {
		this.name = name;
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
