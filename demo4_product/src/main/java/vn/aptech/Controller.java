package vn.aptech;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.aptech.db.ProductDb;
import vn.aptech.dto.ProductDto;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Servlet implementation class Controller
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024,maxFileSize = 5 * 1024 * 1024,maxRequestSize = 5*5*1024*1024)
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file ;
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String a =request.getParameter("a");
		String b= request.getParameter("b");
		ProductDb db = new ProductDb();
		String data = (String) request.getAttribute("demo");
		HttpSession session = request.getSession();
		session.setAttribute("data", data);
		if(a==null) {
			request.setAttribute("prods", db.findAll());
			request.getRequestDispatcher("product/index.jsp").forward(request, response);
		}else {
			switch (a) {
				case "DisplayCreate": {
					response.sendRedirect("product/create.jsp");				
					break;
				}
				case "DisplayUpdate":{
					if(b==null || b.isEmpty()) {
						response.sendRedirect("product/create.jsp");
					}
					int id = Integer.parseInt(b);
					ProductDto pr = db.FindId(id);
					request.setAttribute("product", pr);
					request.getRequestDispatcher("product/update.jsp").forward(request, response);
					break;
				}
				
				default:
					throw new IllegalArgumentException("Unexpected value: " + a);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String a =request.getParameter("a");
		ProductDb db = new ProductDb();
		HttpSession session = request.getSession();
		if(a!=null) {
			switch (a) {
			case "Create": {
				try {
					String name = request.getParameter("name");
					String price = request.getParameter("price");
					if(name.isEmpty()|| price.isEmpty()) {
						session.setAttribute("error","Required Name and Price");
						response.sendRedirect("product/create.jsp");
					}
					Part part = request.getPart("photo");
					String filename = Path.of(part.getSubmittedFileName()).getFileName().toString();
					ProductDto product = new ProductDto();
					if (!filename.isEmpty()) {
						String imgFolderPath = request.getServletContext().getRealPath("/images");
						
						File imgfolder = new File(imgFolderPath);
						if(!Files.exists(Path.of(imgFolderPath))) {
							Files.createDirectory(Path.of(imgFolderPath));
						}
						part.write(imgFolderPath+"/"+filename);	
					}
					int nPrice = !price.isEmpty()?Integer.parseInt(price):0;
					product.setImage(filename);
					product.setName(name);
					product.setPrice(nPrice);
					db.create(product);
					
					session.setAttribute("messageS","Create Product Successfull");
					response.sendRedirect("Controller");
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
				break;
			}
			case "Update": {
				try {
					String id = request.getParameter("id");
					String name = request.getParameter("name");
					String price = request.getParameter("price");
					int nPrice = Integer.parseInt(price);					
					ProductDto product = db.FindId(Integer.parseInt(id));
					if(name!=product.getName()&& name!=null) {
						product.setName(name);						
					}
					if(nPrice !=product.getPrice()) {
						product.setPrice(nPrice);						
					}
					String filename = "";
					Part part = request.getPart("photo");
					filename = Path.of(part.getSubmittedFileName()).getFileName().toString();
					if (!filename.isEmpty()) {
						String imgFolderPath = request.getServletContext().getRealPath("/images");
						File imgfolder = new File(imgFolderPath);
						if(!Files.exists(Path.of(imgFolderPath))) {
							Files.createDirectory(Path.of(imgFolderPath));
						}
						part.write(imgFolderPath+"/"+filename);	
						product.setImage(filename);						
					}
					session.setAttribute("messageS","Update Product Successfull");
					db.update(product);
					response.sendRedirect("Controller");
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
				break;
			}
			case "Delete":{
				String Sid = request.getParameter("id");
				int id = Integer.parseInt(Sid);
				if(id>0) {
					ProductDto product = db.FindId(id);
					String imgFolderPath = request.getServletContext().getRealPath("/images/"+product.getImage());
					File img= new File(imgFolderPath);
					if( img.exists() ) {img.delete();}
					if(db.delete(id)) {
						session.setAttribute("messageS","Delete Product Successfull");
					}else {
						session.setAttribute("messageF","Error Cannot Delete Product");
					}
					response.sendRedirect("Controller");
				}
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + a);
			}
		}
	}

}
