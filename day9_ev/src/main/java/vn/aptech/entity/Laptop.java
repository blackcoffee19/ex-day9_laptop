package vn.aptech.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Laptop {
	private int id;
	private String name;
	private int price;
	private String description;
	private String image;
}
