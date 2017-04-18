package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import edu.uga.cs4300.logiclayer.CreateMenuItemController;
import edu.uga.cs4300.objectlayer.MenuCategory;
import edu.uga.cs4300.objectlayer.Topping;
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
				root.put("hasCategory", true);
			}
			root.put("catagories", catagories);
			renderTemplate(request, response, "catagories.ftl", root);
			return;
		}
		String updateId = (String) request.getParameter("updateId");
		if(StringUtils.isNumeric(updateId)){
			int id = Integer.parseInt(updateId);
			MenuCategory menuCategory = createMenuItemController.getCategoryById(id);
			root.put("createOrUpdate", true);
			if(menuCategory == null){
				root.put("error", true);
				root.put("message", "Error while searching for menuCategory with id " + id + ". Try again.");
				root.put("update", false);
			} else {
				root.put("update", true);
				root.put("category", menuCategory);
			}
			List<MenuCategory> catagories = createMenuItemController.getAllCatagories();
			root.put("catagories", catagories);
			root.put("hasCategory", CollectionUtils.isNotEmpty(catagories));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "catagories.ftl", root);
			return;
		}
		
		String deleteId = (String) request.getParameter("deleteId");
		if(StringUtils.isNumeric(deleteId)){
			int id = Integer.parseInt(deleteId);
			MenuCategory menuCategory = createMenuItemController.getCategoryById(id);
			root.put("createOrUpdate", true);
			if(menuCategory == null){
				root.put("error", true);
				root.put("message", "Error while searching for menu category with id " + id + ". Try again.");
			} else {
				int row = createMenuItemController.deleteCategory(menuCategory);
				if(row == 0){
					root.put("error", true);
					root.put("message", "Error while deleting menu category with id " + id + ". Try again.");
				}
			}
			List<MenuCategory> catagories = createMenuItemController.getAllCatagories();
			root.put("catagories", catagories);
			root.put("hasCategory", CollectionUtils.isNotEmpty(catagories));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "catagories.ftl", root);
			return;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String query = (String) request.getParameter("add");
		boolean isAdd = query != null && query.equalsIgnoreCase("true") ? true : false;
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		if(isAdd){
			createOrUpdate(request, response, root, true, 0);
			return;
		}
		String update = (String) request.getParameter("update");
		boolean isUpdate = "true".equals(update);
		String id = request.getParameter("id");
		if(isUpdate && StringUtils.isNumeric(id)){
			createOrUpdate(request, response, root, true, Integer.parseInt(id));
			return;
		}
		
	}
	private void createOrUpdate(HttpServletRequest request, HttpServletResponse response, SimpleHash root, boolean isCreate, int id)
			throws IOException, ServletException {
		boolean isValidCatagory = validateRequest(request, response, root);
		if(!isValidCatagory){
			root.put("createOrUpdate", true);
			List<MenuCategory> catagories = createMenuItemController.getAllCatagories();
			if(catagories != null && !catagories.isEmpty()){
				root.put("hasCategory", true);
			}
			root.put("catagories", catagories);
			renderTemplate(request, response, "catagories.ftl", root);
			return;
		} else {
			final Part filePart = request.getPart("file");
			String fileName = getFileName(filePart, "filename");
			String catagoryName = request.getParameter("catagoryName");
			String urlAsName = request.getParameter("url");
			if(urlAsName == null || "".equals(urlAsName)){
				urlAsName = fileName;
			}
			MenuCategory menuCategory = new MenuCategory(id, catagoryName, urlAsName);
			saveImage(request, response, root, urlAsName);
			if(isCreate){
				id = createMenuItemController.createCategory(menuCategory);
			} else {
				id = createMenuItemController.updateCategory(menuCategory);
			}
			if(id == 0){
				root.put("createOrUpdate", true);
				List<MenuCategory> catagories = createMenuItemController.getAllCatagories();
				if(catagories != null && !catagories.isEmpty()){
					root.put("hasCategory", true);
				}
				root.put("catagories", catagories);
				renderTemplate(request, response, "catagories.ftl", root);
				return;
			} else {
				root.put("createsubmenu", true);
				renderTemplate(request, response, "catagories.ftl", root);
				return;
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
