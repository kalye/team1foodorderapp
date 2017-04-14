package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import edu.uga.cs4300.logiclayer.CreateMenuItemController;
import edu.uga.cs4300.objectlayer.MenuCategory;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet({ "/catagories" })
@MultipartConfig
public class CatagoryServlet extends BaseFoodOrderServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7122341720546534417L;
	
	private CreateMenuItemController createMenuItemController = CreateMenuItemController.getInstance();
	
	public CatagoryServlet(){
		super();
	}
	public void init(){
		super.init();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String query = (String) request.getParameter("createOrUpdate");
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		if(query != null){
			root.put("createOrUpdate", true);
			List<MenuCategory> catagories = createMenuItemController.getAllCatagories();
			if(catagories != null && !catagories.isEmpty()){
				root.put("hasCatagory", true);
			}
			root.put("catagories", catagories);
			renderTemplate(request, response, "catagories.ftl", root);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String query = (String) request.getParameter("add");
		boolean isAdd = query != null && query.equalsIgnoreCase("true") ? true : false;
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		if(isAdd){
			boolean isValidCatagory = validateRequest(request, response, root);
			if(!isValidCatagory){
				root.put("createOrUpdate", true);
				List<MenuCategory> catagories = createMenuItemController.getAllCatagories();
				if(catagories != null && !catagories.isEmpty()){
					root.put("hasCatagory", true);
				}
				root.put("catagories", catagories);
				renderTemplate(request, response, "catagories.ftl", root);
				return;
			} else {
				final Part filePart = request.getPart("file");
				String fileName = getFileName(filePart, "filename");
				String catagoryName = request.getParameter("catagoryName");
				String urlAsName = request.getParameter("name");
				if(urlAsName == null || "".equals(urlAsName)){
					urlAsName = fileName;
				}
				MenuCategory menuCategory = new MenuCategory(0, catagoryName, urlAsName);
				saveImage(request, response, root);
				int id = createMenuItemController.createCategory(menuCategory);
				if(id == 0){
					root.put("createOrUpdate", true);
					List<MenuCategory> catagories = createMenuItemController.getAllCatagories();
					if(catagories != null && !catagories.isEmpty()){
						root.put("hasCatagory", true);
					}
					root.put("catagories", catagories);
					renderTemplate(request, response, "catagories.ftl", root);
					return;
				} else {
					
				}
				
			}
			
		}
		
	}
	private boolean validateRequest(HttpServletRequest request, HttpServletResponse response, SimpleHash root) throws IllegalStateException, IOException, ServletException {
		
		final Part filePart = request.getPart("file");
		String fileName = getFileName(filePart, "filename");
		
		if(fileName == null || fileName.equals("")){
			root.put("message", "Choose image file.");
			root.put("error", true);
			return false;
		}
		String catagoryName = request.getParameter("catagoryName");
		if(catagoryName == null || catagoryName.equals("")){
			root.put("message", "Catagory Name is required");
			root.put("error", true);
			return false;
		}
		
		
		
		return true;
	}

}
