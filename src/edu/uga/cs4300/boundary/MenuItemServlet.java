package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

	public MenuItemServlet() {
		super();
	}

	public void init() {
		super.init();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String create = (String) request.getParameter("create");
		boolean isCreate = "true".equals(create);
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		long timestamp = System.currentTimeMillis();
		root.put("nocache", timestamp);
		if (isCreate || hasNoCache(request)) {
			List<MenuItem> menuitems = createMenuItemController.getAllMenuItems();
			root.put("menuitems", menuitems);
			root.put("hasOneMoreMenuItem", CollectionUtils.isNotEmpty(menuitems));
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
		if (StringUtils.isNumeric(updateId)) {
			int id = Integer.parseInt(updateId);
			MenuItem menuitem = createMenuItemController.getMenuItemById(id);
			if (menuitem == null) {
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
		if (StringUtils.isNumeric(deleteId)) {
			int id = Integer.parseInt(deleteId);
			MenuItem menuitem = createMenuItemController.getMenuItemById(id);
			if (menuitem == null) {
				root.put("error", true);
				root.put("message", "Error while searching for menuitem with id " + id + ". Try again.");
				root.put("updateMenuItem", false);
			} else {
				int row = createMenuItemController.deleteMenuItem(menuitem);
				if (row == 0) {
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
		if (CollectionUtils.isNotEmpty(sides) && CollectionUtils.isNotEmpty(sides2)) {
			for (Side side : sides) {
				for (Side side2 : sides2) {
					if (side.getId() == side2.getId()) {
						side.setSelected(true);
					}
				}
			}
		}
	}

	private void updateSelectedToppings(List<Topping> toppings, List<Topping> toppings2) {
		if (CollectionUtils.isNotEmpty(toppings) && CollectionUtils.isNotEmpty(toppings2)) {
			for (Topping topping : toppings) {
				for (Topping topping2 : toppings2) {
					if (topping.getId() == topping2.getId()) {
						topping.setSelected(true);
					}
				}
			}
		}
	}

	private void updateSelectedCustomizableItems(List<CustomizableItem> customizableItems,
			List<CustomizableItem> customizableItems2) {
		if (CollectionUtils.isNotEmpty(customizableItems) && CollectionUtils.isNotEmpty(customizableItems2)) {
			for (CustomizableItem customizableItem : customizableItems) {
				for (CustomizableItem customizableItem2 : customizableItems2) {
					if (customizableItem.getId() == customizableItem2.getId()) {
						customizableItem.setSelected(true);
					}
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (hasNoCache(request)) {
			doGet(request, response);
			return;
		}
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		long timestamp = System.currentTimeMillis();
		root.put("nocache", 0);
		String add = (String) request.getParameter("add");
		boolean isAdd = "true".equals(add);
		// this variable will be used to avoid refresh;
		if (isAdd) {
			createOrUpdate(request, response, root, true, 0);
			return;
		}
		String update = (String) request.getParameter("update");
		boolean isUpdate = "true".equals(update);
		String id = request.getParameter("id");
		if (isUpdate && StringUtils.isNumeric(id)) {
			createOrUpdate(request, response, root, false, Integer.parseInt(id));
			return;
		}
	}

	private void createOrUpdate(HttpServletRequest request, HttpServletResponse response, SimpleHash root,
			boolean isCreate, int id) throws IOException, ServletException {
		boolean isValidMenuItem = validateRequest(request, response, root);
		if (!isValidMenuItem) {
			List<MenuItem> menuitems = createMenuItemController.getAllMenuItems();
			root.put("menuitems", menuitems);
			root.put("hasOneMoreMenuItem", CollectionUtils.isNotEmpty(menuitems));
			if (isCreate) {
				root.put("addmenuitem", true);
			} else {
				MenuItem menuitem = createMenuItemController.getMenuItemById(id);
				if (menuitem == null) {
					root.put("error", true);
					root.put("message", "Error while searching for menuitem with id " + id + ". Try again.");
					root.put("updateMenuItem", false);
				} else {
					root.put("updateMenuItem", true);
					root.put("menuitem", menuitem);
				}
			}
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
		} else {
			final Part filePart = request.getPart("file");
			String fileName = getFileName(filePart, "filename");
			MenuItem newMenuItem = getMenuItem(request);
			String urlAsName = request.getParameter("url");
			if(urlAsName == null || "".equals(urlAsName)){
				urlAsName = fileName;
			}
			newMenuItem.setImageUrl(urlAsName);
			saveImage(request, response, root, urlAsName);
			if (isCreate) {
				id = createMenuItemController.createMenuItem(newMenuItem);
			} else if (id > 0) {
				newMenuItem.setId(id);
				List<Topping> toppingsToBeAddToMenu = new ArrayList<>();
				List<Topping> toppingsTobeRemovedFromMenu = new ArrayList<>();
				List<Side> sidesToBeAddedToMenu = new ArrayList<>();
				List<Side> sidesToBeRemovedFromMenu = new ArrayList<>();
				List<CustomizableItem> customizableItemToBeAddedToMenu = new ArrayList<>();
				List<CustomizableItem> customizableItemToBeRemovedFromMenu = new ArrayList<>();
				MenuItem oldMenuItem = createMenuItemController.getMenuItemById(id);
				if (oldMenuItem == null) {
					createMenuItemController.createMenuItem(newMenuItem);
				} else {
					updateItemsTobeAddedAndRemoved(newMenuItem, oldMenuItem, toppingsToBeAddToMenu,
							toppingsTobeRemovedFromMenu, sidesToBeAddedToMenu, sidesToBeRemovedFromMenu,
							customizableItemToBeAddedToMenu, customizableItemToBeRemovedFromMenu);
					id = createMenuItemController.updateMenuItem(newMenuItem, toppingsToBeAddToMenu,
							toppingsTobeRemovedFromMenu, sidesToBeAddedToMenu, sidesToBeRemovedFromMenu,
							customizableItemToBeAddedToMenu, customizableItemToBeRemovedFromMenu);
				}
				if(id == 0){
					root.put("message", "Error Creating Menu Item " + newMenuItem.getName() + ".");
					root.put("error", true);
				}
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

	private void updateItemsTobeAddedAndRemoved(MenuItem newMenuItem, MenuItem oldMenuItem,
			List<Topping> toppingsToBeAddToMenu, List<Topping> toppingsTobeRemovedFromMenu,
			List<Side> sidesToBeAddedToMenu, List<Side> sidesToBeRemovedFromMenu,
			List<CustomizableItem> customizableItemToBeAddedToMenu,
			List<CustomizableItem> customizableItemToBeRemovedFromMenu) {
			//customizableItem
			if(!oldMenuItem.isCustomizable() && newMenuItem.isCustomizable()){
				customizableItemToBeAddedToMenu.addAll(newMenuItem.getCustomizableItems());
			} else if(oldMenuItem.isCustomizable() && !newMenuItem.isCustomizable()){
				customizableItemToBeRemovedFromMenu.addAll(oldMenuItem.getCustomizableItems());
			} else {
				updateCustomizableItemsTobeAddedAndRemoved(newMenuItem, oldMenuItem, customizableItemToBeAddedToMenu, customizableItemToBeRemovedFromMenu);
			}
			//sides
			if(!oldMenuItem.isHasSide() && newMenuItem.isHasSide()){
				sidesToBeAddedToMenu.addAll(newMenuItem.getSides());
			} else if(oldMenuItem.isHasSide() && !newMenuItem.isHasSide()){
				sidesToBeRemovedFromMenu.addAll(oldMenuItem.getSides());
			} else {
				updateSidesTobeAddedAndRemoved(newMenuItem, oldMenuItem, sidesToBeAddedToMenu, sidesToBeRemovedFromMenu);
			}
			
			//toppings
			if(!oldMenuItem.isHasToppings() && newMenuItem.isHasToppings()){
				toppingsToBeAddToMenu.addAll(newMenuItem.getToppings());
			} else if(oldMenuItem.isHasSide() && !newMenuItem.isHasSide()){
				toppingsTobeRemovedFromMenu.addAll(oldMenuItem.getToppings());
			} else {
				updateToppingsTobeAddedAndRemoved(newMenuItem, oldMenuItem, toppingsToBeAddToMenu, toppingsTobeRemovedFromMenu);
			}
	}

	private void updateToppingsTobeAddedAndRemoved(MenuItem newMenuItem, MenuItem oldMenuItem,
			List<Topping> toppingsToBeAddToMenu, List<Topping> toppingsTobeRemovedFromMenu) {
		if(CollectionUtils.isNotEmpty(newMenuItem.getToppings())){
			for(Topping toppingNew: newMenuItem.getToppings()){
				boolean found = false;
				for(Topping toppingOld: oldMenuItem.getToppings()){
					if(toppingOld.getId() == toppingNew.getId()){
						found = true;
					}
				}
				if(!found){
					toppingsToBeAddToMenu.add(toppingNew);
				}
			}
		}
		if(CollectionUtils.isNotEmpty(oldMenuItem.getToppings())){
			for(Topping toppingOld: oldMenuItem.getToppings()){
				boolean found = false;
				for(Topping toppingNew: newMenuItem.getToppings()){
					if(toppingOld.getId() == toppingNew.getId()){
						found = true;
					}
				}
				if(!found){
					toppingsTobeRemovedFromMenu.add(toppingOld);
				}
			}
		}
		
	}

	private void updateSidesTobeAddedAndRemoved(MenuItem newMenuItem, MenuItem oldMenuItem,
			List<Side> sidesToBeAddToMenu, List<Side> sidesTobeRemovedFromMenu) {
		
		if(CollectionUtils.isNotEmpty(newMenuItem.getSides())){
			for(Side sideNew: newMenuItem.getSides()){
				boolean found = false;
				for(Side sideOld: oldMenuItem.getSides()){
					if(sideOld.getId() == sideNew.getId()){
						found = true;
					}
				}
				if(!found){
					sidesToBeAddToMenu.add(sideNew);
				}
			}
		}
		if(CollectionUtils.isNotEmpty(oldMenuItem.getSides())){
			for(Side sideOld: oldMenuItem.getSides()){
				boolean found = false;
				for(Side sideNew: newMenuItem.getSides()){
					if(sideOld.getId() == sideNew.getId()){
						found = true;
					}
				}
				if(!found){
					sidesTobeRemovedFromMenu.add(sideOld);
				}
			}
		}
		
	}
	private void updateCustomizableItemsTobeAddedAndRemoved(MenuItem newMenuItem, MenuItem oldMenuItem,
			List<CustomizableItem> customizableItemsToBeAddToMenu, List<CustomizableItem> CustomizableItemsTobeRemovedFromMenu) {
		for(CustomizableItem customizableItemNew: newMenuItem.getCustomizableItems()){
			boolean found = false;
			for(CustomizableItem customizableItemOld: oldMenuItem.getCustomizableItems()){
				if(customizableItemOld.getId() == customizableItemNew.getId()){
					found = true;
				}
			}
			if(!found){
				customizableItemsToBeAddToMenu.add(customizableItemNew);
			}
		}
		for(CustomizableItem customizableItemOld: oldMenuItem.getCustomizableItems()){
			boolean found = false;
			for(CustomizableItem customizableItemNew: newMenuItem.getCustomizableItems()){
				if(customizableItemOld.getId() == customizableItemNew.getId()){
					found = true;
				}
			}
			if(!found){
				CustomizableItemsTobeRemovedFromMenu.add(customizableItemOld);
			}
		}
		
	}
	private MenuItem getMenuItem(HttpServletRequest request) {
		MenuItem menuItem = new MenuItem();
		String menuName = request.getParameter("name");
		String description = request.getParameter("description");
		menuItem.setDescription(description);
		menuItem.setName(menuName);
		String catId = request.getParameter("cats");
		menuItem.setCategoryId(Integer.parseInt(catId));
		String hasSideSting = request.getParameter("hasside");
		boolean hasSide = "yes".equals(hasSideSting);
		menuItem.setHasSide(hasSide);
		String hasToppingsSting = request.getParameter("hastoppings");
		boolean hasToppings = "yes".equals(hasToppingsSting);
		menuItem.setHasToppings(hasToppings);
		String hasCustomizableItemsSting = request.getParameter("hascustomizableitems");
		boolean hasCustomizableItems = "yes".equals(hasCustomizableItemsSting);
		menuItem.setCustomizable(hasCustomizableItems);
		updateMenuRelatedItem(menuItem, request);
		String price = request.getParameter("price");
		menuItem.setPrice(new BigDecimal(price));
		return menuItem;
	}

	private void updateMenuRelatedItem(MenuItem menuItem, HttpServletRequest request) {
		if(menuItem.isCustomizable()){
			String[] customizableitemsformenu = request.getParameterValues("customizableitemsformenu");
			if(menuItem.getCustomizableItems() == null){
				menuItem.setCustomizableItems(new ArrayList<>());
			}
			for(String customizableitemformenu: customizableitemsformenu){
				menuItem.getCustomizableItems().add(new CustomizableItem(Integer.parseInt(customizableitemformenu), null, null, null));
			}
		}
		if(menuItem.isHasSide()){
			String[] sides = request.getParameterValues("sidesformenu");
			if(menuItem.getSides() == null){
				menuItem.setSides(new ArrayList<>());
			}
			for(String side: sides){
				menuItem.getSides().add(new Side(Integer.parseInt(side), null, null, null));
			}
		}
		if(menuItem.isHasToppings()){
			String[] toppings = request.getParameterValues("toppingsformenu");
			if(menuItem.getToppings() == null){
				menuItem.setToppings(new ArrayList<>());
			}
			for(String topping: toppings){
				menuItem.getToppings().add(new Topping(Integer.parseInt(topping), null, null, null));
			}
		}
	}

	private boolean validateRequest(HttpServletRequest request, HttpServletResponse response, SimpleHash root) throws IOException, ServletException {
		String catId = request.getParameter("cats");
		String menuName = request.getParameter("name");
		if(menuName == null || menuName.equals("")){
			root.put("message", "Menu Name is required");
			root.put("error", true);
			return false;
		}
		final Part filePart = request.getPart("file");
		String fileName = getFileName(filePart, "filename");
		
		if(fileName == null || fileName.equals("")){
			root.put("message", "Choose image file.");
			root.put("error", true);
			return false;
		}
		if(StringUtils.isBlank(catId)){
			root.put("error", true);
			root.put("message", "Category is required");
			return false;
		}
		String hasSideSting = request.getParameter("hasside");
		boolean hasSide = "yes".equals(hasSideSting);
		if(hasSide){
			String[] sides = request.getParameterValues("sidesformenu");
			if(sides == null || sides.length == 0){
				root.put("error", true);
				root.put("message", "If you choose the item has sides you have to choose at least one side item for it. ");
				return false;
			}
		}
		String hasToppingsSting = request.getParameter("hastoppings");
		boolean hasToppings = "yes".equals(hasToppingsSting);
		if(hasToppings){
			String[] toppings = request.getParameterValues("toppingsformenu");
			if(toppings == null || toppings.length == 0){
				root.put("error", true);
				root.put("message", "If you choose the item has toppings you have to choose at least one topping item for it. ");
				return false;
			}
			
		}
		String hasCustomizableItemsSting = request.getParameter("hascustomizableitems");
		boolean hasCustomizableItems = "yes".equals(hasCustomizableItemsSting);
		if(hasCustomizableItems){
			String[] customizableitemsformenu = request.getParameterValues("customizableitemsformenu");
			if(customizableitemsformenu == null || customizableitemsformenu.length == 0){
				root.put("error", true);
				root.put("message", "If you choose the item is customizable you have to choose at least one customizable item for it. ");
				return false;
			}
		}
		String price = request.getParameter("price");
		try{
			Double.valueOf(price);
		}catch(Exception e){
			root.put("error", true);
			root.put("message",
					"Wrong price formate.");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
