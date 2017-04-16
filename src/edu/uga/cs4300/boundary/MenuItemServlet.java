package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;

import edu.uga.cs4300.logiclayer.CreateMenuItemController;
import edu.uga.cs4300.objectlayer.CustomizableItem;
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
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}
