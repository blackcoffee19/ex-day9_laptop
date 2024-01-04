package vn.aptech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
import lombok.NoArgsConstructor;
//use
//@Setter
//@Getter
//or else use
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	private int id;
	private String name;
	private int price;
	private String image;
	
}
