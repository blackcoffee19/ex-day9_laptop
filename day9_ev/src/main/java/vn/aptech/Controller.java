package vn.aptech;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.aptech.dao.LaptopDao;
import vn.aptech.dto.ProductDto;
import vn.aptech.entity.Laptop;

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
    LaptopDao ldao = new LaptopDao();
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
    //Bai tap ve Nha: thu muc laptop la thu muc bao ve
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String a = request.getParameter("a");
		HttpSession session = request.getSession();
		if(a==null || a.isEmpty()) {
			request.setAttribute("laptops", ldao.findAll());
			request.getRequestDispatcher("/laptop/index.jsp").forward(request, response);
		}else {
			switch (a) {
			case "DisplayCreate": {
				request.getRequestDispatcher("/laptop/create.jsp").forward(request, response);
				break;
			}
			case "search": {

				String min = request.getParameter("min");
				String max = request.getParameter("max");
				if(min.isEmpty() || max.isEmpty()) {
					response.sendRedirect("Controller");
					return;
				}
				request.setAttribute("min", min);
				request.setAttribute("max", max);			
				request.setAttribute("laptops", ldao.findByPrice(Integer.parseInt(min), Integer.parseInt(max)));
				request.getRequestDispatcher("/laptop/index.jsp").forward(request, response);
				break;
			}
			case "DisplayUpdate": {
				String idS = request.getParameter("b");
				int id = 0;
				if(idS.isEmpty()) {
					response.sendRedirect("Controller");
					return;
				}
				try {
					id = Integer.parseInt(idS);
					
				}catch (Exception e) {
					e.printStackTrace();
					response.sendRedirect("Controller");
					return;
				}
				Laptop laptop = ldao.findById(id);
				if(laptop.getId()>0) {
					request.setAttribute("laptop", laptop);
					request.getRequestDispatcher("/laptop/update.jsp").forward(request, response);
				}else {
					response.sendRedirect("Controller");
					return;
				}
				break;
			}
			default:
				request.setAttribute("laptops", ldao.findAll());
				request.getRequestDispatcher("/laptop/index.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String a = request.getParameter("a");
		HttpSession session = request.getSession();
		if(a==null || a.isEmpty()) {
			response.sendRedirect("Controller");
			return ;
		}else {
			switch (a) {
			case "Create": {
				String name= request.getParameter("name");
				String priceS = request.getParameter("price");
				String despciption = request.getParameter("description");
				int price =0 ;
				try {
					price = Integer.parseInt(priceS);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Part part = request.getPart("photo");
				String filename = Path.of(part.getSubmittedFileName()).getFileName().toString();
				if (!filename.isEmpty()) {
					String imgFolderPath = request.getServletContext().getRealPath("/images");
					
					File imgfolder = new File(imgFolderPath);
					if(!Files.exists(Path.of(imgFolderPath))) {
						Files.createDirectory(Path.of(imgFolderPath));
					}
					part.write(imgFolderPath+"/"+filename);	
				}
				Laptop lap = new Laptop();
				lap.setName(name);
				lap.setPrice(price);
				lap.setDescription(despciption);
				lap.setImage(filename);
				if(ldao.create(lap)) {
					session.setAttribute("msg", "Create Successfully");
					response.sendRedirect("Controller");
					return;
				}else {
					session.setAttribute("error", "Error Create new Laptop Failure");
					request.getRequestDispatcher("/laptop/create.jsp").forward(request, response);
				}
				break;
			}
			case "Update": {
				String idS = request.getParameter("id");
				String name= request.getParameter("name");
				String priceS = request.getParameter("price");
				String despciption = request.getParameter("description");
				String image = request.getParameter("image");
				int price =0,id =0;
				
				try {
					id = Integer.parseInt(idS);
					price = Integer.parseInt(priceS);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String filename = "";		
				Part part = request.getPart("photo");
				filename = Path.of(part.getSubmittedFileName()).getFileName().toString();
				if (!filename.isEmpty()) {
					try {
						String imgFolderPath = request.getServletContext().getRealPath("/images");
						File imgfolder = new File(imgFolderPath);
						if(!Files.exists(Path.of(imgFolderPath))) {
							Files.createDirectory(Path.of(imgFolderPath));
						}
						part.write(imgFolderPath+"/"+filename);													
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						if(image!=null) {
							String imgPath = request.getServletContext().getRealPath("/images/"+image);
							File img= new File(imgPath);
							if( img.exists() ) {img.delete();}							
						}
					}
				}
				Laptop lap = new Laptop();
				lap.setId(id);
				lap.setName(name);
				lap.setPrice(price);
				lap.setDescription(despciption);
				lap.setImage(filename.isEmpty()?image:filename);
				if(ldao.update(lap)) {
					session.setAttribute("msg", "Update Successfully");
					response.sendRedirect("Controller");
					return;
				}else {
					session.setAttribute("error", "Error Update Laptop Failure");
					request.getRequestDispatcher("/laptop/update.jsp").forward(request, response);
				}
				break;
			}
			case "Delete": {
				String idS = request.getParameter("id");
				String image= request.getParameter("image");
				int id =0;
				try {
					id = Integer.parseInt(idS);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//Delete Image
				if(image!=null && !image.isEmpty()) {
					String imgFolderPath = request.getServletContext().getRealPath("/images/"+image);
					File img= new File(imgFolderPath);
					if( img.exists() ) {img.delete();}					
				}
				if(ldao.delete(id)) {
					session.setAttribute("msg", "Delete Successfully");
					response.sendRedirect("Controller");
					return;
				}else {
					session.setAttribute("error", "Error Delete Laptop Failure");
					request.getRequestDispatcher("/laptop/index.jsp").forward(request, response);
				}
				break;
			}
			default:
				response.sendRedirect("Controller");
				return;
			}
		}
	}

}
