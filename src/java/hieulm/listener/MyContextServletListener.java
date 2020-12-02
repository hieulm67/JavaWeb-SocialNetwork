package hieulm.listener;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Web application lifecycle listener.
 *
 * @author MinHiu
 */
public class MyContextServletListener implements ServletContextListener {
    
    private final String LIST_MEMBER_FUNCTIONS_FILENAME = "/WEB-INF/memberFunctions.txt";
    
    static Logger log = Logger.getLogger(MyContextServletListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
	ServletContext context = sce.getServletContext();
	String realPath = context.getRealPath("/");
	
	List<String> listMemberFunctions = readMemberFunctions(realPath + LIST_MEMBER_FUNCTIONS_FILENAME);
	if(listMemberFunctions != null){
	    context.setAttribute("LIST_MEMBER_FUNCTIONS", listMemberFunctions);
	}
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
    
    private List<String> readMemberFunctions(String filePath){
	FileReader fi = null;
	BufferedReader bf = null;
	
	List<String> listMemberFunctions = null;
	
	try{
	    fi = new FileReader(filePath);
	    bf = new BufferedReader(fi);
	    while(bf.ready()){
		if(listMemberFunctions == null){
		    listMemberFunctions = new ArrayList<>();
		}
		
		listMemberFunctions.add(bf.readLine());
	    }
	}
	catch (FileNotFoundException ex) {	
	    log.error("FileNotFoundException " + ex.getMessage());
	} catch (IOException ex) {
	    log.error("IOException " + ex.getMessage());
	}	
	finally{
	    try{
		if(bf != null){
		    bf.close();
		}
		if(fi != null){
		    fi.close();
		}
	    } catch (IOException ex) {
		log.error("IOException " + ex.getMessage());
	    }
	}
	
	return listMemberFunctions;
    }
}
