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
import edu.uga.cs4300.objectlayer.MenuItem;
import edu.uga.cs4300.objectlayer.Side;
import edu.uga.cs4300.objectlayer.Topping;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet({ "/menuitems" })
@MultipartConfig
public class MenuItemServlet extends BaseFoodOrderServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4835160862898035346L;
	
	private CreateMenuItemController createMenuItemController = CreateMenuItemController.getInstance();
	
	public MenuItemServlet(){
		super();
	}
	
	public void init(){
		super.init();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String create = (String) request.getParameter("create");
		boolean isCreate = "true".equals(create);
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		if(isCreate){
			root.put("addmenuitem", true);
			root.put("catagories", createMenuItemController.getAllCatagories());
			List<Side> sides = createMenuItemController.getAllSides();
			root.put("atleastoneside", CollectionUtils.isNotEmpty(sides));
			root.put("sides", sides);
			List<Topping> toppings = createMenuItemController.getAllToppings();
			root.put("atleastonetopping", CollectionUtils.isNotEmpty(toppings));
			root.put("toppings", toppings);
			List<CustomizableItem> customizableItems = createMenuItemController.getAllCustomizableItems();
			root.put("atleastonecustomizableitem", CollectionUtils.isNotEmpty(customizableItems));
			root.put("customizableitems", customizableItems);
			root.put("createsubmenu", true);
			renderTemplate(request, response, "menuitems.ftl", root);
			return;
		}
		String updateId = (String) request.getParameter("updateId");
		if(StringUtils.isNumeric(updateId)){
			int id = Integer.parseInt(updateId);
			MenuItem menuitem = createMenuItemController.getMenuItemById(id);
			if(menuitem == null){
				root.put("error", true);
				root.put("message", "Error while searching for menuitem with id " + id + ". Try again.");
				root.put("updateMenuItem", false);
			} else {
				root.put("updateMenuItem", true);
				root.put("menuitem", menuitem);
			}
			List<MenuItem> menuitems = createMenuItemController.getAllMenuItems();
			root.put("menuitems", menuitems);
			root.put("hasOneMoreMenuItem", CollectionUtils.isNotEmpty(menuitems));
			root.put("catagories", createMenuItemController.getAllCatagories());
			List<Side> sides = createMenuItemController.getAllSides();
			updateSelectedSides(sides, menuitem.getSides());
			root.put("atleastoneside", CollectionUtils.isNotEmpty(sides));
			root.put("sides", sides);
			List<Topping> toppings = createMenuItemController.getAllToppings();
			updateSelectedToppings(toppings, menuitem.getToppings());
			root.put("atleastonetopping", CollectionUtils.isNotEmpty(toppings));
			root.put("toppings", toppings);
			List<CustomizableItem> customizableItems = createMenuItemController.getAllCustomizableItems();
			updateSelectedCustomizableItems(customizableItems, menuitem.getCustomizableItems());
			root.put("atleastonecustomizableitem", CollectionUtils.isNotEmpty(customizableItems));
			root.put("customizableitems", customizableItems);
			root.put("createsubmenu", true);
			renderTemplate(request, response, "menuitems.ftl", root);
			return;
		}
		String deleteId = (String) request.getParameter("deleteId");
		if(StringUtils.isNumeric(deleteId)){
			int id = Integer.parseInt(deleteId);
			MenuItem menuitem = createMenuItemController.getMenuItemById(id);
			if(menuitem == null){
				root.put("error", true);
				root.put("message", "Error while searching for menuitem with id " + id + ". Try again.");
				root.put("updateMenuItem", false);
			} else {
				int row = createMenuItemController.deleteMenuItem(menuitem);
				if(row == 0){
					root.put("error", true);
					root.put("message", "Error while deleting menuitem with id " + id + ". Try again.");
				}
			}
			root.put("addmenuitem", true);
			List<MenuItem> menuitems = createMenuItemController.getAllMenuItems();
			root.put("menuitems", menuitems);
			root.put("hasOneMoreMenuItem", CollectionUtils.isNotEmpty(menuitems));
			root.put("catagories", createMenuItemController.getAllCatagories());
			List<Side> sides = createMenuItemController.getAllSides();
			root.put("atleastoneside", CollectionUtils.isNotEmpty(sides));
			root.put("sides", sides);
			List<Topping> toppings = createMenuItemController.getAllToppings();
			root.put("atleastonetopping", CollectionUtils.isNotEmpty(toppings));
			root.put("toppings", toppings);
			List<CustomizableItem> customizableItems = createMenuItemController.getAllCustomizableItems();
			root.put("atleastonecustomizableitem", CollectionUtils.isNotEmpty(customizableItems));
			root.put("customizableitems", customizableItems);
			root.put("createsubmenu", true);
			renderTemplate(request, response, "menuitems.ftl", root);
			return;
		}
		
	}
	private void updateSelectedSides(List<Side> sides, List<Side> sides2) {
		if(CollectionUtils.isNotEmpty(sides) && CollectionUtils.isNotEmpty(sides2)){
			for(Side side: sides){
				for(Side side2: sides2){
					if(side.getId() == side2.getId()){
						side.setSelected(true);
					}
				}
			}
		}
	}

	private void updateSelectedToppings(List<Topping> toppings, List<Topping> toppings2) {
		if(CollectionUtils.isNotEmpty(toppings) && CollectionUtils.isNotEmpty(toppings2)){
			for(Topping topping: toppings){
				for(Topping topping2: toppings2){
					if(topping.getId() == topping2.getId()){
						topping.setSelected(true);
					}
				}
			}
		}
	}
	private void updateSelectedCustomizableItems(List<CustomizableItem> customizableItems, List<CustomizableItem> customizableItems2) {
		if(CollectionUtils.isNotEmpty(customizableItems) && CollectionUtils.isNotEmpty(customizableItems2)){
			for(CustomizableItem customizableItem: customizableItems){
				for(CustomizableItem customizableItem2: customizableItems2){
					if(customizableItem.getId() == customizableItem2.getId()){
						customizableItem.setSelected(true);
					}
				}
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}
