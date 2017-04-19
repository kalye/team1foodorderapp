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
import edu.uga.cs4300.objectlayer.CustomizableItem;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet({ "/customizableitems" })
@MultipartConfig
public class CustomizableItemServlet extends BaseFoodOrderServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6487711272770412322L;
	
	private CreateMenuItemController createMenuItemController = CreateMenuItemController.getInstance();
	
	public CustomizableItemServlet(){
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
		long timestamp = System.currentTimeMillis();
		root.put("nocache", 0);
		if(isCreate || hasNoCache(request)){
			root.put("createOrUpdate", true);
			List<CustomizableItem> customizableItems = createMenuItemController.getAllCustomizableItems();
			root.put("customizableitems", customizableItems);
			root.put("hasCustomizableitems", CollectionUtils.isNotEmpty(customizableItems));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "customizableitems.ftl", root);
			return;
		}
		String updateId = (String) request.getParameter("updateId");
		if(StringUtils.isNumeric(updateId)){
			int id = Integer.parseInt(updateId);
			CustomizableItem customizableItem = createMenuItemController.getCustomizableItemById(id);
			root.put("createOrUpdate", true);
			if(customizableItem == null){
				root.put("error", true);
				root.put("message", "Error while searching for customizableItem with id " + id + ". Try again.");
				root.put("update", false);
			} else {
				root.put("update", true);
				root.put("customizableitem", customizableItem);
			}
			List<CustomizableItem> customizableItems = createMenuItemController.getAllCustomizableItems();
			root.put("customizableitems", customizableItems);
			root.put("hasCustomizableitems", CollectionUtils.isNotEmpty(customizableItems));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "customizableitems.ftl", root);
			return;
		}
		
		String deleteId = (String) request.getParameter("deleteId");
		if(StringUtils.isNumeric(deleteId)){
			int id = Integer.parseInt(deleteId);
			CustomizableItem customizableItem = createMenuItemController.getCustomizableItemById(id);
			root.put("createOrUpdate", true);
			if(customizableItem == null){
				root.put("error", true);
				root.put("message", "Error while searching for customizableItem with id " + id + ". Try again.");
			} else {
				int row = createMenuItemController.deleteCustomizableItem(customizableItem);
				if(row == 0){
					root.put("error", true);
					root.put("message", "Error while deleting customizableItem with id " + id + ". Try again.");
				}else {
					//help to eliminate recreate for every refresh
					root.put("nocache", timestamp);
				}
			}
			List<CustomizableItem> customizableItems = createMenuItemController.getAllCustomizableItems();
			root.put("customizableitems", customizableItems);
			root.put("hasCustomizableitems", CollectionUtils.isNotEmpty(customizableItems));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "customizableitems.ftl", root);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(hasNoCache(request)){
			doGet(request, response);
			return;
		}
		String add = (String) request.getParameter("add");
		boolean isAdd = "true".equals(add);
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		root.put("nocache", 0);
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
			List<CustomizableItem> customizableitems = createMenuItemController.getAllCustomizableItems();
			if(customizableitems != null && !customizableitems.isEmpty()){
				root.put("hasCustomizableitems", true);
			}
			root.put("customizableitems", customizableitems);
			renderTemplate(request, response, "customizableitems.ftl", root);
			return;
		} else {
			final Part filePart = request.getPart("file");
			String fileName = getFileName(filePart, "filename");
			String customizableitemName = request.getParameter("customizableitemName");
			String urlAsName = request.getParameter("url");
			String price = request.getParameter("price");
			if(urlAsName == null || "".equals(urlAsName)){
				urlAsName = fileName;
			}
			CustomizableItem customizableItem = new CustomizableItem(id, customizableitemName, urlAsName, new BigDecimal(price));
			saveImage(request, response, root, urlAsName);
			if(isCreate){
				id = createMenuItemController.createCustomizableItem(customizableItem);
			} else {
				id = createMenuItemController.updateCustomizableItem(customizableItem);
			}
			if(id == 0){
				root.put("message", "Error Creating customizableItem " + customizableItem.getName() + ".");
				root.put("error", true);
			} else {
				//help to eliminate recreate for every refresh
				root.put("nocache", System.currentTimeMillis());
			}
			root.put("createOrUpdate", true);
			List<CustomizableItem> toppings = createMenuItemController.getAllCustomizableItems();
			if (toppings != null && !toppings.isEmpty()) {
				root.put("hasCustomizableitems", true);
			}
			root.put("customizableitems", toppings);
			root.put("createsubmenu", true);
			renderTemplate(request, response, "customizableitems.ftl", root);
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
		String sideName = request.getParameter("customizableitemName");
		if(sideName == null || sideName.equals("")){
			root.put("message", "Customizable Item Name is required");
			root.put("error", true);
			return false;
		}
		String price = request.getParameter("price");
		try{
			Double.valueOf(price);
		} catch(Exception e){
			root.put("message", "Customizable Item price is not valid.");
			root.put("error", true);
			return false;
		}
		return true;
	}
}
