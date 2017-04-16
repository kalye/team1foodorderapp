package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		if(isCreate){
			root.put("createOrUpdate", true);
			List<CustomizableItem> customizableItems = createMenuItemController.getAllCustomizableItems();
			root.put("customizableitems", customizableItems);
			root.put("hasCustomizableitems", CollectionUtils.isNotEmpty(customizableItems));
			root.put("createsubmenu", true);
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
				root.put("customizableItem", customizableItem);
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
		
	}
}
