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
import edu.uga.cs4300.objectlayer.MenuCategory;
import edu.uga.cs4300.objectlayer.Side;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

@WebServlet({ "/sides" })
@MultipartConfig
public class SidesServlet extends BaseFoodOrderServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2884859222596350374L;
	
	private CreateMenuItemController createMenuItemController = CreateMenuItemController.getInstance();
	
	public SidesServlet(){
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
			List<Side> sides = createMenuItemController.getAllSides();
			root.put("sides", sides);
			root.put("hasSides", CollectionUtils.isNotEmpty(sides));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "sides.ftl", root);
			return;
		}
		String updateId = (String) request.getParameter("updateId");
		if(StringUtils.isNumeric(updateId)){
			int id = Integer.parseInt(updateId);
			Side side = createMenuItemController.getSideById(id);
			root.put("createOrUpdate", true);
			if(side == null){
				root.put("error", true);
				root.put("message", "Error while searching for side id " + id + ". Try again.");
				root.put("update", false);
			} else {
				root.put("update", true);
				root.put("side", side);
			}
			List<Side> sides = createMenuItemController.getAllSides();
			root.put("sides", sides);
			root.put("hasSides", CollectionUtils.isNotEmpty(sides));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "sides.ftl", root);
			return;
		}
		
		String deleteId = (String) request.getParameter("deleteId");
		if(StringUtils.isNumeric(deleteId)){
			int id = Integer.parseInt(deleteId);
			Side side = createMenuItemController.getSideById(id);
			root.put("createOrUpdate", true);
			if(side == null){
				root.put("error", true);
				root.put("message", "Error while searching for side with id " + id + ". Try again.");
			} else {
				int row = createMenuItemController.deleteSide(side);
				if(row == 0){
					root.put("error", true);
					root.put("message", "Error while deleting side with id " + id + ". Try again.");
				}
			}
			List<Side> sides = createMenuItemController.getAllSides();
			root.put("sides", sides);
			root.put("hasSides", CollectionUtils.isNotEmpty(sides));
			root.put("createsubmenu", true);
			renderTemplate(request, response, "sides.ftl", root);
			return;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String add = (String) request.getParameter("add");
		boolean isAdd = "true".equals(add);
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		if(isAdd){
			boolean isValidCatagory = validateRequest(request, response, root);
			if(!isValidCatagory){
				root.put("createOrUpdate", true);
				List<Side> sides = createMenuItemController.getAllSides();
				if(sides != null && !sides.isEmpty()){
					root.put("hasSides", true);
				}
				root.put("sides", sides);
				renderTemplate(request, response, "sides.ftl", root);
				return;
			} else {
				final Part filePart = request.getPart("file");
				String fileName = getFileName(filePart, "filename");
				String sideName = request.getParameter("sideName");
				String urlAsName = request.getParameter("url");
				String price = request.getParameter("price");
				if(urlAsName == null || "".equals(urlAsName)){
					urlAsName = fileName;
				}
				Side side = new Side(0, sideName, urlAsName, new BigDecimal(price));
				saveImage(request, response, root, urlAsName);
				int id = createMenuItemController.createSide(side);
				if(id == 0){
					root.put("createOrUpdate", true);
					List<Side> sides = createMenuItemController.getAllSides();
					if(sides != null && !sides.isEmpty()){
						root.put("hasSides", true);
					}
					root.put("sides", sides);
					renderTemplate(request, response, "sides.ftl", root);
					return;
				} else {
					root.put("createsubmenu", true);
					renderTemplate(request, response, "sides.ftl", root);
					return;
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
		String sideName = request.getParameter("sideName");
		if(sideName == null || sideName.equals("")){
			root.put("message", "Side Name is required");
			root.put("error", true);
			return false;
		}
		String price = request.getParameter("price");
		try{
			Double.valueOf(price);
		} catch(Exception e){
			root.put("message", "Side price is not valid.");
			root.put("error", true);
			return false;
		}
		return true;
	}

}
