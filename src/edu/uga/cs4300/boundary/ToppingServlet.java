package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.math.BigDecimal;
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
import edu.uga.cs4300.objectlayer.Topping;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet({ "/toppings" })
@MultipartConfig
public class ToppingServlet extends BaseFoodOrderServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4718277299388664083L;
	
	private CreateMenuItemController createMenuItemController = CreateMenuItemController.getInstance();
	
	public ToppingServlet(){
		super();
	}
	
	public void init() {
		super.init();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String create = (String) request.getParameter("create");
		boolean isCreate = "true".equals(create);
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		//this variable will be used to avoid refresh;
		long timestamp = System.currentTimeMillis();
		root.put("nocache", timestamp);
		if(isCreate){
			root.put("createOrUpdate", true);
			List<Topping> toppings = createMenuItemController.getAllToppings();
			root.put("toppings", toppings);
			root.put("hasToppings", CollectionUtils.isNotEmpty(toppings));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "toppings.ftl", root);
			return;
		}
		String updateId = (String) request.getParameter("updateId");
		if(StringUtils.isNumeric(updateId)){
			int id = Integer.parseInt(updateId);
			Topping topping = createMenuItemController.getToppingById(id);
			root.put("createOrUpdate", true);
			if(topping == null){
				root.put("error", true);
				root.put("message", "Error while searching for topping with id " + id + ". Try again.");
				root.put("update", false);
			} else {
				root.put("update", true);
				root.put("topping", topping);
			}
			List<Topping> toppings = createMenuItemController.getAllToppings();
			root.put("toppings", toppings);
			root.put("hasToppings", CollectionUtils.isNotEmpty(toppings));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "toppings.ftl", root);
			return;
		}
		
		String deleteId = (String) request.getParameter("deleteId");
		if(StringUtils.isNumeric(deleteId)){
			int id = Integer.parseInt(deleteId);
			Topping topping = createMenuItemController.getToppingById(id);
			root.put("createOrUpdate", true);
			if(topping == null){
				root.put("error", true);
				root.put("message", "Error while searching for topping with id " + id + ". Try again.");
			} else {
				int row = createMenuItemController.deleteTopping(topping);
				if(row == 0){
					root.put("error", true);
					root.put("message", "Error while deleting topping with id " + id + ". Try again.");
				}
			}
			List<Topping> toppings = createMenuItemController.getAllToppings();
			root.put("toppings", toppings);
			root.put("hasToppings", CollectionUtils.isNotEmpty(toppings));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "toppings.ftl", root);
			return;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String add = (String) request.getParameter("add");
		boolean isAdd = "true".equals(add);
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		//this variable will be used to avoid refresh;
		long timestamp = System.currentTimeMillis();
		root.put("nocache", timestamp);
		if(isAdd){
			createOrUpdate(request, response, root, true, 0);
			return;
		}
		String update = (String) request.getParameter("update");
		boolean isUpdate = "true".equals(update);
		String id = request.getParameter("id");
		if(isUpdate && StringUtils.isNumeric(id)){
			createOrUpdate(request, response, root, false, Integer.parseInt(id));
			return;
		}
	}
	private void createOrUpdate(HttpServletRequest request, HttpServletResponse response, SimpleHash root, boolean isCreate, int id)
			throws IOException, ServletException {
		boolean isValidCatagory = validateRequest(request, response, root);
		if(!isValidCatagory){
			root.put("createOrUpdate", true);
			List<Topping> toppings = createMenuItemController.getAllToppings();
			if(toppings != null && !toppings.isEmpty()){
				root.put("hasToppings", true);
			}
			root.put("toppings", toppings);
			renderTemplate(request, response, "toppings.ftl", root);
			return;
		} else {
			final Part filePart = request.getPart("file");
			String fileName = getFileName(filePart, "filename");
			String toppingName = request.getParameter("toppingName");
			String urlAsName = request.getParameter("url");
			String price = request.getParameter("price");
			if(urlAsName == null || "".equals(urlAsName)){
				urlAsName = fileName;
			}
			Topping topping = new Topping(id, toppingName, urlAsName, new BigDecimal(price));
			saveImage(request, response, root, urlAsName);
			if(isCreate){
				id = createMenuItemController.createTopping(topping);
			} else {
				id = createMenuItemController.updateTopping(topping);
			}
			
			if(id == 0){
				root.put("message", "Error creating topping " + topping.getName() + ".");
				root.put("error", true);
			} 
			root.put("createOrUpdate", true);
			List<Topping> toppings = createMenuItemController.getAllToppings();
			if (toppings != null && !toppings.isEmpty()) {
				root.put("hasToppings", true);
			}
			root.put("toppings", toppings);
			root.put("createsubmenu", true);
			renderTemplate(request, response, "toppings.ftl", root);
			return;
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
		String toppingName = request.getParameter("toppingName");
		if(toppingName == null || toppingName.equals("")){
			root.put("message", "Topping Name is required");
			root.put("error", true);
			return false;
		}
		String price = request.getParameter("price");
		try{
			Double.valueOf(price);
		} catch(Exception e){
			root.put("message", "Topping price is not valid.");
			root.put("error", true);
			return false;
		}
		return true;
	}

}
